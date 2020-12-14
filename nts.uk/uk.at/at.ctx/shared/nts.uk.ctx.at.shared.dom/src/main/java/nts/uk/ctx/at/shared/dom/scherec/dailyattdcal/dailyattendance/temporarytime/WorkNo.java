/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * The Class WorkNo.
 */
// 勤務NO
@IntegerRange(min = 1, max = 3)
public class WorkNo extends IntegerPrimitiveValue<WorkNo> {

	private static final long serialVersionUID = 1L;

	public WorkNo(Integer rawValue) {
		super(rawValue);
	}


	/**
	 * attendancetime の勤務NOから temporarytime の勤務NOを作成する
	 * TODO WorkNoの統合が完了したら削除する
	 * @param workNo 勤務NO(attendancetime)
	 * @return 勤務NO(temporarytime)
	 */
	public static WorkNo fromAttendance(nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo workNo) {
		return new WorkNo( workNo.v() );
	}

	/**
	 * temporarytime の勤務NOを attendancetime の勤務NOに変換する
	 * TODO WorkNoの統合が完了したら削除する
	 * @return 勤務NO(attendancetime)
	 */
	public nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo toAttendance() {
		return new nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkNo( this.v() );
	}

}
