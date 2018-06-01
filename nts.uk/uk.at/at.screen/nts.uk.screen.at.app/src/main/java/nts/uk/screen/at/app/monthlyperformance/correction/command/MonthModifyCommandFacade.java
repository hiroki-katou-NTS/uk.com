package nts.uk.screen.at.app.monthlyperformance.correction.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

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
@Transactional
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

	private MonthlyRecordWorkDto toDto(MonthlyModifyQuery query) {
		MonthlyRecordWorkDto oldValues = finder.find(query.getEmployeeId(), new YearMonth(query.getYearMonth()),
				ClosureId.valueOf(query.getClosureId()),
				new ClosureDate(query.getClosureDate().getClosureDay(), query.getClosureDate().getLastDayOfMonth()));
		return AttendanceItemUtil.fromItemValues(oldValues, query.getItems(), AttendanceItemType.MONTHLY_ITEM);
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
