/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.StringUtil;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceHistory;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.info.service.WorkplaceInfoService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RegisterWorkplaceCommandHandler extends CommandHandler<RegisterWorkplaceCommand> {

	@Inject
	private WorkplaceRepository workplaceRepository;

	@Inject
	private WorkplaceInfoService workplaceInfoService;

	@Inject
	private WorkplaceInfoRepository workplaceInfoRepository;
	
	@Override
	protected void handle(CommandHandlerContext<RegisterWorkplaceCommand> context) {

		RegisterWorkplaceCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Workplace wkp = command.toDomain(companyId);
		String workplaceId = wkp.getWorkplaceId().v();
		String lateHistId = "";
		String addHistId = wkp.getWkpHistoryLatest().getHistoryId().v();
		
		if (command.getWorkplaceHistory().isEmpty()) {
			return;
		}
		
		Optional<Workplace> latestOp = workplaceRepository.findByWorkplaceId(companyId,wkp.getWorkplaceId().v());
		if(latestOp.isPresent())
		{
			Workplace late = latestOp.get();
			lateHistId = late.getWkpHistoryLatest().getHistoryId().v();
			//validate add new history
			this.validateDate(wkp,late);
		}
		
		// add workplace
		workplaceRepository.add(wkp);
		
		//Update endDate previous history
		if(StringUtil.isNullOrEmpty(lateHistId,true))
		{
//			this.updatePreviousHistory(late.getWkpHistoryLatest(),wkp.getWkpHistoryLatest().getPeriod().getStartDate());
		}
		// copy workplace info by historyId
		this.copyWorkplaceInfo(companyId,workplaceId,lateHistId,addHistId);
	}

	private void updatePreviousHistory(WorkplaceHistory wkpHistory, GeneralDate startDate) {
//		Optional<Workplace> updateOp = workplaceRepository.findByWorkplaceId(companyId, workplaceId)
	}

	/**
	 * Validate date.
	 *
	 * @param wkp the wkp
	 */
	private void validateDate(Workplace wkp,Workplace late) {
		GeneralDate addDate = wkp.getWkpHistoryLatest().getPeriod().getStartDate();
		GeneralDate latestDate = late.getWkpHistoryLatest().getPeriod().getStartDate();
		if (addDate.before(latestDate)) {
			throw new BusinessException("Msg_102");
		}
	}

	/**
	 * Copy workplace info.
	 *
	 * @param companyId the company id
	 * @param workplaceId the workplace id
	 * @param lateHistId the late hist id
	 * @param addHistId the add hist id
	 */
	private void copyWorkplaceInfo(String companyId, String workplaceId, String lateHistId, String addHistId) {
		Optional<WorkplaceInfo> findItem = workplaceInfoRepository.find(companyId, workplaceId, lateHistId);
		if (findItem.isPresent()) {
			WorkplaceInfo updateItem = findItem.get().cloneWithHistoryId(addHistId);
			workplaceInfoRepository.update(updateItem);
		}
	}
}
