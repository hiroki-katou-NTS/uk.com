package nts.uk.ctx.bs.employee.app.command.temporaryabsence;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceHistRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddTemporaryAbsenceCommandHandler extends CommandHandlerWithResult<AddTemporaryAbsenceCommand,PeregAddCommandResult>
	implements PeregAddCommandHandler<AddTemporaryAbsenceCommand>{

	@Inject
	private TemporaryAbsenceRepository temporaryAbsenceRepository;
	
	@Inject
	private TemporaryAbsenceHistRepository temporaryAbsenceHistRepository;
	
	@Override
	public String targetCategoryCd() {
		return "CS00008";
	}

	@Override
	public Class<?> commandClass() {
		return AddTemporaryAbsenceCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddTemporaryAbsenceCommand> context) {
		val command = context.getCommand();
		
		String newHistID = IdentifierUtil.randomUniqueId();
		
		TempAbsenceHistory hist = new TempAbsenceHistory(command.getEmployeeId(),new ArrayList<>());
		hist.add(new DateHistoryItem(newHistID, new DatePeriod(command.getStartDate(), command.getEndDate())));
		temporaryAbsenceHistRepository.addTemporaryAbsenceHist(hist);
		
		TempAbsenceHisItem temporaryAbsence = TempAbsenceHisItem.createTempAbsenceHisItem(command.getLeaveHolidayAtr(), newHistID, command.getEmployeeId(), command.getRemarks(), command.getSoInsPayCategory(), command.isMultiple(),
				command.getFamilyMemberId(), command.isSameFamily(), command.getChildType(), command.getCreateDate(), command.isSpouseIsLeave(), command.getSameFamilyDays());
		temporaryAbsenceRepository.addTemporaryAbsence(temporaryAbsence);
		
		return new PeregAddCommandResult(newHistID);
	}

}
