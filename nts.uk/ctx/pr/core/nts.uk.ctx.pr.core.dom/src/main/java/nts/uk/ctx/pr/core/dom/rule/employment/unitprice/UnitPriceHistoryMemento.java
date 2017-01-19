/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.rule.employment.unitprice;

import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Interface UnitPriceHistoryMemento.
 */
public interface UnitPriceHistoryMemento {

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	String getId();

	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	CompanyCode getCompanyCode();

	/**
	 * Gets the unit price code.
	 *
	 * @return the unit price code
	 */
	UnitPriceCode getUnitPriceCode();

	/**
	 * Gets the apply range.
	 *
	 * @return the apply range
	 */
	MonthRange getApplyRange();

	/**
	 * Gets the budget.
	 *
	 * @return the budget
	 */
	Money getBudget();

	/**
	 * Gets the fix pay setting type.
	 *
	 * @return the fix pay setting type
	 */
	SettingType getFixPaySettingType();

	/**
	 * Gets the fix pay atr.
	 *
	 * @return the fix pay atr
	 */
	ApplySetting getFixPayAtr();

	/**
	 * Gets the fix pay atr monthly.
	 *
	 * @return the fix pay atr monthly
	 */
	ApplySetting getFixPayAtrMonthly();

	/**
	 * Gets the fix pay atr day month.
	 *
	 * @return the fix pay atr day month
	 */
	ApplySetting getFixPayAtrDayMonth();

	/**
	 * Gets the fix pay atr daily.
	 *
	 * @return the fix pay atr daily
	 */
	ApplySetting getFixPayAtrDaily();

	/**
	 * Gets the fix pay atr hourly.
	 *
	 * @return the fix pay atr hourly
	 */
	ApplySetting getFixPayAtrHourly();

	/**
	 * Gets the memo.
	 *
	 * @return the memo
	 */
	Memo getMemo();

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	Long getVersion();

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	void setId(String id);

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the new company code
	 */
	void setCompanyCode(CompanyCode companyCode);

	/**
	 * Sets the unit price code.
	 *
	 * @param unitPriceCode the new unit price code
	 */
	void setUnitPriceCode(UnitPriceCode unitPriceCode);

	/**
	 * Sets the apply range.
	 *
	 * @param applyRange the new apply range
	 */
	void setApplyRange(MonthRange applyRange);

	/**
	 * Sets the budget.
	 *
	 * @param budget the new budget
	 */
	void setBudget(Money budget);

	/**
	 * Sets the fix pay setting type.
	 *
	 * @param fixPaySettingType the new fix pay setting type
	 */
	void setFixPaySettingType(SettingType fixPaySettingType);

	/**
	 * Sets the fix pay atr.
	 *
	 * @param fixPayAtr the new fix pay atr
	 */
	void setFixPayAtr(ApplySetting fixPayAtr);

	/**
	 * Sets the fix pay atr monthly.
	 *
	 * @param fixPayAtrMonthly the new fix pay atr monthly
	 */
	void setFixPayAtrMonthly(ApplySetting fixPayAtrMonthly);

	/**
	 * Sets the fix pay atr day month.
	 *
	 * @param fixPayAtrDayMonth the new fix pay atr day month
	 */
	void setFixPayAtrDayMonth(ApplySetting fixPayAtrDayMonth);

	/**
	 * Sets the fix pay atr daily.
	 *
	 * @param fixPayAtrDaily the new fix pay atr daily
	 */
	void setFixPayAtrDaily(ApplySetting fixPayAtrDaily);

	/**
	 * Sets the fix pay atr hourly.
	 *
	 * @param fixPayAtrHourly the new fix pay atr hourly
	 */
	void setFixPayAtrHourly(ApplySetting fixPayAtrHourly);

	/**
	 * Sets the memo.
	 *
	 * @param memo the new memo
	 */
	void setMemo(Memo memo);

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	void setVersion(Long version);

}
