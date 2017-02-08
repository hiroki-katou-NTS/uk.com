package nts.uk.ctx.pr.core.app.rule.employment.unitprice.find;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.ApplySetting;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.SettingType;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistory;

@Builder
@Getter
public class UnitPriceHistoryDto {
	/** The id. */
	private String id;

	/** The version. */
	private long version;

	/** The company code. */
	private String companyCode;

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

	/**
	 * From domain.
	 *
	 * @param domain
	 *            the domain
	 * @return the unit price history dto
	 */
	public static UnitPriceHistoryDto fromDomain(UnitPriceHistory domain) {
		return new UnitPriceHistoryDto(domain.getId(), domain.getVersion(), domain.getCompanyCode().v(),
				domain.getUnitPriceCode().v(), domain.getUnitPriceName().v(),
				domain.getApplyRange().getStartMonth().toString(), domain.getApplyRange().getEndMonth().toString(),
				domain.getBudget().v(), domain.getFixPaySettingType(), domain.getFixPayAtr(),
				domain.getFixPayAtrMonthly(), domain.getFixPayAtrDayMonth(), domain.getFixPayAtrDaily(),
				domain.getFixPayAtrHourly(), domain.getMemo().v());
	}

}
