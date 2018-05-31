package nts.uk.ctx.at.record.dom.monthlyclosureupdatelog;

/**
 * 
 * @author HungTT - 月締め更新エラーアラーム区分
 *
 */

public enum MonthlyClosureUpdateErrorAlarmAtr {

	ALARM(0, "Enum_MonthlyClosureUpdate_Alarm"),

	ERROR(1, "Enum_MonthlyClosureUpdate_Error");

	public int value;

	public String nameId;

	private MonthlyClosureUpdateErrorAlarmAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
}
