package nts.uk.ctx.at.record.dom.monthlyclosureupdatelog;

/**
 * 
 * @author HungTT - 月締め更新エラーアラーム区分
 *
 */

public enum MonthlyClosureUpdateErrorAlarmAtr {

	ALARM(0, "アラーム"),

	ERROR(1, "エラー");

	public int value;

	public String nameId;

	private MonthlyClosureUpdateErrorAlarmAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
