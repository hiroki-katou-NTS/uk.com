package nts.uk.ctx.at.record.infra.repository.divergence.time.message;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageSetMemento;
import nts.uk.ctx.at.record.infra.entity.divergence.time.message.KrcstDvgcwtTimeEaMsg;
import nts.uk.ctx.at.record.infra.entity.divergence.time.message.KrcstDvgcwtTimeEaMsgPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmMessage;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;

/**
 * The Class JpaWorkTypeDivergenceTimeErrorAlarmMessageSetMemento.
 */
public class JpaWorkTypeDivergenceTimeErrorAlarmMessageSetMemento
		implements WorkTypeDivergenceTimeErrorAlarmMessageSetMemento {

	/** The entity. */
	private KrcstDvgcwtTimeEaMsg entity;

	/**
	 * Instantiates a new jpa work type divergence time error alarm message set
	 * memento.
	 */
	public JpaWorkTypeDivergenceTimeErrorAlarmMessageSetMemento() {
	}

	/**
	 * Instantiates a new jpa work type divergence time error alarm message set
	 * memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaWorkTypeDivergenceTimeErrorAlarmMessageSetMemento(KrcstDvgcwtTimeEaMsg entity) {
		this.entity = entity;
	}

	/**
	 * Sets the c id.
	 *
	 * @param cId
	 *            the new c id
	 */
	@Override
	public void setCId(CompanyId cId) {
		KrcstDvgcwtTimeEaMsgPK pk = new KrcstDvgcwtTimeEaMsgPK();
		pk.setCid(cId.v());

		this.entity.setId(pk);
	}

	/**
	 * Sets the work type code.
	 *
	 * @param workTypeCode
	 *            the new work type code
	 */
	@Override
	public void setWorkTypeCode(BusinessTypeCode workTypeCode) {
		this.entity.getId().setWorktypeCd(workTypeCode.v());
	}

	/**
	 * Sets the divergence time no.
	 *
	 * @param divergenceTimeNo
	 *            the new divergence time no
	 */
	@Override
	public void setDivergenceTimeNo(Integer divergenceTimeNo) {
		this.entity.getId().setDvgcTimeNo(divergenceTimeNo);
	}

	/**
	 * Sets the alarm message.
	 *
	 * @param alarmMessage
	 *            the new alarm message
	 */
	@Override
	public void setAlarmMessage(Optional<ErrorAlarmMessage> alarmMessage) {
		this.entity.setAlarmMessage(alarmMessage.isPresent() ? alarmMessage.get().v() : null);
	}

	/**
	 * Sets the error message.
	 *
	 * @param errorMessage
	 *            the new error message
	 */
	@Override
	public void setErrorMessage(Optional<ErrorAlarmMessage> errorMessage) {
		this.entity.setErrorMessage(errorMessage.isPresent() ? errorMessage.get().v() : null);
	}

}
