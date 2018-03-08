package nts.uk.screen.at.app.dailymodify.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.DailyRecordWorkCommandHandler;
import nts.uk.ctx.at.record.app.command.dailyperform.editstatecolor.EditStateColorOfDailyPerformCommand;
import nts.uk.ctx.at.record.app.command.dailyperform.editstatecolor.EditStateColorOfDailyPerformCommandAddHandler;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordWorkFinder;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyQuery;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPItemValue;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
/** 日別修正CommandFacade */
public class DailyModifyCommandFacade {

	/** finder */
	@Inject
	private DailyRecordWorkFinder finder;

	@Inject
	private DailyRecordWorkCommandHandler handler;
	
	@Inject
	private EditStateColorOfDailyPerformCommandAddHandler editStateHandler;

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
	
	public void handleEditCell(List<DPItemValue> data){
		String sid = AppContexts.user().employeeId();
		List<EditStateOfDailyPerformance> editData = data.stream().map(x -> {
			return new EditStateOfDailyPerformance(x.getEmployeeId(), x.getItemId(), x.getDate(),
					sid.equals(x.getEmployeeId()) ? EditStateSetting.HAND_CORRECTION_MYSELF
							: EditStateSetting.HAND_CORRECTION_OTHER);
		}).collect(Collectors.toList());
		EditStateColorOfDailyPerformCommand command = new EditStateColorOfDailyPerformCommand();
		command.getData().addAll(editData);
		editStateHandler.handle(command);
	}
	
}
