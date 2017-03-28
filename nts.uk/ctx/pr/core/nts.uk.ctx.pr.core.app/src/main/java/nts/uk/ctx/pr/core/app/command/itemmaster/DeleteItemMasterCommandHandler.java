package nts.uk.ctx.pr.core.app.command.itemmaster;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemattend.DeleteItemAttendCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemdeduct.DeleteItemDeductCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalary.DeleteItemSalaryCommandHandler;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author sonnlb
 *
 */
@Stateless
@Transactional
public class DeleteItemMasterCommandHandler extends CommandHandler<DeleteItemMasterCommand> {

	@Inject
	private ItemMasterRepository itemMasterRepository;
	@Inject
	private DeleteItemSalaryCommandHandler deleteItemSalaryHandler;
	@Inject
	private DeleteItemDeductCommandHandler deleteItemDeductHandler;
	@Inject
	private DeleteItemAttendCommandHandler deleteItemAttendHandler;

	@Override
	protected void handle(CommandHandlerContext<DeleteItemMasterCommand> context) {

		val companyCode = AppContexts.user().companyCode();
		val categoryAtr = context.getCommand().getCategoryAtr();
		val itemCode = context.getCommand().getItemCode();
		this.itemMasterRepository.remove(companyCode, categoryAtr, itemCode);
		switch (categoryAtr) {
		case 0:
			context.getCommand().getItemSalary().setItemCd(itemCode);
			this.deleteItemSalaryHandler.handle(context.getCommand().getItemSalary());
			break;
		case 1:
			context.getCommand().getItemDeduct().setItemCd(itemCode);
			this.deleteItemDeductHandler.handle(context.getCommand().getItemDeduct());
			break;
		case 2:
			context.getCommand().getItemAttend().setItemCd(itemCode);
			this.deleteItemAttendHandler.handle(context.getCommand().getItemAttend());
			break;
		}

	}
}
