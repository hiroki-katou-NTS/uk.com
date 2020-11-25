package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.singlemonth.SingleMonth;

@Data
public class SingleMonthCommand {

	/** 前・先区分  */
	private int monthPrevious;

	/** 月数 */
	private int monthNo;

	/** 当月とする */
	private boolean curentMonth;

	public static SingleMonth toDomain(SingleMonthCommand command){
		return new SingleMonth(EnumAdaptor.valueOf(command.monthPrevious, PreviousClassification.class),command.monthNo,command.curentMonth);
	}
}
