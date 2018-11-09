package nts.uk.ctx.at.shared.dom.worktype.service;
/**
 *
 * @author do_dt
 *
 */
public interface WorkTypeIsClosedService {
	/**
	 * 勤務種類が休出振出かの判断
	 * @param workTimeCode
	 * @return
	 */
	public boolean checkWorkTypeIsClosed(String workTypeCode);
	/**
	 * 打刻自動セット区分を取得する
	 * @param workTypeCode
	 * @param workTypeAtr
	 * @return
	 */
	public boolean checkStampAutoSet(String workTypeCode, AttendanceOfficeAtr workTypeAtr);
	/**
	 * 1日休日の判定
	 * @param sid
	 * @param ymd
	 * @return
	 */
	public boolean checkHoliday(String workTypeCode);
}
