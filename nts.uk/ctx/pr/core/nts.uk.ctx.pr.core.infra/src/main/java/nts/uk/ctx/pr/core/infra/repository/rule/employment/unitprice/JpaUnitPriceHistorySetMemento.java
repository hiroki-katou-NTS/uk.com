/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
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
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaUnitPriceHistorySetMemento implements UnitPriceHistorySetMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaUnitPriceHistorySetMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCompanyCode(CompanyCode companyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUnitPriceCode(UnitPriceCode unitPriceCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUnitPriceName(UnitPriceName unitPriceName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setApplyRange(MonthRange applyRange) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBudget(Money budget) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFixPaySettingType(SettingType fixPaySettingType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFixPayAtr(ApplySetting fixPayAtr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFixPayAtrMonthly(ApplySetting fixPayAtrMonthly) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFixPayAtrDayMonth(ApplySetting fixPayAtrDayMonth) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFixPayAtrDaily(ApplySetting fixPayAtrDaily) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFixPayAtrHourly(ApplySetting fixPayAtrHourly) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMemo(Memo memo) {
		// TODO Auto-generated method stub

	}

}
