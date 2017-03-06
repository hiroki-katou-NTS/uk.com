/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.wageledger;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItem;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreHead;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreHeadPK;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreHeadPK_;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreHead_;

/**
 * The Class JpaWLAggregateItemRepository.
 */
@Stateless
public class JpaWLAggregateItemRepository extends JpaRepository implements WLAggregateItemRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository
	 * #create(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItem)
	 */
	@Override
	public void create(WLAggregateItem aggregateItem) {
		// Convert domain to entity.
		QlsptLedgerAggreHead entity = new QlsptLedgerAggreHead();
		aggregateItem.saveToMemento(new JpaWLAggregateItemSetMemento(entity));
		
		// Insert to db.
		this.commandProxy().insert(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository
	 * #update(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItem)
	 */
	@Override
	public void update(WLAggregateItem aggregateItem) {
		// Find entity.
		QlsptLedgerAggreHeadPK pk = new QlsptLedgerAggreHeadPK(
				aggregateItem.getCompanyCode().v(),
				aggregateItem.getPaymentType().value,
				aggregateItem.getCode().v(),
				aggregateItem.getCategory().value);
		Optional<QlsptLedgerAggreHead> optinalEntity = this.queryProxy().find(pk, QlsptLedgerAggreHead.class);
		if (optinalEntity.isPresent()) {
			QlsptLedgerAggreHead entity = optinalEntity.get();
			
			// Update entity with new domain.
			aggregateItem.saveToMemento(new JpaWLAggregateItemSetMemento(entity));
			this.commandProxy().update(entity);
		}
		throw new RuntimeException("Cannot update entity not exist!");
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository
	 * #remove(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode)
	 */
	@Override
	public void remove(WLAggregateItemCode code, CompanyCode companyCode) {
		EntityManager em = this.getEntityManager();
		
		// Create criteria buider.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaDelete<QlsptLedgerAggreHead> cd = cb.createCriteriaDelete(QlsptLedgerAggreHead.class);
		Root<QlsptLedgerAggreHead> root = cd.from(QlsptLedgerAggreHead.class);
		Path<QlsptLedgerAggreHeadPK> pkPath = root.get(QlsptLedgerAggreHead_.qlsptLedgerAggreHeadPK);
		cd.where(cb.equal(pkPath.get(QlsptLedgerAggreHeadPK_.aggregateCd), code.v()),
				cb.equal(pkPath.get(QlsptLedgerAggreHeadPK_.ccd), companyCode.v()));
		
		// Excute.
		em.createQuery(cd).executeUpdate();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository
	 * #find(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode, nts.uk.ctx.pr.report.dom.company.CompanyCode)
	 */
	@Override
	public WLAggregateItem find(WLAggregateItemCode code, CompanyCode companyCode) {
		EntityManager em = this.getEntityManager();
		
		// Create criteria buider.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QlsptLedgerAggreHead> cq = cb.createQuery(QlsptLedgerAggreHead.class);
		Root<QlsptLedgerAggreHead> root = cq.from(QlsptLedgerAggreHead.class);
		Path<QlsptLedgerAggreHeadPK> pkPath = root.get(QlsptLedgerAggreHead_.qlsptLedgerAggreHeadPK);
		cq.where(cb.equal(pkPath.get(QlsptLedgerAggreHeadPK_.aggregateCd), code.v()),
				cb.equal(pkPath.get(QlsptLedgerAggreHeadPK_.ccd), companyCode.v()));
		
		// Query.
		QlsptLedgerAggreHead result = em.createQuery(cq).getSingleResult();
		
		// Return.
		return new WLAggregateItem(new JpaWLAggregateItemGetMemento(result));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository
	 * #isExist(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode)
	 */
	@Override
	public boolean isExist(WLAggregateItemCode code, CompanyCode companyCode) {
		EntityManager em = this.getEntityManager();
		
		// Create criteria buider.
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<QlsptLedgerAggreHead> cq = cb.createQuery(QlsptLedgerAggreHead.class);
		Root<QlsptLedgerAggreHead> root = cq.from(QlsptLedgerAggreHead.class);
		Path<QlsptLedgerAggreHeadPK> pkPath = root.get(QlsptLedgerAggreHead_.qlsptLedgerAggreHeadPK);
		cq.where(cb.equal(pkPath.get(QlsptLedgerAggreHeadPK_.aggregateCd), code.v()),
				cb.equal(pkPath.get(QlsptLedgerAggreHeadPK_.ccd), companyCode.v()));
		
		// Query.
		QlsptLedgerAggreHead result;
		try {
			result = em.createQuery(cq).getSingleResult();
		} catch (NoResultException e) {
			return false;
		}
		
		return result != null;
	}
	
}
