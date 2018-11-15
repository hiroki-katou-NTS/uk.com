package nts.uk.screen.at.app.monthlyperformance.correction.command;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.command.monthly.MonthlyRecordWorkCommand;
import nts.uk.ctx.at.record.app.command.monthly.MonthlyRecordWorkCommandHandler;
import nts.uk.ctx.at.record.app.find.monthly.finder.MonthlyRecordWorkFinder;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.IntegrationOfMonthly;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyQuery;
import nts.uk.shr.com.time.calendar.date.ClosureDate;


@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class MonthModifyCommandFacade {

	@Inject
	private MonthlyRecordWorkCommandHandler commandHandler;
	
	@Inject
	private MonthlyRecordWorkFinder finder;
	
	public void handleUpdate(MonthlyModifyQuery query) {
		MonthlyRecordWorkDto dto = toDto(query);
		MonthlyRecordWorkCommand comand = createCommand(dto, query);
		this.commandHandler.handleUpdate(comand);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void handleUpdate(List<MonthlyModifyQuery> query,List<MonthlyRecordWorkDto> values) {
		this.commandHandler.handleUpdate(createMultiCommand(query,values));
	}

	public MonthlyRecordWorkDto toDto(MonthlyModifyQuery query) {
		MonthlyRecordWorkDto oldValues = finder.find(query.getEmployeeId(), new YearMonth(query.getYearMonth()),
				ClosureId.valueOf(query.getClosureId()),
				new ClosureDate(query.getClosureDate().getClosureDay(), query.getClosureDate().getLastDayOfMonth()));
		return AttendanceItemUtil.fromItemValues(oldValues, query.getItems(), AttendanceItemType.MONTHLY_ITEM);
	}
	
	private List<MonthlyRecordWorkCommand> createMultiCommand(List<MonthlyModifyQuery> query,List<MonthlyRecordWorkDto> values) {
		Set<String> emps = new HashSet<>();
		Set<YearMonth> yearmonth = new HashSet<>();
		query.stream().forEach(q -> {
			emps.add(q.getEmployeeId());
			yearmonth.add(new YearMonth(q.getYearMonth()));
		});
		//List<MonthlyRecordWorkDto> oldValues = finder.find(emps, yearmonth);
		return values.stream().map(v -> {
			MonthlyModifyQuery q = query.stream().filter(qr -> {
				return qr.getClosureId() == v.getClosureID() && qr.getEmployeeId().equals(v.getEmployeeId())
						&& v.yearMonth().compareTo(qr.getYearMonth()) == 0 && v.getClosureDate().equals(qr.getClosureDate());
			}).findFirst().orElse(null);
			if(q == null){
				return null;
			}
			IntegrationOfMonthly domain = v.toDomain(v.employeeId(), v.yearMonth(), v.getClosureID(), v.getClosureDate());
			MonthlyRecordWorkDto dtoNew = MonthlyRecordWorkDto.fromOnlyAttTime(domain);
			MonthlyRecordWorkDto dto = AttendanceItemUtil.fromItemValues(dtoNew, q.getItems(), AttendanceItemType.MONTHLY_ITEM);
			return createCommand(dto, q);
		}).filter(v -> v != null).collect(Collectors.toList());
	}

	private MonthlyRecordWorkCommand createCommand(MonthlyRecordWorkDto dto, MonthlyModifyQuery query) {
		MonthlyRecordWorkCommand command = new MonthlyRecordWorkCommand();
		command.withData(dto).fromItems(query.getItems());
		command.yearMonth(new YearMonth(query.getYearMonth()));
		command.closureDate(query.getClosureDate());
		command.closureId(query.getClosureId());
		command.forEmployee(query.getEmployeeId());
		return command;
	}

}
