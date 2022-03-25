package nts.uk.ctx.at.shared.app.command.employeeworkway.medicalworkstyle;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.MedicalCareWorkStyle;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.command.PeregUpdateCommandHandler;

@Stateless
public class UpdateEmpMedicalWorkCommandHandler extends CommandHandler<UpdateEmpMedicalWorkCommand>
	implements PeregUpdateCommandHandler<UpdateEmpMedicalWorkCommand>{
	
	@Inject
	private EmpMedicalWorkStyleHistoryRepository emwHistRepo;

	@Override
	public String targetCategoryCd() {
		return "CS00098";
	}

	@Override
	public Class<?> commandClass() {
		return UpdateEmpMedicalWorkCommand.class;
	}

	@Override
	protected void handle(CommandHandlerContext<UpdateEmpMedicalWorkCommand> context) {
		UpdateEmpMedicalWorkCommand command = context.getCommand();
		String cid = AppContexts.user().companyId();
		if (command.getStartDate() != null) {
			Optional<EmpMedicalWorkStyleHistory> emwHistOp = emwHistRepo.getHistBySid(cid, command.getSId());
			
			if (!emwHistOp.isPresent()) {
				throw new RuntimeException("Invalid EmployeeUnitPriceHistory");
			}
			
			Optional<DateHistoryItem> itemToBeUpdated = emwHistOp.get().getListDateHistoryItem().stream()
					.filter(h -> h.identifier().equals(command.getHistId())).findFirst();
			
			if (!itemToBeUpdated.isPresent()) {
				throw new RuntimeException("Invalid EmployeeUnitPriceHistory");
			}
			
			GeneralDate endDate = command.getEndDate() != null ? command.getEndDate() : GeneralDate.max();
			emwHistOp.get().changeSpan(itemToBeUpdated.get(), new DatePeriod(command.getStartDate(), endDate));
			emwHistOp.get().getListDateHistoryItem().forEach(dateHistItem -> {
				if (dateHistItem.identifier().equals(itemToBeUpdated.get().identifier())) {
					dateHistItem = itemToBeUpdated.get();
				}
			});
			
			emwHistRepo.update(emwHistOp.get());
		}
		
		EmpMedicalWorkStyleHistoryItem emwHistItem = new EmpMedicalWorkStyleHistoryItem(
				command.getSId(),
				command.getHistId(),
				new NurseClassifiCode(command.getNurseClassifiCode()),
				command.getIsOnlyNightShift().intValue() == 1 ? true : false,
				EnumAdaptor.valueOf(command.getMedicalWorkStyle().intValue(), MedicalCareWorkStyle.class),
				command.getIsConcurrently().intValue() == 1 ? true : false);
		
		emwHistRepo.update(emwHistItem);
	}

}
