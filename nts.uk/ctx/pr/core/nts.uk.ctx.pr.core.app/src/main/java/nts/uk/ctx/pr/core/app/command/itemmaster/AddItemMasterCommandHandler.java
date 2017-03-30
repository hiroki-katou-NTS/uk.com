package nts.uk.ctx.pr.core.app.command.itemmaster;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemattend.AddItemAttendCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemdeduct.AddItemDeductCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductbd.AddItemDeductBDCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductperiod.AddItemDeductPeriodCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalary.AddItemSalaryCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalarybd.AddItemSalaryBDCommand;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalarybd.AddItemSalaryBDCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalaryperiod.AddItemSalaryPeriodCommandHandler;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddItemMasterCommandHandler extends CommandHandler<AddItemMasterCommand> {
	@Inject
	private ItemMasterRepository itemMasterRepository;
	@Inject
	private AddItemSalaryCommandHandler salaryHandler;
	@Inject
	private AddItemDeductCommandHandler deductHandler;
	@Inject
	private AddItemAttendCommandHandler attendHandler;
	@Inject
	private AddItemSalaryPeriodCommandHandler salaryPeriodHandler;
	@Inject
	private AddItemDeductPeriodCommandHandler deductPeriodHandler;
	@Inject
	private AddItemSalaryBDCommandHandler salaryBDHandler;
	@Inject
	private AddItemDeductBDCommandHandler deductBDHandler;

	protected void handle(CommandHandlerContext<AddItemMasterCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		int CategoryAtr = context.getCommand().getCategoryAtr();

		ItemMaster itemMaster = context.getCommand().toDomain();
		String itemCD = itemMaster.getItemCode().v();
		// TODO Auto-generated method stub
		if (this.itemMasterRepository.find(companyCode, CategoryAtr, itemCD).isPresent())
			throw new BusinessException(new RawErrorMessage(" 明細書名が入力されていません。"));
		itemMasterRepository.add(itemMaster);

		switch (CategoryAtr) {
		case 0:
			context.getCommand().getItemSalary().setItemCd(itemCD);
			this.salaryHandler.handle(context.getCommand().getItemSalary());
			context.getCommand().getItemPeriod().setItemCd(itemCD);
			this.salaryPeriodHandler.handle(context.getCommand().getItemPeriod());
			for (AddItemSalaryBDCommand addItemSalaryCommand : context.getCommand().getItemBDs()) {
				addItemSalaryCommand.setItemCd(itemCD);
				this.salaryBDHandler.handle(addItemSalaryCommand);
			}
			break;
		case 1:
			context.getCommand().getItemDeduct().setItemCd(itemCD);
			this.deductHandler.handle(context.getCommand().getItemDeduct());
			context.getCommand().getItemPeriod().setItemCd(itemCD);
			this.deductPeriodHandler.handle(context.getCommand().getItemPeriod().toItemDeduct());
			for (AddItemSalaryBDCommand addItemSalaryCommand : context.getCommand().getItemBDs()) {
				addItemSalaryCommand.setItemCd(itemCD);
				this.deductBDHandler.handle(addItemSalaryCommand.toItemDeduct());
			}

			break;
		case 2:
			context.getCommand().getItemAttend().setItemCd(itemCD);
			this.attendHandler.handle(context.getCommand().getItemAttend());
			break;
		}

	}

}
