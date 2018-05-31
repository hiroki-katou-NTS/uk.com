package nts.uk.ctx.at.record.dom.monthlyclosureupdatelog;

/**
 * 
 * @author HungTT - 月締め更新実行状況
 *
 */

public enum MonthlyClosureExecutionStatus {

	RUNNING(0, "実行中"),

	COMPLETED_NOT_CONFIRMED(1, "完了（未確認）"),

	COMPLETED_CONFIRMED(2, "完了（確認済）");

	public int value;

	public String nameId;

	private MonthlyClosureExecutionStatus(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

}
