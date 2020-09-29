package nts.uk.ctx.at.record.dom.divergence.time;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DivergenceReasonContent;

/**
 * The Interface DivergenceReasonInputMethodService.
 */
public interface DivergenceReasonInputMethodService {

	/**
	 * Determine leakage reason.
	 *
	 * @param employeeId the employee id
	 * @param processDate the process date
	 * @param divergenceTimeNo the divergence time no
	 * @param divergenceReasonCode the divergence reason code
	 * @param divergenceReasonContent the divergence reason content
	 * @param justmentResult the justment result
	 * @return true, if successful
	 */
	//理由漏れがあるか判定する
	public JudgmentResult determineLeakageReason(String employeeId, GeneralDate processDate, 
			int divergenceTimeNo,DivergenceReasonCode divergenceReasonCode, DivergenceReasonContent divergenceReasonContent,JudgmentResult justmentResult );

}
