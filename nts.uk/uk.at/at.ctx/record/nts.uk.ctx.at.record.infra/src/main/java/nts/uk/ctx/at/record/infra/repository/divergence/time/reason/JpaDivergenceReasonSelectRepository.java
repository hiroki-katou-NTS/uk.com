package nts.uk.ctx.at.record.infra.repository.divergence.time.reason;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelect;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectRepository;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcstDvgcReason;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime;
import nts.uk.ctx.at.record.infra.repository.divergence.time.setting.JpaDivergenceTimeRepositorySetMemento;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaDivergenceReasonSelectRepository.
 */
public class JpaDivergenceReasonSelectRepository extends JpaRepository implements DivergenceReasonSelectRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectRepository#findAllReason(int)
	 */
	@Override
	public List<DivergenceReasonSelect> findAllReason(int divTimeId) {
		EntityManager em = this.getEntityManager();
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstDvgcTime> cq = criteriaBuilder.createQuery(KrcstDvgcTime.class);
		Root<KrcstDvgcTime> root = cq.from(KrcstDvgcTime.class);
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectRepository#delete(nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelect)
	 */
	@Override
	public void delete(DivergenceReasonSelect divergenceReasonSelect) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectRepository#add(nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelect)
	 */
	@Override
	public void add(DivergenceReasonSelect divergenceReasonSelect) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the krcst dvgc reason
	 */
	private KrcstDvgcReason toEntity(DivergenceReasonSelect domain){
		
		KrcstDvgcReason entity = new KrcstDvgcReason();
		domain.saveToMemento(new JpaDivergenceReasonSelectRepositorySetMemento(entity));

		return entity;
	}

	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the divergence reason select
	 */
	private DivergenceReasonSelect toDomain(KrcstDvgcReason entity){
		DivergenceReasonSelectGetMemento memento = new JpaDivergenceReasonSelectRepositoryGetMemento(entity);
		
		return new DivergenceReasonSelect(memento);
		
	}
}
