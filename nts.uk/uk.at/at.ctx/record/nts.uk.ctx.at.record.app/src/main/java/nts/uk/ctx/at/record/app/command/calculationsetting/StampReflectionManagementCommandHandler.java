package nts.uk.ctx.at.record.app.command.calculationsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.shared.dom.calculationsetting.repository.StampReflectionManagementRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class StampReflectionManagementCommandHandler.
 *
 * @author phongtq
 */
@Stateless
public class StampReflectionManagementCommandHandler extends  CommandHandler<StampReflectionManagementCommand>{
	
	/**  The Repository. */
	@Inject
	private StampReflectionManagementRepository repository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<StampReflectionManagementCommand> context) {
		StampReflectionManagementCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		// convert to domain
		StampReflectionManagement reflectionManagement = command.toDomain(companyId);
		reflectionManagement.validate();
		Optional<StampReflectionManagement> optionalHoliday = this.repository.findByCid(companyId);
		if (optionalHoliday.isPresent()) {
			// update Holiday Addtime
			this.repository.update(reflectionManagement);
		}else {
			// add Holiday Addtime
			this.repository.add(reflectionManagement);
		};
	}

}
