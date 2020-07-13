package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.NewReflectStampOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ReflectStampDomainService;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.PeriodInMasterList;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;

/**
 * 一日の日別実績の作成処理（New）
 * @author tutk
 *
 */
@Stateless
public class CreateDailyOneDay {
	@Inject
    private WorkInformationRepository workRepository;
	
	@Inject
	private BasicScheduleService basicScheduleService;
	
	@Inject
	private ReflectStampDomainService reflectStampDomainServiceImpl;
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
	public List<ErrorMessageInfo> createDailyOneDay(String companyId, String employeeId, GeneralDate ymd,
			boolean reCreateWorkType, boolean reCreateWorkPlace, boolean reCreateRestTime,ExecutionTypeDaily executionType,
			EmbossingExecutionFlag flag,EmployeeGeneralInfoImport employeeGeneralInfoImport,
			PeriodInMasterList periodInMasterList,String empCalAndSumExecLogID) {
		List<ErrorMessageInfo> listErrorMessageInfo = new ArrayList<>();
		//ドメインモデル「日別実績の勤務情報」を取得する (Lấy dữ liệu từ domain)
        Optional<WorkInfoOfDailyPerformance> optDaily = workRepository.find(employeeId, ymd);
        
        //「勤務種類」と「実行タイプ」をチェックする
        //日別実績が既に存在しない場合OR「作成する」の場合	
        if(optDaily.isPresent() || executionType == ExecutionTypeDaily.CREATE) {
        	//TODO:日別実績を作成する (TKT)
        	
        	List<ErrorMessageInfo> optError = new ArrayList<>();
        	if(optError.isEmpty()) {
        		return optError;
        	}
        }
        // TODO:打刻を取得して反映する (TKT)
        List<ErrorMessageInfo> optError1 = reflectStampDomainServiceImpl.acquireReflectEmbossingNew(companyId, employeeId, ymd,
				executionType, flag, empCalAndSumExecLogID,optDaily ,
				Optional.empty(), Optional.empty(), Optional.empty());
        if(!optError1.isEmpty()) {
        	//エラー返す
        	listErrorMessageInfo.addAll(optError1);
        	return listErrorMessageInfo;
        }
        //勤怠ルールの補正処理(TKT)
        //TODO:  Do thanh đảm nhiệm, nhưng thuật toán đang bị tạm dừng k làm nữa, chờ bên a Lẩu xác nhận
        	
		return listErrorMessageInfo;
		
	}

}
