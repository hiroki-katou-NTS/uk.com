/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
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

/**
 * The Class RegisterHealthInsuranceCommand.
 */
@Getter
@Setter
public class RegisterHealthInsuranceCommand extends HealthInsuranceBaseCommand {
	
	/**
	 * To domain.
	 *
	 * @param companyCode the company code
	 * @return the health insurance rate
	 */
	public HealthInsuranceRate toDomain(CompanyCode companyCode) {
		RegisterHealthInsuranceCommand command = this;

		// Transfer data
		HealthInsuranceRate healthInsuranceRate = new HealthInsuranceRate(new HealthInsuranceRateGetMemento() {

			@Override
			public Set<HealthInsuranceRounding> getRoundingMethods() {
				// TODO convert command -> domain
				return null;
			}

			@Override
			public Set<InsuranceRateItem> getRateItems() {
				// TODO convert command -> domain
				return null;
			}

			@Override
			public OfficeCode getOfficeCode() {
				return command.getOfficeCode();
			}

			@Override
			public CommonAmount getMaxAmount() {
				return command.getMaxAmount();
			}

			@Override
			public String getHistoryId() {
				return command.getHistoryId();
			}

			@Override
			public CompanyCode getCompanyCode() {
				return command.getCompanyCode();
			}

			@Override
			public Boolean getAutoCalculate() {
				return command.getAutoCalculate();
			}

			@Override
			public MonthRange getApplyRange() {
				return command.getApplyRange();
			}
		});
		
		return healthInsuranceRate;
	}
}
