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
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateStdOutItemCommandHandler extends CommandHandler<StdOutItemCommand> {

	@Inject
	private StandardOutputItemRepository repository;

	@Override
	protected void handle(CommandHandlerContext<StdOutItemCommand> context) {
		StdOutItemCommand updateCommand = context.getCommand();
		String cid = AppContexts.user().companyId();
		StandardOutputItem domain = new StandardOutputItem(cid, updateCommand.getOutItemCd(),
				updateCommand.getCondSetCd(), updateCommand.getOutItemName(), updateCommand.getItemType(),
				updateCommand.getCategoryItems().stream().map(item -> {
					return new CategoryItem(item.getCategoryItemNo(), item.getCategoryId(), item.getOperationSymbol(),
							item.getDisplayOrder());
				}).collect(Collectors.toList()));
		repository.update(domain);

	}
}
