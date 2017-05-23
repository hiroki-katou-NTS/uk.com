/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package vacation.setting.annualpaidleave.command;

import javax.ejb.Stateless;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
public class AnnualPaidLeaveUpateCommandHandler extends CommandHandler<AnnualPaidLeaveUpateCommand> {

    /* (non-Javadoc)
     * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
     */
    @Override
    protected void handle(CommandHandlerContext<AnnualPaidLeaveUpateCommand> context) {
        // TODO Auto-generated method stub
        
    }
}
