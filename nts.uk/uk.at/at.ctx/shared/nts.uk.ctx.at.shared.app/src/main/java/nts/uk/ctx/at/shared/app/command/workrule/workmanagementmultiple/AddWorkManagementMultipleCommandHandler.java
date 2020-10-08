package nts.uk.ctx.at.shared.app.command.workrule.workmanagementmultiple;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.UseATR;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleDomainEvent;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AddWorkManagementMultipleCommandHandler.
 *
 * @author HoangNDH
 */
@Stateless
public class AddWorkManagementMultipleCommandHandler
		extends CommandHandler<AddWorkManagementMultipleCommand> {

	/** The repository. */
	@Inject
	WorkManagementMultipleRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AddWorkManagementMultipleCommand> context) {
		AddWorkManagementMultipleCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		Optional<WorkManagementMultiple> optWorkMntMultiple = repository.findByCode(companyId);
		WorkManagementMultiple setting = WorkManagementMultiple.createFromJavaType(companyId,
				command.getUseAtr());
		if (optWorkMntMultiple.isPresent()) {
			repository.update(setting);
		} else {
			repository.insert(setting);
		}

		// check managementCategory change
		if (!optWorkMntMultiple.isPresent() || !optWorkMntMultiple.get().getUseATR()
				.equals(EnumAdaptor.valueOf(command.getUseAtr(), UseATR.class))) {
			val workManagementMultipleDomainEvent = new WorkManagementMultipleDomainEvent(
					command.getUseAtr() == UseAtr.USE.value);
			workManagementMultipleDomainEvent.toBePublished();
		}

	}

}
