package nts.uk.ctx.at.record.infra.repository.divergence.time;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethod;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceReasonInputMethodRepository;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcmtDvgcTime;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcmtDvgcTimePK;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcmtDvgcTimePK_;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcmtDvgcTime_;

/**
 * The Class JpaDivergenceReasonInputMethodRepository.
 */
@Stateless
public class JpaDivergenceReasonInputMethodRepository extends JpaRepository
		implements DivergenceReasonInputMethodRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.
	 * DivergenceReasonInputMethodRepository#getAllDivTime(java.lang.String)
	 */
	@Override
	public List<DivergenceReasonInputMethod> getAllDivTime(String companyId) {

		return this.findByCompanyId(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.
	 * DivergenceReasonInputMethodRepository#getDivTimeInfo(java.lang.String,
	 * int)
	 */
	@Override
	public Optional<DivergenceReasonInputMethod> getDivTimeInfo(String companyId, int divTimeNo) {

		KrcmtDvgcTimePK PK = new KrcmtDvgcTimePK(companyId, divTimeNo);
		return this.queryProxy().find(PK, KrcmtDvgcTime.class).map(e->this.toDomain(e));
	}

	/**
	 * To domain.
	 *
	 * @param entities
	 *            the entities
	 * @return the company divergence reference time history
	 */
	private DivergenceReasonInputMethod toDomain(KrcmtDvgcTime entities) {
		DivergenceReasonInputMethodGetMemento memento = new JpaDivergenceReasonInputMethodGetMemento(entities);
		return new DivergenceReasonInputMethod(memento);
	}

	/**
	 * Find by company id.
	 *
	 * @param companyId
	 *            the company id
	 * @return the list
	 */
	private List<DivergenceReasonInputMethod> findByCompanyId(String companyId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcmtDvgcTime> cq = criteriaBuilder.createQuery(KrcmtDvgcTime.class);
		Root<KrcmtDvgcTime> root = cq.from(KrcmtDvgcTime.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcmtDvgcTime_.id).get(KrcmtDvgcTimePK_.cid), companyId));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KrcmtDvgcTime> KrcmtDvgcTime = em.createQuery(cq).getResultList();

		// return
		return KrcmtDvgcTime.isEmpty() ? new ArrayList<DivergenceReasonInputMethod>()
				: KrcmtDvgcTime.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.
	 * DivergenceReasonInputMethodRepository#update(nts.uk.ctx.at.record.dom.
	 * divergence.time.DivergenceReasonInputMethod)
	 */
	@Override
	public void update(DivergenceReasonInputMethod domain) {
		this.commandProxy().update(this.toEntity(domain));

	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the krcst dvgc time
	 */
	private KrcmtDvgcTime toEntity(DivergenceReasonInputMethod domain) {

		KrcmtDvgcTimePK PK = new KrcmtDvgcTimePK(domain.getCompanyId(), domain.getDivergenceTimeNo());
		KrcmtDvgcTime entity = this.queryProxy().find(PK, KrcmtDvgcTime.class).orElse(new KrcmtDvgcTime());

		domain.saveToMemento(new JpaDivergenceReasonInputMethodSetMemento(entity));

		return entity;
	}

}
