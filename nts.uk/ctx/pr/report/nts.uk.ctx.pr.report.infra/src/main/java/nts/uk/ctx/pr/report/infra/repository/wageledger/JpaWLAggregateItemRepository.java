/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.wageledger;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItem;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository;

/**
 * The Class JpaWLAggregateItemRepository.
 */
@Stateless
public class JpaWLAggregateItemRepository implements WLAggregateItemRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository
	 * #create(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItem)
	 */
	@Override
	public void create(WLAggregateItem aggregateItem) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository
	 * #update(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItem)
	 */
	@Override
	public void update(WLAggregateItem aggregateItem) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository
	 * #remove(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode)
	 */
	@Override
	public void remove(WLAggregateItemCode code) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository
	 * #find(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode, nts.uk.ctx.pr.report.dom.company.CompanyCode)
	 */
	@Override
	public WLAggregateItem find(WLAggregateItemCode code, CompanyCode companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository
	 * #isExist(nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode)
	 */
	@Override
	public boolean isExist(WLAggregateItemCode code) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
