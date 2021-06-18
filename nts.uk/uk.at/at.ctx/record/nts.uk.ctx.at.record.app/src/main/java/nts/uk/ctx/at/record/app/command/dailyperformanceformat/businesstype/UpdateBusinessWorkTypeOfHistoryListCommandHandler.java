package nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployee;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHistory;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHistoryInter;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeEmpOfHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeOfEmployeeRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.repository.BusinessTypeOfHistoryGeneralRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.MyCustomizeException;
import nts.uk.shr.pereg.app.command.PeregUpdateListCommandHandler;
@Stateless
public class UpdateBusinessWorkTypeOfHistoryListCommandHandler extends CommandHandlerWithResult<List<UpdateBusinessWorkTypeOfHistoryCommand>, List<MyCustomizeException>>
implements PeregUpdateListCommandHandler<UpdateBusinessWorkTypeOfHistoryCommand>{
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
		return UpdateBusinessWorkTypeOfHistoryCommand.class;
	}

	@Override
	protected List<MyCustomizeException> handle(CommandHandlerContext<List<UpdateBusinessWorkTypeOfHistoryCommand>> context) {
		List<UpdateBusinessWorkTypeOfHistoryCommand> cmd = context.getCommand();
		String cid = AppContexts.user().companyId();
		// Update history table
		// In case of date period are exist in the screen
		List<String> errorLst = new ArrayList<>();
		UpdateBusinessWorkTypeOfHistoryCommand updateFirst = cmd.get(0);
		List<BusinessTypeOfEmployeeHistoryInter> bTypeOfEmployeeHistoryInterLst = new ArrayList<>();
		List<BusinessTypeOfEmployee> histItems = new ArrayList<>();
		List<MyCustomizeException> errorExceptionLst = new ArrayList<>();
		List<BusinessTypeOfEmployeeHistory> bTypeOfEmployeeHistory = new ArrayList<>();
		// sidsPidsMap
		List<String> sids = cmd.stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());
		List<String> histIds = cmd.stream().map(c -> c.getHistoryId()).collect(Collectors.toList());

		List<BusinessTypeOfEmployee> bTypeOfEmployee = typeOfEmployeeRepos.findAllByHistIds(histIds);
		
		if(updateFirst.getStartDate()!= null) {
			List<BusinessTypeOfEmployeeHistory> bTypeEmpHistLst = typeEmployeeOfHistoryRepos.findByEmployeeDesc(cid,  sids);
			bTypeOfEmployeeHistory.addAll(bTypeEmpHistLst);
		}
		
		cmd.stream().forEach(c ->{
			try {
				if (c.getStartDate() != null) {
					Optional<BusinessTypeOfEmployeeHistory> optional = bTypeOfEmployeeHistory.stream()
							.filter(item -> item.getEmployeeId().equals(c.getEmployeeId())).findFirst();
					if (!optional.isPresent()) {
						errorLst.add(c.getEmployeeId());
						return;
					}
					BusinessTypeOfEmployeeHistory bEmployeeHistory = optional.get();
					Optional<DateHistoryItem> optionalHisItem = bEmployeeHistory.getHistory().stream()
							.filter(x -> x.identifier().equals(c.getHistoryId())).findFirst();
					if (!optionalHisItem.isPresent()) {
						errorLst.add(c.getEmployeeId());
						return;
					}
					bEmployeeHistory.changeSpan(optionalHisItem.get(),
							new DatePeriod(c.getStartDate(), c.getEndDate()));
					bTypeOfEmployeeHistoryInterLst
							.add(new BusinessTypeOfEmployeeHistoryInter(bEmployeeHistory, optionalHisItem.get()));
				}
				Optional<BusinessTypeOfEmployee> bTypeOfEmp = bTypeOfEmployee.stream()
						.filter(emp -> emp.getHistoryId().equals(c.getHistoryId())).findFirst();
				// update typeof employee
				if (!bTypeOfEmp.isPresent()) {
					errorLst.add(c.getEmployeeId());
					return;
				}
				BusinessTypeOfEmployee bEmployee = BusinessTypeOfEmployee.createFromJavaType(c.getBusinessTypeCode(),
						c.getHistoryId(), c.getEmployeeId());
				histItems.add(bEmployee);
			} catch (BusinessException e) {
				MyCustomizeException ex = new MyCustomizeException(e.getMessageId(), Arrays.asList(c.getEmployeeId()), "期間");
				errorExceptionLst.add(ex);
			}

		});
		
		if(!bTypeOfEmployeeHistoryInterLst.isEmpty()) {
			typeOfHistoryGeneralRepos.updateAll(bTypeOfEmployeeHistoryInterLst);
		}
		
		if(!histItems.isEmpty()) {
			typeOfEmployeeRepos.updateAll(histItems);
		}
		
		if(!errorLst.isEmpty()) {
			//
		}
		return errorExceptionLst;
	}

}
