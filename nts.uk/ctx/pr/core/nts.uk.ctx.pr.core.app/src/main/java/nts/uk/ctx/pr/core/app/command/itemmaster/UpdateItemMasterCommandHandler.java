package nts.uk.ctx.pr.core.app.command.itemmaster;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemattend.UpdateItemAttendCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemdeduct.UpdateItemDeductCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductbd.UpdateItemDeductBDCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductperiod.UpdateItemDeductPeriodCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalary.UpdateItemSalaryCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalarybd.UpdateItemSalaryBDCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalaryperiod.UpdateItemSalaryPeriodCommandHandler;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateItemMasterCommandHandler extends CommandHandler<UpdateItemMasterCommand> {

	@Inject
	private ItemMasterRepository itemMasterRepository;
	@Inject
	private UpdateItemSalaryCommandHandler itemSalaryHandler;
	@Inject
	private UpdateItemDeductCommandHandler itemDeductHandler;
	@Inject
	private UpdateItemAttendCommandHandler itemAttendHandler;
	@Inject
	private UpdateItemSalaryPeriodCommandHandler itemSalaryPeriodHandler;
	@Inject
	private UpdateItemDeductPeriodCommandHandler itemDeductPeriodHandler;
	@Inject
	private UpdateItemSalaryBDCommandHandler itemSalaryBDHandler;
	@Inject
	private UpdateItemDeductBDCommandHandler itemDeductBDHandler;

	@Override
	protected void handle(CommandHandlerContext<UpdateItemMasterCommand> context) {
		UpdateItemMasterCommand itemCommand = context.getCommand();
		int categoryAtr = itemCommand.getCategoryAtr();
		String companyCode = AppContexts.user().companyCode();
		String itemCode = itemCommand.getItemCode();
		if (!this.itemMasterRepository.find(companyCode, categoryAtr, itemCode).isPresent())
			throw new BusinessException(new RawErrorMessage(" 明細書名が入力されていません。"));
		this.itemMasterRepository.update(context.getCommand().toDomain(companyCode));
		switch (categoryAtr) {
		case 0:
			itemCommand.getItemSalary().setCcd(companyCode);
			itemCommand.getItemSalary().setItemCd(itemCode);
			this.itemSalaryHandler.handle(itemCommand.getItemSalary());
			this.itemSalaryPeriodHandler.handle(context.getCommand().getItemSalaryPeriod());
			this.itemSalaryBDHandler.handle(context.getCommand().getItemSalaryBD());
			break;
		case 1:
			itemCommand.getItemDeduct().setCcd(companyCode);
			itemCommand.getItemDeduct().setItemCd(itemCode);
			this.itemDeductHandler.handle(itemCommand.getItemDeduct());
			this.itemDeductPeriodHandler.handle(context.getCommand().getItemDeductPeriod());
			this.itemDeductBDHandler.handle(context.getCommand().getItemDeductBD());
			break;
		case 2:
			itemCommand.getItemAttend().setCcd(companyCode);
			itemCommand.getItemAttend().setItemCd(itemCode);
			this.itemAttendHandler.handle(itemCommand.getItemAttend());
			break;
		}

		// TODO Auto-generated method stub

	}

}
