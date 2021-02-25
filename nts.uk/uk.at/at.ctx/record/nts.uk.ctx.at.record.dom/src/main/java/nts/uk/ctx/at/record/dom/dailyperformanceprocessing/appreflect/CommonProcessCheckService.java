package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import nts.arc.time.GeneralDate;

/**
 * 反映状況によるチェック
 * @author do_dt
 *
 */
public interface CommonProcessCheckService {
	
	public void createLogError(String sid, GeneralDate ymd, String excLogId);
}
