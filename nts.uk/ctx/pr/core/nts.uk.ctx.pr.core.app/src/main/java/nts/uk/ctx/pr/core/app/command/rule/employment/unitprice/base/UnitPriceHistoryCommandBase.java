package nts.uk.ctx.pr.core.app.command.rule.employment.unitprice.base;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistory;

@Getter
@Setter
public abstract class UnitPriceHistoryCommandBase {
	/** The id. */
	private String id;

	/** The unit price code. */
	private String unitPriceCode;

	/** The unit price name. */
	private String unitPriceName;

	/** The start month. */
	private String startMonth;

	/** The end month. */
	private String endMonth;

	/** The budget. */
	private double budget;

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

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the unit price history
	 */
	public UnitPriceHistory toDomain(String companyCode) {
		UnitPriceHistory unitPriceHistory = new UnitPriceHistory();
		return unitPriceHistory;
	}
}
