package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

/**
 * @author thanh_nx
 *
 *         打刻先
 */
public enum StampDestination {

	ATTENDANCE(0, "出勤"),

	START_IN(1, "入門"),

	SUPPORT_ATTENDANCE(2, "応援出勤"),

	LEAV(3, "退勤"),

	START_OUT(4, "退門"),

	PRIVATE_OUT(5, "私用外出"),

	PUBLIC_OUT(6, "公用外出"),

	PAID_OUT(7, "有償外出"),

	UNION_OUT(8, "組合外出"),

	IN_SUPPORT_START(9, "入（応援開始）"),

	GO_OUT(10, "戻り"),

	OUT_SUPPORT_END(11, "出（応援終了/応援行く）");

	public final int value;

	public final String nameType;

	private static final StampDestination[] values = StampDestination.values();

	private StampDestination(int value, String nameType) {
		this.value = value;
		this.nameType = nameType;
	}

	public static StampDestination valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (StampDestination val : StampDestination.values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}
}
