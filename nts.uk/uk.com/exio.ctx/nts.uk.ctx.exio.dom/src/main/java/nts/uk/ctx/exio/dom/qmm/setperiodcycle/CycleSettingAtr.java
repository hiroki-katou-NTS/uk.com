package nts.uk.ctx.exio.dom.qmm.setperiodcycle;

/**
 * 
 * @author thanh.tq サイクル設定区分
 *
 */
public enum CycleSettingAtr {

	DO_NOT(0, "しない"), 
	TO(1, "する");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private CycleSettingAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
