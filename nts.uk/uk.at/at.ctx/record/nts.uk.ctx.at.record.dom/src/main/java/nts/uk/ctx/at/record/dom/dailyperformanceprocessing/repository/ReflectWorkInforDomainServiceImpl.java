package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.affiliationinformation.repository.AffiliationInforOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.approvalmanagement.repository.ApprovalStatusOfDailyPerforRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TemporaryTimeOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class ReflectWorkInforDomainServiceImpl implements ReflectWorkInforDomainService{
	
	@Inject
	private WorkInformationRepository workInformationRepository;
	
	@Inject
	private ApprovalStatusOfDailyPerforRepository approvalStatusOfDailyPerforRepository;

	@Inject
	private AffiliationInforOfDailyPerforRepository affiliationInforOfDailyPerforRepository;
	
	@Inject
	private IdentificationRepository identificationRepository;
	
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;
	
	@Inject
	private TemporaryTimeOfDailyPerformanceRepository temporaryTimeOfDailyPerformanceRepository;
	
	@Override
	public boolean reflectWorkInformation(List<String> employeeIds, DatePeriod periodTime, String empCalAndSumExecLogID,
			int reCreateAttr) {
		
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		
		// lits day between startDate and endDate
		List<GeneralDate> listDay = this.getDaysBetween(periodTime.start(), periodTime.end());
		
		// ドメインモデル「日別実績の勤務情報」を削除する - rerun
		if (reCreateAttr == 0) {
			this.workInformationRepository.deleteByListEmployeeId(employeeIds, listDay);
			this.approvalStatusOfDailyPerforRepository.deleteByListEmployeeId(employeeIds, listDay);
			this.affiliationInforOfDailyPerforRepository.deleteByListEmployeeId(employeeIds, listDay);
			this.identificationRepository.deleteByListEmployeeId(employeeIds, listDay);
			this.timeLeavingOfDailyPerformanceRepository.deleteByListEmployeeId(employeeIds, listDay);
			this.temporaryTimeOfDailyPerformanceRepository.deleteByListEmployeeId(employeeIds, listDay);
			
		}
		// ドメインモデル「日別実績の勤務情報」を取得する - not rerun
		else {
			this.workInformationRepository.findByListEmployeeId(employeeIds, listDay);
		}
		
		
		
		return false;
	}


	private List<GeneralDate> getDaysBetween(GeneralDate startDate, GeneralDate endDate) {
		List<GeneralDate> daysBetween = new ArrayList<>();

		while (startDate.beforeOrEquals(endDate)) {
			daysBetween.add(startDate);
			GeneralDate temp = startDate.addDays(1);
			startDate = temp;
		}

		return daysBetween;
	}

}
