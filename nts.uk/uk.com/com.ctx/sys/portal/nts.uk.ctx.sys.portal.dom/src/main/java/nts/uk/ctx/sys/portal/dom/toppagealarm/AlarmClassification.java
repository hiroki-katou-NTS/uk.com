package nts.uk.ctx.sys.portal.dom.toppagealarm;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページ.トップページ（New）.レイアウトの表示種類
 * アラーム分類
 */
public enum AlarmClassification {
	/** アラームリスト */
	ALARM_LIST(1, 2),
	
	/** 更新処理自動実行業務エラー */
	AUTO_EXEC_BUSINESS_ERR(2, 1),
	
	/** 更新処理自動実行動作異常 */
	AUTO_EXEC_OPERATION_ERR(3, 0),
	
	/** ヘルス×ライフメッセージ */
	HEALTH_LIFE_MESSAGE(4, 3);

	public final int value;
	
	public final int order;

	/** The Constant values. */
	private final static AlarmClassification[] values = AlarmClassification.values();

	private AlarmClassification(int type, int order) {
		this.value = type;
		this.order = order;
	}
	
	public static AlarmClassification valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}
		// Find value.
		for (AlarmClassification val : AlarmClassification.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
