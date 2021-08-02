package nts.uk.ctx.at.shared.app.command.employeeworkway.medicalworkstyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
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
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;

@Stateless
public class AddEmpMedicalWorkListCommandHandler extends CommandHandlerWithResult<List<AddEmpMedicalWorkCommand>, List<MyCustomizeException>>
	implements PeregAddListCommandHandler<AddEmpMedicalWorkCommand>{

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
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<AddEmpMedicalWorkCommand>> context) {
		List<AddEmpMedicalWorkCommand> command = context.getCommand();
		String cid = AppContexts.user().companyId();
		Map<String, String> recordIds = new HashMap<>();
		List<MyCustomizeException> result = new ArrayList<>();
		
		List<String> sids = command.stream().map(c -> c.getSId()).collect(Collectors.toList());
		
		Map<String, List<EmpMedicalWorkStyleHistory>> histBySidsMap = emwHistRepo.getHistBySidsAndCid(sids, cid)
				.stream().collect(Collectors.groupingBy(c -> c.getEmpID()));
		
		command.stream().forEach(c -> {
			try {
				String newHistId = IdentifierUtil.randomUniqueId();
				GeneralDate startDate = c.getStartDate() != null ? c.getStartDate() : GeneralDate.min();
				
				List<EmpMedicalWorkStyleHistory> histBySidList = histBySidsMap.get(c.getSId());
				EmpMedicalWorkStyleHistory emwHist = new EmpMedicalWorkStyleHistory(c.getSId(), new ArrayList<DateHistoryItem>());
				if (histBySidList != null && histBySidList.size() > 0) {
					emwHist = histBySidList.get(0);
				}
				
				if (emwHist.getListDateHistoryItem().size() > 0) {
					List<DateHistoryItem> histItemList = emwHist.getListDateHistoryItem();
					Comparator<DateHistoryItem> compareByStartDate = 
		    				(DateHistoryItem hist1, DateHistoryItem hist2) 
		    				-> hist1.start().compareTo(hist2.start());
		    		Collections.sort(histItemList, compareByStartDate);
					
					DateHistoryItem itemToBeUpdated = histItemList.get(histItemList.size() - 1);
					GeneralDate endDate = startDate.addDays(-1);
					DateHistoryItem itemUpdate = new DateHistoryItem(itemToBeUpdated.identifier(), new DatePeriod(itemToBeUpdated.start(), endDate));
					emwHist.getListDateHistoryItem().set(histItemList.size() - 1, itemUpdate);
					emwHistRepo.update(emwHist);
				}
				
				GeneralDate endDate = c.getEndDate() != null ? c.getEndDate() : GeneralDate.max();
				DateHistoryItem itemToBeAdded = new DateHistoryItem(newHistId, new DatePeriod(startDate, endDate));
				emwHist.add(itemToBeAdded);
				
				EmpMedicalWorkStyleHistoryItem emwHistItem = new EmpMedicalWorkStyleHistoryItem(
						c.getSId(),
						newHistId,
						new NurseClassifiCode(c.getNurseClassifiCode()),
						c.getIsOnlyNightShift().intValue() == 1 ? true : false,
						EnumAdaptor.valueOf(c.getMedicalWorkStyle().intValue(), MedicalCareWorkStyle.class),
						c.getIsConcurrently().intValue() == 1 ? true : false);
				
				emwHistRepo.insert(emwHist, emwHistItem);
				
				recordIds.put(c.getSId(), newHistId);
				
			}catch(BusinessException e) {
				MyCustomizeException ex = new MyCustomizeException(e.getMessageId(), Arrays.asList(c.getSId()),"期間");
				result.add(ex);
			}
		});
		
		if(!recordIds.isEmpty()) {
			result.add(new MyCustomizeException("NOERROR", recordIds));
		}
		
		return result;
	}

}
