package nts.uk.ctx.at.record.dom.fourweekfourdayoff;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class W4D4CheckService {
	
	@Inject
	private WorkInformationRepository workInformationRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	public void checkHoliday(String employeeID, DatePeriod period) {
		String companyID = AppContexts.user().companyId();

		List<WorkInfoOfDailyPerformance> listWorkInfoOfDailyPerformance = workInformationRepository.findByPeriodOrderByYmd(employeeID, period);
		
		
		
		int countHoliday = 0;
		for (val workInfoOfDailyPerformance: listWorkInfoOfDailyPerformance) {
			val optWorkType = workTypeRepository.findByDeprecated(companyID, workInfoOfDailyPerformance.getRecordWorkInformation().getWorkTypeCode().v());
			if (!optWorkType.isPresent())
				throw new RuntimeException("Can't find WorkType with code: " + workInfoOfDailyPerformance.getRecordWorkInformation().getWorkTypeCode().v());
			
			if (optWorkType.get().getDailyWork().isOneDayHoliday()) {
				countHoliday++;
			}
		}
		
		if (countHoliday < 4) {
			
		}
	}
	
}