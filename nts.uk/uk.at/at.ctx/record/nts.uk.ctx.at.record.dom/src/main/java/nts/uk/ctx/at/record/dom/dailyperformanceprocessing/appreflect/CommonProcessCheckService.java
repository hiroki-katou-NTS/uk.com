package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;


import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;


/**
 * 反映状況によるチェック
 * @author do_dt
 *
 */
public interface CommonProcessCheckService {
	/**
	 * 反映状況によるチェック
	 * @param para
	 * @return false: 反映しない, true: 反映する
	 */
	public boolean commonProcessCheck(CommonCheckParameter para);	

	
	/**
	 * 予定勤種の反映
	 * @param absencePara
	 */
	public WorkInfoOfDailyPerformance reflectScheWorkTimeWorkType(CommonReflectParameter commonPara, boolean isPre,
			WorkInfoOfDailyPerformance dailyInfo);
	/**
	 * 予定勤種を反映できるかチェックする
	 * @param absencePara
	 * @return
	 */
	public boolean checkReflectScheWorkTimeType(CommonReflectParameter commonPara, boolean isPre, WorkInfoOfDailyPerformance dailyInfo);
}
