package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.withdrawalrequestset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetRepository;

/**
 * 
 * @author tanlv
 *
 */
@Stateless
public class UpdateWithDrawalReqSetCommandHandler extends CommandHandler<UpdateWithDrawalReqSetCommand> {
	@Inject
	private WithDrawalReqSetRepository repository;

	@Override
	protected void handle(CommandHandlerContext<UpdateWithDrawalReqSetCommand> context) {
		UpdateWithDrawalReqSetCommand data = context.getCommand();
		Optional<WithDrawalReqSet> withDrawalReqSet = repository.getWithDrawalReqSet();
		
		WithDrawalReqSet item = WithDrawalReqSet.createFromJavaType(data.getCompanyId(),
																	data.getPermissionDivision(),
																	data.getAppliDateContrac(),
																	data.getUseAtr(),
																	data.getCheckUpLimitHalfDayHD(),
																	data.getPickUpComment(),
																	data.getPickUpBold(),
																	data.getPickUpLettleColor(),
																	data.getDeferredComment(),
																	data.getDeferredBold(),
																	data.getDeferredLettleColor(),
																	data.getDeferredWorkTimeSelect(),
																	data.getSimulAppliReq(),
																	data.getLettleSuperLeave(),
																	data.getSimutanAppRequired(),
																	data.getLettleSuspensionLeave());
		
		if(withDrawalReqSet.isPresent()){
			repository.updateWithDrawalReqSet(item);
		}
		
		repository.addWithDrawalReqSet(item);
	};
}
