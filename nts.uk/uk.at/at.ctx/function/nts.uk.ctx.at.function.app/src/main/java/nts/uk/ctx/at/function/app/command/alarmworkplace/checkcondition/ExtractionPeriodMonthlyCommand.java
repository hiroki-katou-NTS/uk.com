package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import lombok.Data;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.NumberOfMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.*;
import nts.uk.ctx.at.function.dom.alarmworkplace.ExtractionPeriodMonthly;

@Data
@Getter
public class ExtractionPeriodMonthlyCommand {

	//Start month

	private int strSpecify;

	private Integer strMonth;

	private Boolean strCurrentMonth;

	private Integer strPreviousAtr;

	private Integer yearType;

	private Integer designatedMonth;

	//End Month

	private int endSpecify;

	private int extractFromStartMonth;

	private Integer endMonth;

	private Boolean endCurrentMonth;

	private Integer endPreviousAtr;
	
	public static ExtractionPeriodMonthly toDomain(ExtractionPeriodMonthlyCommand command) {

		if(command.strMonth==0) command.strCurrentMonth = true;
		if(command.endMonth ==0) command.endCurrentMonth = true;
		
		StartMonth startMonth = new StartMonth(command.strSpecify);
		
		if(command.strSpecify== SpecifyStartMonth.DESIGNATE_CLOSE_START_MONTH.value) {
			startMonth.setStartMonth(EnumAdaptor.valueOf(command.strPreviousAtr, PreviousClassification.class), command.strMonth, command.strCurrentMonth);
		}else {
			startMonth.setFixedMonth(EnumAdaptor.valueOf(command.yearType, YearSpecifiedType.class), command.designatedMonth);
		}

		EndMonth endMonth = new EndMonth(command.endSpecify, command.extractFromStartMonth);
		endMonth.setEndMonthNo(EnumAdaptor.valueOf(command.endPreviousAtr, PreviousClassification.class), command.endMonth, command.endCurrentMonth);
		
		return new ExtractionPeriodMonthly(startMonth, endMonth);
	}
}
