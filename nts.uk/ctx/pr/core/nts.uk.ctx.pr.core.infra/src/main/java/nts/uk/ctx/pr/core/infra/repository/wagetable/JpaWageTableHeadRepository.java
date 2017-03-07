/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.wagetable;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHead;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableHead;
import nts.uk.ctx.pr.core.infra.entity.wagetable.QwtmtWagetableHeadPK;

/**
 * The Class JpaWageTableHeadRepository.
 */
@Stateless
public class JpaWageTableHeadRepository extends JpaRepository implements WageTableHeadRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository#add(nts.uk.ctx.
	 * pr.core.dom.wagetable.WageTableHead)
	 */
	@Override
	public void add(WageTableHead wageTableHead) {
		QwtmtWagetableHead entity = new QwtmtWagetableHead();
		wageTableHead.saveToMemento(new JpaWageTableHeadSetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository#update(nts.uk.
	 * ctx.pr.core.dom.wagetable.WageTableHead)
	 */
	@Override
	public void update(WageTableHead wageTableHead) {
		QwtmtWagetableHead entity = new QwtmtWagetableHead();
		wageTableHead.saveToMemento(new JpaWageTableHeadSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository#remove(nts.uk.
	 * ctx.core.dom.company.CompanyCode,
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableCode)
	 */
	@Override
	public void remove(CompanyCode companyCode, WageTableCode wageTableCode) {
		this.commandProxy().remove(QwtmtWagetableHead.class,
				new QwtmtWagetableHeadPK(companyCode.v(), wageTableCode.v()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository#findAll(nts.uk.
	 * ctx.core.dom.company.CompanyCode)
	 */
	@Override
	public List<WageTableHead> findAll(CompanyCode companyCode) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository#findById(nts.uk.
	 * ctx.core.dom.company.CompanyCode, java.lang.String)
	 */
	@Override
	public Optional<WageTableHead> findById(CompanyCode companyCode, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository#isDuplicateCode(
	 * nts.uk.ctx.core.dom.company.CompanyCode,
	 * nts.uk.ctx.pr.core.dom.wagetable.WageTableCode)
	 */
	@Override
	public boolean isDuplicateCode(CompanyCode companyCode, WageTableCode code) {
		// TODO Auto-generated method stub
		return false;
	}

}
