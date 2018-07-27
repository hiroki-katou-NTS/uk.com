package nts.uk.ctx.at.shared.dom.remainingnumber.base;

/**
 * 対象選択区分
 * @author HopNT
 *
 */
public enum TargetSelectionAtr {
	// 自動
	AUTOMATIC(0, "Enum_TargetSelectionAtr_AUTOMATIC"),
	// 申請
	REQUEST(1, "Enum_TargetSelectionAtr_REQUEST"),
	// 手動
	MANUAL(2, "Enum_TargetSelectionAtr_MANUAL");
	
	public final int value;
	
	/** The name id. */
	public final String nameId;

	private TargetSelectionAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
}
