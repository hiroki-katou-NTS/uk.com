package nts.uk.ctx.at.shared.app.command.employeeworkway.medicalworkstyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.MedicalCareWorkStyle;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiCode;
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
		
		if (emwHist.getListDateHistoryItem().size() > 0) {
			List<DateHistoryItem> histItemList = emwHist.getListDateHistoryItem();
			Comparator<DateHistoryItem> compareByStartDate = 
    				(DateHistoryItem hist1, DateHistoryItem hist2) 
    				-> hist1.start().compareTo(hist2.start());
    		Collections.sort(histItemList, compareByStartDate);
			
			DateHistoryItem itemToBeUpdated = histItemList.get(histItemList.size() - 1);
			GeneralDate endDate = command.getStartDate().addDays(-1);
			DateHistoryItem itemUpdate = new DateHistoryItem(itemToBeUpdated.identifier(), new DatePeriod(itemToBeUpdated.start(), endDate));
			emwHist.getListDateHistoryItem().set(histItemList.size() - 1, itemUpdate);
			emwHistRepo.update(emwHist);
		}
		
		GeneralDate endDate = command.getEndDate() !=null? command.getEndDate() : GeneralDate.max();
		DateHistoryItem itemToBeAdded = new DateHistoryItem(newHistId, new DatePeriod(command.getStartDate(), endDate));
		emwHist.add(itemToBeAdded);
		
		EmpMedicalWorkStyleHistoryItem emwHistItem = new EmpMedicalWorkStyleHistoryItem(
				command.getSId(),
				newHistId,
				new NurseClassifiCode(command.getNurseClassifiCode()),
				command.getIsOnlyNightShift().intValue() == 1 ? true : false,
				EnumAdaptor.valueOf(command.getMedicalWorkStyle().intValue(), MedicalCareWorkStyle.class),
				command.getIsConcurrently().intValue() == 1 ? true : false);
		
		emwHistRepo.insert(emwHist, emwHistItem);
		
		return new PeregAddCommandResult(newHistId);
	}

}
