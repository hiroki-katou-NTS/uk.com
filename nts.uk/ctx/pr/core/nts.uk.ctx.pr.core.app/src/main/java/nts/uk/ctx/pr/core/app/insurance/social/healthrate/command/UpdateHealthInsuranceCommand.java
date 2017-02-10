package nts.uk.ctx.pr.core.app.insurance.social.healthrate.command;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRounding;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceRateItem;

@Getter
@Setter
public class UpdateHealthInsuranceCommand extends HealthInsuranceBaseCommand {
	public HealthInsuranceRate toDomain(CompanyCode companyCode, String historyId, OfficeCode officeCode) {
		HealthInsuranceRate domain = new HealthInsuranceRate(new HealthInsuranceRateGetMemento() {

			@Override
			public Long getVersion() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Set<HealthInsuranceRounding> getRoundingMethods() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Set<InsuranceRateItem> getRateItems() {
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
			public CompanyCode getCompanyCode() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Boolean getAutoCalculate() {
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
