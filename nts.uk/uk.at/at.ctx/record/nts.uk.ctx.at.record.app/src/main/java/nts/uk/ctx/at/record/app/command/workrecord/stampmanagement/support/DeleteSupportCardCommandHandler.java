package nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support;

import java.util.Arrays;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support.SupportCardRepository;

/**
 * 応援カードの削除を行う
 * @author NWS_namnv
 *
 */
@Stateless
public class DeleteSupportCardCommandHandler extends CommandHandler<SupportCardCommand> {
	
	@Inject
	private SupportCardRepository supportCardRepository;

	@Override
	protected void handle(CommandHandlerContext<SupportCardCommand> context) {
		SupportCardCommand command = context.getCommand();
		this.supportCardRepository.delete(Arrays.asList(command.toDomain()));
	}

}
