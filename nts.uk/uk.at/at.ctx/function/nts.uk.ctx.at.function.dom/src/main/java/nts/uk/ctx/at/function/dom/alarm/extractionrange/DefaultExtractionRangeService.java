package nts.uk.ctx.at.function.dom.alarm.extractionrange;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.AlarmPatternSettingRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.CheckCondition;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.EndSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.StartSpecify;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class DefaultExtractionRangeService implements ExtractionRangeService{

	@Inject
	private AlarmPatternSettingRepository	alarmRepo;
	@Inject
	private ClosureService closureService;
	
	@Override
	public List<CheckConditionTimeDto> getPeriodByCategory(String alarmCode, String companyId, int closureId,
			Integer processingYm) {
		List<CheckConditionTimeDto> result = new ArrayList<>();
		List<CheckCondition> checkConList = alarmRepo.getCheckCondition(companyId, alarmCode);
		
		checkConList.forEach( c ->{
			if(c.getAlarmCategory() == AlarmCategory.DAILY) {
				
				CheckConditionTimeDto daily= this.getDailyTime(c, closureId, new YearMonth(processingYm));
				result.add(daily);
			} else if(c.getAlarmCategory() == AlarmCategory.SCHEDULE_4WEEK) {
				
				CheckConditionTimeDto schedual_4week = this.get4WeekTime(c, closureId, new YearMonth(processingYm));
				result.add(schedual_4week);
			}else {
				CheckConditionTimeDto  other = new CheckConditionTimeDto(c.getAlarmCategory().value, EnumAdaptor.convertToValueName(c.getAlarmCategory()).getLocalizedName(), null, null);
				result.add(other);
			}
			
		});
		return result;
	}
	
	
	public CheckConditionTimeDto getDailyTime(CheckCondition c, int closureId, YearMonth yearMonth) {
		Date startDate=null;
		Date endDate =null;
		ExtractionPeriodDaily extraction = (ExtractionPeriodDaily)c.getExtractPeriod();
		
		// Calculate start date 
		if(extraction.getStartDate().getStartSpecify() ==StartSpecify.DAYS) {
			Calendar calendar = Calendar.getInstance();
			if(extraction.getStartDate().getStrDays().get().getDayPrevious() ==PreviousClassification.BEFORE)
				calendar.add(Calendar.DAY_OF_YEAR, -extraction.getStartDate().getStrDays().get().getDay().v()); 
			else
				calendar.add(Calendar.DAY_OF_YEAR, extraction.getStartDate().getStrDays().get().getDay().v()); 

			startDate = calendar.getTime(); 
						
		}else {
			DatePeriod datePeriod = closureService.getClosurePeriod(closureId, yearMonth);
			startDate = datePeriod.start().date();
		}
		
		// Calculate endDate
		if(extraction.getEndDate().getEndSpecify() ==EndSpecify.DAYS) {
			Calendar calendar = Calendar.getInstance();
			if(extraction.getEndDate().getEndDays().get().getDayPrevious() == PreviousClassification.BEFORE)
				calendar.add(Calendar.DAY_OF_YEAR, -extraction.getEndDate().getEndDays().get().getDay().v());
			else
				calendar.add(Calendar.DAY_OF_YEAR, extraction.getEndDate().getEndDays().get().getDay().v());
			
			endDate = calendar.getTime();
		}else {
			DatePeriod datePeriod = closureService.getClosurePeriod(closureId, yearMonth);
			endDate = datePeriod.end().date();
		}
		return new CheckConditionTimeDto(c.getAlarmCategory().value, EnumAdaptor.convertToValueName(c.getAlarmCategory()).getLocalizedName(), startDate, endDate);
	}
	
	
	public CheckConditionTimeDto get4WeekTime(CheckCondition c,int closureId, YearMonth yearMonth) {
		Date startDate=null;
		Date endDate =null;
		return new CheckConditionTimeDto(c.getAlarmCategory().value, EnumAdaptor.convertToValueName(c.getAlarmCategory()).getLocalizedName(), startDate, endDate);
	}

}
