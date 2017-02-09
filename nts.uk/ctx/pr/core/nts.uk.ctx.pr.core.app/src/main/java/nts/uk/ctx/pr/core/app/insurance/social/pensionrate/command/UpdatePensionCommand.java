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
		PensionRate domain = new PensionRate(new PensionRateGetMemento() {

			@Override
			public Long getVersion() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<PensionRateRounding> getRoundingMethods() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<PensionPremiumRateItem> getPremiumRateItems() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public OfficeCode getOfficeCode() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public CommonAmount getMaxAmount() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getHistoryId() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public List<FundRateItem> getFundRateItems() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public CompanyCode getCompanyCode() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Ins2Rate getChildContributionRate() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public MonthRange getApplyRange() {
				// TODO Auto-generated method stub
				return null;
			}
		});
		return domain;
	}
}
