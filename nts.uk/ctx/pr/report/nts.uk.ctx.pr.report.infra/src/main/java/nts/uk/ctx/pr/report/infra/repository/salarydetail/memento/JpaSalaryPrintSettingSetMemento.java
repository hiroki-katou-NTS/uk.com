/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.infra.repository.salarydetail.memento;

import lombok.Getter;
import nts.uk.ctx.pr.report.dom.salarydetail.printsetting.SalaryPrintSettingSetMemento;
import nts.uk.ctx.pr.report.infra.entity.salarydetail.QlsptPaylstPrintSet;
import nts.uk.ctx.pr.report.infra.util.JpaUtil;

/**
 * The Class JpaSalaryPrintSettingSetMemento.
 */
public class JpaSalaryPrintSettingSetMemento implements SalaryPrintSettingSetMemento {

	/** The entity. */
	@Getter
	private QlsptPaylstPrintSet entity;

	/**
	 * Instantiates a new jpa salary print setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaSalaryPrintSettingSetMemento(QlsptPaylstPrintSet entity) {
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setSumPersonSet(boolean)
	 */
	@Override
	public void setSumPersonSet(boolean sumPersonSet) {
		this.entity.setSumPersonSet(JpaUtil.boolean2Short(sumPersonSet));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setCompanyCode(java.lang.String)
	 */
	@Override
	public void setCompanyCode(String companyCode) {
		this.entity.setCcd(companyCode);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setShowPayment(boolean)
	 */
	@Override
	public void setShowPayment(boolean showPayment) {
		this.entity.setShowPayment(JpaUtil.boolean2Short(showPayment));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setSumMonthPersonSet(boolean)
	 */
	@Override
	public void setSumMonthPersonSet(boolean sumMonthPersonSet) {
		this.entity.setSumMonthPersonSet(JpaUtil.boolean2Short(sumMonthPersonSet));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setSumEachDeprtSet(boolean)
	 */
	@Override
	public void setSumEachDeprtSet(boolean sumEachDeprtSet) {
		this.entity.setSumEachDeprtSet(JpaUtil.boolean2Short(sumEachDeprtSet));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setSumMonthDeprtSet(boolean)
	 */
	@Override
	public void setSumMonthDeprtSet(boolean sumMonthDeprtSet) {
		this.entity.setSumMonthDeprtSet(JpaUtil.boolean2Short(sumMonthDeprtSet));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setSumDepHrchyIndexSet(boolean)
	 */
	@Override
	public void setSumDepHrchyIndexSet(boolean sumDepHrchyIndexSet) {
		this.entity.setSumDepHrchyIndexSet(JpaUtil.boolean2Short(sumDepHrchyIndexSet));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setSumMonthDepHrchySet(boolean)
	 */
	@Override
	public void setSumMonthDepHrchySet(boolean sumMonthDepHrchySet) {
		this.entity.setSumMonthDepHrchySet(JpaUtil.boolean2Short(sumMonthDepHrchySet));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setHrchyIndex1(boolean)
	 */
	@Override
	public void setHrchyIndex1(boolean hrchyIndex) {
		this.entity.setHrchyIndex1(JpaUtil.boolean2Short(hrchyIndex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setHrchyIndex2(boolean)
	 */
	@Override
	public void setHrchyIndex2(boolean hrchyIndex) {
		this.entity.setHrchyIndex2(JpaUtil.boolean2Short(hrchyIndex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setHrchyIndex3(boolean)
	 */
	@Override
	public void setHrchyIndex3(boolean hrchyIndex) {
		this.entity.setHrchyIndex3(JpaUtil.boolean2Short(hrchyIndex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setHrchyIndex4(boolean)
	 */
	@Override
	public void setHrchyIndex4(boolean hrchyIndex) {
		this.entity.setHrchyIndex4(JpaUtil.boolean2Short(hrchyIndex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setHrchyIndex5(boolean)
	 */
	@Override
	public void setHrchyIndex5(boolean hrchyIndex) {
		this.entity.setHrchyIndex5(JpaUtil.boolean2Short(hrchyIndex));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setHrchyIndex6(boolean)
	 */
	@Override
	public void setHrchyIndex6(boolean hrchyIndex) {
		// TODO: khong co trong db.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setHrchyIndex7(boolean)
	 */
	@Override
	public void setHrchyIndex7(boolean hrchyIndex) {
		// TODO: khong co trong db.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setHrchyIndex8(boolean)
	 */
	@Override
	public void setHrchyIndex8(boolean hrchyIndex) {
		// TODO: khong co trong db.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#sethrchyIndex(boolean)
	 */
	@Override
	public void setHrchyIndex9(boolean hrchyIndex) {
		// TODO: khong co trong db.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setTotalSet(boolean)
	 */
	@Override
	public void setTotalSet(boolean totalSet) {
		this.entity.setTotalSet(JpaUtil.boolean2Short(totalSet));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.report.dom.salarydetail.printsetting.
	 * SalaryPrintSettingSetMemento#setMonthTotalSet(boolean)
	 */
	@Override
	public void setMonthTotalSet(boolean monthTotalSet) {
		this.entity.setMonthTotalSet(JpaUtil.boolean2Short(monthTotalSet));
	}
}
