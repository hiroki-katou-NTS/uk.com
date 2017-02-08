package nts.uk.ctx.core.app.rule.employment.unitprice.command.base;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.ApplySetting;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.Money;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.SettingType;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistory;
import nts.uk.shr.com.primitive.Memo;

@Getter
@Setter
public abstract class UnitPriceHistoryCommandBase {
	/** The id. */
	private String id;

	/** The version. */
	private long version;

	/** The unit price code. */
	private String unitPriceCode;

	/** The unit price name. */
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

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the unit price history
	 */
	public UnitPriceHistory toDomain(String companyCode) {
		UnitPriceHistory unitPriceHistory = new UnitPriceHistory();
		unitPriceHistory.setId(id);
		unitPriceHistory.setVersion(version);
		unitPriceHistory.setCompanyCode(new CompanyCode(companyCode));
		unitPriceHistory.setUnitPriceCode(new UnitPriceCode(unitPriceCode));
		unitPriceHistory.setBudget(new Money(budget));
		// unitPriceHistory.setApplyRange(new MonthRange());
		unitPriceHistory.setFixPaySettingType(SettingType.valueOf(fixPaySettingType));
		unitPriceHistory.setFixPayAtr(ApplySetting.valueOf(fixPayAtr));
		unitPriceHistory.setFixPayAtrMonthly(ApplySetting.valueOf(fixPayAtrMonthly));
		unitPriceHistory.setFixPayAtrDayMonth(ApplySetting.valueOf(fixPayAtrDayMonth));
		unitPriceHistory.setFixPayAtrDaily(ApplySetting.valueOf(fixPayAtrDaily));
		unitPriceHistory.setFixPayAtrHourly(ApplySetting.valueOf(fixPayAtrHourly));
		unitPriceHistory.setMemo(new Memo(memo));

		return unitPriceHistory;
	}
}
