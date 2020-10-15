package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal;

/**
 * @author ThanhNX
 *
 *         NR-種類
 */
public enum NRErrorType {

	STAMP(0, "打刻"),

	RESERVATION(1, "予約"),

	APPLICATION(2, "申請"),

	EMPLOYEE(3, "個人情報"),

	BENTO(4, "弁当メニュー"),

	WORKTIME(5, "就業時間帯"),

	WORKTYPE(6, "勤務種類"),

	OVERTIME(7, "残業・休日出勤"),

	APPLICATON_REASON(8, "申請理由"),

	TIME(9, "時刻セッ");

	public final int value;

	public final String nameType;

	private static final NRErrorType[] values = NRErrorType.values();

	private NRErrorType(int value, String nameType) {
		this.value = value;
		this.nameType = nameType;
	}

	public static NRErrorType valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (NRErrorType val : NRErrorType.values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}
}
