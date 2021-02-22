package nts.uk.ctx.at.record.app.command.kmk004.s;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author chungnt
 *
 */

@Stateless
public class UsageUnitSettingCommandHandler extends CommandHandler<UsageUnitSettingCommand> {

	@Inject
	private UsageUnitSettingRepository repo;

	@Override
	protected void handle(CommandHandlerContext<UsageUnitSettingCommand> context) {
		String cid = AppContexts.user().companyId();

		UsageUnitSetting setting = new UsageUnitSetting(new CompanyId(cid), context.getCommand().isEmployee(),
				context.getCommand().isWorkPlace(), context.getCommand().isEmployment());

		this.repo.update(setting);
	}
}
