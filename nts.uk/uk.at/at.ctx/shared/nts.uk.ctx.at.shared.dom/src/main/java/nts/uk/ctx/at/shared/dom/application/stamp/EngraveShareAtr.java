package nts.uk.ctx.at.shared.dom.application.stamp;
/**
 * @author thanh_nx
 * 
 *打刻区分
 */
public enum EngraveShareAtr {

	/**
	 * 出勤
	 */
	ATTENDANCE(0, "出勤"),

	/**
	 * 退勤
	 */
	OFFICE_WORK(1, "退勤"),

	/**
	 * 退勤（残業）
	 */
	OVERTIME(2, "退勤（残業）"),

	/**
	 * 外出
	 */
	GO_OUT(3, "外出"),

	/**
	 * 戻り
	 */
	RETURN(4, "戻り"),

	/**
	 * 早出
	 */
	EARLY(5, "早出"),

	/**
	 * 休出
	 */
	HOLIDAY(6, "休出");

	public final int value;

	public final String name;

	EngraveShareAtr(int value, String name) {
		this.value = value;
		this.name = name;
	}

	private final static EngraveShareAtr[] values = EngraveShareAtr.values();

	public static EngraveShareAtr valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (EngraveShareAtr val : EngraveShareAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}
}
