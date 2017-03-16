/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.salarydetail;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItem;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemRepository;

/**
 * The Class JpaSalaryAggregateItemRepository.
 */
@Stateless
public class JpaSalaryAggregateItemRepository implements SalaryAggregateItemRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemRepository#add(nts.uk.ctx.pr.report.dom.salarydetail.
	 * aggregate.SalaryAggregateItem)
	 */
	@Override
	public void add(SalaryAggregateItem aggregateItem) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemRepository#update(nts.uk.ctx.pr.report.dom.
	 * salarydetail.aggregate.SalaryAggregateItem)
	 */
	@Override
	public void update(SalaryAggregateItem aggregateItem) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemRepository#remove(nts.uk.ctx.pr.report.dom.
	 * salarydetail.aggregate.SalaryAggregateItem)
	 */
	@Override
	public void remove(SalaryAggregateItem aggregateItem) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemRepository#findByCode(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public SalaryAggregateItem findByCode(String companyCode, String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
