package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime.PreOvertimeReflectService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.OutputAcquireReflectEmbossingNew;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ReflectStampDomainService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.createdailyresults.CreateDailyResults;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.OutputCreateDailyOneDay;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.PeriodInMasterList;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ICorrectionAttendanceRule;
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
	private ReflectStampDomainService reflectStampDomainServiceImpl;
	
	@Inject
	private CreateDailyResults createDailyResults;
	
	@Inject
	private ICorrectionAttendanceRule iCorrectionAttendanceRule;
	
	@Inject
	private PreOvertimeReflectService preOvertimeReflectService;
	/**
	 * 
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param ymd 年月日
	 * @param reCreateWorkType 勤務種別変更時に再作成
	 * @param reCreateWorkPlace 異動時に再作成
	 * @param reCreateRestTime 休職・休業者再作成
	 * @param executionType 実行タイプ（作成する、打刻反映する、実績削除する）
	 * @param flag 打刻実行フラグ
	 * @param employeeGeneralInfoImport 特定期間の社員情報(optional)
	 * @param periodInMasterList 期間内マスタ一覧(optional)
	 * @return
	 */
	public OutputCreateDailyOneDay createDailyOneDay(String companyId, String employeeId, GeneralDate ymd,
			boolean reCreateWorkType, boolean reCreateWorkPlace, boolean reCreateRestTime,ExecutionTypeDaily executionType,
			EmbossingExecutionFlag flag,EmployeeGeneralInfoImport employeeGeneralInfoImport,
			PeriodInMasterList periodInMasterList) {
		List<ErrorMessageInfo> listErrorMessageInfo = new ArrayList<>();
		//ドメインモデル「日別実績の勤務情報」を取得する (Lấy dữ liệu từ domain)
        // 日別実績の「情報系」のドメインを取得する
 		IntegrationOfDaily integrationOfDaily = preOvertimeReflectService.calculateForAppReflect(employeeId, ymd);
 		integrationOfDaily.setYmd(ymd);
 		integrationOfDaily.setEmployeeId(employeeId);
        //「勤務種類」と「実行タイプ」をチェックする
        //日別実績が既に存在しない場合OR「作成する」の場合	
        if(integrationOfDaily.getWorkInformation() == null || executionType == ExecutionTypeDaily.CREATE) {
        	//日別実績を作成する 
        	OutputCreateDailyOneDay outputCreate = createDailyResults.createDailyResult(companyId, employeeId, ymd,
					reCreateWorkType, reCreateWorkPlace, reCreateRestTime, executionType, flag,
					employeeGeneralInfoImport, periodInMasterList,integrationOfDaily);
        	listErrorMessageInfo.addAll(outputCreate.getListErrorMessageInfo());
        	integrationOfDaily = outputCreate.getIntegrationOfDaily();
        	if(!listErrorMessageInfo.isEmpty()) {
        		return new OutputCreateDailyOneDay( listErrorMessageInfo,null,new ArrayList<>());
        	}
        }
        //打刻を取得して反映する 
		OutputAcquireReflectEmbossingNew outputAcquireReflectEmbossingNew = reflectStampDomainServiceImpl
				.acquireReflectEmbossingNew(companyId, employeeId, ymd, executionType, flag,
						integrationOfDaily);
		integrationOfDaily = outputAcquireReflectEmbossingNew.getIntegrationOfDaily();
        listErrorMessageInfo.addAll(outputAcquireReflectEmbossingNew.getListErrorMessageInfo());
        if(!listErrorMessageInfo.isEmpty()) {
        	//エラー返す
        	return new OutputCreateDailyOneDay( listErrorMessageInfo,null,new ArrayList<>());
        }
        //勤怠ルールの補正処理
		integrationOfDaily = iCorrectionAttendanceRule.process(integrationOfDaily,
				new ChangeDailyAttendance(true, true, true, true));
		integrationOfDaily.setYmd(ymd);
		integrationOfDaily.setEmployeeId(employeeId);
		return new OutputCreateDailyOneDay( listErrorMessageInfo,integrationOfDaily,outputAcquireReflectEmbossingNew.getListStamp());
		
	}

}
