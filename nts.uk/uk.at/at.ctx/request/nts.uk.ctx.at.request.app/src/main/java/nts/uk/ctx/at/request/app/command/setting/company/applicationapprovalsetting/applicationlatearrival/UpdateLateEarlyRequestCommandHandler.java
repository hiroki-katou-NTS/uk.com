package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.applicationlatearrival;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequest;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequestRepository;

/**
 * TanLV
 *
 */
@Stateless
public class UpdateLateEarlyRequestCommandHandler extends CommandHandler<LateEarlyRequestCommand> {
	@Inject
	private LateEarlyRequestRepository repository;

	@Override
	protected void handle(CommandHandlerContext<LateEarlyRequestCommand> context) {
		LateEarlyRequestCommand command = context.getCommand();
		Optional<LateEarlyRequest> lateEarlyRequest = repository.getLateEarlyRequest();
		
		LateEarlyRequest data = LateEarlyRequest.createFromJavaType(
				command.getCompanyId(), 
				command.getShowResult()
		);
		
		if(lateEarlyRequest.isPresent()){
			repository.updateLateEarlyRequest(data);
		}
		
		repository.addLateEarlyRequest(data);
	};
}
