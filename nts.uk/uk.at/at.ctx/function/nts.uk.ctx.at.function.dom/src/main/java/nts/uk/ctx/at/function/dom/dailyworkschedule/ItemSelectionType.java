package nts.uk.ctx.at.function.dom.dailyworkschedule;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.日別勤務表.項目選択種類
 * @author LienPTK
 */
public enum ItemSelectionType {
	/** 定型選択 */
	STANDARD_SELECTION(0),
	/** 目由設定 */
	FREE_SETTING(1);
	
	public final int value;

	private ItemSelectionType(int value) {
		this.value = value;
	}

	public static ItemSelectionType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ItemSelectionType val : ItemSelectionType.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
