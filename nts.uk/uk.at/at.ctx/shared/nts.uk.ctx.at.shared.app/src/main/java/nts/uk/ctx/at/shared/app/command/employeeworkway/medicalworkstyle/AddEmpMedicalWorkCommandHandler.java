package nts.uk.ctx.at.shared.app.command.employeeworkway.medicalworkstyle;

import java.util.ArrayList;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.EmpMedicalWorkStyleHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddEmpMedicalWorkCommandHandler extends CommandHandlerWithResult<AddEmpMedicalWorkCommand, PeregAddCommandResult> 
	implements PeregAddCommandHandler<AddEmpMedicalWorkCommand>{
	
	@Inject
	private EmpMedicalWorkStyleHistoryRepository emwHistRepo;

	@Override
	public String targetCategoryCd() {
		return "CS00098";
	}

	@Override
	public Class<?> commandClass() {
		return AddEmpMedicalWorkCommand.class;
	}

	@Override
	protected PeregAddCommandResult handle(CommandHandlerContext<AddEmpMedicalWorkCommand> context) {
		AddEmpMedicalWorkCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		String newHistId = IdentifierUtil.randomUniqueId();
		
		EmpMedicalWorkStyleHistory emwHist = new EmpMedicalWorkStyleHistory(command.getSId(), new ArrayList<DateHistoryItem>());
		Optional<EmpMedicalWorkStyleHistory> emwHistOp = emwHistRepo.getHistBySid(cid, command.getSId());
		
		if (emwHistOp.isPresent()) {
			emwHist = emwHistOp.get();
		}
		
		GeneralDate endDate = command.getEndDate() !=null? command.getEndDate() : GeneralDate.max();
		DateHistoryItem itemToBeAdded = new DateHistoryItem(newHistId, new DatePeriod(command.getStartDate(), endDate));
		emwHist.add(itemToBeAdded);
		
		return new PeregAddCommandResult(newHistId);
	}

}
