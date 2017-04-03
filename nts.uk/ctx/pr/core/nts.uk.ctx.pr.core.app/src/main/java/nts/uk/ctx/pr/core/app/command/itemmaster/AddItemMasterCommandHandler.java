package nts.uk.ctx.pr.core.app.command.itemmaster;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalarybd.AddItemSalaryBDCommand;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMaster;
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
public class AddItemMasterCommandHandler extends CommandHandler<AddItemMasterCommand> {
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

	protected void handle(CommandHandlerContext<AddItemMasterCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		int CategoryAtr = context.getCommand().getCategoryAtr();
		ItemMaster itemMaster = context.getCommand().toDomain();
		
		// validate
		itemMaster.validate();
		
		String itemCD = itemMaster.getItemCode().v();
		if (this.itemMasterRepository.find(companyCode, CategoryAtr, itemCD).isPresent())
			throw new BusinessException(" 明細書名が入力されていません。");
		itemMasterRepository.add(itemMaster);
		switch (CategoryAtr) {
		case 0:
			addItemSalary(context);
			break;
		case 1:
			addItemDeduct(context);
			break;
		case 2:
			addItemAttend(context);
			break;
		}

	}

	private void addItemAttend(CommandHandlerContext<AddItemMasterCommand> context) {
		String itemCD = context.getCommand().toDomain().getItemCode().v();
		context.getCommand().getItemAttend().setItemCd(itemCD);
		this.itemAttendRespository.add(context.getCommand().getItemAttend().toDomain());

	}

	private void addItemDeduct(CommandHandlerContext<AddItemMasterCommand> context) {
		String itemCD = context.getCommand().toDomain().getItemCode().v();
		context.getCommand().getItemDeduct().setItemCd(itemCD);
		this.itemDeductRespository.add(context.getCommand().getItemDeduct().toDomain());
		context.getCommand().getItemPeriod().setItemCd(itemCD);
		this.itemDeductPeriodRepository.add(context.getCommand().getItemPeriod().toItemDeduct().toDomain());
		for (AddItemSalaryBDCommand addItemSalaryCommand : context.getCommand().getItemBDs()) {
			addItemSalaryCommand.setItemCd(itemCD);
			this.itemDeductBDRepository.add(addItemSalaryCommand.toItemDeduct().toDomain());
		}
	}

	private void addItemSalary(CommandHandlerContext<AddItemMasterCommand> context) {
		String itemCD = context.getCommand().toDomain().getItemCode().v();
		context.getCommand().getItemSalary().setItemCd(itemCD);
		this.itemSalaryRespository.add(context.getCommand().getItemSalary().toDomain());
		context.getCommand().getItemPeriod().setItemCd(itemCD);
		this.itemSalaryPeriodRepository.add(context.getCommand().getItemPeriod().toDomain());
		for (AddItemSalaryBDCommand addItemSalaryCommand : context.getCommand().getItemBDs()) {
			addItemSalaryCommand.setItemCd(itemCD);
			this.itemSalaryBDRepository.add(addItemSalaryCommand.toDomain());
		}
	}

}
