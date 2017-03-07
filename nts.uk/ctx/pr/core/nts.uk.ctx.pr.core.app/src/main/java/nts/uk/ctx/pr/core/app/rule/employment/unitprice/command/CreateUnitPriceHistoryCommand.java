/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.rule.employment.unitprice.command;

import lombok.Getter;
import lombok.Setter;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.core.dom.util.PrimitiveUtil;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.ApplySetting;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.Money;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.SettingType;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistory;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistoryGetMemento;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class CreateUnitPriceHistoryCommand.
 */
@Getter
@Setter
public class CreateUnitPriceHistoryCommand extends UnitPriceHistoryBaseCommand {

	/** The new mode. */
	private boolean newMode;

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the unit price history
	 */
	public UnitPriceHistory toDomain(CompanyCode companyCode) {
		CreateUnitPriceHistoryCommand command = this;

		// Transfer data
		UnitPriceHistory unitPriceHistory = new UnitPriceHistory(new UnitPriceHistoryGetMemento() {

			@Override
			public UnitPriceCode getUnitPriceCode() {
				return new UnitPriceCode(command.getUnitPriceCode());
			}

			@Override
			public Memo getMemo() {
				return new Memo(command.getMemo());
			}

			@Override
			public String getId() {
				return IdentifierUtil.randomUniqueId();
			}

			@Override
			public SettingType getFixPaySettingType() {
				return SettingType.valueOf(command.getFixPaySettingType());
			}

			@Override
			public ApplySetting getFixPayAtrMonthly() {
				return ApplySetting.valueOf(command.getFixPayAtrMonthly());
			}

			@Override
			public ApplySetting getFixPayAtrHourly() {
				return ApplySetting.valueOf(command.getFixPayAtrHourly());
			}

			@Override
			public ApplySetting getFixPayAtrDayMonth() {
				return ApplySetting.valueOf(command.getFixPayAtrDayMonth());
			}

			@Override
			public ApplySetting getFixPayAtrDaily() {
				return ApplySetting.valueOf(command.getFixPayAtrDaily());
			}

			@Override
			public ApplySetting getFixPayAtr() {
				return ApplySetting.valueOf(command.getFixPayAtr());
			}

			@Override
			public CompanyCode getCompanyCode() {
				return companyCode;
			}

			@Override
			public Money getBudget() {
				return new Money(command.getBudget());
			}

			@Override
			public MonthRange getApplyRange() {
				return MonthRange.toMaxDate(
						PrimitiveUtil.toYearMonth(command.getStartMonth(), PrimitiveUtil.DEFAULT_YM_SEPARATOR_CHAR));
			}
		});

		return unitPriceHistory;

	}
}
