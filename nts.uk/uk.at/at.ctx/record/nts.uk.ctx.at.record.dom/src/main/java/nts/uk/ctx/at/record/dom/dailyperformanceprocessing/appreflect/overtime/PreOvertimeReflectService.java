package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ApplicationReflectOutput;

/**
 * 残業申請: 事前申請処理
 * @author do_dt
 *
 */
public interface PreOvertimeReflectService {
	/**
	 * 事前申請の処理
	 * @param param
	 * @return
	 */
	public ApplicationReflectOutput overtimeReflect(PreOvertimeParameter param);
}
