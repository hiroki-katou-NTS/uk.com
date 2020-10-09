package nts.uk.ctx.sys.assist.app.find.deletedata;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.deletedata.ResultDeletionRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteDelDataHandler extends CommandHandler<String> {
	
	@Inject
	private ResultDeletionRepository resultDeletionRepository;

	@Override
	protected void handle(CommandHandlerContext<String> context) {
		String fileId = context.getCommand();
		resultDeletionRepository.update(fileId);
	}

}
