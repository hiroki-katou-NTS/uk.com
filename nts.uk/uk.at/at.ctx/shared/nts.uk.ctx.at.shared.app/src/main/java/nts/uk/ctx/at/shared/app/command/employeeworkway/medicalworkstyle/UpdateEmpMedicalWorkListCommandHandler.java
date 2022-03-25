package nts.uk.ctx.at.shared.app.command.employeeworkway.medicalworkstyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.MedicalCareWorkStyle;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassifiCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;

@Stateless
public class UpdateEmpMedicalWorkListCommandHandler extends CommandHandlerWithResult<List<UpdateEmpMedicalWorkCommand>, List<MyCustomizeException>>
	implements PeregUpdateListCommandHandler<UpdateEmpMedicalWorkCommand>{

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
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<UpdateEmpMedicalWorkCommand>> context) {
		List<UpdateEmpMedicalWorkCommand> command = context.getCommand();
		String cid = AppContexts.user().companyId();
		List<String> sidErrorLst = new ArrayList<>();
		List<MyCustomizeException> errorExceptionLst = new ArrayList<>();
		// sidsPidsMap
		List<String> sids = command.stream().map(c -> c.getSId()).collect(Collectors.toList());

		Map<String, List<EmpMedicalWorkStyleHistory>> histBySidsMap = emwHistRepo.getHistBySidsAndCid(sids, cid)
				.stream().collect(Collectors.groupingBy(c -> c.getEmpID()));
		
		command.stream().forEach(c -> {
			try {
				// In case of date period are exist in the screen,
				if (c.getStartDate() != null) {
					List<EmpMedicalWorkStyleHistory> histBySidList = histBySidsMap.get(c.getSId());
					if (histBySidList != null && histBySidList.size() > 0) {
						Optional<DateHistoryItem> itemToBeUpdated = histBySidList.get(0).getListDateHistoryItem().stream()
								.filter(h -> h.identifier().equals(c.getHistId())).findFirst();
						if (itemToBeUpdated.isPresent()) {
							GeneralDate endDate = c.getEndDate() != null ? c.getEndDate() : GeneralDate.max();
							histBySidList.get(0).changeSpan(itemToBeUpdated.get(), new DatePeriod(c.getStartDate(), endDate));
							histBySidList.get(0).getListDateHistoryItem().forEach(dateHistItem -> {
								if (dateHistItem.identifier().equals(itemToBeUpdated.get().identifier())) {
									dateHistItem = itemToBeUpdated.get();
								}
							});
							
							emwHistRepo.update(histBySidList.get(0));
						} else {
							sidErrorLst.add(c.getSId());
							return;
						}
					} else {
						sidErrorLst.add(c.getSId());
						return;
					}
				}
				
				EmpMedicalWorkStyleHistoryItem emwHistItem = new EmpMedicalWorkStyleHistoryItem(
						c.getSId(),
						c.getHistId(),
						new NurseClassifiCode(c.getNurseClassifiCode()),
						c.getIsOnlyNightShift().intValue() == 1 ? true : false,
						EnumAdaptor.valueOf(c.getMedicalWorkStyle().intValue(), MedicalCareWorkStyle.class),
						c.getIsConcurrently().intValue() == 1 ? true : false);
				
				emwHistRepo.update(emwHistItem);
			} catch (BusinessException e) {
				MyCustomizeException ex = new MyCustomizeException(e.getMessageId(), Arrays.asList(c.getSId()), "期間");
				errorExceptionLst.add(ex);
			}
		});
		
		if(!sidErrorLst.isEmpty()) {
			errorExceptionLst.add(new MyCustomizeException("invalid employmentHistory", sidErrorLst));
		}
		
		return errorExceptionLst;
	}

}
