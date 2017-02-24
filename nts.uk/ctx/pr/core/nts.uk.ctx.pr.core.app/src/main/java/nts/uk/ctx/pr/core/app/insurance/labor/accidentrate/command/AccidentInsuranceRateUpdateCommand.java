/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.command.dto.AccidentInsuranceRateDto;
import nts.uk.ctx.pr.core.app.insurance.labor.accidentrate.find.dto.InsuBizRateItemFindOutDto;
import nts.uk.ctx.pr.core.dom.insurance.MonthRange;
import nts.uk.ctx.pr.core.dom.insurance.RoundingMethod;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.AccidentInsuranceRateGetMemento;
import nts.uk.ctx.pr.core.dom.insurance.labor.accidentrate.InsuBizRateItem;
import nts.uk.ctx.pr.core.dom.insurance.labor.businesstype.BusinessTypeEnum;

/**
 * The Class AccidentInsuranceRateUpdateCommand.
 */
@Getter
@Setter
public class AccidentInsuranceRateUpdateCommand {

	/** The accident insurance rate. */
	private AccidentInsuranceRateDto accidentInsuranceRate;

	/**
	 * To domain.
	 *
	 * @return the accident insurance rate
	 */
	public AccidentInsuranceRate toDomain(String companyCode) {
		return this.accidentInsuranceRate.toDomain(companyCode);

	}
}
