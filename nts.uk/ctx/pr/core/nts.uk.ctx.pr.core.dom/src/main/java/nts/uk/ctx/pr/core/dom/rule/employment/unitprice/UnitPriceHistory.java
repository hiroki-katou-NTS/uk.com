/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.rule.employment.unitprice;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class UnitPriceHistory.
 */
@Getter
public class UnitPriceHistory extends AggregateRoot {

	/** The id. */
	// UuId
	private String id;

	/** The company code. */
	private CompanyCode companyCode;

	/** The unit price code. */
	private UnitPriceCode unitPriceCode;

	/** The unit price name. */
	@Setter
	private UnitPriceName unitPriceName;

	/** The apply range. */
	@Setter
	private MonthRange applyRange;

	/** The budget. */
	@Setter
	private Money budget;

	/** The fix pay setting type. */
	@Setter
	private SettingType fixPaySettingType;

	/** The fix pay atr. */
	@Setter
	private ApplySetting fixPayAtr;

	/** The fix pay atr monthly. */
	@Setter
	private ApplySetting fixPayAtrMonthly;

	/** The fix pay atr day month. */
	@Setter
	private ApplySetting fixPayAtrDayMonth;

	/** The fix pay atr daily. */
	@Setter
	private ApplySetting fixPayAtrDaily;

	/** The fix pay atr hourly. */
	@Setter
	private ApplySetting fixPayAtrHourly;

	/** The memo. */
	@Setter
	private Memo memo;

	/**
	 * Validate.
	 *
	 * @param service
	 *            the service
	 */
	public void validate(UnitPriceHistoryService service) {
		// Validate required item
		service.validateRequiredItem(this);
		// if (StringUtil.isNullOrEmpty(unitPriceCode.v(), true) ||
		// StringUtil.isNullOrEmpty(unitPriceName.v(), true)
		// || applyRange == null || budget == null) {
		// throw new BusinessException("ER001");
		// }

		// Check consistency date range.
		service.validateDateRange(this);
		// History after start date and time exists
		// throw new BusinessException("ER010");
	}

	// =================== Memento State Support Method ===================
	/**
	 * Instantiates a new unit price history.
	 *
	 * @param memento
	 *            the memento
	 */
	public UnitPriceHistory(UnitPriceHistoryGetMemento memento) {
		this.id = memento.getId();
		this.companyCode = memento.getCompanyCode();
		this.unitPriceCode = memento.getUnitPriceCode();
		this.unitPriceName = memento.getUnitPriceName();
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
	public void saveToMemento(UnitPriceHistorySetMemento memento) {
		memento.setId(this.id);
		memento.setCompanyCode(this.companyCode);
		memento.setUnitPriceCode(this.unitPriceCode);
		memento.setUnitPriceName(this.unitPriceName);
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
