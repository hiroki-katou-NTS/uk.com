package nts.uk.ctx.at.shared.app.command.worktype;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
//import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.app.service.worktype.WorkTypeService;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
//import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnh
 *
 */
@Stateless
public class UpdateWorkTypeCommandHandler extends CommandHandler<WorkTypeCommandBase> {

	@Inject
	private WorkTypeRepository workTypeRepo;
	
	@Inject
	private WorkTypeService workTypeService;

	@Override
	protected void handle(CommandHandlerContext<WorkTypeCommandBase> context) {

		String companyId = AppContexts.user().companyId();
		WorkTypeCommandBase workTypeCommandBase = context.getCommand();
		// Check work type code is present
		Optional<WorkType> optEntity = workTypeRepo.findByPK(companyId, workTypeCommandBase.getWorkTypeCode());
		if (optEntity.isPresent()) {
			WorkType workType = workTypeCommandBase.toDomain(companyId);
			workType.validate();
			if (workTypeService.isExistingCloseAtr(workType, workTypeCommandBase)) {
				// エラーメッセージ(#Msg_2207)を表示する
				throw new BusinessException("Msg_2207");
			}
			workTypeRepo.update(workType);
			// Remove data before add new data work type set
			workTypeRepo.removeWorkTypeSet(companyId, workTypeCommandBase.getWorkTypeCode());
			// Add data WorkTypeSet in OneDay or Morning and Afternoon
			if (workType.isOneDay()) {
				workTypeCommandBase.getOneDay().setWorkAtr(WorkAtr.OneDay.value);
				WorkTypeSet oneDay = workTypeCommandBase.getOneDay().toDomainWorkTypeSet(companyId);
				if (!workType.getDailyWork().isClosure()) {
					oneDay.changeCloseAtr(null);
				}
				workTypeRepo.addWorkTypeSet(oneDay);
			} else {
				workTypeCommandBase.getMorning().setWorkAtr(WorkAtr.Monring.value);
				WorkTypeSet monring =workTypeCommandBase.getMorning().toDomainWorkTypeSet(companyId);
				monring.changeCloseAtr(null);
				workTypeRepo.addWorkTypeSet(monring);
				
				workTypeCommandBase.getAfternoon().setWorkAtr(WorkAtr.Afternoon.value);
				WorkTypeSet afternoon = workTypeCommandBase.getAfternoon().toDomainWorkTypeSet(companyId);
				afternoon.changeCloseAtr(null);
				workTypeRepo.addWorkTypeSet(afternoon);
			}
		}
	}
}
