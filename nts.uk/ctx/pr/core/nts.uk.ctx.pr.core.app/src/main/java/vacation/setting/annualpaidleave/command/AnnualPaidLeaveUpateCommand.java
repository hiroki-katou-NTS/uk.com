/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package vacation.setting.annualpaidleave.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.vacation.setting.ManageDistinct;
import vacation.setting.annualpaidleave.command.dto.ManageAnnualSettingDto;

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
