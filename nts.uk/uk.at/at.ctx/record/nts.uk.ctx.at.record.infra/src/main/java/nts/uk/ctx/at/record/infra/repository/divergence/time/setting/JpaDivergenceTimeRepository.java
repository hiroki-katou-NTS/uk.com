package nts.uk.ctx.at.record.infra.repository.divergence.time.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.setting.*;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTimePK_;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime_;

@Stateless
public class JpaDivergenceTimeRepository extends JpaRepository implements DivergenceTimeRepository {

	/**
	 * get all divergence time.
	 *
	 * @param companyId
	 *            the company id
	 * @return the all div time
	 */

	@Override
	public List<DivergenceTime> getAllDivTime(String companyId) {
		return this.findByCompanyId(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeRepository
	 * #getDivTimeInfo(java.lang.String, int)
	 */
	@Override
	public DivergenceTime getDivTimeInfo(String companyId, int divTimeNo) {

		return this.findByCode(companyId, divTimeNo);
	}

	/**
	 * To domain.
	 *
	 * @param entities
	 *            the entities
	 * @return the divergence time
	 */
	private DivergenceTime toDomain(KrcstDvgcTime entities) {
		DivergenceTimeGetMemento memento = new JpaDivergenceTimeRepositoryGetMemento(entities);
		return new DivergenceTime(memento);
	}

	/**
	 * Find by company id.
	 *
	 * @param companyId
	 *            the company id
	 * @return the list
	 */
	private List<DivergenceTime> findByCompanyId(String companyId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstDvgcTime> cq = criteriaBuilder.createQuery(KrcstDvgcTime.class);
		Root<KrcstDvgcTime> root = cq.from(KrcstDvgcTime.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcstDvgcTime_.id).get(KrcstDvgcTimePK_.cid), companyId));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KrcstDvgcTime> KrcstDvgcTime = em.createQuery(cq).getResultList();

		// return
		return KrcstDvgcTime.isEmpty() ? new ArrayList<DivergenceTime>()
				: KrcstDvgcTime.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	/**
	 * Find by code.
	 *
	 * @param companyId
	 *            the company id
	 * @param divTimeNo
	 *            the div time no
	 * @return the divergence time
	 */
	private DivergenceTime findByCode(String companyId, int divTimeNo) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstDvgcTime> cq = criteriaBuilder.createQuery(KrcstDvgcTime.class);
		Root<KrcstDvgcTime> root = cq.from(KrcstDvgcTime.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcstDvgcTime_.id).get(KrcstDvgcTimePK_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KrcstDvgcTime_.id).get(KrcstDvgcTimePK_.no), divTimeNo));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		KrcstDvgcTime KrcstDvgcTime = em.createQuery(cq).getResultList().get(0);

		// return
		if (KrcstDvgcTime != null)
			return this.toDomain(KrcstDvgcTime);
		return new DivergenceTime(null);

	}

}
