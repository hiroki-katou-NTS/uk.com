/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthrate.command;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.core.dom.util.PrimitiveUtil;
import nts.uk.ctx.pr.core.dom.insurance.CalculateMethod;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRounding;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.InsuranceRateItem;

/**
 * The Class UpdateHealthInsuranceCommand.
 */
@Getter
@Setter
public class UpdateHealthInsuranceCommand extends HealthInsuranceBaseCommand {

	/**
	 * To domain.
	 *
	 * @param companyCode
	 *            the company code
	 * @param historyId
	 *            the history id
	 * @param officeCode
	 *            the office code
	 * @return the health insurance rate
	 */
	public HealthInsuranceRate toDomain(CompanyCode companyCode, String historyId, OfficeCode officeCode) {
		UpdateHealthInsuranceCommand command = this;

		// Transfer data
		HealthInsuranceRate updatedHealthInsuranceRate = new HealthInsuranceRate(new HealthInsuranceRateGetMemento() {

			@Override
			public Set<HealthInsuranceRounding> getRoundingMethods() {
				return null;
			}

			@Override
			public Set<InsuranceRateItem> getRateItems() {
				// TODO Auto-generated method stub
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
			public CompanyCode getCompanyCode() {
				return new CompanyCode(command.getCompanyCode());
			}

			@Override
			public CalculateMethod getAutoCalculate() {
				return CalculateMethod.valueOf(command.getAutoCalculate());
			}

			@Override
			public MonthRange getApplyRange() {
				return MonthRange.range(PrimitiveUtil.toYearMonth(command.getStartMonth(), "/"),PrimitiveUtil.toYearMonth(command.getEndMonth(), "/"));
			}
		});

		return updatedHealthInsuranceRate;
	}
}
