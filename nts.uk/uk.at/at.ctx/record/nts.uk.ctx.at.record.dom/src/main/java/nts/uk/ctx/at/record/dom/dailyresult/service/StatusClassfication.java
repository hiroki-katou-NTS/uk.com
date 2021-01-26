package nts.uk.ctx.at.record.dom.dailyresult.service;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.日別実績.実績データに従う在席状態
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
