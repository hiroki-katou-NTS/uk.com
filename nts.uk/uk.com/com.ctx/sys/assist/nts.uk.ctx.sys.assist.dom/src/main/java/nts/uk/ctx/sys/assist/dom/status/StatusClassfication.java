package nts.uk.ctx.sys.assist.dom.status;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.ステータス分類
 */
public enum StatusClassfication {
	// 在席
	PRESENT(0),

	// 外出
	GO_OUT(1),

	// 帰宅
	GO_HOME(2),

	// 未出社
	NOT_PRESENT(3),

	// 休み
	HOLIDAY(4);

	public int value;

	StatusClassfication(int val) {
		this.value = val;
	}
}
