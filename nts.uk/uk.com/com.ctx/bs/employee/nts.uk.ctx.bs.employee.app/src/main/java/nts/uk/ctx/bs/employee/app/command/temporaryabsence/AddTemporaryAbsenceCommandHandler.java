package nts.uk.ctx.bs.employee.app.command.temporaryabsence;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsHistRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsItemRepository;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddTemporaryAbsenceCommandHandler extends CommandHandlerWithResult<AddTemporaryAbsenceCommand,PeregAddCommandResult>
	implements PeregAddCommandHandler<AddTemporaryAbsenceCommand>{

	@Inject
	private TempAbsItemRepository temporaryAbsenceRepository;
	
	@Inject
	private TempAbsHistRepository temporaryAbsenceHistRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00018";
	}

	@Override
	public Class<?> commandClass() {
		return AddTemporaryAbsenceCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddTemporaryAbsenceCommand> context) {
		val command = context.getCommand();
		
		String newHistID = IdentifierUtil.randomUniqueId();
		DateHistoryItem dateItem = new DateHistoryItem(newHistID, new DatePeriod(command.getStartDate(), command.getEndDate()));
		
		TempAbsenceHistory itemtoBeAdded = null;
		
		Optional<TempAbsenceHistory> existHist = temporaryAbsenceHistRepository.getByEmployeeId(command.getEmployeeId());
		
		// In case of exist history of this employee
		if (existHist.isPresent()){
			itemtoBeAdded = existHist.get();
		} else {
			// In case of non - exist history of this employee
			itemtoBeAdded = new TempAbsenceHistory(command.getEmployeeId(),new ArrayList<>());
		}
		itemtoBeAdded.add(dateItem);
		
		temporaryAbsenceHistRepository.add(itemtoBeAdded);
		
		TempAbsenceHisItem temporaryAbsence = TempAbsenceHisItem.createTempAbsenceHisItem(command.getLeaveHolidayAtr(), newHistID, command.getEmployeeId(), command.getRemarks(), command.getSoInsPayCategory(), command.isMultiple(),
				command.getFamilyMemberId(), command.isSameFamily(), command.getChildType(), command.getCreateDate(), command.isSpouseIsLeave(), command.getSameFamilyDays());
		temporaryAbsenceRepository.add(temporaryAbsence);
		
		return new PeregAddCommandResult(newHistID);
	}

}
