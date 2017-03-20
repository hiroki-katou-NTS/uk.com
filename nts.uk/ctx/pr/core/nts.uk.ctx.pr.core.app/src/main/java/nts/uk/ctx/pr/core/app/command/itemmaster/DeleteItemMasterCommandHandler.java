package nts.uk.ctx.pr.core.app.command.itemmaster;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author sonnlb
 *
 */
@RequestScoped
@Transactional
public class DeleteItemMasterCommandHandler extends CommandHandler<DeleteItemMasterCommand> {

	@Inject
	private ItemMasterRepository itemMasterRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteItemMasterCommand> context) {

		val companyCode = AppContexts.user().companyCode();
		val categoryAtr = context.getCommand().getCategoryAtr();
		val itemCode = context.getCommand().getItemCode();

		this.itemMasterRepository.remove(companyCode, categoryAtr, itemCode);
	}
}
