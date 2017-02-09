/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.command.BaseInsuranceCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.AccidentInsuranceRateDto;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;

// TODO: Auto-generated Javadoc
@Getter
@Setter
public class AccidentInsuranceRateAddCommand extends BaseInsuranceCommand {

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
			public Long getVersion() {
				// TODO Auto-generated method stub
				return command.accidentInsuranceRate.getVersion();
			}

			@Override
			public Set<InsuBizRateItem> getRateItems() {
				// TODO Auto-generated method stub
				return defaultSetInsuBizRateItem();
			}

			@Override
			public String getHistoryId() {
				return command.accidentInsuranceRate.getHistoryInsurance().getHistoryId();
			}

			@Override
			public CompanyCode getCompanyCode() {
				// TODO Auto-generated method stub
				return new CompanyCode(command.companyCode);
			}

			@Override
			public MonthRange getApplyRange() {
				// TODO Auto-generated method stub
				return convertMonthRange(command.accidentInsuranceRate.getHistoryInsurance().getStartMonthRage(),
						command.accidentInsuranceRate.getHistoryInsurance().getEndMonthRage());
			}
		});
		return accidentInsuranceRate;

	}
}
