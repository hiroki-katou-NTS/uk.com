/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.workdayoff.frame;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.infra.entity.workdayoff.frame.KshmtHdWorkFrame;
import nts.uk.ctx.at.shared.infra.entity.workdayoff.frame.KshmtHdWorkFramePK;
import nts.uk.ctx.at.shared.infra.entity.workdayoff.frame.KshmtHdWorkFramePK_;
import nts.uk.ctx.at.shared.infra.entity.workdayoff.frame.KshmtHdWorkFrame_;

/**
 * The Class JpaWorkdayoffFrameRepository.
 */
@Stateless
public class JpaWorkdayoffFrameRepository extends JpaRepository
		implements WorkdayoffFrameRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository#
	 * findWorkdayoffFrame(nts.uk.ctx.at.shared.dom.common.CompanyId, int)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public Optional<WorkdayoffFrame> findWorkdayoffFrame(CompanyId companyId,
			int workdayoffFrameNo) {
		return this.queryProxy()
				.find(new KshmtHdWorkFramePK(companyId.v(), (short) workdayoffFrameNo),
						KshmtHdWorkFrame.class)
				.map(e -> this.toDomain(e));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository#
	 * update(nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame)
	 */
	@Override
	public void update(WorkdayoffFrame workdayoffFrame) {
		this.commandProxy().update(this.toEntity(workdayoffFrame));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository#
	 * getAllOvertimeWorkFrame(java.lang.String)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<WorkdayoffFrame> getAllWorkdayoffFrame(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtHdWorkFrame> cq = criteriaBuilder
				.createQuery(KshmtHdWorkFrame.class);

		// root data
		Root<KshmtHdWorkFrame> root = cq.from(KshmtHdWorkFrame.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq company id
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KshmtHdWorkFrame_.kshmtHdWorkFramePK)
						.get(KshmtHdWorkFramePK_.cid), companyId));
		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<KshmtHdWorkFrame> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the kshst workdayoff frame
	 */
	private KshmtHdWorkFrame toEntity(WorkdayoffFrame domain) {
		KshmtHdWorkFrame entity = new KshmtHdWorkFrame();
		domain.saveToMemento(new JpaWorkdayoffFrameSetMemento(entity));
		return entity;
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the workdayoff frame
	 */
	private WorkdayoffFrame toDomain(KshmtHdWorkFrame entity) {
		return new WorkdayoffFrame(new JpaWorkdayoffFrameGetMemento(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository#
	 * getWorkdayoffFrameBy(nts.uk.ctx.at.shared.dom.common.CompanyId,
	 * java.util.List)
	 */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<WorkdayoffFrame> getWorkdayoffFrameBy(String companyId,
			List<Integer> workdayoffFrNos) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtHdWorkFrame> cq = criteriaBuilder
				.createQuery(KshmtHdWorkFrame.class);

		// root data
		Root<KshmtHdWorkFrame> root = cq.from(KshmtHdWorkFrame.class);

		// select root
		cq.select(root);
		
		List<KshmtHdWorkFrame> resultList = new ArrayList<>();

		CollectionUtil.split(workdayoffFrNos, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, splitData -> {
			// add where
			List<Predicate> lstpredicateWhere = new ArrayList<>();

			// eq company id
			lstpredicateWhere
					.add(criteriaBuilder.equal(root.get(KshmtHdWorkFrame_.kshmtHdWorkFramePK)
							.get(KshmtHdWorkFramePK_.cid), companyId));
			lstpredicateWhere.add(root.get(KshmtHdWorkFrame_.kshmtHdWorkFramePK)
					.get(KshmtHdWorkFramePK_.wdoFrNo).in(splitData));
			
			// set where to SQL
			cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

			resultList.addAll(em.createQuery(cq).getResultList());
		});

		// exclude select
		return resultList.stream().map(category -> toDomain(category))
				.collect(Collectors.toList());
	}

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
	@Override
	public List<WorkdayoffFrame> findByUseAtr(String companyId, int useAtr) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KshmtHdWorkFrame> cq = criteriaBuilder.createQuery(KshmtHdWorkFrame.class);

		// root data
		Root<KshmtHdWorkFrame> root = cq.from(KshmtHdWorkFrame.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// eq company id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KshmtHdWorkFrame_.kshmtHdWorkFramePK).get(KshmtHdWorkFramePK_.cid), companyId));
		// useAtr condition
		lstpredicateWhere.add(criteriaBuilder.equal(root.get(KshmtHdWorkFrame_.useAtr), useAtr));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// creat query
		TypedQuery<KshmtHdWorkFrame> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(category -> toDomain(category)).collect(Collectors.toList());
	}
}
