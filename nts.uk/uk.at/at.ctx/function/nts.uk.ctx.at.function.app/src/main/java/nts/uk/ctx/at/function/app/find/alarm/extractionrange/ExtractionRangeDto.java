package nts.uk.ctx.at.function.app.find.alarm.extractionrange;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRange;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.ExtractionRangeBase;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;

@NoArgsConstructor
public class ExtractionRangeDto {

	public String extractionId;
	
	public int extractionRange;
	
	public int previousClassification;
	
	public int day;
	
	public boolean makeToDay;
	
	public int specifyStartDate;
	
	public int specifyEndDate;
	
	public static ExtractionRangeDto FromDomain(ExtractionRangeBase extractionRangeBase){
		ExtractionRangeDto rangeDto = new ExtractionRangeDto();
		if(extractionRangeBase.getExtractionRange().equals(ExtractionRange.PERIOD)){
			ExtractionPeriodDaily daily = (ExtractionPeriodDaily)extractionRangeBase;
			rangeDto.extractionId = daily.getExtractionId();
			rangeDto.extractionRange = daily.getExtractionRange().value;
			rangeDto.previousClassification = daily.getNumberOfDays().getPreviousClassification().value;
			rangeDto.day = daily.getNumberOfDays().getDay();
			rangeDto.makeToDay = daily.getNumberOfDays().isMakeToDay();
			rangeDto.specifyStartDate = daily.getSpecifyStartDate().value;
			rangeDto.specifyEndDate =  daily.getSpecifyEndDate().value;
			
		}
		return rangeDto;
	}
}
