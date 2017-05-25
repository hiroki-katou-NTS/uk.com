/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.acquisitionrule.command;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleGetMemento;

/**
 * The Class CreateVaAcRuleCommand.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CreateVaAcRuleCommand extends VacationAcquisitionRuleBaseCommand
		implements AcquisitionRuleGetMemento {
	
}