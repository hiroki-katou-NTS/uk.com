package nts.uk.ctx.sys.assist.app.find.resultofsaving;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.dom.storage.ResultOfSavingRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DeleteDataHandler extends CommandHandler<String> {
	
	@Inject
	private ResultOfSavingRepository resultOfSavingRepository;

	@Override
	protected void handle(CommandHandlerContext<String> context) {
		String fileId = context.getCommand();
		resultOfSavingRepository.update(fileId);
	}
}
