package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;

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
	public void reflectScheWorkTimeWorkType(CommonReflectParameter commonPara, boolean isPre, IntegrationOfDaily dailyInfor);
	/**
	 * 予定勤種を反映できるかチェックする
	 * @param absencePara
	 * @return
	 */
	public boolean checkReflectScheWorkTimeType(CommonReflectParameter commonPara, boolean isPre, String workTimeCode);
	/**
	 * 
	 * @param integrationOfDaily
	 */
	public void updateDailyAfterReflect(List<IntegrationOfDaily> integrationOfDaily);
	/**
	 * 就業時間帯の休憩時間帯を日別実績に反映する
	 * @param sid
	 * @param ymd
	 * @param optTimeLeaving
	 */
	public IntegrationOfDaily updateBreakTimeInfor(String sid, GeneralDate ymd, IntegrationOfDaily integrationOfDaily, String companyId);

	public List<IntegrationOfDaily> lstIntegrationOfDaily(IntegrationOfDaily integrationOfDaily, String sid,
			GeneralDate ymd);
	
	public void calculateOfAppReflect(IntegrationOfDaily integrationOfDaily, String sid, GeneralDate ymd);
	
}
