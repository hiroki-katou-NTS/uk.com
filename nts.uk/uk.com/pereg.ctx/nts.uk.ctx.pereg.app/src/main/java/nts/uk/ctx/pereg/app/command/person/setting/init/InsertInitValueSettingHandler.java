package nts.uk.ctx.pereg.app.command.person.setting.init;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pereg.dom.person.setting.init.PerInfoInitValueSetting;
import nts.uk.ctx.pereg.dom.person.setting.init.PerInfoInitValueSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The InsertInitValueSettingHandler
 * 
 * @author lanlt
 *
 */
@Stateless
@Transactional
public class InsertInitValueSettingHandler extends CommandHandlerWithResult<InsertInitValueSettingCommand, String> {

	@Inject
	private PerInfoInitValueSettingRepository settingRepo;

	@Override
	protected String handle(CommandHandlerContext<InsertInitValueSettingCommand> context) {
		InsertInitValueSettingCommand insert = context.getCommand();
		String companyId = AppContexts.user().companyId();
		String initSettingId = 	IdentifierUtil.randomUniqueId();
		Optional<PerInfoInitValueSetting> setting = this.settingRepo.getDetailInitValSetting(companyId,
				insert.getItemCode());
		if (setting.isPresent()) {
			throw new BusinessException("Msg_3");
		} else {
			PerInfoInitValueSetting initSetting = PerInfoInitValueSetting.createFromJavaType(
					initSettingId, companyId, insert.getItemCode(), insert.getItemName());
			this.settingRepo.insert(initSetting);
		}

		return initSettingId;

	}

}
