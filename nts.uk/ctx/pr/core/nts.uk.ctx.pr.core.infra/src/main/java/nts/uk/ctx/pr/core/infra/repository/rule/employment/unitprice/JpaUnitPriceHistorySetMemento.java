/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.repository.rule.employment.unitprice;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.ApplySetting;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.Money;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.SettingType;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistorySetMemento;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceName;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceHist;
import nts.uk.ctx.pr.core.infra.entity.rule.employment.unitprice.QupmtCUnitpriceHistPK;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class JpaUnitPriceHistorySetMemento.
 */
public class JpaUnitPriceHistorySetMemento implements UnitPriceHistorySetMemento {

	/** The type value. */
	protected QupmtCUnitpriceHist typeValue;

	/**
	 * Instantiates a new jpa unit price history set memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaUnitPriceHistorySetMemento(QupmtCUnitpriceHist typeValue) {
		this.typeValue = typeValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistorySetMemento#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		QupmtCUnitpriceHistPK qupmtCUnitpriceHistPK = new QupmtCUnitpriceHistPK();
		qupmtCUnitpriceHistPK.setHistId(id);
		this.typeValue.setQupmtCUnitpriceHistPK(qupmtCUnitpriceHistPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistorySetMemento#setCompanyCode(nts.uk.ctx.core.dom.company.
	 * CompanyCode)
	 */
	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		QupmtCUnitpriceHistPK qupmtCUnitpriceHistPK = this.typeValue.getQupmtCUnitpriceHistPK();
		qupmtCUnitpriceHistPK.setCcd(companyCode.v());
		this.typeValue.setQupmtCUnitpriceHistPK(qupmtCUnitpriceHistPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistorySetMemento#setUnitPriceCode(nts.uk.ctx.pr.core.dom.rule.
	 * employment.unitprice.UnitPriceCode)
	 */
	@Override
	public void setUnitPriceCode(UnitPriceCode unitPriceCode) {
		QupmtCUnitpriceHistPK qupmtCUnitpriceHistPK = this.typeValue.getQupmtCUnitpriceHistPK();
		qupmtCUnitpriceHistPK.setCUnitpriceCd(unitPriceCode.v());
		this.typeValue.setQupmtCUnitpriceHistPK(qupmtCUnitpriceHistPK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistorySetMemento#setApplyRange(nts.uk.ctx.pr.core.dom.insurance
	 * .MonthRange)
	 */
	@Override
	public void setApplyRange(MonthRange applyRange) {
		this.typeValue.setStrYm(applyRange.getStartMonth().v());
		this.typeValue.setEndYm(applyRange.getEndMonth().v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistorySetMemento#setBudget(nts.uk.ctx.pr.core.dom.rule.
	 * employment.unitprice.Money)
	 */
	@Override
	public void setBudget(Money budget) {
		this.typeValue.setBudget(budget.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistorySetMemento#setFixPaySettingType(nts.uk.ctx.pr.core.dom.
	 * rule.employment.unitprice.SettingType)
	 */
	@Override
	public void setFixPaySettingType(SettingType fixPaySettingType) {
		this.typeValue.setFixPaySet(fixPaySettingType.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistorySetMemento#setFixPayAtr(nts.uk.ctx.pr.core.dom.rule.
	 * employment.unitprice.ApplySetting)
	 */
	@Override
	public void setFixPayAtr(ApplySetting fixPayAtr) {
		this.typeValue.setFixPayAtr(fixPayAtr.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistorySetMemento#setFixPayAtrMonthly(nts.uk.ctx.pr.core.dom.
	 * rule.employment.unitprice.ApplySetting)
	 */
	@Override
	public void setFixPayAtrMonthly(ApplySetting fixPayAtrMonthly) {
		this.typeValue.setFixPayAtrMonthly(fixPayAtrMonthly.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistorySetMemento#setFixPayAtrDayMonth(nts.uk.ctx.pr.core.dom.
	 * rule.employment.unitprice.ApplySetting)
	 */
	@Override
	public void setFixPayAtrDayMonth(ApplySetting fixPayAtrDayMonth) {
		this.typeValue.setFixPayAtrDaymonth(fixPayAtrDayMonth.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistorySetMemento#setFixPayAtrDaily(nts.uk.ctx.pr.core.dom.rule.
	 * employment.unitprice.ApplySetting)
	 */
	@Override
	public void setFixPayAtrDaily(ApplySetting fixPayAtrDaily) {
		this.typeValue.setFixPayAtrDaily(fixPayAtrDaily.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistorySetMemento#setFixPayAtrHourly(nts.uk.ctx.pr.core.dom.rule
	 * .employment.unitprice.ApplySetting)
	 */
	@Override
	public void setFixPayAtrHourly(ApplySetting fixPayAtrHourly) {
		this.typeValue.setFixPayAtrHourly(fixPayAtrHourly.value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistorySetMemento#setMemo(nts.uk.shr.com.primitive.Memo)
	 */
	@Override
	public void setMemo(Memo memo) {
		this.typeValue.setMemo(memo.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.rule.employment.unitprice.
	 * UnitPriceHistorySetMemento#setUnitPriceName(nts.uk.ctx.pr.core.dom.rule.
	 * employment.unitprice.UnitPriceName)
	 */
	@Override
	public void setUnitPriceName(UnitPriceName unitPriceName) {
		// Do nothing.
	}

}
