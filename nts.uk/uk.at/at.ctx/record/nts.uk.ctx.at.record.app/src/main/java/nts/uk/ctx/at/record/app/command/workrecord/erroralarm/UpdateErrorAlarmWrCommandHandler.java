/**
 * 9:47:12 AM Jul 24, 2017
 */
package nts.uk.ctx.at.record.app.command.workrecord.erroralarm;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
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
		if (this.repository.findByCode(command.getFixedAtr() == 0 ? "U" + command.getCode() : "S" + command.getCode()).isPresent()) {
			if (command.getNewMode() == 1)
				throw new BusinessException("Msg_3");
			command.setCode(command.getFixedAtr() == 0 ? "U" + command.getCode() : "S" + command.getCode());
			this.repository.updateErrorAlarmWorkRecord(command.toDomain(),
					command.toConditionDomain(command.toDomain()));
		} else {
			this.repository.addErrorAlarmWorkRecord(command.toDomain(), command.toConditionDomain(command.toDomain()));
		}
	}

}
