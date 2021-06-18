package nts.uk.ctx.at.request.dom.application.holidayshipment;

/**
 * 申請組み合わせ
 * 
 * @author sonnlb
 */
public enum ApplicationCombination {
	/**
	 * 振出＋振休
	 */
	RecAndAbs(0, "振出＋振休"),
	/**
	 * 振出
	 */
	Rec(1, "振出のみ"),
	/**
	 * 振休
	 */
	Abs(2, "振休のみ");

	public final int value;
	public final String nameId;

	private ApplicationCombination(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
