package nts.uk.ctx.at.record.app.command.calculationsetting;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.calculationsetting.StampReflectionManagement;
import nts.uk.ctx.at.record.dom.calculationsetting.repository.StampReflectionManagementRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
public class StampReflectionManagementCommandHandler extends  CommandHandler<StampReflectionManagementCommand>{
	/** The Repository */
	@Inject
	private StampReflectionManagementRepository repository;
	@Override
	protected void handle(CommandHandlerContext<StampReflectionManagementCommand> context) {
		// TODO Auto-generated method stub
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
