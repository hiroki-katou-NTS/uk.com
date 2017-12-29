package nts.uk.ctx.at.function.app.find.alarm.extractionrange;

import lombok.Data;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.EndSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.StartSpecify;

@Data
public class ExtractionRangeDto {

	private String extractionId;
	
	private int extractionRange;
	
	private int strSpecify;

	private Integer strPreviousDay;
	
	private Integer strMakeToDay;

	private Integer strDay;

	private Integer strPreviousMonth;
	
	private Integer strCurrentMonth;

	private Integer strMonth;

	private int endSpecify;

	private Integer endPreviousDay;
	
	private Integer endMakeToDay;

	private Integer endDay;

	private Integer endPreviousMonth;
	
	private Integer endCurrentMonth;

	private Integer endMonth;
	
	
	public static ExtractionRangeDto fromDomain(ExtractionPeriodDaily domain){
		ExtractionRangeDto dto = new  ExtractionRangeDto();
		
		dto.setExtractionId(domain.getExtractionId());
		dto.setExtractionRange(domain.getExtractionRange().value);
		//set start date
		dto.setStrSpecify(domain.getStartDate().getStartSpecify().value);
		if(domain.getStartDate().getStartSpecify().value == StartSpecify.DAYS.value){
			dto.setStrPreviousDay(domain.getStartDate().getStrDays().get().getDayPrevious().value);
			dto.setStrMakeToDay(domain.getStartDate().getStrDays().get().isMakeToDay()==true?1:0);
			dto.setStrDay(domain.getStartDate().getStrDays().get().getDay());
		}else if(domain.getStartDate().getStartSpecify().value == StartSpecify.MONTH.value){
			dto.setStrPreviousMonth(domain.getStartDate().getStrMonth().get().getMonthPrevious().value);
			dto.setStrCurrentMonth(domain.getStartDate().getStrMonth().get().isCurentMonth()==true?1:0);
			dto.setStrMonth(domain.getStartDate().getStrMonth().get().getMonth());
		}
		// set end date
		dto.setEndSpecify(domain.getEndDate().getEndSpecify().value);
		if(domain.getEndDate().getEndSpecify().value == EndSpecify.DAYS.value){
			dto.setEndPreviousDay(domain.getEndDate().getEndDays().get().getDayPrevious().value);
			dto.setEndMakeToDay(domain.getEndDate().getEndDays().get().isMakeToDay()==true?1:0);
			dto.setEndDay(domain.getEndDate().getEndDays().get().getDay());
		}else if(domain.getEndDate().getEndSpecify().value == EndSpecify.MONTH.value){
			dto.setEndPreviousMonth(domain.getEndDate().getEndMonth().get().getMonthPrevious().value);
			dto.setEndCurrentMonth(domain.getEndDate().getEndMonth().get().isCurentMonth()==true?1:0);
			dto.setEndMonth(domain.getEndDate().getEndMonth().get().getMonth());
		}
		
		return dto;
	}
}
