package nts.uk.ctx.at.function.app.find.alarm.extractionrange;

import lombok.Data;
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
		dto.setStrSpecify(domain.getStartDate().getStartSpecify().value);
		if(domain.getStartDate().getStartSpecify().value == StartSpecify.DAYS.value){
			dto.setStrPreviousDay(domain.getStartDate().getStartSpecify().value);
			dto.setStrMakeToDay(domain.getStartDate().getStrDays().get().isMakeToDay()==true?1:0);
			dto.setStrDay(domain.getStartDate().getStrDays().get().getDay());
		}
		
		
		return null;
	}
}
