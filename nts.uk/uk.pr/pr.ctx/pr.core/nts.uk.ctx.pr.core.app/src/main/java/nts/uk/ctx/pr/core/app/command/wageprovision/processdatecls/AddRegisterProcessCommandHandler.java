package nts.uk.ctx.pr.core.app.command.wageprovision.processdatecls;

import java.util.Iterator;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.AbolitionAtr;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformation;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.ProcessInformationRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddRegisterProcessCommandHandler extends CommandHandler<AddRegisterProcessCommand> {
	@Inject
	private ProcessInformationRepository repoProcessInformation;

	@Override
	protected void handle(CommandHandlerContext<AddRegisterProcessCommand> context) {
		// TODO Auto-generated method stub
		AddRegisterProcessCommand addCommand = context.getCommand();
		String cid = AppContexts.user().companyId();
		int deprecatedCategory = AbolitionAtr.NOT_ABOLITION.value;
		// ドメインモデル「処理区分基本情報」を取得する
		Optional<ProcessInformation> dataProcessInformation = repoProcessInformation.getProcessInformationByDeprecatedCategory(cid, deprecatedCategory);
		if (dataProcessInformation.isPresent()) {

		}

	}

}
