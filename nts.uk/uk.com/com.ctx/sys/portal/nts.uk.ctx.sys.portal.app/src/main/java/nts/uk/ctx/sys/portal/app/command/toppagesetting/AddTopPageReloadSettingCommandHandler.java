package nts.uk.ctx.sys.portal.app.command.toppagesetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageReloadSetting;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageReloadSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddTopPageReloadSettingCommandHandler extends CommandHandler<ToppageReloadSettingCommand>{
	@Inject
	private TopPageReloadSettingRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<ToppageReloadSettingCommand> context) {
		ToppageReloadSettingCommand command = context.getCommand();
		String cId = AppContexts.user().companyId();
		Optional<TopPageReloadSetting> data = this.repo.getByCompanyId(cId);
		if (data.isPresent()) {
			this.repo.update(TopPageReloadSetting.toDomain(cId, command.getReloadInteval()));
		} else {
			this.repo.insert(TopPageReloadSetting.toDomain(cId, command.getReloadInteval()));
		}
	}
}
