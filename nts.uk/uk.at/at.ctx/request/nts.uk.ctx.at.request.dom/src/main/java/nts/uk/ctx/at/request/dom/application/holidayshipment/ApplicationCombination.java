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
	TakingOutAndHoliday(0),
	/**
	 * 振出
	 */
	TakingOut(1),
	/**
	 * 振休
	 */
	Holiday(2);

	public final int value;

	private ApplicationCombination(int value) {
		this.value = value;
	}
}
