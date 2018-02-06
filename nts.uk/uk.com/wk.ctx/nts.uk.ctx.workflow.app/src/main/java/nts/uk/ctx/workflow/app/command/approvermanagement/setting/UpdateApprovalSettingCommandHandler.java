package nts.uk.ctx.workflow.app.command.approvermanagement.setting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
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
		ApprovalSettingCommand data = context.getCommand();
		Optional<ApprovalSetting> approOld = appRep.getApprovalByComId(data.getCompanyId());
		ApprovalSetting appro = ApprovalSetting.createFromJavaType(data.getCompanyId(), data.getPrinFlg());
		appro.validate();
		if(approOld.isPresent()){
			appRep.update(appro);
			return;
		}
		appRep.insert(appro);
	}
	
}
