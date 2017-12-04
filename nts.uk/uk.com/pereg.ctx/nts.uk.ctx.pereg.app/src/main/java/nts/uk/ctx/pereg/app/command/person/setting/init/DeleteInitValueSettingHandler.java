package nts.uk.ctx.pereg.app.command.person.setting.init;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.dom.person.setting.init.PerInfoInitValueSetting;
import nts.uk.ctx.pereg.dom.person.setting.init.PerInfoInitValueSettingRepository;
import nts.uk.ctx.pereg.dom.person.setting.init.category.PerInfoInitValSetCtg;
import nts.uk.ctx.pereg.dom.person.setting.init.category.PerInfoInitValSetCtgRepository;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItem;
import nts.uk.ctx.pereg.dom.person.setting.init.item.PerInfoInitValueSetItemRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The DeleteInitValueSettingHandler
 * 
 * @author lanlt
 *
 */
@Stateless
public class DeleteInitValueSettingHandler extends CommandHandler<DeleteInitValueSettingCommand> {

	@Inject
	private PerInfoInitValueSettingRepository settingRepo;

	@Inject
	private PerInfoInitValSetCtgRepository ctgRepo;
	
	@Inject
	private PerInfoInitValueSetItemRepository itemRepo;

	@Override
	protected void handle(CommandHandlerContext<DeleteInitValueSettingCommand> context) {
		DeleteInitValueSettingCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Optional<PerInfoInitValueSetting> isDelete = this.settingRepo.getDetailInitValSetting(companyId,
				command.getSettingCode(), command.getSettingId());
		List<PerInfoInitValSetCtg> ctgLst = this.ctgRepo.getAllInitValueCtg(command.getSettingId());
		List<PerInfoInitValueSetItem> itemLst = this.itemRepo.getAllInitValueItem(command.getSettingId());
		if (isDelete.isPresent()) {
			this.settingRepo.delete(companyId, command.getSettingId(), command.getSettingCode());
			if (!CollectionUtil.isEmpty(ctgLst) || ctgLst != null) {
				this.ctgRepo.delete(command.getSettingId());
				if (!CollectionUtil.isEmpty(itemLst) || itemLst !=null) {
					this.itemRepo.deleteAllBySetId(command.getSettingId());
				}
			}
		}

	}

}
