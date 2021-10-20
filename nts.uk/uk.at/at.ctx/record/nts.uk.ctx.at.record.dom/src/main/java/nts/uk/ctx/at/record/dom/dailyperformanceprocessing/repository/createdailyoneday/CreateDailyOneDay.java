package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.createdailyresults.CreateDailyResults;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.OutputCreateDailyOneDay;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.IntegrationOfDailyGetter;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.PeriodInMasterList;
import nts.uk.ctx.at.shared.dom.scherec.appreflectprocess.appreflectcondition.reflectprocess.ScheduleRecordClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;

/**
 * 一日の日別実績の作成処理（New）
 * @author tutk
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class CreateDailyOneDay {
	
	@Inject
	private CreateDailyResults createDailyResults;
	
	@Inject
	private IntegrationOfDailyGetter integrationGetter;
	
	/**
	 * 
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param ymd 年月日
	 * @param executionType 実行タイプ（作成する、打刻反映する、実績削除する）
	 * @param employeeGeneralInfoImport 特定期間の社員情報(optional)
	 * @param periodInMasterList 期間内マスタ一覧(optional)
	 * @return
	 */
	public OutputCreateDailyOneDay createDailyOneDay(String companyId, String employeeId, GeneralDate ymd,
			ExecutionTypeDaily executionType,
			EmployeeGeneralInfoImport employeeGeneralInfoImport, PeriodInMasterList periodInMasterList) {
		List<ErrorMessageInfo> listErrorMessageInfo = new ArrayList<>();
		//ドメインモデル「日別実績の勤務情報」を取得する (Lấy dữ liệu từ domain)
        // 日別実績の「情報系」のドメインを取得する
 		List<IntegrationOfDaily> integrationOfDailys = integrationGetter.getIntegrationOfDaily(employeeId, new DatePeriod(ymd, ymd));
 		IntegrationOfDaily integrationOfDaily = integrationOfDailys.isEmpty() ? createNull(employeeId, ymd) : integrationOfDailys.get(0);
        //「勤務種類」と「実行タイプ」をチェックする
        //日別実績が既に存在しない場合OR「作成する」の場合	
 		ChangeDailyAttendance changeDailyAtt;
        if(integrationOfDailys.isEmpty() || executionType == ExecutionTypeDaily.CREATE) {
        	
        	//日別実績を作成する 
			OutputCreateDailyOneDay outputCreate = createDailyResults.createDailyResult(companyId, employeeId, ymd,
					executionType, employeeGeneralInfoImport, periodInMasterList, integrationOfDaily);
        	listErrorMessageInfo.addAll(outputCreate.getListErrorMessageInfo());
        	integrationOfDaily = outputCreate.getIntegrationOfDaily();
        	changeDailyAtt = new ChangeDailyAttendance(true, true, true, false, ScheduleRecordClassifi.RECORD, false);
        } else { 
        	
        	changeDailyAtt = new ChangeDailyAttendance(false, false, false, false, ScheduleRecordClassifi.RECORD, false);
        }
        
		return new OutputCreateDailyOneDay( listErrorMessageInfo,integrationOfDaily,new ArrayList<>(),changeDailyAtt);
		
	}
	
	private IntegrationOfDaily createNull(String sid, GeneralDate dateData) {
		
		return new IntegrationOfDaily(
				sid,
				dateData,
				null, 
				null, 
				null,
				Optional.empty(), 
				new ArrayList<>(), 
				Optional.empty(), 
				new BreakTimeOfDailyAttd(), 
				Optional.empty(), 
				Optional.empty(), 
				Optional.empty(), 
				Optional.empty(), 
				Optional.empty(), 
				Optional.empty(), 
				new ArrayList<>(),
				Optional.empty(),
				new ArrayList<>(),
				Optional.empty());
	}

}
