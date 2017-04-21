package nts.uk.ctx.pr.core.app.command.itemmaster;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMaster;
import nts.uk.ctx.pr.core.dom.itemmaster.ItemMasterRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class UpdateItemMasterNameCommandHandler extends CommandHandler<List<UpdateItemMasterCommand>> {

	@Inject
	private ItemMasterRepository itemMasterRepository;

	@Override
	protected void handle(CommandHandlerContext<List<UpdateItemMasterCommand>> context) {
		String companyCode = AppContexts.user().companyCode();
		for (UpdateItemMasterCommand updateCommand : context.getCommand()) {
			int categoryAtr = updateCommand.getCategoryAtr();
			String itemCode = updateCommand.getItemCode();
			Optional<ItemMaster> itemmaster = this.itemMasterRepository.find(companyCode, categoryAtr, itemCode);
			if (!itemmaster.isPresent())
				throw new BusinessException(new RawErrorMessage("更新対象のデータが存在しません。"));
			ItemMaster newItemmaster = ItemMaster.createFromJavaType(companyCode, itemCode,
					itemmaster.get().getItemName().v(), itemmaster.get().getItemAbName().v(),
					updateCommand.getItemAbNameE(), updateCommand.getItemAbNameO(),
					itemmaster.get().getCategoryAtr().value, itemmaster.get().getFixAtr(),
					itemmaster.get().getDisplaySet().value, itemmaster.get().getUniteCode().v(),
					itemmaster.get().getZeroDisplaySet().value, itemmaster.get().getItemDisplayAtr().value);
			this.itemMasterRepository.update(newItemmaster);
		}
	}
}
