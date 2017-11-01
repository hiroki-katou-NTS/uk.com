package nts.uk.shr.com.permit.app;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.permit.AvailabilityPermissionBase;
import nts.uk.shr.com.permit.AvailabilityPermissionRepositoryBase;
import nts.uk.shr.com.permit.RestoreAvailabilityPermission;

public abstract class SaveAvailabilityPermissionCommandHandler
		<D extends AvailabilityPermissionBase, R extends AvailabilityPermissionRepositoryBase<D>>
		extends CommandHandler<SaveAvailabilityPermissionCommand> {

	@Override
	protected void handle(CommandHandlerContext<SaveAvailabilityPermissionCommand> context) {
		
		val repository = this.getInjectedRepository();
		String companyId = AppContexts.user().companyId();
		String roleId = context.getCommand().getRoleId();
		
		context.getCommand().createRestore(companyId).forEach(restore -> {
			val existed = repository.find(companyId, roleId, restore.functionNo());
			val domainToSave = this.createDomain(restore);
			if (existed.isPresent()) {
				repository.update(domainToSave);
			} else {
				repository.add(domainToSave);
			}
		});
	}

	protected abstract R getInjectedRepository();
	
	protected abstract D createDomain(RestoreAvailabilityPermission restore);
}
