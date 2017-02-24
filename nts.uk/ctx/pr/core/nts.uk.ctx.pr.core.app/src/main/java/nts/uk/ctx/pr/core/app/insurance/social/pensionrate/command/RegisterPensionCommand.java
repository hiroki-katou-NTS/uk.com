/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.core.dom.util.PrimitiveUtil;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.Ins2Rate;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.FundRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionPremiumRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRounding;

/**
 * The Class RegisterPensionCommand.
 */
@Getter
@Setter
public class RegisterPensionCommand extends PensionBaseCommand {

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the pension rate
	 */
	public PensionRate toDomain(CompanyCode companyCode) {
		RegisterPensionCommand command = this;

		// Transfer data
		PensionRate pensionRate = new PensionRate(new PensionRateGetMemento() {

			@Override
			public List<PensionRateRounding> getRoundingMethods() {
				return command.getRoundingMethods();
			}

			@Override
			public List<PensionPremiumRateItem> getPremiumRateItems() {
				return this.getPremiumRateItems();
			}

			@Override
			public OfficeCode getOfficeCode() {
				return this.getOfficeCode();
			}

			@Override
			public CommonAmount getMaxAmount() {
				return this.getMaxAmount();
			}

			@Override
			public String getHistoryId() {
				return this.getHistoryId();
			}

			@Override
			public List<FundRateItem> getFundRateItems() {
				return this.getFundRateItems();
			}

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode(command.getCompanyCode());
			}

			@Override
			public Ins2Rate getChildContributionRate() {
				return new Ins2Rate(command.getChildContributionRate());
			}

			@Override
			public MonthRange getApplyRange() {
				return MonthRange.toMaxDate(
						PrimitiveUtil.toYearMonth(command.getStartMonth(), PrimitiveUtil.DEFAULT_YM_SEPARATOR_CHAR));
			}

			@Override
			public Boolean getFundInputApply() {
				return command.getFundInputApply();
			}

			@Override
			public Boolean getAutoCalculate() {
				return command.getAutoCalculate();
			}
		});

		return pensionRate;
	}
}
