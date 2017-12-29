/**
 * 9:47:12 AM Jul 24, 2017
 */
package nts.uk.ctx.at.record.app.command.workrecord.erroralarm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;

/**
 * @author hungnm
 *
 */
@Stateless
public class UpdateErrorAlarmWrCommandHandler extends CommandHandler<UpdateErrorAlarmWrCommand> {

	@Inject
	private ErrorAlarmWorkRecordRepository repository;

	@Override
	protected void handle(CommandHandlerContext<UpdateErrorAlarmWrCommand> context) {
		UpdateErrorAlarmWrCommand command = context.getCommand();
		if(this.repository.findByCode(command.getCode()).isPresent()){
			this.repository.updateErrorAlarmWorkRecord(command.toDomain());
		} else {
			this.repository.addErrorAlarmWorkRecord(command.toDomain());
		}
	}

}
