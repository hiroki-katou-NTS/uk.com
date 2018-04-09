package nts.uk.ctx.at.record.app.command.monthlyclosureupdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.auth.dom.employmentrole.EmployeeReferenceRange;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQuery;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryAdapter;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureCompleteStatus;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureExecutionStatus;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.AlCheckTargetCondition;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureInfor;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class CheckMonthlyClosureCommandHandler extends CommandHandlerWithResult<Integer, MonthlyClosureResponse> {

	@Inject
	private MonthlyClosureUpdateLogRepository monthlyClosureRepo;

	@Inject
	private RegulationInfoEmployeeQueryAdapter employeeSearch;

	@Inject
	private ClosureService closureService;

	@Inject
	private ClosureRepository closureRepo;

	@Override
	protected MonthlyClosureResponse handle(CommandHandlerContext<Integer> context) {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		int closureId = context.getCommand();
		switch (checkExecutionStatus(closureId, companyId)) {
		case 0:// not executable
			throw new BusinessException("Msg_1104");
		case 1:// executable
			if (checkExecutableClosure(context.getCommand())) {
				List<String> listEmpId = employeeSearch.search(createQueryToFilterEmployees(null, null)).stream()
						.map(item -> item.getEmployeeId()).collect(Collectors.toList());
				// 締め情報の取得
				Optional<Closure> optClosure = closureRepo.findById(companyId, closureId);
				Closure closure = optClosure.get();
				DatePeriod dp = closureService.getClosurePeriod(closureId, closure.getClosureMonth().getProcessingYm());

				GeneralDateTime executionDT = GeneralDateTime.now();
				MonthlyClosureUpdateLog log = new MonthlyClosureUpdateLog(IdentifierUtil.randomUniqueId(), companyId,
						MonthlyClosureExecutionStatus.RUNNING.value, MonthlyClosureCompleteStatus.INCOMPLETE.value,
						executionDT, dp, employeeId, closure.getClosureMonth().getProcessingYm(), closureId,
						closure.getClosureHistories().get(0).getClosureDate());
				monthlyClosureRepo.add(log);
				MonthlyClosureResponse result = new MonthlyClosureResponse(log.getId(), listEmpId, closureId,
						executionDT, closure.getClosureMonth().getProcessingYm().v(), 30, true, dp.start(), dp.end());
				return result;
			} else {
				throw new BusinessException("Msg_1105");
			}
		case 2:// running
			return null;
		default:
			return null;
		}

	}

	// 実行状況を確認する
	private int checkExecutionStatus(int closureId, String companyId) {
		List<MonthlyClosureUpdateLog> list = monthlyClosureRepo.getAll(companyId, closureId).stream()
				.filter(item -> item.getExecutionStatus() == MonthlyClosureExecutionStatus.RUNNING
						|| item.getExecutionStatus() == MonthlyClosureExecutionStatus.COMPLETED_NOT_CONFIRMED)
				.collect(Collectors.toList());
		if (list.isEmpty())
			// return executable
			return 1;
		String empId = AppContexts.user().employeeId();
		for (MonthlyClosureUpdateLog log : list) {
			if (log.getExecuteEmployeeId().equals(empId)) {
				// return running
				return 2;
			}
		}
		// return not executable
		return 0;

	}

	private boolean checkExecutableClosure(int closureId) {
		List<ClosureInfor> listClosureInfor = closureService.getClosureInfo();
		ClosureInfor result = listClosureInfor.get(0);
		for (ClosureInfor infor : listClosureInfor) {
			if (infor.getPeriod().end().after(result.getPeriod().end())) {
				result = infor;
			}
		}
//		List<ClosureId> listClosureId = listClosureInfor.stream()
//				.filter(item -> item.getPeriod().end().afterOrEquals(result.getPeriod().end()))
//				.map(item -> item.getClosureId()).collect(Collectors.toList());
		return true;
	}

	private RegulationInfoEmployeeQuery createQueryToFilterEmployees(GeneralDate endDateOfClosurePeriod,
			AlCheckTargetCondition checkCondition) {
		RegulationInfoEmployeeQuery query = new RegulationInfoEmployeeQuery();
		query.setBaseDate(endDateOfClosurePeriod);
		query.setReferenceRange(EmployeeReferenceRange.ALL_EMPLOYEE.value);
		query.setFilterByEmployment(true);
		query.setEmploymentCodes(new ArrayList<>());
		query.setFilterByDepartment(false);
		// query.setDepartmentCodes(new ArrayList<>());
		query.setFilterByWorkplace(false);
		// query.setWorkplaceCodes(new ArrayList<>());
		query.setFilterByClassification(false);
		// query.setClassificationCodes(new ArrayList<>());
		query.setFilterByJobTitle(false);
		// query.setJobTitleCodes(new ArrayList<>());
		query.setFilterByWorktype(false);
		// query.setWorktypeCodes(new ArrayList<>());
		// query.setPeriodStart(workingDate.toString());
		// query.setPeriodEnd(workingDate.toString());
		query.setIncludeIncumbents(true);
		query.setIncludeWorkersOnLeave(true);
		query.setIncludeOccupancy(true);
		// query.setIncludeAreOnLoan(true);
		// query.setIncludeGoingOnLoan(false);
		query.setIncludeRetirees(false);
		// query.setRetireStart(retireStart);
		// query.setRetireEnd(retireEnd);
		query.setSortOrderNo(1);
		// query.setNameType(nameType);
		return query;
	}

}
