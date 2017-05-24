/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.command.dto.ManageAnnualSettingDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Class AnnualPaidLeaveUpateCommand.
 */
@Setter
@Getter
public class AnnualPaidLeaveUpateCommand {
    
    /** The annual manage. */
    private ManageDistinct annualManage;
    
    /** The manage setting. */
    private ManageAnnualSettingDto setting;
}
