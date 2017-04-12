/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.salarydetail.memento;

import lombok.Getter;
import nts.uk.ctx.pr.report.dom.salarydetail.SalaryOutputDistinction;
import nts.uk.ctx.pr.report.dom.salarydetail.printsetting.SalaryPrintSettingGetMemento;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstPrintSet;

/**
 * The Class JpaSalaryPrintSettingGetMemento.
 */
public class JpaSalaryPrintSettingGetMemento implements SalaryPrintSettingGetMemento {

	/** The entity. */
	@Getter
	private QlsptPaylstPrintSet entity;

	/**
	 * Instantiates a new jpa salary print setting get memento.
	 *
	 * @param entity
	 *            the entity
	 */
	public JpaSalaryPrintSettingGetMemento(QlsptPaylstPrintSet entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.aggregate.
	 * SalaryAggregateItemGetMemento#getCompanyCode()
	 */
	@Override
	public String getCompanyCode() {
		return this.entity.getCcd();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getShowPayment()
	 */
	@Override
	public Boolean getShowPayment() {
		return this.entity.getShowPayment() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getSumPersonSet()
	 */
	@Override
	public Boolean getSumPersonSet() {
		return this.entity.getSumPersonSet() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getSumMonthPersonSet()
	 */
	@Override
	public Boolean getSumMonthPersonSet() {
		return this.entity.getSumMonthPersonSet() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getSumEachDeprtSet()
	 */
	@Override
	public Boolean getSumEachDeprtSet() {
		return this.entity.getSumEachDeprtSet() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getSumMonthDeprtSet()
	 */
	@Override
	public Boolean getSumMonthDeprtSet() {
		return this.entity.getSumMonthDeprtSet() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getSumDepHrchyIndexSet()
	 */
	@Override
	public Boolean getSumDepHrchyIndexSet() {
		return this.entity.getSumDepHrchyIndexSet() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getSumMonthDepHrchySet()
	 */
	@Override
	public Boolean getSumMonthDepHrchySet() {
		return this.entity.getSumMonthDepHrchySet() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getHrchyIndex1()
	 */
	@Override
	public Boolean getHrchyIndex1() {
		return this.entity.getHrchyIndex1() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getHrchyIndex2()
	 */
	@Override
	public Boolean getHrchyIndex2() {
		return this.entity.getHrchyIndex2() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getHrchyIndex3()
	 */
	@Override
	public Boolean getHrchyIndex3() {
		return this.entity.getHrchyIndex3() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getHrchyIndex4()
	 */
	@Override
	public Boolean getHrchyIndex4() {
		return this.entity.getHrchyIndex4() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getHrchyIndex5()
	 */
	@Override
	public Boolean getHrchyIndex5() {
		return this.entity.getHrchyIndex5() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getHrchyIndex6()
	 */
	@Override
	public Boolean getHrchyIndex6() {
		// TODO Khong co trong db.
		return this.entity.getHrchyIndex5() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getHrchyIndex7()
	 */
	@Override
	public Boolean getHrchyIndex7() {
		// TODO Khong co trong db.
		return this.entity.getHrchyIndex5() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getHrchyIndex8()
	 */
	@Override
	public Boolean getHrchyIndex8() {
		// TODO Khong co trong db.
		return this.entity.getHrchyIndex5() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getHrchyIndex9()
	 */
	@Override
	public Boolean getHrchyIndex9() {
		// TODO Khong co trong db.
		return this.entity.getHrchyIndex5() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getTotalSet()
	 */
	@Override
	public Boolean getTotalSet() {
		return this.entity.getTotalSet() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getMonthTotalSet()
	 */
	@Override
	public Boolean getMonthTotalSet() {
		return this.entity.getMonthTotalSet() == 1 ? true : false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingGetMemento#getOutputDistinction()
	 */
	@Override
	public SalaryOutputDistinction getOutputDistinction() {
		// TODO Khong co trong db.
		return SalaryOutputDistinction.Hourly;
	}

}
