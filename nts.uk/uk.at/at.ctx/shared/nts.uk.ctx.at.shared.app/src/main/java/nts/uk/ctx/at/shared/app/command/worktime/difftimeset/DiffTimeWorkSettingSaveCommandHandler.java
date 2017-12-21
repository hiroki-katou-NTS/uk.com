/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;

@Stateless
public class DiffTimeWorkSettingSaveCommandHandler extends CommandHandler<DiffTimeWorkSettingSaveCommand>{

	@Inject
	private DiffTimeWorkSettingRepository diffTimeWorkSettingRepository;
	
	@Override
	protected void handle(CommandHandlerContext<DiffTimeWorkSettingSaveCommand> context) {
		// TODO Auto-generated method stub
		
	}

}
