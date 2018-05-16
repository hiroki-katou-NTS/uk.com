package nts.uk.ctx.sys.assist.app.command.deletedata.management;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.assist.app.command.datastoragemng.DataStorageMngCommand;
import nts.uk.ctx.sys.assist.dom.deletedata.ManagementDeletion;
import nts.uk.ctx.sys.assist.dom.deletedata.ManagementDeletionRepository;
import nts.uk.ctx.sys.assist.dom.deletedata.OperatingCondition;
import nts.uk.ctx.sys.assist.dom.storage.DataStorageMngRepository;

@Stateless
@Transactional
public class RemoveManagementDelCommandHandler extends CommandHandler<ManagementDelCommand> {

	@Inject
	private DataStorageMngRepository repository;

	@Override
	protected void handle(CommandHandlerContext<ManagementDelCommand> context) {
		String delId = context.getCommand().getDelId();
		repository.remove(delId);
	}
}
