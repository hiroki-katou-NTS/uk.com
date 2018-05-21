package nts.uk.ctx.at.record.app.command.monthlyclosureupdate;

import java.util.Arrays;
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
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
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
		switch (checkExecutionStatus(companyId)) {
		case 0:// not executable
			throw new BusinessException("Msg_1104");
		case 1:// executable
			if (checkExecutableClosure(context.getCommand())) {
				// 締め情報の取得
				Optional<Closure> optClosure = closureRepo.findById(companyId, closureId);
				Closure closure = optClosure.get();
				ClosureDate closureDate = closure.getClosureHistories().get(0).getClosureDate();
				DatePeriod closurePeriod = closureService.getClosurePeriod(closureId,
						closure.getClosureMonth().getProcessingYm());

				List<String> listEmpId = employeeSearch.search(createQueryToFilterEmployees(closurePeriod, closureId)).stream()
						.map(item -> item.getEmployeeId()).collect(Collectors.toList());

				GeneralDateTime executionDT = GeneralDateTime.now();
				MonthlyClosureUpdateLog log = new MonthlyClosureUpdateLog(IdentifierUtil.randomUniqueId(), companyId,
						MonthlyClosureExecutionStatus.RUNNING.value, MonthlyClosureCompleteStatus.INCOMPLETE.value,
						executionDT, closurePeriod, employeeId, closure.getClosureMonth().getProcessingYm(), closureId,
						closureDate);
				monthlyClosureRepo.add(log);
				MonthlyClosureResponse result = new MonthlyClosureResponse(log.getId(), listEmpId, closureId,
						executionDT, closure.getClosureMonth().getProcessingYm().v(), closureDate.getClosureDay().v(),
						closureDate.getLastDayOfMonth(), closurePeriod.start(), closurePeriod.end());
				return result;
			} else {
				throw new BusinessException("Msg_1105");
			}
		default: // running => open dialog F
			return null;
		}

	}

	// 実行状況を確認する
	private int checkExecutionStatus(String companyId) {
		List<MonthlyClosureUpdateLog> list = monthlyClosureRepo.getAll(companyId).stream()
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

	// 実行可能な締めかチェックする
	private boolean checkExecutableClosure(int closureId) {
		List<ClosureInfor> listClosureInfor = closureService.getClosureInfo();
		ClosureInfor result = listClosureInfor.get(0);
		for (ClosureInfor infor : listClosureInfor) {
			if (infor.getPeriod().end().before(result.getPeriod().end())) {
				result = infor;
			}
		}
		final GeneralDate end = result.getPeriod().end();
		List<ClosureId> listClosureId = listClosureInfor.stream()
				.filter(item -> item.getPeriod().end().beforeOrEquals(end)).map(item -> item.getClosureId())
				.collect(Collectors.toList());
		return listClosureId.contains(ClosureId.valueOf(closureId));
	}

	private RegulationInfoEmployeeQuery createQueryToFilterEmployees(DatePeriod closurePeriod, int closureId) {
		RegulationInfoEmployeeQuery query = new RegulationInfoEmployeeQuery();
		query.setBaseDate(closurePeriod.end());
		query.setReferenceRange(EmployeeReferenceRange.ALL_EMPLOYEE.value);
		query.setFilterByEmployment(false);
		query.setFilterByDepartment(false);
		query.setFilterByWorkplace(false);
		query.setFilterByClassification(false);
		query.setFilterByJobTitle(false);
		query.setFilterByWorktype(false);
		query.setPeriodStart(closurePeriod.start());
		query.setPeriodEnd(closurePeriod.end());
		query.setIncludeIncumbents(true);
		query.setIncludeWorkersOnLeave(true);
		query.setIncludeOccupancy(true);
		query.setIncludeRetirees(false);
		query.setRetireStart(GeneralDate.today());
		query.setRetireEnd(GeneralDate.today());
		query.setSortOrderNo(1);
		// query.setNameType(nameType);
		query.setSystemType(2);
		query.setFilterByClosure(true);
		query.setClosureIds(Arrays.asList(closureId));
		return query;
	}

}
