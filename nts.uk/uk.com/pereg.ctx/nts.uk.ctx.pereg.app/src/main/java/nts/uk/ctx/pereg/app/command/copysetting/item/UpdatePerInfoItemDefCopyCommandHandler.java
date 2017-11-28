package nts.uk.ctx.pereg.app.command.copysetting.item;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pereg.app.find.copysetting.item.CopySettingItemDto;
import nts.uk.ctx.pereg.app.find.copysetting.item.CopySettingItemFinder;
import nts.uk.ctx.pereg.dom.copysetting.item.EmpCopySettingItemRepository;

@Stateless
public class UpdatePerInfoItemDefCopyCommandHandler extends CommandHandler<UpdatePerInfoItemDefCopy> {

	@Inject
	private EmpCopySettingItemRepository empCopyRepo;

	@Inject
	private CopySettingItemFinder itemFinder;

	@Override
	protected void handle(CommandHandlerContext<UpdatePerInfoItemDefCopy> context) {
		UpdatePerInfoItemDefCopy command = context.getCommand();

		String ctgId = command.getPerInfoCtgId();

		List<CopySettingItemDto> itemList = this.itemFinder.getPerInfoDefById(ctgId).stream()
				.filter(x -> x.isAlreadyItemDefCopy()).collect(Collectors.toList());

		itemList.forEach(x -> {

			empCopyRepo.removePerInfoItemInCopySetting(x.getId());

		});
		// delete object

		// add objects
		empCopyRepo.updatePerInfoItemInCopySetting(ctgId, command.getPerInfoItemDefIds());
	}

}
