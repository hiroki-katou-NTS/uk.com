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
		// Check the data exists
		if (!this.itemMasterRepository.find(companyCode, categoryAtr, itemCode).isPresent())
			throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません。"));
		this.itemMasterRepository.update(context.getCommand().toDomain());
		// after update item master , update sub item
		switch (categoryAtr) {
		case 0:
			// if item Category 支給
			updateItemSalary(companyCode, context);
			break;
		case 1:
			// 控除
			updateItemDeduct(companyCode, context);
			break;
		case 2:
			// 勤怠
			updateItemAttend(companyCode, context);
			break;
		}
	}

	private void updateItemSalary(String companyCode, CommandHandlerContext<UpdateItemMasterCommand> context) {
		UpdateItemMasterCommand itemCommand = context.getCommand();
		String itemCode = itemCommand.getItemCode();
		itemCommand.getItemSalary().setItemCode(itemCode);
		if (!this.itemSalaryRespository.find(companyCode, itemCode).isPresent())
			throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません。"));
		this.itemSalaryRespository.update(companyCode, itemCommand.getItemSalary().toDomain());
	}

	private void updateItemDeduct(String companyCode, CommandHandlerContext<UpdateItemMasterCommand> context) {
		UpdateItemMasterCommand itemCommand = context.getCommand();
		String itemCode = itemCommand.getItemCode();
		itemCommand.getItemDeduct().setItemCode(itemCode);
		if (!this.itemDeductRespository.find(companyCode, itemCode).isPresent())
			throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません。"));
		this.itemDeductRespository.update(companyCode, itemCommand.getItemDeduct().toDomain());

	}

	private void updateItemAttend(String companyCode, CommandHandlerContext<UpdateItemMasterCommand> context) {
		UpdateItemMasterCommand itemCommand = context.getCommand();
		String itemCode = itemCommand.getItemCode();
		itemCommand.getItemAttend().setItemCode(itemCode);
		if (!this.itemAttendRespository.find(companyCode, itemCode).isPresent())
			throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません。"));
		this.itemAttendRespository.update(companyCode, itemCommand.getItemAttend().toDomain());
	}

}
