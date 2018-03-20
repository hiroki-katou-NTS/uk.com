package nts.uk.ctx.at.record.infra.repository.divergence.time.message;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.divergence.time.message.ErrorAlarmMessage;
import nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageGetMemento;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstWtdvgcTimeEaMsg;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * The Class JpaWorkTypeDivergenceTimeErrorAlarmMessageGetMemento.
 */
public class JpaWorkTypeDivergenceTimeErrorAlarmMessageGetMemento
		implements WorkTypeDivergenceTimeErrorAlarmMessageGetMemento {
	
	/** The entity. */
	private KrcstWtdvgcTimeEaMsg entity;

	/**
	 * Instantiates a new jpa work type divergence time error alarm message get memento.
	 */
	public JpaWorkTypeDivergenceTimeErrorAlarmMessageGetMemento() {
	}

	/**
	 * Instantiates a new jpa work type divergence time error alarm message get memento.
	 *
	 * @param entity the entity
	 */
	public JpaWorkTypeDivergenceTimeErrorAlarmMessageGetMemento(KrcstWtdvgcTimeEaMsg entity) {
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageGetMemento#getCId()
	 */
	@Override
	public CompanyId getCId() {
		return new CompanyId(this.entity.getId().getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageGetMemento#getWorkTypeCode()
	 */
	@Override
	public WorkTypeCode getWorkTypeCode() {
		return new WorkTypeCode(this.entity.getId().getWorktypeCd());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageGetMemento#getDivergenceTimeNo()
	 */
	@Override
	public Integer getDivergenceTimeNo() {
		return this.entity.getId().getDvgcTimeNo();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageGetMemento#getAlarmMessage()
	 */
	@Override
	public Optional<ErrorAlarmMessage> getAlarmMessage() {
		ErrorAlarmMessage message = new ErrorAlarmMessage(this.entity.getAlarmMessage());
		return Optional.of(message);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.message.WorkTypeDivergenceTimeErrorAlarmMessageGetMemento#getErrorMessage()
	 */
	@Override
	public Optional<ErrorAlarmMessage> getErrorMessage() {
		ErrorAlarmMessage message = new ErrorAlarmMessage(this.entity.getErrorMessage());
		return Optional.of(message);
	}

}
