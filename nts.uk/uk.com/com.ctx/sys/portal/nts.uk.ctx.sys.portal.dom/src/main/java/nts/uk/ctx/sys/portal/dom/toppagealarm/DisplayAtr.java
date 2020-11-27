package nts.uk.ctx.sys.portal.dom.toppagealarm;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページ.トップページ（New）.レイアウトの表示種類
 * 表示社員区分
 */
public enum DisplayAtr {
	/** 本人 */
	PRINCIPAL(0),
	
	/** 上長 */
	SUPERIOR(1),
	
	/** 担当者 */
	PIC(2);

	public final int value;

	/** The Constant values. */
	private final static DisplayAtr[] values = DisplayAtr.values();

	private DisplayAtr(int type) {
		this.value = type;
	}
	
	public static DisplayAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}
		// Find value.
		for (DisplayAtr val : DisplayAtr.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
