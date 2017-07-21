/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.dailypattern.command;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPattern;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternRepository;
import nts.uk.shr.com.context.AppContexts;

// TODO: Auto-generated Javadoc
/**
 * The Class PatternCalendarCommandHandler.
 */
@Stateless
public class DailyPatternCommandHandler extends CommandHandler<DailyPatternCommand>{
	
	   /** The pattern calendar repository. */
    @Inject
    private DailyPatternRepository patternCalendarRepository;

    /* (non-Javadoc)
     * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
     */
    @Override
    protected void handle(CommandHandlerContext<DailyPatternCommand> context) {
        String companyId = AppContexts.user().companyId();
        DailyPatternCommand command = context.getCommand();
        List<DailyPattern> result = this.patternCalendarRepository.findByCompanyId(companyId);
        
        // Check fields enable/disable.
//        this.validateField(command, result);
        
        DailyPattern nursingSetting = command.getPatternCalendarDto().toDomain(companyId);
        if (CollectionUtil.isEmpty(result)) {
            this.patternCalendarRepository.add(nursingSetting);
        } else {
            this.patternCalendarRepository.update(nursingSetting);
        }
    }

}
