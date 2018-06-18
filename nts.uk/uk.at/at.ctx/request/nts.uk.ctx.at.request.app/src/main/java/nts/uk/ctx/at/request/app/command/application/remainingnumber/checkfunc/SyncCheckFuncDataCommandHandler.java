package nts.uk.ctx.at.request.app.command.application.remainingnumber.checkfunc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.SyEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SyEmployeeImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.AnnualBreakManageAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.AnnualBreakManageImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.DailyWorkTypeListImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.YearlyHolidaysTimeRemainingImport;
import nts.uk.ctx.at.request.dom.application.remainingnumer.ExcelInforCommand;
import nts.uk.ctx.at.request.dom.application.remainingnumer.PlannedVacationListCommand;
import nts.uk.ctx.at.request.dom.vacation.history.PlanVacationHistory;
import nts.uk.ctx.at.request.dom.vacation.history.VacationHistoryRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateful
public class SyncCheckFuncDataCommandHandler extends AsyncCommandHandler<CheckFuncDataCommand> {

	private static final String NUMBER_OF_SUCCESS = "NUMBER_OF_SUCCESS";
	private static final String NUMBER_OF_ERROR = "NUMBER_OF_ERROR";
	private static final String MSG_1116 = "Msg_1116";
	private static final String ERROR_LIST = "ERROR_LIST";

	@Inject
	private SyEmployeeAdapter employeeRecordAdapter;

	@Inject
	private WorkTypeRepository workTypeRepository;

	@Inject
	private AnnualBreakManageAdapter annualBreakManageAdapter;

	@Inject
	private VacationHistoryRepository vacationHistoryRepository;
	
	@Override
	protected void handle(CommandHandlerContext<CheckFuncDataCommand> context) {
		val asyncTask = context.asAsync();
		TaskDataSetter setter = asyncTask.getDataSetter();

		// employee export list, error export list
		List<EmployeeSearchCommand> employeeListResult = new ArrayList<>();
		List<OutputErrorInfoCommand> outputErrorInfoCommand = new ArrayList<>();

		// get data from client to server
		CheckFuncDataCommand command = context.getCommand();
		List<EmployeeSearchCommand> employeeSearchCommand = command.getEmployeeList();
		setter.setData(NUMBER_OF_SUCCESS, command.getPass());
		setter.setData(NUMBER_OF_ERROR, command.getError());
		if (asyncTask.hasBeenRequestedToCancel()) {
			return;
		}
		// アルゴリズム「社員ID、期間をもとに期間内に年休付与日がある社員を抽出する」を実行する
		// RequestList 要求No304. 
		List<String> employeeList = employeeSearchCommand.stream().map(f -> f.getEmployeeId())
				.collect(Collectors.toList());
		if (asyncTask.hasBeenRequestedToCancel()) {
			asyncTask.finishedAsCancelled();
			return;
		}
		List<AnnualBreakManageImport> employeeIdRq304 = annualBreakManageAdapter.getEmployeeId(employeeList,
				command.getStartTime(), command.getEndTime());
		if (asyncTask.hasBeenRequestedToCancel()) {
			asyncTask.finishedAsCancelled();
			return;
		}
		// パラメータ.社員Listと取得した年休付与がある社員ID(List)を比較する
		checkEmployeeListId(asyncTask, employeeSearchCommand, employeeIdRq304, employeeListResult, outputErrorInfoCommand);
		if (asyncTask.hasBeenRequestedToCancel()) {
			asyncTask.finishedAsCancelled();
			return;
		}
		List<PlannedVacationListCommand> plannedVacationList = new ArrayList<>();
		if (outputErrorInfoCommand.isEmpty()) {
			// 計画休暇一覧を取得する
			plannedVacationList = getPlannedVacationList(asyncTask, command.getDate(),
				outputErrorInfoCommand);
		}
		
		if (asyncTask.hasBeenRequestedToCancel()) {
			asyncTask.finishedAsCancelled();
			return;
		}
		if (outputErrorInfoCommand.size() > 0) {
			// エラーがあった場合
			for (int i = 0; i < outputErrorInfoCommand.size(); i++) {
				JsonObject value = Json.createObjectBuilder()
						.add("employeeCode", outputErrorInfoCommand.get(i).getEmployeeCode())
						.add("employeeName", outputErrorInfoCommand.get(i).getEmployeeName())
						.add("errorMessage", outputErrorInfoCommand.get(i).getErrorMessage()).build();
				setter.setData(ERROR_LIST + i, value);
				setter.updateData(NUMBER_OF_SUCCESS, i+1);
				setter.updateData(NUMBER_OF_ERROR, i+1);
				
				if (asyncTask.hasBeenRequestedToCancel()) {
					asyncTask.finishedAsCancelled();
					break;
				}
			}
		} else {
			// エラーがなかった場合

			// アルゴリズム「Excel出力データ取得」を実行する
			List<ExcelInforCommand> excelInforList = new ArrayList<>();
			for (int i = 0; i < employeeSearchCommand.size(); i++) {
				if (asyncTask.hasBeenRequestedToCancel()) {
					asyncTask.finishedAsCancelled();
					break;
				}
				// Imported(就業)「社員」を取得する
				SyEmployeeImport employeeRecordImport = employeeRecordAdapter
						.getPersonInfor(employeeSearchCommand.get(i).getEmployeeId());

				if (employeeRecordImport == null) {
					// 取得失敗
					//パラメータ.処理人数に＋１加算する
					setter.updateData(NUMBER_OF_SUCCESS, i + 1);
					continue;
				}
				// 取得成功
				// RequestList 要求No327 - アルゴリズム「指定年月日時点の年休残数を取得」を実行する
				//(Thực hiện thuật toán 「指定年月日時点の年休残数を取得-lấy số phép còn lại tại thời điểm xác định」)
				List<YearlyHolidaysTimeRemainingImport> yearlyHolidaysTimeRemainingImport = annualBreakManageAdapter
						.getYearHolidayTimeAnnualRemaining(employeeSearchCommand.get(i).getEmployeeId(), command.getDate());
				if (yearlyHolidaysTimeRemainingImport.isEmpty()) {
					//取得失敗
					//パラメータ.処理人数に＋１加算する
					setter.updateData(NUMBER_OF_SUCCESS, i + 1);
					continue;
				}
				if (asyncTask.hasBeenRequestedToCancel()) {
					asyncTask.finishedAsCancelled();
					break;
				}
				
				// パラメータ.年休残数をチェックする
				//(Check số phép còn lại trong param -パラメータ.年休残数)
				if (command.getMaxDay() != null) {
					for (YearlyHolidaysTimeRemainingImport yearlyHolidaysTimeRemaining : yearlyHolidaysTimeRemainingImport) {
						if (yearlyHolidaysTimeRemaining.getAnnualRemaining().compareTo(command.getMaxDay()) > 0) {
							//取得した年休残数　≧　パラメータ.年休残数
							//パラメータ.処理人数に＋１加算する
							setter.updateData(NUMBER_OF_SUCCESS, i + 1);
							continue;
						}
					}
				}
				if (asyncTask.hasBeenRequestedToCancel()) {
					asyncTask.finishedAsCancelled();
					break;
				}
				// 取得成功
				// RequestList 要求No328
				List<String> workTypeCode = plannedVacationList.stream().map(x -> x.getWorkTypeCode()).collect(Collectors.toList());
				Optional<DailyWorkTypeListImport> dailyWorkTypeListImport = this.annualBreakManageAdapter.getDailyWorkTypeUsed(employeeSearchCommand.get(i).getEmployeeId(), workTypeCode, command.getStartTime(), command.getEndTime());
				if(!dailyWorkTypeListImport.isPresent()){
					//取得失敗
					//パラメータ.処理人数に＋１加算する
					setter.updateData(NUMBER_OF_SUCCESS, i + 1);
					continue;
				}
				if (asyncTask.hasBeenRequestedToCancel()) {
					asyncTask.finishedAsCancelled();
					break;
				}
				//取得した情報をもとにExcel 出力情報Listに設定する
				ExcelInforCommand excelInforCommand = new ExcelInforCommand();
				excelInforCommand.setName(employeeRecordImport.getBusinessName());
				excelInforCommand.setDateStart(employeeRecordImport.getEntryDate().toString());
				//GeneralDate temDate9999 = GeneralDate.fromString("99991231", "YYYY/MM/DD");
				excelInforCommand.setDateEnd("9999/12/31".equals(employeeRecordImport.getRetiredDate().toString()) ?
					"" : employeeRecordImport.getRetiredDate().toString());
				
				excelInforCommand.setDateOffYear(yearlyHolidaysTimeRemainingImport.get(0).getAnnualHolidayGrantDay().toString());
				excelInforCommand.setDateTargetRemaining(command.getDate().toString());
				excelInforCommand.setDateAnnualRetirement(yearlyHolidaysTimeRemainingImport.get(0).getAnnualRemainingGrantTime());
				excelInforCommand.setDateAnnualRest(yearlyHolidaysTimeRemainingImport.get(0).getAnnualRemaining());
				excelInforCommand.setNumberOfWorkTypeUsedImport(dailyWorkTypeListImport.get().getNumberOfWorkTypeUsedExports());
				excelInforCommand.setPlannedVacationListCommand(plannedVacationList);
				excelInforList.add(excelInforCommand);
				
				setter.updateData(NUMBER_OF_SUCCESS, i + 1);
				
				ObjectMapper mapper = new ObjectMapper();
				try {
					String jsonInString = mapper.writeValueAsString(excelInforCommand);
					setter.setData("EXCEL_LIST"  + i, jsonInString);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				
			}
			
			if (asyncTask.hasBeenRequestedToCancel()) {
				asyncTask.finishedAsCancelled();
				return;
			}

		}
		//delay a moment.
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 計画休暇一覧を取得する
	 * 
	 * @param outputErrorInfoCommand
	 * @param maxDay
	 * @return
	 */
	private List<PlannedVacationListCommand> getPlannedVacationList(AsyncCommandHandlerContext<CheckFuncDataCommand> asyncTask, GeneralDate confirmDate,
			List<OutputErrorInfoCommand> outputErrorInfoCommand) {
		List<PlannedVacationListCommand> plannedVacationListCommand = new ArrayList<>();
		LoginUserContext loginUserContext = AppContexts.user();
		String companyId = AppContexts.user().companyId();
		if (asyncTask.hasBeenRequestedToCancel()) {
			asyncTask.finishedAsCancelled();
			return plannedVacationListCommand;
		}
		// ドメインモデル「計画休暇のルールの履歴」を取得する (Lấy domain 「計画休暇のルールの履歴」)
		List<PlanVacationHistory> planVacationHistory = vacationHistoryRepository.findHistoryByBaseDate(companyId, confirmDate);
				//iVactionHistoryRulesService
				//.getPlanVacationHistoryByDate(companyId, date);
		if (asyncTask.hasBeenRequestedToCancel()) {
			asyncTask.finishedAsCancelled();
			return plannedVacationListCommand;
		}
		if (planVacationHistory.isEmpty()) {
			// 出力エラー情報に追加する
			OutputErrorInfoCommand outputErrorInfo = new OutputErrorInfoCommand();
			outputErrorInfo.setEmployeeCode("");
			outputErrorInfo.setEmployeeName("");
			outputErrorInfo.setErrorMessage("Msg_1139");

			outputErrorInfoCommand.add(outputErrorInfo);
			return plannedVacationListCommand;
		}
		for (PlanVacationHistory element : planVacationHistory) {
			if (asyncTask.hasBeenRequestedToCancel()) {
				asyncTask.finishedAsCancelled();
				return plannedVacationListCommand;
			}
			// ドメインモデル「計画休暇を取得できる上限日数」を取得する (Lấy domain 「計画休暇を取得できる上限日数」)
			List<PlanVacationHistory> planVacationHistoryMaxDay = vacationHistoryRepository.findHistory(companyId, element.identifier());
			// ドメインモデル「勤務種類」を取得する (lấy domain 「勤務種類」)
			if (asyncTask.hasBeenRequestedToCancel()) {
				asyncTask.finishedAsCancelled();
				return plannedVacationListCommand;
			}
			Optional<WorkType> workType = workTypeRepository.findByDeprecated(loginUserContext.companyId(), element.getWorkTypeCode());
			if (workType.isPresent()) {
				PlannedVacationListCommand plannedVacation = new PlannedVacationListCommand();
				plannedVacation.setWorkTypeCode(workType.get().getWorkTypeCode().toString());
				plannedVacation.setWorkTypeName(workType.get().getName().toString());
				plannedVacation.setMaxNumberDays(planVacationHistoryMaxDay.get(0).getMaxDay().v());
				plannedVacationListCommand.add(plannedVacation);
			}
		}

		return plannedVacationListCommand;
	}

	/**
	 * @param setter
	 * @param employeeIdRq304
	 * @param employeeListResult
	 * @param outputErrorInfoCommand
	 * @param employeeSearchCommand
	 */
	private void checkEmployeeListId(AsyncCommandHandlerContext<CheckFuncDataCommand> asyncTask, List<EmployeeSearchCommand> employeeSearchCommand, List<AnnualBreakManageImport> employeeIdRq304,
			List<EmployeeSearchCommand> employeeListResult, List<OutputErrorInfoCommand> outputErrorInfoCommand
			) {
		for (EmployeeSearchCommand employee : employeeSearchCommand) {
			if (asyncTask.hasBeenRequestedToCancel()) {
				asyncTask.finishedAsCancelled();
				return;
			}
			Optional<AnnualBreakManageImport> findEmployeeById = employeeIdRq304.stream()
					.filter(x -> employee.getEmployeeId().equals(x.getEmployeeId())).findFirst();
			if (findEmployeeById.isPresent()) {
				// 社員が両方に存在する場合
				employeeListResult.add(employee);				
				
			} else {
				// パラメータ.社員Listにのみ存在する社員の場合
				OutputErrorInfoCommand outputErrorInfo = new OutputErrorInfoCommand();
				outputErrorInfo.setEmployeeCode(employee.getEmployeeCode());
				outputErrorInfo.setEmployeeName(employee.getEmployeeName());
				outputErrorInfo.setErrorMessage(MSG_1116);

				outputErrorInfoCommand.add(outputErrorInfo);
			}
		}

	}

}
