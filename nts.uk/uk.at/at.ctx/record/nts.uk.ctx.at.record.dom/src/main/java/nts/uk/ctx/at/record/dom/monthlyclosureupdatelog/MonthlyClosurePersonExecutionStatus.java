package nts.uk.ctx.at.record.dom.monthlyclosureupdatelog;

/**
 * 
 * @author HungTT - 月締め更新対象者毎の実行状況
 *
 */

public enum MonthlyClosurePersonExecutionStatus {

	INCOMPLETE(0, "未完了"),

	COMPLETE(1, "完了");

	public int value;

	public String nameId;

	private MonthlyClosurePersonExecutionStatus(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
