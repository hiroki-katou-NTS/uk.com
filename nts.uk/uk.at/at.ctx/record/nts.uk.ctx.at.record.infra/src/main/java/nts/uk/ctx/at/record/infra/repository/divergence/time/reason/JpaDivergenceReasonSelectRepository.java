package nts.uk.ctx.at.record.infra.repository.divergence.time.reason;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelect;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectRepository;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcstDvgcReason;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcstDvgcReasonPK;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcstDvgcReasonPK_;
import nts.uk.ctx.at.record.infra.entity.divergence.reason.KrcstDvgcReason_;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaDivergenceReasonSelectRepository.
 */
public class JpaDivergenceReasonSelectRepository extends JpaRepository implements DivergenceReasonSelectRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonSelectRepository#findAllReason(int)
	 */
	@Override
	public List<DivergenceReasonSelect> findAllReason(int divTimeNo, String companyId) {

		EntityManager em = this.getEntityManager();

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstDvgcReason> cq = criteriaBuilder.createQuery(KrcstDvgcReason.class);
		Root<KrcstDvgcReason> root = cq.from(KrcstDvgcReason.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcstDvgcReason_.id).get(KrcstDvgcReasonPK_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KrcstDvgcReason_.id).get(KrcstDvgcReasonPK_.no), divTimeNo));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KrcstDvgcReason> KrcstDvgcReason = em.createQuery(cq).getResultList();

		// return
		if (KrcstDvgcReason != null)
			return KrcstDvgcReason.stream().map(e -> this.toDomain(e)).collect(Collectors.toList());
		return new ArrayList<DivergenceReasonSelect>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonSelectRepository#delete(nts.uk.ctx.at.record.dom.
	 * divergence.time.setting.DivergenceReasonSelect)
	 */
	@Override
	public void delete(DivergenceReasonSelect divReasonSelect) {
		this.commandProxy().remove(divReasonSelect);
		this.getEntityManager().flush();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.
	 * DivergenceReasonSelectRepository#add(nts.uk.ctx.at.record.dom.divergence.
	 * time.setting.DivergenceReasonSelect)
	 */
	@Override
	public void add(DivergenceReasonSelect divReasonSelect) {
		this.commandProxy().insert(divReasonSelect);

	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the krcst dvgc reason
	 */
	private KrcstDvgcReason toEntity(DivergenceReasonSelect domain) {

		KrcstDvgcReason entity = new KrcstDvgcReason();
		domain.saveToMemento(new JpaDivergenceReasonSelectRepositorySetMemento(entity));

		return entity;
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the divergence reason select
	 */
	private DivergenceReasonSelect toDomain(KrcstDvgcReason entity) {
		DivergenceReasonSelectGetMemento memento = new JpaDivergenceReasonSelectRepositoryGetMemento(entity);

		return new DivergenceReasonSelect(memento);

	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectRepository#findReasonInfo(int, java.lang.String, java.lang.String)
	 */
	@Override
	public DivergenceReasonSelect findReasonInfo(int divTimeNo, String companyId, String reasonCode) {

		KrcstDvgcReasonPK PK = new KrcstDvgcReasonPK(divTimeNo, companyId, reasonCode);

		KrcstDvgcReason entity = this.queryProxy().find(PK, KrcstDvgcReason.class).orElse(new KrcstDvgcReason());

		if (entity != null)
			return this.toDomain(entity);
		else
			return new DivergenceReasonSelect(null);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelectRepository#update(nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceReasonSelect)
	 */
	@Override
	public void update(DivergenceReasonSelect divergenceReasonSelect) {
		this.commandProxy().update(this.toEntity(divergenceReasonSelect));

	}
}
