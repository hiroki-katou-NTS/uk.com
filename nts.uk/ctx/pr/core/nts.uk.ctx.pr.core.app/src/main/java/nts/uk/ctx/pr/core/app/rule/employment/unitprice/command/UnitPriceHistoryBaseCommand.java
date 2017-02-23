/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.rule.employment.unitprice.command;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class UnitPriceHistoryBaseCommand.
 */
@Getter
@Setter
public abstract class UnitPriceHistoryBaseCommand {

	/** The id. */
	private String id;

	/** The unit price code. */
	private String unitPriceCode;

	private String unitPriceName;

	/** The start month. */
	private String startMonth;

	/** The end month. */
	private String endMonth;

	/** The budget. */
	private BigDecimal budget;

	/** The fix pay setting type. */
	private String fixPaySettingType;

	/** The fix pay atr. */
	private String fixPayAtr;

	/** The fix pay atr monthly. */
	private String fixPayAtrMonthly;

	/** The fix pay atr day month. */
	private String fixPayAtrDayMonth;

	/** The fix pay atr daily. */
	private String fixPayAtrDaily;

	/** The fix pay atr hourly. */
	private String fixPayAtrHourly;

	/** The memo. */
	private String memo;
}
