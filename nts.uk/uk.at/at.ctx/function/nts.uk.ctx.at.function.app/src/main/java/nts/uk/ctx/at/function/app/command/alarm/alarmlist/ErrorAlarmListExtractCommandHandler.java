package nts.uk.ctx.at.function.app.command.alarm.alarmlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.app.find.alarm.alarmlist.ExtractAlarmListFinder;
import nts.uk.ctx.at.function.app.find.alarm.alarmlist.ExtractAlarmQuery;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.AlarmExtraValueWkReDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.EmployeeSearchDto;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.ExtractedAlarmDto;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatus;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.AlarmListExtraProcessStatusRepository;
import nts.uk.ctx.at.function.dom.alarm.extraprocessstatus.ExtractionState;

@Stateless
public class ErrorAlarmListExtractCommandHandler extends AsyncCommandHandler<ErrorAlarmListCommand> {

	@Inject
	private ExtractAlarmListFinder extractAlarmFinder;
	
	@Inject
	private AlarmListExtraProcessStatusRepository repo;

	@Override
	protected void handle(CommandHandlerContext<ErrorAlarmListCommand> context) {
		val asyncContext = context.asAsync();
		int numberEmpProcess = 0;

		ErrorAlarmListCommand command = context.getCommand();
		List<EmployeeSearchDto> listEmpId = command.getListEmployee();

		TaskDataSetter dataSetter = asyncContext.getDataSetter();
		dataSetter.setData("empCount", numberEmpProcess);

		ObjectMapper mapper = new ObjectMapper();
		/** Convert to json string */
		List<String> dataString = new ArrayList<>();
		boolean isExtracting = false;
		dataSetter.setData("extracting", isExtracting);
		for (EmployeeSearchDto employeeSearchDto : listEmpId) {
			// get status check
			Optional<AlarmListExtraProcessStatus> alarmListExtraProcessStatus= this.repo.getAlListExtaProcessByID(context.getCommand().getStatusProcessId());
			if (alarmListExtraProcessStatus.isPresent()
					&& alarmListExtraProcessStatus.get().getStatus() == ExtractionState.INTERRUPTION) {
				asyncContext.finishedAsCancelled();
				break;
			}
			numberEmpProcess++;
			dataSetter.updateData("empCount", numberEmpProcess);
			List<EmployeeSearchDto> lstTemp = new ArrayList<>();
			lstTemp.add(employeeSearchDto);
			ExtractAlarmQuery query = new ExtractAlarmQuery(lstTemp, command.getAlarmCode(),
					command.getListPeriodByCategory());
			ExtractedAlarmDto dto = extractAlarmFinder.extractAlarm(query);
			try {
				for (AlarmExtraValueWkReDto a : dto.getExtractedAlarmData()) {
					dataString.add(mapper.writeValueAsString(a));
				}
				isExtracting = dto.isExtracting();
				dataSetter.updateData("extracting", isExtracting);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		}
		for (int i = 0; i < dataString.size(); i++) {
			dataSetter.setData("dataNo" + i, dataString.get(i));
		}

		if (CollectionUtil.isEmpty(dataString)) {
			dataSetter.setData("nullData", true);
		}

	}

}