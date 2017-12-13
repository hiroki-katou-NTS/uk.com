package nts.uk.ctx.at.record.app.command.dailyperformanceformat.businesstype;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.pereg.app.command.PeregAddCommandHandler;
import nts.uk.shr.pereg.app.command.PeregAddCommandResult;

@Stateless
public class AddBusinessWokrTypeOfHistoryCommandHandler
		extends CommandHandlerWithResult<AddBusinessWokrTypeOfHistoryCommand, PeregAddCommandResult>
		implements PeregAddCommandHandler<AddBusinessWokrTypeOfHistoryCommand> {
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
	protected PeregAddCommandResult handle(CommandHandlerContext<AddBusinessWokrTypeOfHistoryCommand> context) {
		String companyId = AppContexts.user().companyId();
		String historyId = IdentifierUtil.randomUniqueId();

		AddBusinessWokrTypeOfHistoryCommand command = context.getCommand();
		String employeeId = command.getEmployeeId();
		String businessTypeCode = command.getBusinessTypeCode();
		GeneralDate startDate = command.getStartDate();
		GeneralDate endDate = command.getEndDate();
		Optional<BusinessTypeOfEmployeeHistory> optional = typeEmployeeOfHistoryRepos.findByEmployee(employeeId);
		BusinessTypeOfEmployeeHistory bEmployeeHistory = new BusinessTypeOfEmployeeHistory();
		List<DateHistoryItem> history = new ArrayList<DateHistoryItem>();
		if (optional.isPresent()) {
			bEmployeeHistory = optional.get();
		} else {
			bEmployeeHistory = new BusinessTypeOfEmployeeHistory(companyId, history, employeeId);
		}
		DateHistoryItem newDate = new DateHistoryItem(historyId, new DatePeriod(startDate, endDate));
		bEmployeeHistory.add(newDate);
		typeOfHistoryGeneralRepos.addBusinessTypeEmpOfHistory(bEmployeeHistory);
		// insert typeof employee
		BusinessTypeOfEmployee bEmployee = new BusinessTypeOfEmployee(new BusinessTypeCode(businessTypeCode), historyId,
				employeeId);
		this.typeOfEmployeeRepos.insert(bEmployee);
		return new PeregAddCommandResult(historyId);
	}

}
