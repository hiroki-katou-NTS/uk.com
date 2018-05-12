package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.workchange;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonReflectParameter;
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
	public boolean workchangeReflect(CommonReflectParameter workchangePara, boolean isPre); 
	
	

}
