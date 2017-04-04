package nts.uk.ctx.pr.core.app.command.itemmaster;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttendRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.ItemDeductRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalaryRespository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateItemMasterCommandHandler extends CommandHandler<UpdateItemMasterCommand> {

	@Inject
	private ItemMasterRepository itemMasterRepository;
	// item salary
	@Inject
	private ItemSalaryRespository itemSalaryRespository;

	// item deduct
	@Inject
	private ItemDeductRespository itemDeductRespository;

	// item attend
	@Inject
	private ItemAttendRespository itemAttendRespository;

	@Override
	protected void handle(CommandHandlerContext<UpdateItemMasterCommand> context) {
		int categoryAtr = context.getCommand().getCategoryAtr();
		String companyCode = AppContexts.user().companyCode();
		String itemCode = context.getCommand().getItemCode();
		ItemMaster itemMaster = context.getCommand().toDomain();
		itemMaster.validate();
		if (!this.itemMasterRepository.find(companyCode, categoryAtr, itemCode).isPresent())
			throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません。"));
		this.itemMasterRepository.update(context.getCommand().toDomain());
		// after update item master , update sub item
		switch (categoryAtr) {
		case 0:
			// if item Category 支給
			updateItemSalary(context);
			break;
		case 1:
			// 控除
			updateItemDeduct(context);
			break;
		case 2:
			// 勤怠
			updateItemAttend(context);
			break;
		}

		// TODO Auto-generated method stub

	}

	private void updateItemSalary(CommandHandlerContext<UpdateItemMasterCommand> context) {
		UpdateItemMasterCommand itemCommand = context.getCommand();
		String itemCode = itemCommand.getItemCode();
		itemCommand.getItemSalary().setItemCode(itemCode);
		this.itemSalaryRespository.update(itemCommand.getItemSalary().toDomain());
	}

	private void updateItemDeduct(CommandHandlerContext<UpdateItemMasterCommand> context) {
		UpdateItemMasterCommand itemCommand = context.getCommand();
		String itemCode = itemCommand.getItemCode();
		itemCommand.getItemDeduct().setItemCode(itemCode);
		this.itemDeductRespository.update(itemCommand.getItemDeduct().toDomain());

	}

	private void updateItemAttend(CommandHandlerContext<UpdateItemMasterCommand> context) {
		UpdateItemMasterCommand itemCommand = context.getCommand();
		String itemCode = itemCommand.getItemCode();
		itemCommand.getItemAttend().setItemCode(itemCode);
		this.itemAttendRespository.update(itemCommand.getItemAttend().toDomain());
	}

}
