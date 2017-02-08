/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.core.app.insurance.labor.unemployeerate.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.app.insurance.command.BaseInsuranceCommand;
import nts.uk.ctx.core.app.insurance.labor.unemployeerate.UnemployeeInsuranceRateDto;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;

@Getter
@Setter
public class UnemployeeInsuranceRateUpdateCommand extends BaseInsuranceCommand{
	/** The history info dto. */
	private UnemployeeInsuranceRateDto unemployeeInsuranceRate;

	/** The comany code. */
	private String comanyCode;

	/**
	 * To domain.
	 *
	 * @return the labor insurance office
	 */
	public UnemployeeInsuranceRate toDomain() {
		UnemployeeInsuranceRate unemployeeInsuranceRate = new UnemployeeInsuranceRate();
		unemployeeInsuranceRate.setHistoryId(this.unemployeeInsuranceRate.getHistoryInsurance().getHistoryId());
		unemployeeInsuranceRate.setApplyRange(convertMonthRange(this.unemployeeInsuranceRate.getHistoryInsurance().getStartMonthRage(),
				this.unemployeeInsuranceRate.getHistoryInsurance().getEndMonthRage()));
		unemployeeInsuranceRate.setCompanyCode(new CompanyCode(this.comanyCode));
		unemployeeInsuranceRate.setRateItems(defaultSetUnemployeeInsuranceRateItem());
		return unemployeeInsuranceRate;
	}

}
