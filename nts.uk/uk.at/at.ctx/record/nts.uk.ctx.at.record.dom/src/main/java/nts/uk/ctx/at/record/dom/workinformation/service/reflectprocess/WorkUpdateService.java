package nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;

/**
 * 反映処理
 * 
 * @author do_dt
 *
 */
public interface WorkUpdateService {

	void editStateOfDailyPerformance(String sid, GeneralDate ymd, List<EditStateOfDailyAttd> lstEditState,
			List<Integer> lstItem);

	/**
	 * 事前残業の勤務項目
	 * 
	 * @return
	 */
	public List<Integer> lstPreOvertimeItem();

	/**
	 * 事前休日出勤時間の項目ID
	 * 
	 * @return
	 */
	List<Integer> lstPreWorktimeFrameItem();

	/**
	 * 事後休日出勤時間帯の項目ID
	 * 
	 * @return
	 */
	List<Integer> lstAfterWorktimeFrameItem();

	/**
	 * 振替時間の項目ID
	 * 
	 * @return
	 */
	List<Integer> lstTranfertimeFrameItem();

	/**
	 * 事後残業の勤務項目
	 * 
	 * @return
	 */
	List<Integer> lstAfterOvertimeItem();

	/**
	 * 残業枠時間．振替時間
	 * 
	 * @return
	 */
	List<Integer> lstTransferTimeOtItem();

	/**
	 * 休憩開始時刻
	 * 
	 * @return
	 */
	List<Integer> lstBreakStartTime();

	/**
	 * 休憩終了時刻
	 * 
	 * @return
	 */
	List<Integer> lstBreakEndTime();

	/**
	 * 予定休憩開始時刻
	 * 
	 * @return
	 */
	List<Integer> lstScheBreakStartTime();

	/**
	 * 予定休憩終了時刻
	 * 
	 * @return
	 */
	List<Integer> lstScheBreakEndTime();
}
