package command.person.info.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.person.info.item.PerInfoItemDefFinder;
import find.person.info.item.PerInfoItemDefMapDto;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.person.dom.person.info.item.PerInfoItemDefRepositoty;

@Stateless
public class UpdatePerInfoItemDefCopyCommandHandler extends CommandHandler<UpdatePerInfoItemDefCopy> {

	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;

	@Inject
	private PerInfoItemDefFinder itemDefFinder;

	@Override
	protected void handle(CommandHandlerContext<UpdatePerInfoItemDefCopy> context) {
		UpdatePerInfoItemDefCopy command = context.getCommand();

		String ctgId = command.getPerInfoCtgId();

		List<PerInfoItemDefMapDto> itemList = this.itemDefFinder.getPerInfoDefById(ctgId).stream()
				.filter(x -> x.isAlreadyItemDefCopy()).collect(Collectors.toList());

		itemList.forEach(x -> {

			perInfoItemDefRepositoty.removePerInfoItemInCopySetting(x.getId());

		});
		// delete object

		// add objects
		perInfoItemDefRepositoty.updatePerInfoItemInCopySetting(ctgId, command.getPerInfoItemDefIds());
	}

}
