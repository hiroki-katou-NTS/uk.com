/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.nursingleave.command;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class NursingLeaveCommandHandler extends CommandHandler<NursingLeaveCommand> {
    
    @Inject
    private NursingLeaveSettingRepository nursingLeaveRepo;
    
    /* (non-Javadoc)
     * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
     */
    @Override
    protected void handle(CommandHandlerContext<NursingLeaveCommand> context) {
        String companyId = AppContexts.user().companyId();
        NursingLeaveCommand command = context.getCommand();
        
        NursingLeaveSetting nursingSetting = command.getNursingSetting().toDomain(companyId);
        NursingLeaveSetting childNursingSetting = command.getChildNursingSetting().toDomain(companyId);
        
        List<NursingLeaveSetting> result = this.nursingLeaveRepo.findByCompanyId(companyId);
        if (CollectionUtil.isEmpty(result)) {
            this.nursingLeaveRepo.add(nursingSetting, childNursingSetting);
        } else {
            this.nursingLeaveRepo.update(nursingSetting, childNursingSetting);
        }
    }

}
