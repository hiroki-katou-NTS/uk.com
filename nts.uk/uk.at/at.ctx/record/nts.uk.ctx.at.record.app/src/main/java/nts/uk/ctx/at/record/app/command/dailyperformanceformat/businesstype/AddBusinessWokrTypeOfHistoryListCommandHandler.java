package nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployee;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeOfEmployeeRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeOfHistoryGeneralRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
@Stateless
public class AddBusinessWokrTypeOfHistoryListCommandHandler extends CommandHandlerWithResult<List<AddBusinessWokrTypeOfHistoryCommand>, List<MyCustomizeException>>
implements PeregAddListCommandHandler<AddBusinessWokrTypeOfHistoryCommand>{
	@Inject
	private BusinessTypeOfEmployeeRepository typeOfEmployeeRepos;

	@Inject
	private BusinessTypeEmpOfHistoryRepository typeEmployeeOfHistoryRepos;

	@Inject
	private BusinessTypeOfHistoryGeneralRepository typeOfHistoryGeneralRepos;

	@Override
	public String targetCategoryCd() {
		return "CS00021";
	}

	@Override
	public Class<?> commandClass() {
		return AddBusinessWokrTypeOfHistoryCommand.class;
	}

	@Override
	protected List<MyCustomizeException> handle(
			CommandHandlerContext<List<AddBusinessWokrTypeOfHistoryCommand>> context) {
		List<AddBusinessWokrTypeOfHistoryCommand> cmd  = context.getCommand();
		String cid = AppContexts.user().companyId();
		List<MyCustomizeException> result =  new ArrayList<>();
		List<BusinessTypeOfEmployee> bTypeOfEmployeeLst = new ArrayList<>();
		List<BusinessTypeOfEmployeeHistory> bTypeOfEmployeeHistoryLst = new ArrayList<>();
		// sidsPidsMap
		List<String> sids = cmd.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		Map<String, List<BusinessTypeOfEmployeeHistory>> bTypeOfEmployeeHistLst = typeEmployeeOfHistoryRepos.findByEmployeeDesc(cid,sids).stream().collect(Collectors.groupingBy(c -> c.getEmployeeId()));
		cmd.stream().forEach(c ->{
			try {
				String historyId = IdentifierUtil.randomUniqueId();
				// update in case of startDate is null set to minDate
				GeneralDate startDate = c.getStartDate() != null ? c.getStartDate() : GeneralDate.min();
				// update in case of endDate is null set to maxDate
				GeneralDate endDate = c.getEndDate() != null ? c.getEndDate() : GeneralDate.max();
				List<BusinessTypeOfEmployeeHistory> bTypeOfEmployeeHist = bTypeOfEmployeeHistLst.get(c.getEmployeeId());
				List<DateHistoryItem> history = new ArrayList<DateHistoryItem>();
				BusinessTypeOfEmployeeHistory bEmployeeHistory = new BusinessTypeOfEmployeeHistory(cid, history, c.getEmployeeId());
				if(!CollectionUtil.isEmpty(bTypeOfEmployeeHist)) {
					bEmployeeHistory = bTypeOfEmployeeHist.get(0);
				}
				
				DateHistoryItem newDate = new DateHistoryItem(historyId, new DatePeriod(startDate, endDate));
				bEmployeeHistory.add(newDate);
				bTypeOfEmployeeHistoryLst.add(bEmployeeHistory);
				
				// insert typeof employee
				BusinessTypeOfEmployee bEmployee = BusinessTypeOfEmployee.createFromJavaType(c.getBusinessTypeCode(), historyId,
						c.getEmployeeId());
				bTypeOfEmployeeLst.add(bEmployee);
			}catch(BusinessException e) {
				MyCustomizeException ex = new MyCustomizeException(e.getMessageId(), Arrays.asList(c.getEmployeeId()));
				result.add(ex);
			}

		});
		
		if(!bTypeOfEmployeeHistoryLst.isEmpty()) {
			typeOfHistoryGeneralRepos.addAll(bTypeOfEmployeeHistoryLst);
		}
		
		if(!bTypeOfEmployeeLst.isEmpty()) {
			typeOfEmployeeRepos.addAll(bTypeOfEmployeeLst);
		}
		return result;
	}

}
