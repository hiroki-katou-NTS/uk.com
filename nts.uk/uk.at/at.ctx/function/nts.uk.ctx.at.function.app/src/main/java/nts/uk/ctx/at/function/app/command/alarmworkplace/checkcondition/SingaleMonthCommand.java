package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.EndDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.StartDate;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.EndSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.ExtractionPeriodDaily;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.SpecifiedMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.StartSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.singlemonth.SingleMonth;

@Data
public class SingaleMonthCommand {

	/** 前・先区分  */
	private int monthPrevious;

	/** 月数 */
	private int monthNo;

	/** 当月とする */
	private boolean curentMonth;
	
	public static SingleMonth toDomain(SingaleMonthCommand command){
		return new SingleMonth(EnumAdaptor.valueOf(command.monthPrevious, PreviousClassification.class),command.monthNo,command.curentMonth);
	}
}
