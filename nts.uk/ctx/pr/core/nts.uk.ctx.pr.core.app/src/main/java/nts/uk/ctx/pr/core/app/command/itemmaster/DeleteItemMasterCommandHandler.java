package nts.uk.ctx.pr.core.app.command.itemmaster;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalarybd.DeleteItemSalaryBDCommand;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttendRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.ItemDeductRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBDRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductperiod.ItemDeductPeriodRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalaryRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBDRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalaryperiod.ItemSalaryPeriodRepository;
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
	private ItemSalaryRespository itemSalaryRespository;
	@Inject
	private ItemSalaryPeriodRepository itemSalaryPeriodRepository;
	@Inject
	private ItemSalaryBDRepository itemSalaryBDRepository;
	@Inject
	private ItemDeductRespository itemDeductRespository;
	@Inject
	private ItemDeductPeriodRepository itemDeductPeriodRepository;
	@Inject
	private ItemDeductBDRepository itemDeductBDRepository;
	@Inject
	private ItemAttendRespository itemAttendRespository;

	@Override
	protected void handle(CommandHandlerContext<DeleteItemMasterCommand> context) {

		val companyCode = AppContexts.user().companyCode();
		val categoryAtr = context.getCommand().getCategoryAtr();
		val itemCode = context.getCommand().getItemCode();
		this.itemMasterRepository.remove(companyCode, categoryAtr, itemCode);
		switch (categoryAtr) {
		case 0:
			deleteItemSalary(context);
			break;
		case 1:
			deleteItemDeduct(context);
			break;
		case 2:
			deleteItemAttend(context);
			break;
		}
	}

	private void deleteItemSalary(CommandHandlerContext<DeleteItemMasterCommand> context) {
		val itemCode = context.getCommand().getItemCode();
		this.itemSalaryRespository.delete(itemCode);
		this.itemSalaryPeriodRepository.delete(itemCode);
		for (DeleteItemSalaryBDCommand deleteItemSalaryBDCommand : context.getCommand().getItemBDs())
			this.itemSalaryBDRepository.delete(itemCode, deleteItemSalaryBDCommand.getItemBreakdownCd());
	}

	private void deleteItemDeduct(CommandHandlerContext<DeleteItemMasterCommand> context) {
		val itemCode = context.getCommand().getItemCode();
		this.itemDeductRespository.delete(itemCode);
		this.itemDeductPeriodRepository.delete(itemCode);
		for (DeleteItemSalaryBDCommand deleteItemSalaryBDCommand : context.getCommand().getItemBDs())
			this.itemDeductBDRepository.delete(itemCode, deleteItemSalaryBDCommand.toItemDeduct().getItemBreakdownCd());

	}

	private void deleteItemAttend(CommandHandlerContext<DeleteItemMasterCommand> context) {
		val itemCode = context.getCommand().getItemCode();
		this.itemAttendRespository.delete(itemCode);

	}

}
