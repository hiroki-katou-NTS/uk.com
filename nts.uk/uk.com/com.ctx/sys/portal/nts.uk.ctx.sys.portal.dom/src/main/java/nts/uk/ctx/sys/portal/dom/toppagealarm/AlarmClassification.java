package nts.uk.ctx.sys.portal.dom.toppagealarm;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.ポータル.トップページ.トップページ（New）.レイアウトの表示種類
 * アラーム分類
 */
public enum AlarmClassification {
	/** アラームリスト */
	ALARM_LIST(0),
	
	/** 更新処理自動実行業務エラー */
	AUTO_EXEC_BUSINESS_ERR(1),
	
	/** 更新処理自動実行動作異常 */
	AUTO_EXEC_OPERATION_ERR(2),
	
	/** ヘルス×ライフメッセージ */
	HEALTH_LIFE_MESSAGE(3);

	public final int value;

	/** The Constant values. */
	private final static AlarmClassification[] values = AlarmClassification.values();

	private AlarmClassification(int type) {
		this.value = type;
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
