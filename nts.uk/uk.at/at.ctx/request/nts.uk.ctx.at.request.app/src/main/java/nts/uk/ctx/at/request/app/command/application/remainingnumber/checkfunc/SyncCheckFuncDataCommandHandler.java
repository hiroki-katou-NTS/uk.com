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

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.file.export.ExportServiceResult;
import nts.arc.task.data.TaskDataSetter;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.SyEmployeeAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SyEmployeeImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.AnnualBreakManageAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.AnnualBreakManageImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.DailyWorkTypeListImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.YearlyHolidaysTimeRemainingImport;
import nts.uk.ctx.at.request.dom.settting.worktype.history.PlanVacationHistory;
import nts.uk.ctx.at.request.dom.settting.worktype.history.VacationHistoryRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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

//	@Inject
//	private IVactionHistoryRulesService iVactionHistoryRulesService;
	
	@Inject
	private VacationHistoryRepository vacationHistoryRepository;
	
	ExcelExportService excelExportService;
	
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

		// アルゴリズム「社員ID、期間をもとに期間内に年休付与日がある社員を抽出する」を実行する
		// TODO RequestList 要求No304. 
		List<String> employeeList = command.getEmployeeList().stream().map(f -> f.getEmployeeId())
				.collect(Collectors.toList());

		List<AnnualBreakManageImport> employeeIdRq304 = annualBreakManageAdapter.getEmployeeId(employeeList,
				command.getStartTime(), command.getEndTime());

		// パラメータ.社員Listと取得した年休付与がある社員ID(List)を比較する
		checkEmployeeListId(setter, employeeIdRq304, employeeListResult, outputErrorInfoCommand, employeeSearchCommand);

		// TODO 計画休暇一覧を取得する
		List<PlannedVacationListCommand> plannedVacationList = getPlannedVacationList(new DatePeriod(command.getStartTime(), command.getEndTime()),
				outputErrorInfoCommand);
		if (outputErrorInfoCommand.size() > 0) {
			// エラーがあった場合
			for (int i = 0; i < outputErrorInfoCommand.size(); i++) {
				if (!StringUtil.isNullOrEmpty(outputErrorInfoCommand.get(i).getEmployeeCode(), true)) {
					JsonObject value = Json.createObjectBuilder()
							.add("employeeCode", outputErrorInfoCommand.get(i).getEmployeeCode())
							.add("employeeName", outputErrorInfoCommand.get(i).getEmployeeName())
							.add("errorMessage", outputErrorInfoCommand.get(i).getErrorMessage()).build();
					setter.setData(ERROR_LIST + i, value);
					setter.updateData(NUMBER_OF_SUCCESS, outputErrorInfoCommand.size());
					setter.updateData(NUMBER_OF_ERROR, outputErrorInfoCommand.size());
				}
			}
		} else {
			// エラーがなかった場合
			// TODO アルゴリズム「Excel出力データ取得」を実行する
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
					continue;
				}
				// 取得成功
				// RequestList 要求No327
				List<YearlyHolidaysTimeRemainingImport> yearlyHolidaysTimeRemainingImport = annualBreakManageAdapter
						.getYearHolidayTimeAnnualRemaining(employeeSearchCommand.get(i).getEmployeeId(), command.getDate());
				
				if (yearlyHolidaysTimeRemainingImport.isEmpty()) {
					//取得失敗
					continue;
				}
				// 取得成功
				// TODO RequestList 要求No328
				List<String> workTypeCode = plannedVacationList.stream().map(x -> x.getWorkTypeCode()).collect(Collectors.toList());
				Optional<DailyWorkTypeListImport> dailyWorkTypeListImport = this.annualBreakManageAdapter.getDailyWorkTypeUsed(employeeSearchCommand.get(i).getEmployeeId(), workTypeCode, command.getStartTime(), command.getEndTime());
				//取得した情報をもとにExcel 出力情報Listに設定する
				ExcelInforCommand excelInforCommand = new ExcelInforCommand();
				excelInforCommand.setName(employeeRecordImport.getPname());
				excelInforCommand.setDateStart(employeeRecordImport.getEntryDate());
				excelInforCommand.setDateEnd(employeeRecordImport.getRetiredDate());
				excelInforCommand.setDateOffYear(yearlyHolidaysTimeRemainingImport.get(i).getAnnualHolidayGrantDay());
				excelInforCommand.setDateTargetRemaining(command.getDate());
				excelInforCommand.setDateAnnualRetirement(yearlyHolidaysTimeRemainingImport.get(i).getAnnualRemainingGrantTime());
				excelInforCommand.setDateAnnualRest(yearlyHolidaysTimeRemainingImport.get(i).getAnnualRemaining());
				if(dailyWorkTypeListImport.isPresent()){
					excelInforCommand.setNumberOfWorkTypeUsedImport(dailyWorkTypeListImport.get().getNumberOfWorkTypeUsedExports());
				}
				excelInforCommand.setPlannedVacationListCommand(plannedVacationList);
				excelInforList.add(excelInforCommand);

				setter.updateData(NUMBER_OF_SUCCESS, i + 1);

				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// Excel出力情報ListをもとにExcel出力をする (Xuất ra file excel)
			exportCsv(excelInforList);
		}

	}
	/**
	 * 計画休暇一覧を取得する
	 * 
	 * @param exportExcel
	 * @param excelInfoComand
	 * @return
	 */
	public ExportServiceResult exportCsv(List<ExcelInforCommand> command) {
		return this.excelExportService.start(command);
	}
	/**
	 * 計画休暇一覧を取得する
	 * 
	 * @param outputErrorInfoCommand
	 * @param maxDay
	 * @return
	 */
	private List<PlannedVacationListCommand> getPlannedVacationList(DatePeriod period,
			List<OutputErrorInfoCommand> outputErrorInfoCommand) {
		List<PlannedVacationListCommand> plannedVacationListCommand = new ArrayList<>();
		LoginUserContext loginUserContext = AppContexts.user();

		// ドメインモデル「計画休暇のルールの履歴」を取得する (Lấy domain 「計画休暇のルールの履歴」)
		String companyId = AppContexts.user().companyId();
		List<PlanVacationHistory> planVacationHistory = vacationHistoryRepository.findHistoryByPeriod(companyId, period);
				//iVactionHistoryRulesService
				//.getPlanVacationHistoryByDate(companyId, date);
		if (planVacationHistory.isEmpty()) {
			// 出力エラー情報に追加する
			OutputErrorInfoCommand outputErrorInfo = new OutputErrorInfoCommand();
			outputErrorInfo.setEmployeeCode(null);
			outputErrorInfo.setEmployeeName(null);
			outputErrorInfo.setErrorMessage("Msg_1138");

			outputErrorInfoCommand.add(outputErrorInfo);
			return plannedVacationListCommand;
		}
		for (PlanVacationHistory element : planVacationHistory) {
			// ドメインモデル「計画休暇を取得できる上限日数」を取得する (Lấy domain 「計画休暇を取得できる上限日数」)
			List<PlanVacationHistory> planVacationHistoryMaxDay = vacationHistoryRepository.findHistory(companyId,
					element.getWorkTypeCode());
			// ドメインモデル「勤務種類」を取得する (lấy domain 「勤務種類」)
			String workTypeCd = "";
			Optional<WorkType> workType = workTypeRepository.findByDeprecated(loginUserContext.companyId(), workTypeCd);
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
	private void checkEmployeeListId(TaskDataSetter setter, List<AnnualBreakManageImport> employeeIdRq304,
			List<EmployeeSearchCommand> employeeListResult, List<OutputErrorInfoCommand> outputErrorInfoCommand,
			List<EmployeeSearchCommand> employeeSearchCommand) {
		for (EmployeeSearchCommand employee : employeeSearchCommand) {
			AnnualBreakManageImport findEmployeeById = employeeIdRq304.stream()
					.filter(x -> employee.getEmployeeId().equals(x)).findAny().orElse(null);
			if (findEmployeeById == null) {
				// パラメータ.社員Listにのみ存在する社員の場合
				OutputErrorInfoCommand outputErrorInfo = new OutputErrorInfoCommand();
				outputErrorInfo.setEmployeeCode(employee.getEmployeeCode());
				outputErrorInfo.setEmployeeName(employee.getEmployeeName());
				outputErrorInfo.setErrorMessage(MSG_1116);

				outputErrorInfoCommand.add(outputErrorInfo);
			} else {
				// 社員が両方に存在する場合
				employeeListResult.add(employee);
			}
		}

	}

}
