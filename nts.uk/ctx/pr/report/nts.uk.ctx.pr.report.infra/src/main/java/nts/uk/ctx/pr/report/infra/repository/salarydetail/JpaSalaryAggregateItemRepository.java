/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.salarydetail;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItem;
import nts.uk.ctx.pr.report.dom.salarydetail.aggregate.SalaryAggregateItemRepository;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstAggreHead;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstAggreHeadPK;
import nts.uk.ctx.pr.report.infra.repository.salarydetail.memento.JpaSalaryAggregateItemGetMemento;
import nts.uk.ctx.pr.report.infra.repository.salarydetail.memento.JpaSalaryAggregateItemSetMemento;

/**
 * The Class JpaSalaryAggregateItemRepository.
 */
@Stateless
public class JpaSalaryAggregateItemRepository extends JpaRepository implements SalaryAggregateItemRepository {

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
		this.commandProxy().update(this.toEntity(aggregateItem));
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

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @return the salary aggregate item
	 */
	private SalaryAggregateItem toDomain(QlsptPaylstAggreHead entity) {
		return new SalaryAggregateItem(new JpaSalaryAggregateItemGetMemento(entity));
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the qlspt paylst aggre head
	 */
	private QlsptPaylstAggreHead toEntity(SalaryAggregateItem domain) {
		QlsptPaylstAggreHead entity = new QlsptPaylstAggreHead();
		domain.saveToMemento(new JpaSalaryAggregateItemSetMemento(entity));
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemRepository#findByCode(java.lang.String,
	 * java.lang.String, int)
	 */
	@Override
	public Optional<SalaryAggregateItem> findByCode(String companyCode, String aggregateItemCode,
		int categoryCode) {
		return this.queryProxy()
			.find(new QlsptPaylstAggreHeadPK(companyCode, aggregateItemCode, categoryCode),
				QlsptPaylstAggreHead.class)
			.map(c -> this.toDomain(c));
	}

}
