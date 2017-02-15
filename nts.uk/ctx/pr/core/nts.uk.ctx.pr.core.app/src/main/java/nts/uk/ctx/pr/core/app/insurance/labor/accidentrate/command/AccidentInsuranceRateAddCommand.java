/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.command.InsuranceBaseCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.AccidentInsuranceRateDto;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;

/**
 * The Class AccidentInsuranceRateAddCommand.
 */
@Getter
@Setter
public class AccidentInsuranceRateAddCommand extends InsuranceBaseCommand {

	/** The accident insurance rate. */
	private AccidentInsuranceRateDto accidentInsuranceRate;

	/** The company code. */
	private String companyCode;

	/**
	 * To domain.
	 *
	 * @return the accident insurance rate
	 */
	public AccidentInsuranceRate toDomain() {
		AccidentInsuranceRateAddCommand command = this;

		// Transfer data
		AccidentInsuranceRate accidentInsuranceRate = new AccidentInsuranceRate(new AccidentInsuranceRateGetMemento() {

			@Override
			public Set<InsuBizRateItem> getRateItems() {
				return defaultSetInsuBizRateItem();
			}

			@Override
			public String getHistoryId() {
				return command.accidentInsuranceRate.getHistoryInsurance().getHistoryId();
			}

			@Override
			public CompanyCode getCompanyCode() {
				return new CompanyCode(command.companyCode);
			}

			@Override
			public MonthRange getApplyRange() {
				return convertMonthRange(command.accidentInsuranceRate.getHistoryInsurance().getStartMonthRage(),
						command.accidentInsuranceRate.getHistoryInsurance().getEndMonthRage());
			}
		});
		return accidentInsuranceRate;

	}
}
