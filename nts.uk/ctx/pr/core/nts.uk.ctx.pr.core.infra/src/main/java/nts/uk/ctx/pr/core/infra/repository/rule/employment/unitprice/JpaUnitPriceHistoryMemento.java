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
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistoryMemento;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class JpaAggrSchemaMemento.
 */
public class JpaUnitPriceHistoryMemento implements UnitPriceHistoryMemento {

	// TODO: Object -> entity class.
	protected Object typeValue;

	/**
	 * Instantiates a new jpa aggr schema memento.
	 *
	 * @param typeValue
	 *            the type value
	 */
	public JpaUnitPriceHistoryMemento(Object typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CompanyCode getCompanyCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnitPriceCode getUnitPriceCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MonthRange getApplyRange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Money getBudget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SettingType getFixPaySettingType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplySetting getFixPayAtr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplySetting getFixPayAtrMonthly() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplySetting getFixPayAtrDayMonth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplySetting getFixPayAtrDaily() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplySetting getFixPayAtrHourly() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Memo getMemo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getVersion() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public void setVersion(Long version) {
		// TODO Auto-generated method stub
		
	}

}
