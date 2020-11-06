package nts.uk.ctx.sys.portal.app.command.toppagesetting;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSetting;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPagePersonSettingRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CopyTopPagePersonSettingCommandHandler extends CommandHandler<TopPagePersonSettingCommand> {
	/** The repo. */
	@Inject
	private TopPagePersonSettingRepository repo;

	@Override
	protected void handle(CommandHandlerContext<TopPagePersonSettingCommand> context) {
		String companyId = AppContexts.user().companyId();
		String contractCd = AppContexts.user().contractCode();
		List<TopPagePersonSettingCommandBase> listCommand = context.getCommand().getListTopPagePersonSetting();
		if (listCommand.size() > 0) {
			this.repo.updateAll(contractCd, companyId, listCommand.stream()
					.map(TopPagePersonSetting::createFromMemento)
					.collect(Collectors.toList()));
		}
	}
}
