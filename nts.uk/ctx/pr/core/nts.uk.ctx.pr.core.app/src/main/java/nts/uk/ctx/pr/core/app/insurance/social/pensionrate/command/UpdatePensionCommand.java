package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.Ins2Rate;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.FundRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionPremiumRateItem;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRounding;

@Getter
@Setter
public class UpdatePensionCommand extends PensionBaseCommand {
	public PensionRate toDomain(CompanyCode companyCode, String historyId, OfficeCode officeCode) {
		UpdatePensionCommand command = this;
		PensionRate updatedPensionRate = new PensionRate(new PensionRateGetMemento() {

			@Override
			public Long getVersion() {
				return command.getVersion();
			}

			@Override
			public List<PensionRateRounding> getRoundingMethods() {
				return command.getRoundingMethods();
			}

			@Override
			public List<PensionPremiumRateItem> getPremiumRateItems() {
				// TODO convert command -> domain
				return null;
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
				return command.getHistoryId();
			}

			@Override
			public List<FundRateItem> getFundRateItems() {
				// TODO convert command -> domain
				return null;
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
				return MonthRange.range(command.getStartMonth(), command.getEndMonth(), "/");
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
		return updatedPensionRate;
	}
}
