package nts.uk.ctx.at.record.dom.workrecord.daily.erroralarm.createerrorforemployee;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionDataRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.ValueExtractAlarmWR;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class CreateErrorForEmployeeService {
	
	@Inject
	private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepository;
	
	public Optional<ValueExtractAlarmWR> createErrorForEmployeeService(String workplaceID,String companyID,String employeeID,GeneralDate date,String errorCode,List<Integer> listTimeItem) {
		//ドメインモデル「社員の日別実績エラー一覧」の事前条件をチェックする
		boolean check  = employeeDailyPerErrorRepository.checkExistErrorCode(employeeID, date, errorCode);
		//エラーあり(có error)
		if(check)
			return Optional.empty();
		//社員の日別実績エラーを作成する(tạo dữ liệu 社員の日別実績エラー)
		EmployeeDailyPerError  employeeDailyPerError = new EmployeeDailyPerError(
				companyID, employeeID, date,new ErrorAlarmWorkRecordCode(errorCode), listTimeItem, 0);
		employeeDailyPerErrorRepository.insert(employeeDailyPerError);
		
		ValueExtractAlarmWR valueExtractAlarmWR = new ValueExtractAlarmWR(
				workplaceID,
				employeeID,
				date,
				TextResource.localize("KAL010_1"),
				null,
				null,
				null
				);
		return Optional.ofNullable(valueExtractAlarmWR);
	}
}
