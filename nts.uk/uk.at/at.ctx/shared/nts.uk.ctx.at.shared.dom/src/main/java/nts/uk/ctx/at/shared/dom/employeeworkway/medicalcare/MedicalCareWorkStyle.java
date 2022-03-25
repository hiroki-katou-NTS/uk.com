package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare;

import lombok.RequiredArgsConstructor;
/**
 * 医療介護勤務形態
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.社員の働き方.医療介護.医療介護勤務形態
 * @author HieuLT
 *
 */
@RequiredArgsConstructor
public enum MedicalCareWorkStyle {
	
	// 0:常勤

	FULLTIME(0, "常勤"),

	// 1:非常勤
	PARTTIME_JOB(1, "非常勤");


	public final int value;

	public final String name;

	/** The Constant values. */
	private final static MedicalCareWorkStyle[] values = MedicalCareWorkStyle.values();

	public static MedicalCareWorkStyle valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (MedicalCareWorkStyle val : MedicalCareWorkStyle.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
