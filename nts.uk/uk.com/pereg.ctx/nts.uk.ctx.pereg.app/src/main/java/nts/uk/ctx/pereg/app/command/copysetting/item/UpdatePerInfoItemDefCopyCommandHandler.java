package nts.uk.ctx.pereg.app.command.copysetting.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import find.person.info.item.PerInfoItemDefFinder;
import find.person.info.item.PerInfoItemDefMapDto;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.dom.copysetting.item.EmpCopySettingItemRepository;

@Stateless
public class UpdatePerInfoItemDefCopyCommandHandler extends CommandHandler<UpdatePerInfoItemDefCopy> {

	@Inject
	private EmpCopySettingItemRepository empCopyRepo;

	@Inject
	private PerInfoItemDefFinder itemDefFinder;

	@Override
	protected void handle(CommandHandlerContext<UpdatePerInfoItemDefCopy> context) {
		UpdatePerInfoItemDefCopy command = context.getCommand();

		String ctgId = command.getPerInfoCtgId();

		List<PerInfoItemDefMapDto> itemList = this.itemDefFinder.getPerInfoDefById(ctgId).stream()
				.filter(x -> x.isAlreadyItemDefCopy()).collect(Collectors.toList());

		itemList.forEach(x -> {

			empCopyRepo.removePerInfoItemInCopySetting(x.getId());

		});
		// delete object

		// add objects
		empCopyRepo.updatePerInfoItemInCopySetting(ctgId, command.getPerInfoItemDefIds());
	}

}
