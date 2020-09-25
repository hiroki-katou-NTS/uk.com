package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.enums;

/**
 * @author ThanhNX
 *
 *         予実反映するしない区分
 */
public enum ClassificationReflect {

	// 反映する
	REFLECT(0),

	// 反映しない
	NO_REFLECT(1),

	// 流動勤務のみ反映する
	REFLECT_MOBI(2);

	public final int value;

	private static final ClassificationReflect[] values = ClassificationReflect.values();

	private ClassificationReflect(int value) {
		this.value = value;
	}

	public static ClassificationReflect valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (ClassificationReflect val : ClassificationReflect.values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}
}
