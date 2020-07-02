package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.createdailyresults;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.PreOvertimeReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.EmbossingExecutionFlag;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.shared.dom.dailyattdcal.converter.DailyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.PeriodInMasterList;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;

/**
 * 日別実績を作成する
 * @author tutk
 *
 */
@Stateless
public class CreateDailyResults {
	
	@Inject
	private PreOvertimeReflectService preOvertimeReflectService;
	
	@Inject
	private DailyRecordToAttendanceItemConverter converter;
	
	public List<ErrorMessageInfo> createDailyResult(String companyId, String employeeId, GeneralDate ymd,
			boolean reCreateWorkType, boolean reCreateWorkPlace, boolean reCreateRestTime,
			ExecutionTypeDaily executionType,EmbossingExecutionFlag flag,EmployeeGeneralInfoImport employeeGeneralInfoImport,
			PeriodInMasterList periodInMasterList) {
		List<ErrorMessageInfo> listErrorMessageInfo = new ArrayList<>();
		
		//日別実績の「情報系」のドメインを取得する
		IntegrationOfDaily integrationOfDaily = preOvertimeReflectService.calculateForAppReflect(employeeId, ymd);
		
		//日別実績のコンバーターを作成する
		//日別実績のデータをコンバーターに入れる
		DailyRecordToAttendanceItemConverter data =  converter.setData(integrationOfDaily);
//		data.withWorkInfo(employeeId, ymd, domain)
		return listErrorMessageInfo;
		
	}

}
