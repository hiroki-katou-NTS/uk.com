package nts.uk.ctx.at.record.app.command.workrecord.log;

import javax.ejb.Stateless;

//import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonth;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExeStateOfCalAndSum;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AddEmpCalSumAndTargetCommandAssembler {

	public EmpCalAndSumExeLog fromDTO(AddEmpCalSumAndTargetCommand command) {
		// ログインしている社員の社員IDを取得する (Lấy login EmployeeID)
		String employeeID = AppContexts.user().employeeId();
		// 実行ボタン押下時のシステム日付を取得する (lấy thời gian hệ thống)
		GeneralDateTime systemTime = GeneralDateTime.now();
		int yearMonth = Integer.valueOf(command.getProcessingMonth());
		String empCalAndSumExecLogID = IdentifierUtil.randomUniqueId();

		EmpCalAndSumExeLog empCalAndSumExeLog = EmpCalAndSumExeLog.createFromJavaType(
				// empCalAndSumExecLogID
				empCalAndSumExecLogID,
				// companyID
				AppContexts.user().companyId(),
				// processingMonth
				new YearMonth(yearMonth),
				// executionDate
				systemTime,
				// executionStatus
				ExeStateOfCalAndSum.PROCESSING.value,
				// employeeID
				employeeID,
				// closureID
				command.getClosureID(),0);
		
		return empCalAndSumExeLog;

	}

}
