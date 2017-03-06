/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.core.dom.util.PrimitiveUtil;
import nts.uk.ctx.pr.core.dom.insurance.CalculateMethod;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.Ins2Rate;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.FundRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionChargeRateItem;
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
	 * @param companyCode the company code
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
				return command.getPremiumRateItems().stream().map(dto -> {
					PensionChargeRateItem pensionChargeRateItem = new PensionChargeRateItem();
					pensionChargeRateItem.setCompanyRate(new Ins2Rate(dto.getCompanyRate()));
					pensionChargeRateItem.setPersonalRate(new Ins2Rate(dto.getPersonalRate()));
					return new PensionPremiumRateItem(dto.getPayType(), dto.getGenderType(), pensionChargeRateItem);
				}).collect(Collectors.toList());
			}

			@Override
			public OfficeCode getOfficeCode() {
				return new OfficeCode(command.getOfficeCode());
			}

			@Override
			public CommonAmount getMaxAmount() {
				return new CommonAmount(command.getMaxAmount());
			}

			@Override
			public String getHistoryId() {
				if (command.getHistoryId().equals("")) {
					return IdentifierUtil.randomUniqueId();
				}
				return command.getHistoryId();
			}

			@Override
			public List<FundRateItem> getFundRateItems() {
				return command.getFundRateItems().stream().map(dto -> {
					PensionChargeRateItem burdenPensionChargeRateItem = new PensionChargeRateItem();
					burdenPensionChargeRateItem.setCompanyRate(new Ins2Rate(dto.getBurdenChargeCompanyRate()));
					burdenPensionChargeRateItem.setPersonalRate(new Ins2Rate(dto.getBurdenChargePersonalRate()));

					PensionChargeRateItem exemptionPensionChargeRateItem = new PensionChargeRateItem();
					exemptionPensionChargeRateItem.setCompanyRate(new Ins2Rate(dto.getExemptionChargeCompanyRate()));
					exemptionPensionChargeRateItem.setPersonalRate(new Ins2Rate(dto.getExemptionChargePersonalRate()));

					return new FundRateItem(dto.getPayType(), dto.getGenderType(), burdenPensionChargeRateItem,exemptionPensionChargeRateItem);
				}).collect(Collectors.toList());
			}

			@Override
			public CompanyCode getCompanyCode() {
				return companyCode;
			}

			@Override
			public Ins2Rate getChildContributionRate() {
				return new Ins2Rate(command.getChildContributionRate());
			}

			@Override
			public MonthRange getApplyRange() {
				return MonthRange.range(PrimitiveUtil.toYearMonth(command.getStartMonth(), "/"),PrimitiveUtil.toYearMonth(command.getEndMonth(), "/"));
			}

			@Override
			public Boolean getFundInputApply() {
				return command.getFundInputApply();
			}

			@Override
			public CalculateMethod getAutoCalculate() {
				return CalculateMethod.valueOf(command.getAutoCalculate());
			}
		});

		return pensionRate;
	}
}
