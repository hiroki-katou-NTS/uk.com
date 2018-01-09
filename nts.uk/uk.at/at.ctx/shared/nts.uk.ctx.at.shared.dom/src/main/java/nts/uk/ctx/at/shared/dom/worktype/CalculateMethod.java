package nts.uk.ctx.at.shared.dom.worktype;

import lombok.AllArgsConstructor;

/**
 * The Enum CalulateMethod
 * 出勤率の計算方法
 * @author sonnh
 *
 */
@AllArgsConstructor
public enum CalculateMethod {

	/**
	 * 出勤としない（分子に加算しない）
	 */
	DO_NOT_GO_TO_WORK(0, "Enum_CalculateMethod_DO_NOT_GO_TO_WORK"),
	
	/**
	 * 出勤とみなす（分子に加算する）
	 */
	MAKE_ATTENDANCE_DAY(1, "Enum_CalculateMethod_MAKE_ATTENDANCE_DAY"),
	

	/**
	 * 労働日から除外する（分母から減算）
	 */
	EXCLUDE_FROM_WORK_DAY(2, "Enum_CalculateMethod_EXCLUDE_FROM_WORK_DAY"),
	

	/**
	 * 時間年休時のみ出勤扱い
	 */
	TIME_DIGEST_VACATION(3, "Enum_CalculateMethod_TIME_DIGEST_VACATION");

	public final int value;
	public final String nameId;
}
