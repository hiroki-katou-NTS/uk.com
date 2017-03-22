package nts.uk.ctx.pr.core.app.command.itemmaster;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemattend.AddItemAttendCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemdeduct.AddItemDeductCommandHandler;
import nts.uk.ctx.pr.core.app.command.itemmaster.itemsalary.AddItemSalaryCommandHandler;
import nts.uk.ctx.pr.core.dom.enums.CategoryAtr;
import nts.uk.ctx.pr.core.dom.enums.DisplayAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemCode;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemDisplayAtr;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemName;
import nts.uk.ctx.pr.core.dom.itemmaster.UniteCode;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
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

	protected void handle(CommandHandlerContext<AddItemMasterCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		int CategoryAtr = context.getCommand().getCategoryAtr();

		ItemMaster itemMaster = new ItemMaster(new CompanyCode(companyCode),
				new ItemCode(context.getCommand().getItemCode()), new ItemName(context.getCommand().getItemName()),
				new ItemName(context.getCommand().getItemAbName()), new ItemName(context.getCommand().getItemAbNameE()),
				new ItemName(context.getCommand().getItemAbNameO()),
				EnumAdaptor.valueOf(context.getCommand().getCategoryAtr(), CategoryAtr.class),
				context.getCommand().getFixAtr(),
				EnumAdaptor.valueOf(context.getCommand().getDisplaySet(), DisplayAtr.class),
				new UniteCode(context.getCommand().getUniteCode()),
				EnumAdaptor.valueOf(context.getCommand().getZeroDisplaySet(), DisplayAtr.class),
				EnumAdaptor.valueOf(context.getCommand().getItemDisplayAtr(), ItemDisplayAtr.class));
		String itemCode = itemMaster.getItemCode().v();
		// TODO Auto-generated method stub
		if (this.itemMasterRepository.find(companyCode, CategoryAtr, itemCode).isPresent())
			throw new BusinessException(new RawErrorMessage(" 明細書名が入力されていません。"));
		itemMasterRepository.add(itemMaster);
		switch (CategoryAtr) {
		case 0:
			context.getCommand().getItemSalary().setItemCd(itemCode);
			salaryHandler.handle(context.getCommand().getItemSalary());
			break;
		case 1:
			context.getCommand().getItemDeduct().setItemCd(itemCode);
			deductHandler.handle(context.getCommand().getItemDeduct());
			break;
		case 2:
			context.getCommand().getItemAttend().setItemCd(itemCode);
			attendHandler.handle(context.getCommand().getItemAttend());
			break;
		}

	}

}
