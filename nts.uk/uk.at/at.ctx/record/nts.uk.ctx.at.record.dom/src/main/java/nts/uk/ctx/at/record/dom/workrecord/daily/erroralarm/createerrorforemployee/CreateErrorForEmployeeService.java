package nts.uk.ctx.at.record.dom.workrecord.daily.erroralarm.createerrorforemployee;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;

@Stateless
public class CreateErrorForEmployeeService {
	
	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepository;
	
	public boolean createErrorForEmployeeService(String companyID,String employeeID,GeneralDate date,String errorCode,List<Integer> listTimeItem) {
		//ドメインモデル「社員の日別実績エラー一覧」の事前条件をチェックする
		boolean check  = employeeDailyPerErrorRepository.checkExistErrorCode(employeeID, date, errorCode);
		//エラーあり(có error)
		if(check)
			return true;
		//社員の日別実績エラーを作成する(tạo dữ liệu 社員の日別実績エラー)
		EmployeeDailyPerError  employeeDailyPerError = new EmployeeDailyPerError(
				companyID, employeeID, date,new ErrorAlarmWorkRecordCode(errorCode), listTimeItem, 0);
		employeeDailyPerErrorRepository.insert(employeeDailyPerError);
		return false;
	}
}
