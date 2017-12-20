package nts.uk.ctx.pereg.app.command.optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.person.additemdata.category.EmInfoCtgDataRepository;
import nts.uk.ctx.pereg.dom.person.additemdata.item.EmpInfoItemDataRepository;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefDeleteCommand;
import nts.uk.shr.pereg.app.command.userdef.PeregUserDefDeleteCommandHandler;

@Stateless
public class DeleteOptionalCommandHandler extends CommandHandler<PeregUserDefDeleteCommand>
		implements PeregUserDefDeleteCommandHandler {

	@Inject
	private EmInfoCtgDataRepository emInfoCtgDataRepository;
	
	@Inject
	private EmpInfoItemDataRepository empInfoItemDataRepository;
	@Override
	protected void handle(CommandHandlerContext<PeregUserDefDeleteCommand> context) {
		val command = context.getCommand();
		emInfoCtgDataRepository.deleteEmpInfoCtgData(command.getRecordId());
		empInfoItemDataRepository.deleteEmployInfoItemData(command.getRecordId());
	}


}
