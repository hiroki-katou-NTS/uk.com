package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerMaxValue;
import nts.arc.primitive.constraint.IntegerMinValue;

/**
 *
 * @author nampt
 * 勤務NO
 *
 */
@IntegerMinValue(1)
@IntegerMaxValue(3)
public class WorkNo extends IntegerPrimitiveValue<WorkNo>{

	private static final long serialVersionUID = 1L;

	public WorkNo(Integer rawValue) {
		super(rawValue);
	}

	public static WorkNo converFromOtherWorkNo(nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo workNo) {
		return new WorkNo(workNo.v());
	}

	/**
	 * attendancetime の勤務NOを temporarytime の勤務NOに変換する
	 * TODO WorkNoの統合が完了したら削除する
	 * @return 勤務NO(temporarytime)
	 */
	public nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo toTemporary() {
		return new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.WorkNo( this.v() );
	}
}
