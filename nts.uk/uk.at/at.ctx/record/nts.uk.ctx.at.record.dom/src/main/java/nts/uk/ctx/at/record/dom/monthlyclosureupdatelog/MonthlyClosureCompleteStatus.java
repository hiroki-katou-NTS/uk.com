package nts.uk.ctx.at.record.dom.monthlyclosureupdatelog;

/**
 * 
 * @author HungTT - 月締め更新完了状態
 *
 */

public enum MonthlyClosureCompleteStatus {

	INCOMPLETE(0, "未完了"),

	COMPLETE(1, "完了"),

	COMPLETE_WITH_ERROR(2, "完了（エラーあり）"),

	COMPLETE_WITH_ALARM(3, "完了（アラームあり）");

	public int value;

	public String nameId;

	private MonthlyClosureCompleteStatus(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
