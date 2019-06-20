package nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployee;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeOfEmployeeRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.businesstype.repository.BusinessTypeOfHistoryGeneralRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;
import nts.uk.shr.pereg.app.command.PeregAddListCommandHandler;
@Stateless
public class AddBusinessWokrTypeOfHistoryListCommandHandler extends CommandHandlerWithResult<List<AddBusinessWokrTypeOfHistoryCommand>, List<PeregAddCommandResult>>
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
	protected List<PeregAddCommandResult> handle(
			CommandHandlerContext<List<AddBusinessWokrTypeOfHistoryCommand>> context) {
		List<AddBusinessWokrTypeOfHistoryCommand> cmd  = context.getCommand();
		String cid = AppContexts.user().companyId();
		List<PeregAddCommandResult> result =  new ArrayList<>();
		List<BusinessTypeOfEmployee> bTypeOfEmployeeLst = new ArrayList<>();
		List<BusinessTypeOfEmployeeHistory> bTypeOfEmployeeHistoryLst = new ArrayList<>();
		// sidsPidsMap
		List<String> sids = cmd.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		List<BusinessTypeOfEmployeeHistory> bTypeOfEmployeeHistLst = typeEmployeeOfHistoryRepos.findByEmployeeDesc(cid,sids);
		cmd.stream().forEach(c ->{
			String historyId = IdentifierUtil.randomUniqueId();
			// update in case of startDate is null set to minDate
			GeneralDate startDate = c.getStartDate() != null ? c.getStartDate() : GeneralDate.min();
			// update in case of endDate is null set to maxDate
			GeneralDate endDate = c.getEndDate() != null ? c.getEndDate() : GeneralDate.max();
			Optional<BusinessTypeOfEmployeeHistory> optional = bTypeOfEmployeeHistLst.stream().filter(item -> item.getEmployeeId().equals(c.getEmployeeId())).findFirst();
			List<DateHistoryItem> history = new ArrayList<DateHistoryItem>();
			BusinessTypeOfEmployeeHistory bEmployeeHistory = new BusinessTypeOfEmployeeHistory(cid, history, c.getEmployeeId());
			if(optional.isPresent()) {
				bEmployeeHistory = optional.get();
			}
			
			DateHistoryItem newDate = new DateHistoryItem(historyId, new DatePeriod(startDate, endDate));
			bEmployeeHistory.add(newDate);
			bTypeOfEmployeeHistoryLst.add(bEmployeeHistory);
			
			// insert typeof employee
			BusinessTypeOfEmployee bEmployee = BusinessTypeOfEmployee.createFromJavaType(c.getBusinessTypeCode(), historyId,
					c.getEmployeeId());
			bTypeOfEmployeeLst.add(bEmployee);
			result.add(new PeregAddCommandResult(historyId));
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
