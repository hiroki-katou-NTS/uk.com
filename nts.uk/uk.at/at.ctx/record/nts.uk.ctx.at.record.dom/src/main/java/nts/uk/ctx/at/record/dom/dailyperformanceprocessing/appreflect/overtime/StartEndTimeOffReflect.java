package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import nts.arc.time.GeneralDate;

/**
 * 開始終了時刻の反映(事前申請)
 * @author do_dt
 *
 */
public interface StartEndTimeOffReflect {
	/**
	 * 開始終了時刻の反映(事前申請)
	 * @param param
	 */
	public void startEndTimeOffReflect(PreOvertimeParameter param);
	/**
	 * 自動打刻をクリアする
	 * @param employeeId
	 * @param dateData
	 * @param worktypeCode
	 * @param isClearAuto
	 */
	public void clearAutomaticEmbossing(String employeeId, GeneralDate dateData, String worktypeCode, boolean isClearAuto, Integer timeData);

}
