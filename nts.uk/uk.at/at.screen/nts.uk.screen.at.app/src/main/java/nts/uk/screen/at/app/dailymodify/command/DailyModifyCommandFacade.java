package nts.uk.screen.at.app.dailymodify.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

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
	
	public void handle(DailyModifyQuery query) {
		DailyRecordDto dto = toDto(query);
		DailyRecordWorkCommand command = new DailyRecordWorkCommand();
		command.setRecords(dto);
		this.handler.handle(command);
	}

	private DailyRecordDto toDto(DailyModifyQuery query) {
		DailyRecordDto oldValues = finder.find(query.getEmployeeId(), query.getBaseDate());
		return AttendanceItemUtil.toConvertibleAttendanceItem(oldValues, query.getItemValues());
	}
	
}
