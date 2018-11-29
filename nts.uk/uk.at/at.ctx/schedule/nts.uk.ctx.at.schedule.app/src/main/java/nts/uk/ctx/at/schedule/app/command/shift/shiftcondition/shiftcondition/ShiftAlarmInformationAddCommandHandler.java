package nts.uk.ctx.at.schedule.app.command.shift.shiftcondition.shiftcondition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
//import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ErrorState;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ExcutionState;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftAlarm;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftAlarmInformation;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftCondition;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftConditionCategory;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftConditionCategoryRepository;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftConditionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * command add shift alarm
 * 
 * @author trungtran
 *
 */
@Stateless
public class ShiftAlarmInformationAddCommandHandler extends AsyncCommandHandler<ShiftAlarmInformationCommand> {
	@Inject
	ShiftConditionRepository conditionRepo;
	@Inject
	ShiftConditionCategoryRepository condtionCateRepo;

	@Override
	protected void handle(CommandHandlerContext<ShiftAlarmInformationCommand> context) {
		ShiftAlarmInformationCommand command = context.getCommand();
		if (command == null) {
			return;
		}
		List<EmployeeCommand> employees = command.getEmployee();
		Map<String, EmployeeCommand> mapEmployee = employees.stream()
				.collect(Collectors.toMap(EmployeeCommand::getEmpId, Function.<EmployeeCommand>identity(), (u, v) -> {
					throw new IllegalStateException(String.format("Duplicate key %s", u));
				}, LinkedHashMap::new));
		 
		List<String> empIds = new ArrayList<>();
		empIds.addAll(mapEmployee.keySet());
		List<Integer> conditionNos = command.getConditionNos();
		TimeWithDayAttr startTime = command.getStartTime() == null ? null : new TimeWithDayAttr(command.getStartTime());
		TimeWithDayAttr endTime = command.getEndTime() == null ? null : new TimeWithDayAttr(command.getEndTime());
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();
		val asyncTask = context.asAsync();
		TaskDataSetter setter = asyncTask.getDataSetter();
		ShiftAlarmInformation alarmInformation = new ShiftAlarmInformation(ErrorState.NO_ERROR,
				ExcutionState.BEFORE_EXCUTION, endTime, startTime, null);
		if (conditionNos.size() == 0) {
			throw new BusinessException("Msg_527");

		}
		alarmInformation = processCheckAlarm(alarmInformation, empIds, conditionNos, startTime, endTime, companyId);
		setter.setData("IS_ERROR", alarmInformation.isError());
		// 条件NO → 条件名称
		List<ShiftCondition> conditions = conditionRepo.getListShiftCondition(companyId);
		Map<Integer, ShiftCondition> mapConditon = conditions.stream()
				.collect(Collectors.toMap(ShiftCondition::getConditionNo, Function.identity()));
		// カテゴリNO → カテゴリ名称
		List<ShiftConditionCategory> conditionCates = condtionCateRepo.getListShifConditionCategory(companyId);
		Map<Integer, ShiftConditionCategory> mapConditonCategory = conditionCates.stream()
				.collect(Collectors.toMap(ShiftConditionCategory::getCategoryNo, Function.identity()));
		if(!alarmInformation.getAlarm().isPresent()){
			return;
		}
		List<ShiftAlarm> liShiftAlarms = alarmInformation.getAlarm().get();
		for (int i = 0; i < liShiftAlarms.size(); i++) {
			ShiftAlarm alarm = liShiftAlarms.get(i);
			String empId = alarm.getSId();
			EmployeeCommand empCommand = mapEmployee.get(empId);
			JsonObject value = Json.createObjectBuilder().add("employeeCode", empCommand.getEmpCd())
					.add("employeeName", empCommand.getEmpName()).add("date", alarm.getStartDate().toString())
					.add("category", mapConditonCategory.get(alarm.getCategory()).getCategoryName().v())
					.add("condition", mapConditon.get(alarm.getConditions()).getConditionName().v())
					.add("message", alarm.getMessage()).build();
			setter.setData("data" + i, value);
		}

	}

	private ShiftAlarmInformation processCheckAlarm(ShiftAlarmInformation alarmInformation, List<String> empIds,
			List<Integer> conditionNos, TimeWithDayAttr startDate, TimeWithDayAttr endDate, String companyId) {
		List<ShiftCondition> conditions = conditionRepo.getShiftCondition(companyId, conditionNos);
		List<ShiftAlarm> shiftAlarm = new ArrayList<>();
		empIds.stream().forEachOrdered((empId) -> {
			alarmInformation.setExcutionState(ExcutionState.DURING_EXCUTION);
			conditions.stream().forEachOrdered((condition) -> {
				// TODO getWorkPlace employee by employeeID and endDate
				// TODO processAlarmChecked
				int category = condition.getCategoryNo();
				String message = condition.getConditionErrorMessage().v();
				int conditionNO = condition.getConditionNo();
				GeneralDate date = GeneralDate.today();
				ShiftAlarm sa = new ShiftAlarm(category, message, conditionNO, empId, date, date);
				shiftAlarm.add(sa);

			});
		});

		alarmInformation.setAlarm(Optional.of(shiftAlarm));
		alarmInformation.setExcutionState(ExcutionState.DONE);
		if (!alarmInformation.getAlarm().get().isEmpty()) {
			alarmInformation.setErrorState(ErrorState.ERROR);
		}
		return alarmInformation;
	}

}
