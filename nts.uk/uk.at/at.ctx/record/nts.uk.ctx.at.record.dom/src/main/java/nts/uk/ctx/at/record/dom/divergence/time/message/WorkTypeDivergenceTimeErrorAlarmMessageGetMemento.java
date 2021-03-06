package nts.uk.ctx.at.record.dom.divergence.time.message;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;

/**
 * The Interface WorkTypeDivergenceTimeErrorAlarmMessageGetMemento.
 */
public interface WorkTypeDivergenceTimeErrorAlarmMessageGetMemento {
	
	/**
	 * Gets the c id.
	 *
	 * @return the c id
	 */
	public CompanyId getCId();

	/**
	 * Gets the work type code.
	 *
	 * @return the work type code
	 */
	public BusinessTypeCode getWorkTypeCode();

	/**
	 * Gets the divergence time no.
	 *
	 * @return the divergence time no
	 */
	public Integer getDivergenceTimeNo();

	/**
	 * Gets the alarm message.
	 *
	 * @return the alarm message
	 */
	public Optional<ErrorAlarmMessage> getAlarmMessage();

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public Optional<ErrorAlarmMessage> getErrorMessage();
}
