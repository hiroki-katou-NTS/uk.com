package nts.uk.ctx.at.record.infra.repository.divergence.time;

import java.util.List;

import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeErrorCancelMethod;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeName;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeUseSet;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceType;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime;

/**
 * The Class JpaDivergenceTimeRepositoryGetMemento.
 */
public class JpaDivergenceTimeRepositoryGetMemento implements DivergenceTimeGetMemento {

	/** The entities. */
	private KrcstDvgcTime entities;

	/**
	 * Instantiates a new jpa divergence time repository get memento.
	 *
	 * @param entities
	 *            the entities
	 */
	public JpaDivergenceTimeRepositoryGetMemento(KrcstDvgcTime entities) {

		this.entities = entities;
	}

	@Override
	public Integer getDivergenceTimeNo() {
		return entities.getId().getNo();
	}

	@Override
	public String getCompanyId() {
		return entities.getId().getCid();
	}

	@Override
	public DivergenceTimeUseSet getDivTimeUseSet() {
		return DivergenceTimeUseSet.valueOf(entities.getDvgcTimeUseSet().intValue());
	}

	@Override
	public DivergenceTimeName getDivTimeName() {
		return new DivergenceTimeName(entities.getDvgcTimeName());
	}

	@Override
	public DivergenceType getDivType() {
		return DivergenceType.valueOf(entities.getDvgcType().intValue());
	}

	@Override
	public DivergenceTimeErrorCancelMethod getErrorCancelMedthod() {
		return new DivergenceTimeErrorCancelMethod(entities.getReasonInputCanceled().intValue(),
				entities.getReasonSelectCanceled().intValue());
	}

	@Override
	public List<Double> getTargetItems() {
		// TODO Auto-generated method stub
		return null;
	}

}
