package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 1, min = 0)
public enum WorkTypeBusinessTrip {
	// 出勤の勤務種類
	WORK_TYPE_FOR_WORK(0),
	// 出勤系の勤務種類
	WORK_TYPE_ATTENDANCE_SYSTEM(1);
	public final int value;
}
