package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.holidayworktime;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ApplicationReflectOutput;

/**
 * 勤務実績に反映
 * 事前申請の処理(休日出勤申請)
 * @author do_dt
 *
 */
public interface PreHolidayWorktimeReflectService {
	/**
	 * 事前申請の処理(休日出勤申請)
	 * @param holidayWorkPara
	 * @return
	 */
	public boolean preHolidayWorktimeReflect(HolidayWorktimePara holidayWorkPara);
	
}
