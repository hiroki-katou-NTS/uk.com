/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.rule.employment.unitprice;

import lombok.Data;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.AggregateRoot;
import nts.gul.text.StringUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class UnitPriceHistory.
 */
// TODO: @Data -> @Getter
@Data
public class UnitPriceHistory extends AggregateRoot {

	/** The id. */
	// UuId
	private String id;

	/** The company code. */
	private CompanyCode companyCode;

	/** The unit price code. */
	private UnitPriceCode unitPriceCode;

	/** The unit price name. */
	private UnitPriceName unitPriceName;

	/** The apply range. */
	private MonthRange applyRange;

	/** The budget. */
	private Money budget;

	/** The fix pay setting type. */
	private SettingType fixPaySettingType;

	/** The fix pay atr. */
	private ApplySetting fixPayAtr;

	/** The fix pay atr monthly. */
	private ApplySetting fixPayAtrMonthly;

	/** The fix pay atr day month. */
	private ApplySetting fixPayAtrDayMonth;

	/** The fix pay atr daily. */
	private ApplySetting fixPayAtrDaily;

	/** The fix pay atr hourly. */
	private ApplySetting fixPayAtrHourly;

	/** The memo. */
	private Memo memo;

	/**
	 * Instantiates a new unit price history.
	 */
	public UnitPriceHistory() {
		super();
	}

	/**
	 * Instantiates a new unit price history.
	 *
	 * @param id
	 *            the id
	 * @param companyCode
	 *            the company code
	 * @param unitPriceCode
	 *            the unit price code
	 * @param unitPriceName
	 *            the unit price name
	 * @param applyRange
	 *            the apply range
	 * @param budget
	 *            the budget
	 * @param fixPaySettingType
	 *            the fix pay setting type
	 * @param fixPayAtr
	 *            the fix pay atr
	 * @param fixPayAtrMonthly
	 *            the fix pay atr monthly
	 * @param fixPayAtrDayMonth
	 *            the fix pay atr day month
	 * @param fixPayAtrDaily
	 *            the fix pay atr daily
	 * @param fixPayAtrHourly
	 *            the fix pay atr hourly
	 * @param memo
	 *            the memo
	 */
	public UnitPriceHistory(String id, CompanyCode companyCode, UnitPriceCode unitPriceCode,
			UnitPriceName unitPriceName, MonthRange applyRange, Money budget, SettingType fixPaySettingType,
			ApplySetting fixPayAtr, ApplySetting fixPayAtrMonthly, ApplySetting fixPayAtrDayMonth,
			ApplySetting fixPayAtrDaily, ApplySetting fixPayAtrHourly, Memo memo) {
		super();

		// Validate required item
		if (StringUtil.isNullOrEmpty(unitPriceCode.v(), true) || StringUtil.isNullOrEmpty(unitPriceName.v(), true)
				|| applyRange == null || budget == null) {
			throw new BusinessException("ER001");
		}

		// TODO: Check consistency date range.
		// History after start date and time exists
		// throw new BusinessException("ER010");

		this.id = id;
		this.companyCode = companyCode;
		this.unitPriceCode = unitPriceCode;
		this.unitPriceName = unitPriceName;
		this.applyRange = applyRange;
		this.budget = budget;
		this.fixPaySettingType = fixPaySettingType;
		this.fixPayAtr = fixPayAtr;
		this.fixPayAtrMonthly = fixPayAtrMonthly;
		this.fixPayAtrDayMonth = fixPayAtrDayMonth;
		this.fixPayAtrDaily = fixPayAtrDaily;
		this.fixPayAtrHourly = fixPayAtrHourly;
		this.memo = memo;
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new unit price history.
	 *
	 * @param memento
	 *            the memento
	 */
	public UnitPriceHistory(UnitPriceHistoryMemento memento) {
		this.id = memento.getId();
		this.companyCode = memento.getCompanyCode();
		this.unitPriceCode = memento.getUnitPriceCode();
		this.applyRange = memento.getApplyRange();
		this.budget = memento.getBudget();
		this.fixPaySettingType = memento.getFixPaySettingType();
		this.fixPayAtr = memento.getFixPayAtr();
		this.fixPayAtrMonthly = memento.getFixPayAtrMonthly();
		this.fixPayAtrDayMonth = memento.getFixPayAtrDayMonth();
		this.fixPayAtrDaily = memento.getFixPayAtrDaily();
		this.fixPayAtrHourly = memento.getFixPayAtrHourly();
		this.memo = memento.getMemo();
		this.setVersion(memento.getVersion());
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(UnitPriceHistoryMemento memento) {
		memento.setId(this.id);
		memento.setCompanyCode(this.companyCode);
		memento.setUnitPriceCode(this.unitPriceCode);
		memento.setApplyRange(this.applyRange);
		memento.setBudget(this.budget);
		memento.setFixPaySettingType(this.fixPaySettingType);
		memento.setFixPayAtr(this.fixPayAtr);
		memento.setFixPayAtrMonthly(this.fixPayAtrMonthly);
		memento.setFixPayAtrDayMonth(this.fixPayAtrDayMonth);
		memento.setFixPayAtrDaily(this.fixPayAtrDaily);
		memento.setFixPayAtrHourly(this.fixPayAtrHourly);
		memento.setMemo(this.memo);
		memento.setVersion(this.getVersion());
	}

}
