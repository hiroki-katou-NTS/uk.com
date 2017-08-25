package nts.uk.ctx.at.shared.app.command.worktype;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class InsertWorkTypeCommandHandler extends CommandHandler<WorkTypeCommandBase> {

	@Inject
	public WorkTypeRepository workTypeRepo;

	@Override
	protected void handle(CommandHandlerContext<WorkTypeCommandBase> context) {
		String companyId = AppContexts.user().companyId();
		WorkTypeCommandBase workTypeCommandBase = context.getCommand();
		// Check language is selected
		// if language is japanese
		if (workTypeRepo.findByPK(companyId, workTypeCommandBase.getWorkTypeCode()).isPresent()) {
			throw new BusinessException("Msg_3");
		}
		WorkType workType = workTypeCommandBase.toDomain(companyId);
		workType.validate();
		
		workTypeRepo.add(workType);
	}
}
