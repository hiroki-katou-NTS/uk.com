package nts.uk.ctx.at.function.dom.adapter.agreement;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingImport;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeCondOt;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.AgreeConditionError;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface CheckRecordAgreementAdapter {
	
	// check for 36協定エラーアラームのチェック条件 domain
	List<CheckedAgreementImport> checkArgreement(List<String> employeeIds, List<DatePeriod> periods, List<AgreeConditionError> listCondErrorAlarm);
	
	// check for 36協定時間超過回数のチェック条件
	List<CheckedOvertimeImport> checkNumberOvertime(List<String> employeeIds, List<DatePeriod> periods, List<AgreeCondOt> listCondOt);
	
	// check for 36協定実績をチェックする domain
	List<CheckedAgreementResult> checkArgreementResult(List<String> employeeIds, DatePeriod period, AgreeConditionError agreeConditionError,Optional<AgreementOperationSettingImport> agreementSetObj, List<Closure> closureList, Map<String,Integer> mapEmpIdClosureID);
}
