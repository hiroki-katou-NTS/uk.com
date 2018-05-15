package nts.uk.ctx.at.function.app.command.alarm.alarmlist;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.uk.ctx.at.function.app.find.alarm.alarmlist.ExtractAlarmListFinder;
import nts.uk.ctx.at.function.app.find.alarm.alarmlist.ExtractAlarmQuery;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.AlarmExtraValueWkReDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.ExtractedAlarmDto;

@Stateless
public class ErrorAlarmListExtractCommandHandler extends AsyncCommandHandler<ErrorAlarmListCommand> {

	private final int MAX_LENGTH_FOR_DATA_SETTER = 1000;
	@Inject
	private ExtractAlarmListFinder extractAlarmFinder;

	@Override
	protected void handle(CommandHandlerContext<ErrorAlarmListCommand> context) {
		ErrorAlarmListCommand command = context.getCommand();
		ExtractAlarmQuery query = new ExtractAlarmQuery(command.getListEmployee(), command.getAlarmCode(), command.getListPeriodByCategory());
		
		ExtractedAlarmDto dto = extractAlarmFinder.extractAlarm(query);
		TaskDataSetter dataSetter = context.asAsync().getDataSetter();
		try {
			ObjectMapper mapper = new ObjectMapper();
			/** Convert to json string */
			List<String> dataString = new ArrayList<>();
			for(AlarmExtraValueWkReDto a : dto.getExtractedAlarmData()) {
				dataString.add(mapper.writeValueAsString(a));
			}
			for (int i = 0; i < dataString.size(); i++) {
				dataSetter.setData("dataNo" + i, dataString.get(i));
			}
			dataSetter.setData("extracting", dto.isExtracting());
			dataSetter.setData("nullData", dto.isNullData());
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
