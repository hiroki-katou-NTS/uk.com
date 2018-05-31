package nts.uk.ctx.at.record.dom.monthlyclosureupdatelog;

/**
 * 
 * @author HungTT - 月締め更新対象者毎の実行結果
 *
 */

public enum MonthlyClosurePersonExecutionResult {

	UPDATED(0, "更新済"),

	ALREADY_CLOSURE(1, "既に締め処理済み"),

	UPDATED_WITH_ALARM(2, "更新済（アラームあり）"),

	NOT_UPDATED_WITH_ERROR(3, "未更新（エラーあり）");

	public int value;

	public String nameId;

	private MonthlyClosurePersonExecutionResult(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
