package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult;

/**
 * 超過状態
 * @author quang.nh1
 *
 */
public enum OverState {

	/** 時間. */
	NORMAL(0, "時間"),

	/** アラーム時間超過. */
	ALARM_OVER(1, "アラーム時間超過"),

	/** エラー時間超過. */
	ERROR_OVER(2, "エラー時間超過"),

	/** 上限時間超過. */
	UPPER_LIMIT_OVER(3, "上限時間超過");

	public final int value;
	/** The name id. */
	public String nameId;

	private OverState(int value,String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
