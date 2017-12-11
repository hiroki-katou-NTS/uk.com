package nts.uk.ctx.bs.employee.app.command.temporaryabsence;

import java.math.BigDecimal;
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
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsHistoryService;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsItemRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddTemporaryAbsenceCommandHandler
		extends CommandHandlerWithResult<AddTemporaryAbsenceCommand, PeregAddCommandResult>
		implements PeregAddCommandHandler<AddTemporaryAbsenceCommand> {

	@Inject
	private TempAbsItemRepository temporaryAbsenceRepository;

	@Inject
	private TempAbsHistRepository temporaryAbsenceHistRepository;

	@Inject
	private TempAbsHistoryService tempAbsHistoryService;

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
		String companyId = AppContexts.user().companyId();

		String newHistID = IdentifierUtil.randomUniqueId();
		DateHistoryItem dateItem = new DateHistoryItem(newHistID,new DatePeriod(command.getStartDate(), command.getEndDate()));

		Optional<TempAbsenceHistory> existHist = temporaryAbsenceHistRepository.getByEmployeeId(command.getEmployeeId());
		
		TempAbsenceHistory itemtoBeAdded = new TempAbsenceHistory(companyId, command.getEmployeeId(), new ArrayList<>());
		// In case of exist history of this employee
		if (existHist.isPresent()) {
			itemtoBeAdded = existHist.get();
		}
		itemtoBeAdded.add(dateItem);

		tempAbsHistoryService.add(itemtoBeAdded);
		
		BigDecimal falseValue = new BigDecimal(0);
		boolean multiple = false;
		if (command.getMultiple() != null){
			multiple = falseValue.compareTo(command.getMultiple()) == 0 ? false : true;
		}
		boolean sameFamily = false;
		if (command.getSameFamily() != null){
			sameFamily = falseValue.compareTo(command.getSameFamily()) == 0 ? false : true;
		}
		boolean spouseIsLeave = false;
		if (command.getSpouseIsLeave() != null){
			spouseIsLeave = falseValue.compareTo(command.getSpouseIsLeave()) == 0 ? false : true;
		}
		
		TempAbsenceHisItem temporaryAbsence = TempAbsenceHisItem.createTempAbsenceHisItem(command.getLeaveHolidayAtr().intValue(),
				newHistID, command.getEmployeeId(), command.getRemarks(), command.getSoInsPayCategory()!= null? command.getSoInsPayCategory().intValue(): 0,
				multiple, command.getFamilyMemberId(), sameFamily, command.getChildType() != null? command.getChildType().intValue(): 0,
				command.getCreateDate(), spouseIsLeave, command.getSameFamilyDays()!= null? command.getSameFamilyDays().intValue(): 0);
		temporaryAbsenceRepository.add(temporaryAbsence);

		return new PeregAddCommandResult(newHistID);
	}

}
