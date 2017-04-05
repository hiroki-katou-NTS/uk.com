package nts.uk.ctx.pr.core.app.command.itemmaster;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttendRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.ItemDeductRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBD;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBDRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductperiod.ItemDeductPeriodRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalaryRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBD;
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
		// remove all sub item linked with item master through the item code
		switch (categoryAtr) {
		case 0:
			// if item Category 支給
			deleteItemSalary(context);
			break;
		case 1:
			// 控除
			deleteItemDeduct(context);
			break;
		case 2:
			// 勤怠
			deleteItemAttend(companyCode, context);
			break;
		}
	}

	private void deleteItemSalary(CommandHandlerContext<DeleteItemMasterCommand> context) {
		val itemCode = context.getCommand().getItemCode();
		this.itemSalaryRespository.delete(itemCode);
		this.itemSalaryPeriodRepository.delete(itemCode);
		//Then  remove itemBD 
		List<ItemSalaryBD> itemBDList = this.itemSalaryBDRepository.findAll(itemCode);
		for (ItemSalaryBD itemBD : itemBDList) {
			this.itemSalaryBDRepository.delete(itemBD.getItemCode().v(), itemBD.getItemBreakdownCode().v());
		}

	}

	private void deleteItemDeduct(CommandHandlerContext<DeleteItemMasterCommand> context) {
		val itemCode = context.getCommand().getItemCode();
		this.itemDeductRespository.delete(itemCode);
		this.itemDeductPeriodRepository.delete(itemCode);
		List<ItemDeductBD> itemBDList = this.itemDeductBDRepository.findAll(itemCode);
		for (ItemDeductBD itemBD : itemBDList) {
			this.itemDeductBDRepository.delete(itemBD.getItemCode().v(), itemBD.getItemBreakdownCode().v());
		}

	}

	private void deleteItemAttend(String companyCode, CommandHandlerContext<DeleteItemMasterCommand> context) {
		val itemCode = context.getCommand().getItemCode();
		this.itemAttendRespository.delete(companyCode, itemCode);

	}

}
