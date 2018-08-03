package nts.uk.ctx.exio.app.command.exo.outputitemsetting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.outputitem.CategoryItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddOutputItemService extends CommandHandler<List<AddOutputItemCommand>> {

	@Inject
	private StandardOutputItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<List<AddOutputItemCommand>> context) {
		List<AddOutputItemCommand> listCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		int count = 1;
		for (AddOutputItemCommand addCommand : listCommand) {
			List<CategoryItem> listCategoryItem = new ArrayList<>(Arrays.asList(new CategoryItem(addCommand.getItemNo(), addCommand.getCategoryId(), null, 1)));
			StandardOutputItem domain = new StandardOutputItem(companyId, (String.format("%03d", (addCommand.getOutItemCd() + count))),
					addCommand.getCondSetCd(), addCommand.getOutItemName(), addCommand.getItemType(),
					listCategoryItem);
			count++;
			repository.add(domain);
		}
	}
}
