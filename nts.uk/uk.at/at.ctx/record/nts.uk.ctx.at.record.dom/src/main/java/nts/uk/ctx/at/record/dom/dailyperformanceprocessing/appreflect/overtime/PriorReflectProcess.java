package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;

/**
 * 事前申請の反映処理
 * @author do_dt
 *
 */
public interface PriorReflectProcess {
	/**
	 * 予定勤種・就時の反映
	 * @param para
	 */
	public void workTimeWorkTimeUpdate(PreOvertimeParameter para);
	/**
	 * 勤種・就時の反映
	 * @param para
	 * @return True: 反映前後勤就の変更する
	 * False: 反映前後勤就の変更したい
	 */
	public boolean changeFlg(PreOvertimeParameter para);
	/**
	 * 予定開始終了時刻の反映
	 * @param para
	 * @param changeFlg
	 * @param dailyData
	 * @return
	 */
	public boolean startAndEndTimeReflectSche(PreOvertimeParameter para,
			boolean changeFlg,
			WorkInfoOfDailyPerformance dailyData);
	/**
	 * 設定による予定開始終了時刻を反映できるかチェックする
	 * @param para
	 * @param changeFlg: 反映前後勤就の変更フラグ
	 * @param dailyData: 日別実績の勤務情報
	 * @return
	 */
	public boolean timeReflectCheck(PreOvertimeParameter para,
			boolean changeFlg,
			WorkInfoOfDailyPerformance dailyData);

}
