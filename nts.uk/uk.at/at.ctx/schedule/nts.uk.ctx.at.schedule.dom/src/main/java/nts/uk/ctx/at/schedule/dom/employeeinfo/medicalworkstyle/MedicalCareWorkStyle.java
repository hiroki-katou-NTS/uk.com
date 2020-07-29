package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import lombok.RequiredArgsConstructor;
/**
 * 医療介護勤務形態
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
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
