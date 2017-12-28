package nts.uk.screen.at.app.dailymodify.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommandHandler;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.shared.app.util.attendanceitem.AttendanceItemUtil;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyQuery;

@Stateless
@Transactional
/** 日別修正CommandFacade */
public class DailyModifyCommandFacade {

	/** finder */
	@Inject
	private DailyRecordWorkFinder finder;

	@Inject
	private DailyRecordWorkCommandHandler handler;

	public void handleAdd(DailyModifyQuery query) {
		DailyRecordDto dto = toDto(query);
		this.handler.handleAdd(createCommand(dto, query.getEmployeeId(), query.getBaseDate()));
	}

	private DailyRecordDto toDto(DailyModifyQuery query) {
		DailyRecordDto oldValues = finder.find(query.getEmployeeId(), query.getBaseDate());
		return AttendanceItemUtil.toConvertibleAttendanceItem(oldValues, query.getItemValues());
	}

	private DailyRecordWorkCommand createCommand(DailyRecordDto dto, String employeeId, GeneralDate workDate){
		return DailyRecordWorkCommand.open().forEmployeeId(employeeId)
		.withWokingDate(workDate).withData(dto);
	}
}
