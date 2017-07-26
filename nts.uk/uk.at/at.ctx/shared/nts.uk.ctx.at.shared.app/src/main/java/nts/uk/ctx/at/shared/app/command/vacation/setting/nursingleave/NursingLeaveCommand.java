/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.nursingleave;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.command.vacation.setting.nursingleave.dto.NursingLeaveSettingDto;

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
