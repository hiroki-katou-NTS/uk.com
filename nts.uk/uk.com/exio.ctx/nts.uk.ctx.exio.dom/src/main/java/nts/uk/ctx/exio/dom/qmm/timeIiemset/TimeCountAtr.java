package nts.uk.ctx.exio.dom.qmm.timeIiemset;

/**
 * 
 * @author thanh.tq 平均賃金区分
 *
 */
public enum TimeCountAtr {

	TIME(0, "時間"), TIMES(1, "回数");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private TimeCountAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
