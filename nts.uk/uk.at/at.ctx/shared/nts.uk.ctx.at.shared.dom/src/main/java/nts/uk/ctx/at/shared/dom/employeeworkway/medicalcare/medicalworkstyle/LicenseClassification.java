package nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle;

import lombok.AllArgsConstructor;

/**
 * @author ThanhNX 免許区分
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.社員の働き方.医療介護.医療勤務形態.免許区分
 */
@AllArgsConstructor
public enum LicenseClassification {

	NURSE(0, "看護師"),

	//Associate nurse
	NURSE_ASSOCIATE(1, "准看護師"),

	//Nursing assistant
	NURSE_ASSIST(2, "看護補助者");

	public final int value;

	public final String name;

	private final static LicenseClassification[] values = LicenseClassification.values();

	public static LicenseClassification valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (LicenseClassification val : LicenseClassification.values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}
}
