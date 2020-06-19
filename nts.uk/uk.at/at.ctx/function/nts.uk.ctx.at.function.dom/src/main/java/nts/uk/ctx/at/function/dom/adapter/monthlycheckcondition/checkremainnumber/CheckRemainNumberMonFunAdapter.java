package nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.checkremainnumber;

public interface CheckRemainNumberMonFunAdapter {
	/**
	 * 月別実績の残数チェックを取得
	 * @param errorAlarmID
	 * @return
	 */
	CheckRemainNumberMonFunImport getByEralCheckID(String errorAlarmID);
}
