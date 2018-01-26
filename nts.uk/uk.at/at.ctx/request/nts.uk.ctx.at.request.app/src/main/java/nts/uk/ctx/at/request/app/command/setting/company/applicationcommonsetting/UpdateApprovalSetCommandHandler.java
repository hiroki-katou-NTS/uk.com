package nts.uk.ctx.at.request.app.command.setting.company.applicationcommonsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.ApprovalSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.ApprovalSetRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * update approval set
 * @author yennth
 *
 */
@Stateless
public class UpdateApprovalSetCommandHandler extends CommandHandler<ApprovalSetCommand>{
	@Inject
	private ApprovalSetRepository approRep;

	@Override
	protected void handle(CommandHandlerContext<ApprovalSetCommand> context) {
		ApprovalSetCommand data = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Optional<ApprovalSet> app = approRep.getApproval();
		ApprovalSet appro = data.toDomain(companyId);
		if(app.isPresent()){
			approRep.update(appro);
		}
		approRep.insert(appro);
	}
	
}
