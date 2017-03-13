package nts.uk.ctx.pr.core.app.command.itemmaster;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
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

	@Override
	protected void handle(CommandHandlerContext<AddItemMasterCommand> context) {

		ItemMaster itemMaster = new ItemMaster(
				new CompanyCode(AppContexts.user().companyCode()),
				new ItemCode(context.getCommand().getItemCode()), 
				new ItemName(context.getCommand().getItemName()),
				new ItemName(context.getCommand().getItemAbName()), 
				new ItemName(context.getCommand().getItemAbNameE()),
				new ItemName(context.getCommand().getItemAbNameO()),
				EnumAdaptor.valueOf(context.getCommand().getCategoryAtr(), CategoryAtr.class),
				context.getCommand().getFixAtr(),
				EnumAdaptor.valueOf(context.getCommand().getDisplaySet(), DisplayAtr.class),
				new UniteCode(context.getCommand().getUniteCode()),
				EnumAdaptor.valueOf(context.getCommand().getZeroDisplaySet(), DisplayAtr.class),
				EnumAdaptor.valueOf(context.getCommand().getItemDisplayAtr(), ItemDisplayAtr.class));
		// TODO Auto-generated method stub
		itemMasterRepository.add(itemMaster);
	}

}
