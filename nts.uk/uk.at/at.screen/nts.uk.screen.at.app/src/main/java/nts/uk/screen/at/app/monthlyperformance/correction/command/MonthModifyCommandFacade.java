package nts.uk.screen.at.app.monthlyperformance.correction.command;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.app.command.monthly.MonthlyRecordWorkCommand;
import nts.uk.ctx.at.record.app.command.monthly.MonthlyRecordWorkCommandHandler;
import nts.uk.ctx.at.record.app.find.monthly.finder.MonthlyRecordWorkFinder;
import nts.uk.ctx.at.record.app.find.monthly.root.MonthlyRecordWorkDto;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureDate;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyQuery;


@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
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

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void handleUpdate(List<MonthlyModifyQuery> query, List<MonthlyRecordWorkDto> dtos) {
		this.commandHandler.handleUpdate(createMultiCommand(query, dtos));
	}

	public MonthlyRecordWorkDto toDto(MonthlyModifyQuery query) {
		MonthlyRecordWorkDto oldValues = finder.find(query.getEmployeeId(), new YearMonth(query.getYearMonth()),
				ClosureId.valueOf(query.getClosureId()),
				new ClosureDate(query.getClosureDate().getClosureDay(), query.getClosureDate().getLastDayOfMonth()));
		return AttendanceItemUtil.fromItemValues(oldValues, query.getItems(), AttendanceItemType.MONTHLY_ITEM);
	}
	
	private List<MonthlyRecordWorkCommand> createMultiCommand(List<MonthlyModifyQuery> query, List<MonthlyRecordWorkDto> oldValues) {
		List<MonthlyRecordWorkCommand> newValues = new ArrayList<>();
		oldValues.forEach(oldDto -> {
			MonthlyModifyQuery q = query.stream().filter(qr -> {
				return qr.getClosureId() == oldDto.getClosureID() && qr.getEmployeeId().equals(oldDto.getEmployeeId())
						&& oldDto.yearMonth().compareTo(qr.getYearMonth()) == 0 && oldDto.getClosureDate().equals(qr.getClosureDate());
			}).findFirst().orElse(null);
			if (q != null) {
				MonthlyRecordWorkDto newDto = AttendanceItemUtil.fromItemValues(new MonthlyRecordWorkDto(),
						q.getItems(), AttendanceItemType.MONTHLY_ITEM);
				newValues.add(createCommand(newDto, q));
			}
		});
		return newValues;
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
