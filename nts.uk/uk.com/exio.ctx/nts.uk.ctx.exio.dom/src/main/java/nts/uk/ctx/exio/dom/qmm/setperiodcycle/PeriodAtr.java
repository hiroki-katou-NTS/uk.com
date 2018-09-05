package nts.uk.ctx.exio.dom.qmm.setperiodcycle;

/**
 * 
 * @author thanh.tq 有効期間設定区分
 *
 */
public enum PeriodAtr {

	NOT_SETUP(0, "設定しない"), 
	TO_SETUP(1, "設定する");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private PeriodAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
