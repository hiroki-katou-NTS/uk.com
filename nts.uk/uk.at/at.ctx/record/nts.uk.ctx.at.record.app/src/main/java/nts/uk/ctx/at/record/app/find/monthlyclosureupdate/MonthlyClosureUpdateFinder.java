package nts.uk.ctx.at.record.app.find.monthlyclosureupdate;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInfor;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateErrorInforRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLog;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdateLogRepository;
import nts.uk.ctx.at.record.dom.monthlyclosureupdatelog.MonthlyClosureUpdatePersonLogRepository;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class MonthlyClosureUpdateFinder {

	@Inject
	private MonthlyClosureUpdateLogRepository monthlyClosureUpdateRepo;

//	@Inject
//	private MonthlyClosureUpdatePersonLogRepository personLogRepo;

	@Inject
	private MonthlyClosureUpdateErrorInforRepository errorInforRepo;

	@Inject
	private EmployeeRecordAdapter empImportAdapter;

	public MonthlyClosureUpdateLogDto findById(String id) {
		return domainToDto(monthlyClosureUpdateRepo.getLogById(id).get());
	}

	public Kmw006ResultDto getClosureResult(String monthlyClosureUpdateLogId) {
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
		Kmw006ResultDto dto = new Kmw006ResultDto(listResult, domainToDto(updateLog));
		return dto;
	}

	private MonthlyClosureUpdateLogDto domainToDto(MonthlyClosureUpdateLog domain) {
		return new MonthlyClosureUpdateLogDto(domain.getId(), domain.getCompanyId(), domain.getExecutionStatus().value,
				domain.getCompleteStatus().value, domain.getExecutionDateTime(), domain.getExecutionPeriod().start(),
				domain.getExecutionPeriod().end(), domain.getExecuteEmployeeId(), domain.getTargetYearMonth().v(),
				domain.getClosureId().value, domain.getClosureDate().getClosureDay().v(),
				domain.getClosureDate().getLastDayOfMonth());
	}

}
