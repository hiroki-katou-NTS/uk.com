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
    
    /** The child nursing setting. */   //because in frontend code, nursingSetting.nursingCategory = 1 (ChildNursing)
    private NursingLeaveSettingDto nursingSetting;
    
    /** The nursing setting. */			//because in frontend code, childNursingSetting.nursingCategory = 0 (Nursing)
    private NursingLeaveSettingDto childNursingSetting;
}
