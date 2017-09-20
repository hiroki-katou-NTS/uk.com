/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.workplace;

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
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistory;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfig;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfigPK_;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWkpConfig_;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceHist;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceHistPK_;
import nts.uk.ctx.bs.employee.infra.entity.workplace.BsymtWorkplaceHist_;
import nts.uk.ctx.bs.employee.infra.entity.workplace_old.CwpmtWkpHierarchy_;

/**
 * The Class JpaWorkplaceConfigRepository.
 */
@Stateless
public class JpaWorkplaceConfigRepository extends JpaRepository implements WorkplaceConfigRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository#findAllByCompanyId(java.lang.String)
	 */
	@Override
	public WorkplaceConfig findAllByCompanyId(String companyId) {
		List<WorkplaceConfigHistory> lstWorkplaceConfigHistory = new ArrayList<>();
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<BsymtWkpConfig> cq = criteriaBuilder.createQuery(BsymtWkpConfig.class);
		Root<BsymtWkpConfig> root = cq.from(BsymtWkpConfig.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(BsymtWkpConfig_.bsymtWkpConfigPK).get(BsymtWkpConfigPK_.cid), companyId));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// exclude select
		lstWorkplaceConfigHistory = em.createQuery(cq).getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
		return new WorkplaceConfig(new JpaWorkplaceConfigGetMemento(companyId, lstWorkplaceConfigHistory));
	}

	/**
	 * To domain.
	 *
	 * @param item the item
	 * @return the workplace config history
	 */
	private WorkplaceConfigHistory toDomain(BsymtWkpConfig item) {
		return new WorkplaceConfigHistory(new JpaWorkplaceConfigHistoryGetMemento(item));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository#findLastestByCompanyId(java.lang.String)
	 */
	@Override
	public Optional<WorkplaceConfig> findLastestByCompanyId(String companyId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<BsymtWorkplaceHist> cq = criteriaBuilder.createQuery(BsymtWorkplaceHist.class);
		Root<BsymtWorkplaceHist> root = cq.from(BsymtWorkplaceHist.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(BsymtWorkplaceHist_.bsymtWorkplaceHistPK).get(BsymtWorkplaceHistPK_.cid), companyId));

		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));
		cq.orderBy(criteriaBuilder.desc(root.get(BsymtWorkplaceHist_.strD)));
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository#add(nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig)
	 */
	@Override
	public String add(WorkplaceConfig workplaceConfig) {
		this.commandProxy().insert(this.toEntity(workplaceConfig));
		return workplaceConfig.getWkpConfigHistory().get(0).getHistoryId();
	}
	
	/**
	 * To entity.
	 *
	 * @param workplaceConfig the workplace config
	 * @return the bsymt wkp config
	 */
	private BsymtWkpConfig toEntity(WorkplaceConfig workplaceConfig) {
		BsymtWkpConfig entity = new BsymtWkpConfig();
		workplaceConfig.saveToMemento(new JpaWorkplaceConfigSetMemento(entity));
		return entity;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository#update(nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig)
	 */
	@Override
	public void update(WorkplaceConfig wkpConfig,GeneralDate endĐate) {
		BsymtWkpConfig entity = this.toEntity(wkpConfig);
		//update end date
		entity.setStrD(endĐate);
		this.commandProxy().update(entity);
	}

	@Override
	public Optional<WorkplaceConfig> findByHistId(String companyId,String prevHistId) {
		List<WorkplaceConfigHistory> lstWorkplaceConfigHistory = new ArrayList<>();
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<BsymtWkpConfig> cq = criteriaBuilder.createQuery(BsymtWkpConfig.class);
		Root<BsymtWkpConfig> root = cq.from(BsymtWkpConfig.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(BsymtWkpConfig_.bsymtWkpConfigPK).get(BsymtWkpConfigPK_.cid), companyId));
		
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(BsymtWkpConfig_.bsymtWkpConfigPK).get(BsymtWkpConfigPK_.historyId), prevHistId));
		
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// exclude select
		lstWorkplaceConfigHistory = em.createQuery(cq).getResultList().stream().map(item -> this.toDomain(item))
				.collect(Collectors.toList());
		return Optional.of(new WorkplaceConfig(new JpaWorkplaceConfigGetMemento(companyId, lstWorkplaceConfigHistory)));
	}
}
