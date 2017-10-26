/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;

/**
 * The Class BasicScheduleSaveCommandHandler.
 */
// 勤務予定情報を登録する
@Stateless
public class BasicScheduleSaveCommandHandler extends CommandHandler<BasicScheduleSaveCommand> {
	
	/** The basic schedule repository. */
	@Inject
	private BasicScheduleRepository basicScheduleRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<BasicScheduleSaveCommand> context) {
		BasicScheduleSaveCommand command = context.getCommand();

		Optional<BasicSchedule> optionalBasicSchedule = this.basicScheduleRepository.find(command.getEmployeeId(),
				command.getYmd());
		if (optionalBasicSchedule.isPresent()) {
			if (command.getIsDeleteBeforeSave()) {
				this.basicScheduleRepository.delete(command.getEmployeeId(), command.getYmd());
				this.basicScheduleRepository.insert(command.toDomain());
			} else {
				this.basicScheduleRepository.update(command.toDomain());
			}
		} else {
			this.basicScheduleRepository.insert(command.toDomain());
		}
	}

}
