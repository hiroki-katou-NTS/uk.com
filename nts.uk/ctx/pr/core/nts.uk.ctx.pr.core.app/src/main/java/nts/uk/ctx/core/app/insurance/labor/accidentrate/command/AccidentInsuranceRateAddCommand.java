/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.core.app.insurance.labor.accidentrate.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.app.insurance.HistoryInfoDto;
import nts.uk.ctx.core.app.insurance.command.BaseInsuranceCommand;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;

// TODO: Auto-generated Javadoc
@Getter
@Setter
public class AccidentInsuranceRateAddCommand extends BaseInsuranceCommand {

	/** The history info dto. */
	private HistoryInfoDto historyInfoDto;

	/** The comany code. */
	private String comanyCode;

	/**
	 * To domain.
	 *
	 * @return the accident insurance rate
	 */
	public AccidentInsuranceRate toDomain() {
		AccidentInsuranceRate accidentInsuranceRate = new AccidentInsuranceRate();
		accidentInsuranceRate.setCompanyCode(new CompanyCode(this.comanyCode));
		accidentInsuranceRate.setHistoryId(this.historyInfoDto.getHistoryId());
		accidentInsuranceRate.setApplyRange(
				convertMonthRange(this.historyInfoDto.getStartMonthRage(), this.historyInfoDto.getEndMonthRage()));
		accidentInsuranceRate.setRateItems(defaultSetInsuBizRateItem());
		return accidentInsuranceRate;

	}
}
