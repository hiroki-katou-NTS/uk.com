package nts.uk.ctx.pr.core.app.find.rule.employment.unitprice;

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.ApplySetting;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.SettingType;

@Builder
@Getter
public class UnitPriceHistoryDto {
	/** The id. */
	private String id;

	/** The company code. */
	private CompanyCode companyCode;

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
	private String memo;

}
