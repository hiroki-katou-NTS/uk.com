package nts.uk.ctx.at.record.app.command.remainingnumber.checkfunc;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordImport;

@Stateful
public class SyncCheckFuncDataCommandHandler extends AsyncCommandHandler<CheckFuncDataCommand> {
	
	private static final String NUMBER_OF_SUCCESS = "NUMBER_OF_SUCCESS";
	private static final String NUMBER_OF_ERROR = "NUMBER_OF_ERROR";
	private static final String MSG_1116 = "MSG_1116";
	private static final String ERROR_LIST = "ERROR_LIST";
	
	@Inject
	private EmployeeRecordAdapter employeeRecordAdapter;
	
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

		//アルゴリズム「社員ID、期間をもとに期間内に年休付与日がある社員を抽出する」を実行する
		// TODO RequestList　要求No304. Result from RequestList 304
		List<String> employeeIdRq304 = new ArrayList<>();
		
		// パラメータ.社員Listと取得した年休付与がある社員ID(List)を比較する
		checkEmployeeListId(setter, employeeIdRq304, employeeListResult, outputErrorInfoCommand, employeeSearchCommand);

		// TODO 計画休暇一覧を取得する
		List<PlannedVacationListCommand> plannedVacationList = getPlannedVacationList();
		if (outputErrorInfoCommand.size() > 0) {
			// エラーがあった場合
			for(int i=0; i< outputErrorInfoCommand.size(); i++) {
				JsonObject value = Json.createObjectBuilder().add("employeeCode", outputErrorInfoCommand.get(i).getEmployeeCode())
						.add("employeeName",  outputErrorInfoCommand.get(i).getEmployeeName())
						.add("errorMessage", outputErrorInfoCommand.get(i).getErrorMessage()).build();
				setter.setData(ERROR_LIST + i, value);
			}
		}
		else {
			// エラーがなかった場合
			// TODO アルゴリズム「Excel出力データ取得」を実行する
			List<ExcelInforCommand> excelInforList = new ArrayList<>();
			for (int i = 0; i < employeeSearchCommand.size(); i++) {
				if (asyncTask.hasBeenRequestedToCancel()) {
					asyncTask.finishedAsCancelled();
					break;
				}
				EmployeeRecordImport employeeRecordImport = 
						employeeRecordAdapter.getPersonInfor(employeeSearchCommand.get(i).getEmployeeId());
				if (employeeRecordImport== null) {
					// 取得失敗
					continue;
				}
				// 取得成功
				// TODO  RequestList　要求No327
				// TODO  RequestList　要求No328
				
				setter.updateData(NUMBER_OF_SUCCESS, i+1);
				
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 計画休暇一覧を取得する
	 * @return
	 */
	private List<PlannedVacationListCommand> getPlannedVacationList() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param setter
	 * @param employeeIdRq304
	 * @param employeeListResult
	 * @param outputErrorInfoCommand
	 * @param employeeSearchCommand
	 */
	private void checkEmployeeListId(TaskDataSetter setter, List<String> employeeIdRq304, List<EmployeeSearchCommand> employeeListResult,
			List<OutputErrorInfoCommand> outputErrorInfoCommand, List<EmployeeSearchCommand> employeeSearchCommand) {
		for (EmployeeSearchCommand employee : employeeSearchCommand) {
			String findEmployeeById = employeeIdRq304.stream()
					.filter(x -> employee.getEmployeeId().equals(x))
					.findAny()
					.orElse(null);  
			if (findEmployeeById == null) {
				// パラメータ.社員Listにのみ存在する社員の場合
				OutputErrorInfoCommand outputErrorInfo = new OutputErrorInfoCommand();
				outputErrorInfo.setEmployeeCode(employee.getEmployeeCode());
				outputErrorInfo.setEmployeeName(employee.getEmployeeName());
				outputErrorInfo.setErrorMessage(MSG_1116);
				
				outputErrorInfoCommand.add(outputErrorInfo);
				setter.updateData(NUMBER_OF_ERROR, outputErrorInfoCommand.size());
				
			}
			else {
				// 社員が両方に存在する場合
				employeeListResult.add(employee);
			}
		}
		
	}
	

}

