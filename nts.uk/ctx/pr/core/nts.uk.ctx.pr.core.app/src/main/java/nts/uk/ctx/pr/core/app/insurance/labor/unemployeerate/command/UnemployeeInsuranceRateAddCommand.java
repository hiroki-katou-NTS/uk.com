/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.command.BaseInsuranceCommand;
import nts.uk.ctx.pr.core.app.insurance.labor.unemployeerate.UnemployeeInsuranceRateDto;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;

// TODO: Auto-generated Javadoc
/**
 * The Class UnemployeeInsuranceRateAddCommand.
 */
// TODO: Auto-generated Javadoc

@Getter
@Setter
public class UnemployeeInsuranceRateAddCommand extends BaseInsuranceCommand {

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
		UnemployeeInsuranceRate unemployeeInsuranceRate = new UnemployeeInsuranceRate();
		unemployeeInsuranceRate.setHistoryId(this.unemployeeInsuranceRate.getHistoryInsurance().getHistoryId());
		unemployeeInsuranceRate
				.setApplyRange(convertMonthRange(this.unemployeeInsuranceRate.getHistoryInsurance().getStartMonthRage(),
						this.unemployeeInsuranceRate.getHistoryInsurance().getEndMonthRage()));
		unemployeeInsuranceRate.setCompanyCode(new CompanyCode(this.companyCode));
		unemployeeInsuranceRate.setRateItems(defaultSetUnemployeeInsuranceRateItem());
		return unemployeeInsuranceRate;
	}

}
