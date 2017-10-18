package command.person.setting.init;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.person.dom.person.setting.init.PerInfoInitValueSetting;
import nts.uk.ctx.bs.person.dom.person.setting.init.PerInfoInitValueSettingRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * The InsertInitValueSettingHandler
 * @author lanlt
 *
 */
@Stateless
public class InsertInitValueSettingHandler extends  CommandHandler<InsertInitValueSettingCommand> {

	@Inject
	private PerInfoInitValueSettingRepository settingRepo;
	@Override
	protected void handle(CommandHandlerContext<InsertInitValueSettingCommand> context) {
		InsertInitValueSettingCommand insert = context.getCommand();
		String companyId = AppContexts.user().companyId();
		String initValueSettingId = IdentifierUtil.randomUniqueId();
		PerInfoInitValueSetting initSetting = PerInfoInitValueSetting.createFromJavaType(initValueSettingId,companyId, insert.getItemCode(), insert.getItemName());
		this.settingRepo.insert(initSetting);
	}

}
