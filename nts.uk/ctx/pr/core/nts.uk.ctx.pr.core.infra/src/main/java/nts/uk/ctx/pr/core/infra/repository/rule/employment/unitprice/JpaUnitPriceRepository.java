/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.rule.employment.unitprice;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPrice;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository;

/**
 * The Class JpaUnitPriceRepository.
 */
@Stateless
public class JpaUnitPriceRepository extends JpaRepository implements UnitPriceRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository#add(
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPrice)
	 */
	@Override
	public void add(UnitPrice unitPrice) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository#
	 * update(nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPrice)
	 */
	@Override
	public void update(UnitPrice unitPrice) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository#
	 * remove(java.lang.String, java.lang.Long)
	 */
	@Override
	public void remove(String id, Long version) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository#
	 * findAll(int)
	 */
	@Override
	public List<UnitPrice> findAll(String companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository#
	 * findById(java.lang.String)
	 */
	@Override
	public UnitPrice findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository#
	 * isDuplicateCode(nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceCode)
	 */
	@Override
	public boolean isDuplicateCode(UnitPriceCode code) {
		// TODO Auto-generated method stub
		return false;
	}

}
