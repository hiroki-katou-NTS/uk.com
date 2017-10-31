package nts.uk.ctx.at.shared.app.command.worktype;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
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
		//Add data WorkTypeSet in OneDay or Morning and Afternoon
		if (workType.isOneDay()) {
			workTypeCommandBase.getOneDay().setWorkAtr(WorkAtr.OneDay.value);
			workTypeRepo.addWorkTypeSet(workTypeCommandBase.getOneDay().toDomainWorkTypeSet(companyId));
		} else {
			workTypeCommandBase.getMorning().setWorkAtr(WorkAtr.Monring.value);
			workTypeRepo.addWorkTypeSet(workTypeCommandBase.getMorning().toDomainWorkTypeSet(companyId));
			workTypeCommandBase.getAfternoon().setWorkAtr(WorkAtr.Afternoon.value);
			workTypeRepo.addWorkTypeSet(workTypeCommandBase.getAfternoon().toDomainWorkTypeSet(companyId));
		}
	}
}
