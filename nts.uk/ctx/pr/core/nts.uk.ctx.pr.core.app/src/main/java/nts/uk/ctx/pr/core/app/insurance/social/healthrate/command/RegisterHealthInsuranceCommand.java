/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthrate.command;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.core.dom.util.PrimitiveUtil;
import nts.uk.ctx.pr.core.dom.insurance.CalculateMethod;
import nts.uk.ctx.pr.core.dom.insurance.CommonAmount;
import nts.uk.ctx.pr.core.dom.insurance.Ins3Rate;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthChargeRateItem;
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
	 * @param companyCode
	 *            the company code
	 * @return the health insurance rate
	 */
	public HealthInsuranceRate toDomain(CompanyCode companyCode) {
		RegisterHealthInsuranceCommand command = this;

		// Transfer data
		HealthInsuranceRate healthInsuranceRate = new HealthInsuranceRate(new HealthInsuranceRateGetMemento() {

			@Override
			public Set<HealthInsuranceRounding> getRoundingMethods() {
				// TODO convert command -> domain
				if (command.getRoundingMethods().isEmpty()) {
					return null;
				}
				return new HashSet<HealthInsuranceRounding>(command.getRoundingMethods());
			}

			@Override
			public Set<InsuranceRateItem> getRateItems() {
				// TODO convert command -> domain
				return new HashSet<InsuranceRateItem>(command.getRateItems().stream()
						.map(dto -> new InsuranceRateItem(dto.getPayType(), dto.getInsuranceType(),
								new HealthChargeRateItem(new Ins3Rate(dto.getChargeRate().getCompanyRate()),
										new Ins3Rate(dto.getChargeRate().getPersonalRate()))))
						.collect(Collectors.toList()));
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
			public CompanyCode getCompanyCode() {
				return companyCode;
			}

			@Override
			public CalculateMethod getAutoCalculate() {
				return CalculateMethod.valueOf(command.getAutoCalculate());
			}

			@Override
			public MonthRange getApplyRange() {
				return MonthRange.range(PrimitiveUtil.toYearMonth(command.getStartMonth(), "/"),
						PrimitiveUtil.toYearMonth(command.getEndMonth(), "/"));
			}
		});

		return healthInsuranceRate;
	}
}
