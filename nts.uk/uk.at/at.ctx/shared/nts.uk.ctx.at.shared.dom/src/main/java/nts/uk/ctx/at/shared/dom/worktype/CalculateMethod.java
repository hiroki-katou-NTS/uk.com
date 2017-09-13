package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;
/**
 * The Enum CalulateMethod
 * 
 * @author sonnh
 *
 */
@AllArgsConstructor
public enum CalculateMethod {

	/**
	 * 出勤日しない
	 */
	DO_NOT_GO_TO_WORK(0, "Enum_CalculateMethod_DO_NOT_GO_TO_WORK"),
	
	/**
	 * 出勤日とする
	 */
	MAKE_ATTENDANCE_DAY(1, "Enum_CalculateMethod_MAKE_ATTENDANCE_DAY"),
	

	/**
	 * 労働日から除外する
	 */
	EXCLUDE_FROM_WORK_DAY(2, "Enum_CalculateMethod_EXCLUDE_FROM_WORK_DAY"),
	

	/**
	 * 時間消化休暇
	 */
	TIME_DIGEST_VACATION(3, "Enum_CalculateMethod_TIME_DIGEST_VACATION");

	public final int value;
	public final String nameId;
}
