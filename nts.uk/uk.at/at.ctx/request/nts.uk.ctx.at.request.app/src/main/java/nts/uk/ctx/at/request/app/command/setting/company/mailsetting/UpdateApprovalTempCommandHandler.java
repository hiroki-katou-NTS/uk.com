package nts.uk.ctx.at.request.app.command.setting.company.mailsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailapplicationapproval.ApprovalTemp;
import nts.uk.ctx.at.request.dom.setting.company.mailsetting.mailapplicationapproval.ApprovalTempRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * update approval template
 * @author yennth
 *
 */
@Stateless
@Transactional
public class UpdateApprovalTempCommandHandler extends CommandHandler<ApprovalTempCommand>{
	@Inject
	private ApprovalTempRepository appRep;

	@Override
	protected void handle(CommandHandlerContext<ApprovalTempCommand> context) {
		String companyId = AppContexts.user().companyId();
		ApprovalTempCommand data = context.getCommand();
		Optional<ApprovalTemp> appTemp = appRep.getAppTem();
		ApprovalTemp app = ApprovalTemp.createFromJavaType(companyId, data.getContent());
		if(appTemp.isPresent()){
			appRep.update(app);
			return;
		}
		appRep.insert(app);
	}
	
}
