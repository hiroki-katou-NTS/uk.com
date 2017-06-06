/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.nursingleave.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.vacation.setting.nursingleave.command.dto.NursingLeaveSettingDto;

/**
 * The Class NursingLeaveCommand.
 */
@Setter
@Getter
public class NursingLeaveCommand {
    
    /** The nursing setting. */
    private NursingLeaveSettingDto nursingSetting;
    
    /** The child nursing setting. */
    private NursingLeaveSettingDto childNursingSetting;
}
