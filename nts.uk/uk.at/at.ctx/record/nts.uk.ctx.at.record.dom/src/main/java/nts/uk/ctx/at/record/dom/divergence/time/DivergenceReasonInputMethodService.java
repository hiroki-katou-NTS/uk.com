package nts.uk.ctx.at.record.dom.divergence.time;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReason;
import nts.uk.ctx.at.record.dom.divergence.time.reason.DivergenceReasonCode;

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
	 * @param divergenceReason the divergence reason
	 * @param justmentResult the justment result
	 * @return true, if successful
	 */
	//理由漏れがあるか判定する
	public boolean DetermineLeakageReason(String employeeId, GeneralDate processDate, 
			Integer divergenceTimeNo,DivergenceReasonCode divergenceReasonCode, DivergenceReason divergenceReason,boolean justmentResult );

}
