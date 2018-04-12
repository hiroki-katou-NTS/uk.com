package nts.uk.ctx.at.record.app.find.monthlyclosureupdate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureExecutionStatus;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInfor;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInforRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLogRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureInfor;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class MonthlyClosureUpdateFinder {

	@Inject
	private MonthlyClosureUpdateLogRepository monthlyClosureUpdateRepo;

	@Inject
	private ClosureService closureService;

	@Inject
	private MonthlyClosureUpdateErrorInforRepository errorInforRepo;

	@Inject
	private EmployeeRecordAdapter empImportAdapter;

	public MonthlyClosureUpdateLogDto findById(String id) {
		return domainToDto(monthlyClosureUpdateRepo.getLogById(id).get());
	}

	public Kmw006fResultDto getClosureResult(String monthlyClosureUpdateLogId) {
		List<MonthlyClosureErrorInforDto> listResult = new ArrayList<>();
		MonthlyClosureUpdateLog updateLog = monthlyClosureUpdateRepo.getLogById(monthlyClosureUpdateLogId).get();
		// List<MonthlyClosureUpdatePersonLog> listPersonLog =
		// personLogRepo.getAll(monthlyClosureUpdateLogId);
		List<MonthlyClosureUpdateErrorInfor> listErrorInfo = errorInforRepo.getAll(monthlyClosureUpdateLogId);
		if (!listErrorInfo.isEmpty()) {
			for (MonthlyClosureUpdateErrorInfor errInfor : listErrorInfo) {
				EmployeeRecordImport empImport = empImportAdapter.getPersonInfor(errInfor.getEmployeeId());
				MonthlyClosureErrorInforDto result = new MonthlyClosureErrorInforDto(empImport.getEmployeeCode(),
						empImport.getPname(), errInfor.getErrorMessage(), errInfor.getAtr().value);
				listResult.add(result);
			}
		}
		Kmw006fResultDto dto = new Kmw006fResultDto(listResult, domainToDto(updateLog));
		return dto;
	}

	private MonthlyClosureUpdateLogDto domainToDto(MonthlyClosureUpdateLog domain) {
		return new MonthlyClosureUpdateLogDto(domain.getId(), domain.getCompanyId(), domain.getExecutionStatus().value,
				domain.getCompleteStatus().value, domain.getExecutionDateTime(), domain.getExecutionPeriod().start(),
				domain.getExecutionPeriod().end(), domain.getExecuteEmployeeId(), domain.getTargetYearMonth().v(),
				domain.getClosureId().value, domain.getClosureDate().getClosureDay().v(),
				domain.getClosureDate().getLastDayOfMonth());
	}

	public Kmw006aResultDto getClosureInfors() {
		String companyId = AppContexts.user().companyId();
		if (checkExecutionStatus() == 2) { // running => open dialog F
			return null;
		} else {
			boolean executable = true;
			if (checkExecutionStatus() == 0) { // not executable
				executable = false;
			}
			List<ClosureInfor> listClosureInfor = closureService.getClosureInfo();
			ClosureInfor tmp = listClosureInfor.get(0);
			for (ClosureInfor infor : listClosureInfor) {
				if (infor.getPeriod().end().after(tmp.getPeriod().end())) {
					tmp = infor;
				}
			}
			final GeneralDate end = tmp.getPeriod().end();
			listClosureInfor = listClosureInfor.stream().filter(item -> item.getPeriod().end().afterOrEquals(end))
					.sorted((o1, o2) -> o1.getClosureId().compareTo(o2.getClosureId())).collect(Collectors.toList());
			tmp = listClosureInfor.get(0);
			List<MonthlyClosureUpdateLog> listMonthlyLog = monthlyClosureUpdateRepo
					.getAllByClosureId(companyId, tmp.getClosureId().value).stream()
					.sorted((o1, o2) -> o1.getExecutionDateTime().compareTo(o2.getExecutionDateTime()))
					.collect(Collectors.toList());
			MonthlyClosureUpdateLog log = null;
			if (!listMonthlyLog.isEmpty())
				log = listMonthlyLog.get(0);
			return new Kmw006aResultDto(executable, log == null ? null : log.getTargetYearMonth().v(), log == null ? null : log.getExecutionDateTime(),
					tmp.getClosureId().value, listClosureInfor.stream().map(item -> ClosureInforDto.fromDomain(item))
							.collect(Collectors.toList()));
		}
	}

	// 実行状況を確認する
	private int checkExecutionStatus() {
		String companyId = AppContexts.user().companyId();
		List<MonthlyClosureUpdateLog> list = monthlyClosureUpdateRepo.getAll(companyId).stream()
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
	
	public List<Kmw006cDto> getClosureLogInfor() {
		String companyId = AppContexts.user().companyId();
		
		return null;
	}

}
