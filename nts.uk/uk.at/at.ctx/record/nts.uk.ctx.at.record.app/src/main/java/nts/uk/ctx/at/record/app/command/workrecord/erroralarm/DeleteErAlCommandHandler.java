/**
 * 5:22:44 AM Dec 8, 2017
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
public class DeleteErAlCommandHandler extends CommandHandler<String> {

	@Inject
	private ErrorAlarmWorkRecordRepository repository;

	@Override
	protected void handle(CommandHandlerContext<String> context) {
		String erCode = "U" + context.getCommand();
		this.repository.removeErrorAlarmWorkRecord(erCode);
	}
}
