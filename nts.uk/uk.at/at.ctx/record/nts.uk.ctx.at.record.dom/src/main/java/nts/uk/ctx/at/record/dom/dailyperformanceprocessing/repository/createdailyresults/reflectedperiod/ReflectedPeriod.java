package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.reflectedperiod;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.OutputAcquireReflectEmbossingNew;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ReflectStampDomainService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.EmbossingExecutionFlag;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.clearreflectstatusperiod.ClearReflectStatusPeriod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExeStateOfCalAndSum;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 期間で打刻反映する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績処理.作成処理.日別作成Mgrクラス.アルゴリズム.社員の日別実績を作成する.期間で打刻反映する.期間で打刻反映する
 * 
 * @author tutk
 *
 */
@Stateless
public class ReflectedPeriod {
	
	@Inject
	private ClearReflectStatusPeriod clearReflectStatusPeriod;
	
	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	
	@Inject
	private ReflectStampDomainService reflectStampDomainServiceImpl;
	
	/**
	 * 
	 * @param employeeId             社員ID
	 * @param period                 期間
	 * @param executionTypeDaily     実行タイプ
	 * @param lisIntegrationOfDaily  日別実績一覧
	 * @param listError              エラー一覧
	 * @param changedDailyAttendance 日別勤怠の何が変更されたか一覧 （Map＜年月日、日別勤怠の何が変更されたか＞）
	 * @param empCalAndSumExecLogId  実行ログID<optional>
	 */
	public ReflectedPeriodOutput reflect(String employeeId, DatePeriod period, ExecutionTypeDaily executionTypeDaily,
			List<IntegrationOfDaily> listIntegrationOfDaily, List<ErrorMessageInfo> listError,
			Map<GeneralDate, ChangeDailyAttendance> changedDailyAttendance,Optional<String> empCalAndSumExecLogId) {
		String companyId = AppContexts.user().companyId();
		List<Stamp> listStamp = new ArrayList<>();
		//実行タイプを確認する
		if(executionTypeDaily == ExecutionTypeDaily.DELETE_ACHIEVEMENTS ) {
			//期間で反映状態をクリアする
			clearReflectStatusPeriod.clear(listIntegrationOfDaily);
		}
		//日別実績一覧の分をループする
		for(int index = 0;index<listIntegrationOfDaily.size();index++) {
			IntegrationOfDaily integrationOfDaily = listIntegrationOfDaily.get(index);
			//実行状態を確認します
			if(empCalAndSumExecLogId.isPresent()) {
				 //ドメインモデル「就業計算と集計実行ログ」を取得し、実行状況を確認する
				Optional<EmpCalAndSumExeLog> logOptional = this.empCalAndSumExeLogRepository
						.getByEmpCalAndSumExecLogID(empCalAndSumExecLogId.get());
				// 実行状況.中断開始 の場合
				if (logOptional.isPresent() && logOptional.get().getExecutionStatus().isPresent()
						&& logOptional.get().getExecutionStatus().get() == ExeStateOfCalAndSum.START_INTERRUPTION) {
					return new ReflectedPeriodOutput(ReflectedAtr.SUSPENDED,listStamp);
				}
			}
			ChangeDailyAttendance changeDailyAtt = changedDailyAttendance.get(integrationOfDaily.getYmd());
			EmbossingExecutionFlag flag = executionTypeDaily == ExecutionTypeDaily.DELETE_ACHIEVEMENTS?EmbossingExecutionFlag.ALL:EmbossingExecutionFlag.NOT_REFECT_ONLY;
			//打刻を取得して反映する (Lấy phản ánh 打刻)
			OutputAcquireReflectEmbossingNew outputAcquireReflectEmbossingNew = reflectStampDomainServiceImpl
					.acquireReflectEmbossingNew(companyId, employeeId, integrationOfDaily.getYmd(), executionTypeDaily, flag,
							integrationOfDaily, changeDailyAtt);
			listStamp.addAll(outputAcquireReflectEmbossingNew.getListStamp());
			if(!outputAcquireReflectEmbossingNew.getListErrorMessageInfo().isEmpty()) {
				//エラー一覧に追加する
				listError.addAll(outputAcquireReflectEmbossingNew.getListErrorMessageInfo());
			}else {
				//日別実績一覧と日別勤怠の何が変更されたか一覧に更新
				listIntegrationOfDaily.set(index, outputAcquireReflectEmbossingNew.getIntegrationOfDaily());
			}
			
		}
		return new ReflectedPeriodOutput(ReflectedAtr.NORMAL,listStamp);
	}
}
