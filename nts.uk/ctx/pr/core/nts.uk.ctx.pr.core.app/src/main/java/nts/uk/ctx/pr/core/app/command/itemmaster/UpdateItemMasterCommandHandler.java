package nts.uk.ctx.pr.core.app.command.itemmaster;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalarybd.UpdateItemSalaryBDCommand;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemattend.ItemAttendRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.ItemDeductRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBDRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductperiod.ItemDeductPeriodRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalaryRespository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBDRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalaryperiod.ItemSalaryPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateItemMasterCommandHandler extends CommandHandler<UpdateItemMasterCommand> {

	@Inject
	private ItemMasterRepository itemMasterRepository;
	// item salary
	@Inject
	private ItemSalaryRespository itemSalaryRespository;
	@Inject
	private ItemSalaryPeriodRepository itemSalaryPeriodRepository;
	@Inject
	private ItemSalaryBDRepository itemSalaryBDRepository;

	// item deduct
	@Inject
	private ItemDeductRespository itemDeductRespository;
	@Inject
	private ItemDeductPeriodRepository itemDeductPeriodRepository;
	@Inject
	private ItemDeductBDRepository itemDeductBDRepository;
	
	// item attend
	@Inject
	private ItemAttendRespository itemAttendRespository;

	@Override
	protected void handle(CommandHandlerContext<UpdateItemMasterCommand> context) {
		UpdateItemMasterCommand itemCommand = context.getCommand();
		int categoryAtr = itemCommand.getCategoryAtr();
		String companyCode = AppContexts.user().companyCode();
		String itemCode = itemCommand.getItemCode();
		if (!this.itemMasterRepository.find(companyCode, categoryAtr, itemCode).isPresent())
			throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません。"));
		this.itemMasterRepository.update(context.getCommand().toDomain(companyCode));
		switch (categoryAtr) {
		case 0:
			updateItemSalary(context);
			break;
		case 1:
			updateItemDeduct(context);
			break;
		case 2:
			updateItemAttend(context);
			break;
		}

		// TODO Auto-generated method stub

	}

	private void updateItemSalary(CommandHandlerContext<UpdateItemMasterCommand> context) {
		UpdateItemMasterCommand itemCommand = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		String itemCode = itemCommand.getItemCode();
		itemCommand.getItemSalary().setCcd(companyCode);
		itemCommand.getItemSalary().setItemCd(itemCode);
		this.itemSalaryRespository.update(itemCommand.getItemSalary().toDomain());
		this.itemSalaryPeriodRepository.update(itemCommand.getItemPeriod().toDomain());
		for (UpdateItemSalaryBDCommand itemSalaryBD : context.getCommand().getItemBDs())
			this.itemSalaryBDRepository.update(itemSalaryBD.toDomain());
	}

	private void updateItemDeduct(CommandHandlerContext<UpdateItemMasterCommand> context) {
		UpdateItemMasterCommand itemCommand = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		String itemCode = itemCommand.getItemCode();
		itemCommand.getItemDeduct().setCcd(companyCode);
		itemCommand.getItemDeduct().setItemCd(itemCode);
		this.itemDeductRespository.update(itemCommand.getItemDeduct().toDomain());
		this.itemDeductPeriodRepository.update(context.getCommand().getItemPeriod().toDeduct().toDomain());
		for (UpdateItemSalaryBDCommand itemSalaryBD : context.getCommand().getItemBDs())
			this.itemDeductBDRepository.update(itemSalaryBD.toDeduct().toDomain());

	}

	private void updateItemAttend(CommandHandlerContext<UpdateItemMasterCommand> context) {
		UpdateItemMasterCommand itemCommand = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		String itemCode = itemCommand.getItemCode();
		itemCommand.getItemAttend().setCcd(companyCode);
		itemCommand.getItemAttend().setItemCd(itemCode);
		this.itemAttendRespository.update(itemCommand.getItemAttend().toDomain());
	}

}
