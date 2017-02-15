/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.command;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.command.InsuranceBaseCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.UnemployeeInsuranceRateDto;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRateItem;

/**
 * The Class UnemployeeInsuranceRateAddCommand.
 */
@Getter
@Setter
public class UnemployeeInsuranceRateAddCommand extends InsuranceBaseCommand {

	/** The history info dto. */
	private UnemployeeInsuranceRateDto unemployeeInsuranceRate;

	/** The company code. */
	private String companyCode;

	/**
	 * To domain.
	 *
	 * @return the labor insurance office
	 */
	public UnemployeeInsuranceRate toDomain() {
		UnemployeeInsuranceRateAddCommand command = this;
		UnemployeeInsuranceRate unemployeeInsuranceRate = new UnemployeeInsuranceRate(
				new UnemployeeInsuranceRateGetMemento() {

					@Override
					public Set<UnemployeeInsuranceRateItem> getRateItems() {
						return command.convertSetUnemployeeInsuranceRateItem(command.unemployeeInsuranceRate);
					}

					@Override
					public String getHistoryId() {
						return command.unemployeeInsuranceRate.getHistoryInsurance().getHistoryId();
					}

					@Override
					public CompanyCode getCompanyCode() {
						return new CompanyCode(command.companyCode);
					}

					@Override
					public MonthRange getApplyRange() {
						return command.convertMonthRange(
								command.unemployeeInsuranceRate.getHistoryInsurance().getStartMonthRage(),
								command.unemployeeInsuranceRate.getHistoryInsurance().getEndMonthRage());
					}
				});
		return unemployeeInsuranceRate;
	}

}
