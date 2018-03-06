package nts.uk.ctx.exio.dom.exi.dataformat;

/**
 * 
 * @author DatLH 端数処理
 *
 */
public enum RoundingProcessing {
	//1分未満切り上げ
	LESS_1_MINUTE(0, "Enum_RoundingProcessing_LESS_1_MINUTE"), 
	//1分未満切り捨て
	DOWN_LESS_1_MINUTE(1, "Enum_RoundingProcessing_DOWN_LESS_1_MINUTE"), 
	//1分未満四捨五入（小数点第1位迄）
	OFF_TO_LESS_1_MINUTE(2, "Enum_RoundingProcessing_OFF_TO_LESS_1_MINUTE");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private RoundingProcessing(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
