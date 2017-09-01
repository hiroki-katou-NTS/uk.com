package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CalculateMethod {
	
	//出勤日しない
	DO_NOT_GO_TO_WORK(0),
	//出勤日とする
	MAKE_ATTENDANCE_DAY(1),
	//労働日から除外する（分母から減算）
	EXCLUDE_FROM_WORK_DAY(2),
	//時間消化休暇
	TIME_DIGEST_VACATION(3);
	
	public final int value;
}
