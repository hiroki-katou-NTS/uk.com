package nts.uk.ctx.exio.app.command.exo.outputitem;

import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.outputitem.CategoryItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItem;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;

@Stateless
@Transactional
public class AddStdOutItemCommandHandler extends CommandHandler<StdOutItemCommand> {

	@Inject
	private StandardOutputItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<StdOutItemCommand> context) {
		StdOutItemCommand addCommand = context.getCommand();

		StandardOutputItem domain = new StandardOutputItem(addCommand.getCid(), addCommand.getOutItemCd(),
				addCommand.getCondSetCd(), addCommand.getOutItemName(), addCommand.getItemType(),
				addCommand.getCategoryItems().stream().map(item -> {
					return new CategoryItem(item.getCategoryItemNo(), item.getCategoryId(), item.getOperationSymbol(),
							item.getDisplayOrder());
				}).collect(Collectors.toList()));
		repository.add(domain);

	}
}
