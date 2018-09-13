package nts.uk.ctx.at.function.dom.alarm.extraprocessstatus;
/**
 * 抽出状態
 * @author tutk
 *
 */
public enum ExtractionState {
	/** 正常終了*/
	SUCCESSFUL_COMPLE(0, "Enum_ExtractionState_SuccessfulComple"),

	/** 異常終了*/
	ABNORMAL_TERMI(1, "Enum_ExtractionState_AbnormalTermi"),

	/** 処理中*/
	PROCESSING(2, "Enum_ExtractionState_Processing"),

	/** 中断*/
	INTERRUPTION(3, "Enum_ExtractionState_Interruption");
	
	public final int value;

	/** The name id. */
	public final String nameId;
	
	private ExtractionState(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
