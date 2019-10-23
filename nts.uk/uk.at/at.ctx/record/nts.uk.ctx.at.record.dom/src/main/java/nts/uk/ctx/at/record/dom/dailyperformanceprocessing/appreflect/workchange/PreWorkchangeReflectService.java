package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.workchange;

import java.util.List;

import nts.uk.ctx.at.record.dom.dailyprocess.calc.IntegrationOfDaily;

/**
 * 勤務実績に反映
 * 事前申請の処理(勤務変更申請)
 * @author do_dt
 *
 */
public interface PreWorkchangeReflectService {
	/**
	 * 事前申請の処理(勤務変更申請)
	 * @param workchangePara
	 * @return
	 */
	public boolean workchangeReflect(WorkChangeCommonReflectPara param, boolean isPre); 
	
	public List<IntegrationOfDaily> getByWorkChange(WorkChangeCommonReflectPara param, boolean isPre);

}
