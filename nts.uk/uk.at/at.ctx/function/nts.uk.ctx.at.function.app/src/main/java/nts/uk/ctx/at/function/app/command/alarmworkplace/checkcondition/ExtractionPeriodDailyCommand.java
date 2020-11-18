package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.EndDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.StartDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.EndSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.SpecifiedMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.StartSpecify;
import nts.uk.ctx.at.function.dom.alarmworkplace.ExtractionPeriodDaily;

@Data
public class ExtractionPeriodDailyCommand {

	//Start date
	private int strSpecify;

	private Integer strPreviousMonth;

	private Integer strMonth;

	private Boolean strCurrentMonth;

	private Integer strPreviousDay;

	private Integer strDay;

	//End date

	private int endSpecify;

	private Integer endPreviousDay;

	private Boolean endMakeToDay;

	private Integer endDay;

	private Integer endPreviousMonth;

	private Integer endMonth;
	
	public static ExtractionPeriodDaily toDomain(ExtractionPeriodDailyCommand command){
		
		StartDate startDate = new StartDate(command.strSpecify);
		
		if(command.strSpecify == StartSpecify.DAYS.value){
			startDate.setStartDay(EnumAdaptor.valueOf(command.strPreviousDay, PreviousClassification.class), command.strDay, command.strDay == 0);
		}else if(command.strSpecify == StartSpecify.MONTH.value){
			startDate.setStartMonth(EnumAdaptor.valueOf(command.strPreviousMonth, PreviousClassification.class), command.strMonth, command.strMonth == SpecifiedMonth.CURRENTMONTH.value);
		}
		
		EndDate endDate = new EndDate(command.endSpecify);
		
		if(command.endSpecify == EndSpecify.DAYS.value){
			endDate.setEndDay(EnumAdaptor.valueOf(command.endPreviousDay, PreviousClassification.class),command.endDay, command.endDay == 0);
		}else if(command.endSpecify == EndSpecify.MONTH.value){
			endDate.setEndMonth(EnumAdaptor.valueOf(command.endPreviousMonth, PreviousClassification.class), command.endMonth, command.endMonth == SpecifiedMonth.CURRENTMONTH.value);
		}
		
		return new ExtractionPeriodDaily(startDate, endDate);
	}
}
