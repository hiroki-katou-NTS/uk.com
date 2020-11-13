package nts.uk.ctx.sys.portal.app.command.toppagesetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageReloadSetting;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageReloadSettingRepository;

@Stateless
public class AddTopPageReloadSettingCommandHandler extends CommandHandler<ToppageReloadSettingCommand>{
	@Inject
	private TopPageReloadSettingRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<ToppageReloadSettingCommand> context) {
		ToppageReloadSettingCommand command = context.getCommand();
		Optional<TopPageReloadSetting> data = this.repo.getByCompanyId(command.getCId());
		if (data.isPresent()) {
			this.repo.update(TopPageReloadSetting.toDomain(command.getCId(), command.getReloadInteval()));
		} else {
			this.repo.insert(TopPageReloadSetting.toDomain(command.getCId(), command.getReloadInteval()));
		}
	}
}
