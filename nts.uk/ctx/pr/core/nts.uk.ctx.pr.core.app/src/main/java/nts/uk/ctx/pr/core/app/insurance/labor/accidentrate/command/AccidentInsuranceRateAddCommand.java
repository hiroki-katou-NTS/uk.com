/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.HistoryInfoDto;
import nts.uk.ctx.pr.core.app.insurance.command.BaseInsuranceCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.AccidentInsuranceRateDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;

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
		AccidentInsuranceRate accidentInsuranceRate = new AccidentInsuranceRate();
		accidentInsuranceRate.setHistoryId(this.accidentInsuranceRate.getHistoryInsurance().getHistoryId());
		accidentInsuranceRate
				.setApplyRange(convertMonthRange(this.accidentInsuranceRate.getHistoryInsurance().getStartMonthRage(),
						this.accidentInsuranceRate.getHistoryInsurance().getEndMonthRage()));
		accidentInsuranceRate.setRateItems(defaultSetInsuBizRateItem());
		accidentInsuranceRate.setCompanyCode(new CompanyCode(this.companyCode));
		return accidentInsuranceRate;

	}
}
