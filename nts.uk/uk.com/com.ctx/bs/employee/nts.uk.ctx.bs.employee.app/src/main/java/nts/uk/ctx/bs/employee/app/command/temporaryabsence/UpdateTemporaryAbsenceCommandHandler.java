package nts.uk.ctx.bs.employee.app.command.temporaryabsence;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceHistRepository;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TemporaryAbsenceRepository;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateTemporaryAbsenceCommandHandler extends CommandHandler<UpdateTemporaryAbsenceCommand>
	implements PeregUpdateCommandHandler<UpdateTemporaryAbsenceCommand>{

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
		return UpdateTemporaryAbsenceCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateTemporaryAbsenceCommand> context) {
		val command = context.getCommand();
		
		TempAbsenceHistory hist = new TempAbsenceHistory(command.getEmployeeId(),new ArrayList<>());
		hist.add(new DateHistoryItem(command.getHistoyId(), new DatePeriod(command.getStartDate(), command.getEndDate())));
		temporaryAbsenceHistRepository.updateTemporaryAbsenceHist(hist);
		
		TempAbsenceHisItem temporaryAbsence = TempAbsenceHisItem.createTempAbsenceHisItem(command.getLeaveHolidayAtr(), command.getHistoyId(), command.getEmployeeId(), command.getRemarks(), command.getSoInsPayCategory(), command.isMultiple(),
				command.getFamilyMemberId(), command.isSameFamily(), command.getChildType(), command.getCreateDate(), command.isSpouseIsLeave(), command.getSameFamilyDays());
		temporaryAbsenceRepository.updateTemporaryAbsence(temporaryAbsence);
	}

}
