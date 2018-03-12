package nts.uk.ctx.workflow.app.command.approvermanagement.setting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author yennth
 *
 */
@Stateless
@Transactional
public class UpdateApprovalSettingCommandHandler extends CommandHandler<ApprovalSettingCommand>{
	@Inject
	private ApprovalSettingRepository appRep;

	@Override
	protected void handle(CommandHandlerContext<ApprovalSettingCommand> context) {
		String companyId = AppContexts.user().companyId();
		ApprovalSettingCommand data = context.getCommand();		
		ApprovalSetting appro = ApprovalSetting.createFromJavaType(companyId, data.getPrinFlg());
		appro.validate();
		
		Optional<ApprovalSetting> approOld = appRep.getApprovalByComId(companyId);
		if(approOld.isPresent()){
			appRep.update(appro);
			return;
		}
		appRep.insert(appro);
	}
	
}
