package nts.uk.ctx.at.function.dom.alarm.checkcondition.fourweekfourdayoff;


import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;

/**
 * 
 * @author hieult
 *
 */
@Getter
/** 4週4休のアラームチェック条件 */
public class AlarmCheckCondition4W4D extends ExtractionCondition {
	private	 String alCheck4w4dID;
	private  FourW4DCheckCond fourW4DCheckCond;

	public AlarmCheckCondition4W4D(String id, int fourW4DCheckCond) {
		super();
		this.alCheck4w4dID = id;
		this.fourW4DCheckCond = EnumAdaptor.valueOf(fourW4DCheckCond, FourW4DCheckCond.class);
	}
	
	@Override
	public void changeState(ExtractionCondition extractionCondition) {
		if (extractionCondition instanceof AlarmCheckCondition4W4D) {
			AlarmCheckCondition4W4D value = (AlarmCheckCondition4W4D) extractionCondition;
			this.fourW4DCheckCond = value.fourW4DCheckCond;
		}
	}

}
