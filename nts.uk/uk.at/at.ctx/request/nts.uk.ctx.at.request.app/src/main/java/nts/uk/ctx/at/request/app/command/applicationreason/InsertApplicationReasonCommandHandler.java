package nts.uk.ctx.at.request.app.command.applicationreason;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * insert a item application reason
 * @author yennth
 *
 */
@Stateless
@Transactional
public class InsertApplicationReasonCommandHandler extends CommandHandler<ApplicationReasonCommand>{
	@Inject
	private ApplicationReasonRepository reasonRep;
	
	@Override
	protected void handle(CommandHandlerContext<ApplicationReasonCommand> context) {
		ApplicationReasonCommand insert = context.getCommand();
		String companyId = AppContexts.user().companyId();
		reasonRep.insertReason(ApplicationReason.createNew(companyId, insert.getAppType(), insert.getDispOrder(), insert.getReasonTemp(), insert.getDefaultFlg()));
	}

}
