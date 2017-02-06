/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.core.app.insurance.labor.unemployeerate.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.app.insurance.HistoryInfoDto;
import nts.uk.ctx.core.app.insurance.command.ActionCommand;
import nts.uk.ctx.core.app.insurance.labor.unemployeerate.UnemployeeInsuranceRateDto;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.Address;
import nts.uk.ctx.pr.core.dom.insurance.KanaAddress;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeName;
import nts.uk.ctx.pr.core.dom.insurance.PicName;
import nts.uk.ctx.pr.core.dom.insurance.PicPosition;
import nts.uk.ctx.pr.core.dom.insurance.PotalCode;
import nts.uk.ctx.pr.core.dom.insurance.ShortName;
import nts.uk.ctx.pr.core.dom.insurance.labor.LaborInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.labor.unemployeerate.UnemployeeInsuranceRate;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Class UnemployeeInsuranceRateAddCommand.
 */
// TODO: Auto-generated Javadoc
@Getter
@Setter
public class UnemployeeInsuranceRateAddCommand {

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
		unemployeeInsuranceRate.setApplyRange(ActionCommand.convertMonthRange(
				this.unemployeeInsuranceRate.getHistoryInsurance().getStartMonthRage(),
				this.unemployeeInsuranceRate.getHistoryInsurance().getEndMonthRage()));
		unemployeeInsuranceRate.setCompanyCode(new CompanyCode(this.comanyCode));
		unemployeeInsuranceRate.setRateItems(ActionCommand.defaultSetUnemployeeInsuranceRateItem());
		return unemployeeInsuranceRate;
	}

}
