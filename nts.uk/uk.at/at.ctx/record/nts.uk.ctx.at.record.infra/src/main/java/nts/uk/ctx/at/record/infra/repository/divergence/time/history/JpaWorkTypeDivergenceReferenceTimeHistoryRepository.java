package nts.uk.ctx.at.record.infra.repository.divergence.time.history;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistory;
import nts.uk.ctx.at.record.dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistoryRepository;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcstWorktypeDrtHist;
import nts.uk.ctx.at.record.infra.entity.divergence.time.history.KrcstWorktypeDrtHist_;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * The Class JpaWorkTypeDivergenceReferenceTimeHistoryRepository.
 */
@Stateless
public class JpaWorkTypeDivergenceReferenceTimeHistoryRepository extends JpaRepository
		implements WorkTypeDivergenceReferenceTimeHistoryRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeHistoryRepository#findByKey(java.lang.String,
	 * nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode, java.lang.String)
	 */
	@Override
	public WorkTypeDivergenceReferenceTimeHistory findByKey(String histId) {
		KrcstWorktypeDrtHist worktypeDrtHist = this.queryProxy().find(histId, KrcstWorktypeDrtHist.class)
				.orElse(new KrcstWorktypeDrtHist());
		ArrayList<KrcstWorktypeDrtHist> entities = new ArrayList<>();
		entities.add(worktypeDrtHist);

		return this.toDomain(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeHistoryRepository#findAll(java.lang.String,
	 * nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode)
	 */
	@Override
	public WorkTypeDivergenceReferenceTimeHistory findAll(String companyId, WorkTypeCode workTypeCode) {

		return this.toDomain(this.findByCompanyIdAndWorkType(companyId, workTypeCode.v()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeHistoryRepository#add(nts.uk.ctx.at.record.dom
	 * .divergence.time.history.WorkTypeDivergenceReferenceTimeHistory)
	 */
	@Override
	public void add(WorkTypeDivergenceReferenceTimeHistory domain) {
		this.commandProxy().insertAll(this.toEntities(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeHistoryRepository#update(nts.uk.ctx.at.record.
	 * dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistory)
	 */
	@Override
	public void update(WorkTypeDivergenceReferenceTimeHistory domain) {
		this.commandProxy().updateAll(this.toEntities(domain));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.divergence.time.history.
	 * WorkTypeDivergenceReferenceTimeHistoryRepository#delete(nts.uk.ctx.at.record.
	 * dom.divergence.time.history.WorkTypeDivergenceReferenceTimeHistory)
	 */
	@Override
	public void delete(WorkTypeDivergenceReferenceTimeHistory domain) {
		this.commandProxy().removeAll(this.toEntities(domain));
	}

	/**
	 * To domain.
	 *
	 * @param entities
	 *            the entities
	 * @return the work type divergence reference time history
	 */
	private WorkTypeDivergenceReferenceTimeHistory toDomain(List<KrcstWorktypeDrtHist> entities) {
		JpaWorkTypeDivergenceReferenceTimeHistoryGetMemento memento = new JpaWorkTypeDivergenceReferenceTimeHistoryGetMemento(
				entities);
		return new WorkTypeDivergenceReferenceTimeHistory(memento);
	}

	/**
	 * To entities.
	 *
	 * @param domain
	 *            the domain
	 * @return the list
	 */
	private List<KrcstWorktypeDrtHist> toEntities(WorkTypeDivergenceReferenceTimeHistory domain) {
		List<KrcstWorktypeDrtHist> comDrtHists = this.findByCompanyIdAndWorkType(domain.getCId(),
				domain.getWorkTypeCode().v());

		JpaWorkTypeDivergenceReferenceTimeHistorySetMemento memento = new JpaWorkTypeDivergenceReferenceTimeHistorySetMemento(
				comDrtHists);

		domain.saveToMemento(memento);

		return comDrtHists;
	}

	/**
	 * Find by company id and work type.
	 *
	 * @param companyId the company id
	 * @param workType the work type
	 * @return the list
	 */
	private List<KrcstWorktypeDrtHist> findByCompanyIdAndWorkType(String companyId, String workTypeCode) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstWorktypeDrtHist> cq = criteriaBuilder.createQuery(KrcstWorktypeDrtHist.class);
		Root<KrcstWorktypeDrtHist> root = cq.from(KrcstWorktypeDrtHist.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcstWorktypeDrtHist_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KrcstWorktypeDrtHist_.worktypeCd), workTypeCode));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KrcstWorktypeDrtHist> worktypeDrtHists = em.createQuery(cq).getResultList();

		return worktypeDrtHists;
	}
}
