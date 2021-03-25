package nts.uk.ctx.office.dom.status;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.ステータス分類
 */
public enum StatusClassfication {

	// 未出社
	NOT_PRESENT(0),

	// 在席
	PRESENT(1),

	// 外出
	GO_OUT(2),

	// 帰宅
	GO_HOME(3),

	// 休み
	HOLIDAY(4);

	public int value;

	StatusClassfication(int val) {
		this.value = val;
	}
}
