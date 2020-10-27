package nts.uk.ctx.at.record.infra.repository.divergence.time.history;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnit;
import nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnitRepository;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcmtDvgcUnitSet;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaDivergenceReferenceTimeUsageUnitRepository.
 */
@Stateless
public class JpaDivergenceReferenceTimeUsageUnitRepository extends JpaRepository
		implements DivergenceReferenceTimeUsageUnitRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * DivergenceReferenceTimeUsageUnitRepository#update(nts.uk.ctx.at.record.
	 * dom.divergence.time.history.DivergenceReferenceTimeUsageUnit)
	 */
	@Override
	public void update(DivergenceReferenceTimeUsageUnit domain) {
		this.commandProxy().update(this.toEntity(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * DivergenceReferenceTimeUsageUnitRepository#findById(java.lang.String)
	 */
	@Override
	public Optional<DivergenceReferenceTimeUsageUnit> findByCompanyId(String companyId) {
		return this.queryProxy().find(companyId, KrcmtDvgcUnitSet.class).map(e -> this.toDomain(e));
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the krcmtDvgcUnitSet
	 */
	private KrcmtDvgcUnitSet toEntity(DivergenceReferenceTimeUsageUnit domain) {
		KrcmtDvgcUnitSet entity = this.queryProxy().find(domain.getCId(), KrcmtDvgcUnitSet.class)
				.orElse(new KrcmtDvgcUnitSet());
		domain.saveToMemento(new JpaDivergenceReferenceTimeUsageUnitSetMemento(entity));
		return entity;
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the divergence reference time usage unit
	 */
	private DivergenceReferenceTimeUsageUnit toDomain(KrcmtDvgcUnitSet entity) {
		JpaDivergenceReferenceTimeUsageUnitGetMemento memento = new JpaDivergenceReferenceTimeUsageUnitGetMemento(
				entity);
		return new DivergenceReferenceTimeUsageUnit(memento);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnitRepository#add(nts.uk.ctx.at.record.dom.divergence.time.history.DivergenceReferenceTimeUsageUnit)
	 */
	@Override
	public void add(DivergenceReferenceTimeUsageUnit domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}
}
