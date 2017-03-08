/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.wageledger;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItem;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLItemSubject;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreHead;
import nts.uk.ctx.pr.report.infra.entity.wageledger.QlsptLedgerAggreHeadPK;
import nts.uk.ctx.pr.report.infra.repository.wageledger.memento.JpaWLAggregateItemGetMemento;
import nts.uk.ctx.pr.report.infra.repository.wageledger.memento.JpaWLAggregateItemSetMemento;
import nts.uk.ctx.pr.report.infra.repository.wageledger.memento.JpaWLItemSubjectSetMemento;

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
		QlsptLedgerAggreHeadPK pk = new QlsptLedgerAggreHeadPK();
		aggregateItem.getSubject().saveToMemento(new JpaWLItemSubjectSetMemento(pk));
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
	public void remove(WLItemSubject subject) {
		QlsptLedgerAggreHeadPK pk = new QlsptLedgerAggreHeadPK();
		subject.saveToMemento(new JpaWLItemSubjectSetMemento(pk));
		this.commandProxy().remove(QlsptLedgerAggreHead.class, pk);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository
	 * #find(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode, nts.uk.ctx.pr.report.dom.company.CompanyCode)
	 */
	@Override
	public WLAggregateItem findByCode(WLItemSubject subject) {
		// Find entity by pk.
		QlsptLedgerAggreHeadPK pk = new QlsptLedgerAggreHeadPK();
		subject.saveToMemento(new JpaWLItemSubjectSetMemento(pk));
		Optional<QlsptLedgerAggreHead> optionalEntity = this.queryProxy().find(pk, QlsptLedgerAggreHead.class);
		
		// Check entity.
		if (optionalEntity.isPresent()) {
			return new WLAggregateItem(new JpaWLAggregateItemGetMemento(optionalEntity.get()));
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository
	 * #isExist(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode)
	 */
	@Override
	public boolean isExist(WLItemSubject subject) {
		// Find entity by pk.
		QlsptLedgerAggreHeadPK pk = new QlsptLedgerAggreHeadPK();
		subject.saveToMemento(new JpaWLItemSubjectSetMemento(pk));
		Optional<QlsptLedgerAggreHead> optionalEntity = this.queryProxy().find(pk, QlsptLedgerAggreHead.class);
		
		// Return
		return optionalEntity.isPresent();
		
	}
	
}
