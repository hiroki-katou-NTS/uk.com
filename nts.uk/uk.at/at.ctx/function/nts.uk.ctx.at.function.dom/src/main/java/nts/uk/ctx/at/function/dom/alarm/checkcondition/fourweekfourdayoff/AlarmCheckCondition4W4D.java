package nts.uk.ctx.at.function.dom.alarm.checkcondition.fourweekfourdayoff;


import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

/**
 * 
 * @author hieult
 *
 */
@Getter
@Setter

/** 4週4休のアラームチェック条件 */
public class AlarmCheckCondition4W4D extends ExtractionCondition {
	private	 String alCheck4w4dID;
	private  FourW4DCheckCond fourW4DCheckCond;

	public AlarmCheckCondition4W4D(int fourW4DCheckCond) {
		super();
		this.fourW4DCheckCond = EnumAdaptor.valueOf(fourW4DCheckCond, FourW4DCheckCond.class);
	}

}
