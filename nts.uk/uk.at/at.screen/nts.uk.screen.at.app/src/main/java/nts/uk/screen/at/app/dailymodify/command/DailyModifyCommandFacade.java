package nts.uk.screen.at.app.dailymodify.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommandHandler;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
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
		this.handler.handleAdd(createCommand(dto, query));
	}
	
	public void handleUpdate(DailyModifyQuery query) {
		DailyRecordDto dto = toDto(query);
		this.handler.handleUpdate(createCommand(dto, query));
	}


	private DailyRecordDto toDto(DailyModifyQuery query) {
		DailyRecordDto oldValues = finder.find(query.getEmployeeId(), query.getBaseDate());
		return AttendanceItemUtil.fromItemValues(oldValues, query.getItemValues());
	}

	private DailyRecordWorkCommand createCommand(DailyRecordDto dto, DailyModifyQuery query){
		return DailyRecordWorkCommand.open().forEmployeeId(query.getEmployeeId())
				.withWokingDate(query.getBaseDate()).withData(dto).fromItems(query.getItemValues());
	}
}
