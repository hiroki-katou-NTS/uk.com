/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.rule.employment.unitprice;

import lombok.Getter;
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

}
