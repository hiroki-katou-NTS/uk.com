package nts.uk.ctx.at.record.dom.dailyprocess.calc;

/**
 * 実働or予定時間帯作成から呼び出されたか
 * @author keisuke_hoshina
 *
 */
public enum TimeSheetAtr {
	RECORD,
	SCHEDULE;
	
	/**
	 * 予定時間帯から呼び出されたか判定する
	 * @return　予定時間帯である
	 */
	public boolean isSchedule() {
		return SCHEDULE.equals(this);
	}
}
