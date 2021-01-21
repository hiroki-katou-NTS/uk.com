package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applatearrival;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequest_Old;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequestRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Update Late Early Request Command Handler
 * TanLV
 *
 */
@Stateless
@Transactional
public class UpdateLateEarReqHandler extends CommandHandler<LateEarlyRequestCommand> {
	@Inject
	private LateEarlyRequestRepository repository;

	@Override
	protected void handle(CommandHandlerContext<LateEarlyRequestCommand> context) {
		String companyId = AppContexts.user().companyId();
		LateEarlyRequestCommand command = context.getCommand();
		Optional<LateEarlyRequest_Old> lateEarlyRequest = repository.getLateEarlyRequest();
		LateEarlyRequest_Old data = LateEarlyRequest_Old.createFromJavaType(
				companyId, 
				command.getShowResult()
		);
		if(lateEarlyRequest.isPresent()){
			repository.updateLateEarlyRequest(data);
			return;
		}
		repository.addLateEarlyRequest(data);
	}
}
