package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.absence;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.CommonReflectParameter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ApplicationReflectOutput;

/**
 * 勤務実績に反映: 休暇申請処理
 * @author do_dt
 *
 */
public interface AbsenceReflectService {
	/**
	 * 	(休暇申請)
	 * @param absencePara
	 * @param isPre: True - 事前申請の処理, False - 事後申請の処理
	 * @return
	 */
	public ApplicationReflectOutput absenceReflect(CommonReflectParameter absencePara, boolean isPre);
	
}
