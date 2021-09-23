package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.CreateDailyOneDay;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.deleteworkinfor.DeleteWorkInfor;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.reflectedperiod.ReflectedAtr;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLog;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.EmpCalAndSumExeLogRepository;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExeStateOfCalAndSum;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.PeriodInMasterList;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.shr.com.context.AppContexts;

/**
 * 期間で社員の日別実績を作成する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.日別実績処理.作成処理.日別作成Mgrクラス.アルゴリズム.社員の日別実績を作成する.期間で社員の日別実績を作成する.期間で社員の日別実績を作成する
 * 
 * @author tutk
 *
 */
@Stateless
public class CreateDailyEmployeesByPeriod {
	
	@Inject
	private EmpCalAndSumExeLogRepository empCalAndSumExeLogRepository;
	
	@Inject
	private DeleteWorkInfor deleteWorkInfor;
	
	@Inject
	private CreateDailyOneDay createDailyOneDay;
	
	/**
	 * 
	 * @param period                    期間
	 * @param employeeId                社員ID
	 * @param executionType             実行タイプ
	 * @param empCalAndSumExecLogId     実行ログID
	 * @param employeeGeneralInfoImport 特定期間の社員情報
	 * @param periodInMasterList        期間内マスタ一覧
	 */
	public CreateDailyOuput create(DatePeriod period, String employeeId, ExecutionTypeDaily executionType,
			Optional<String> empCalAndSumExecLogId, EmployeeGeneralInfoImport employeeGeneralInfoImport,
			PeriodInMasterList periodInMasterList) {
		String companyId = AppContexts.user().companyId();
		//日別実績一覧、エラー一覧、日別勤怠の何が変更されたか一覧を作る
		List<IntegrationOfDaily> listIntegrationOfDaily = new ArrayList<>();
		List<ErrorMessageInfo> listError = new ArrayList<>();
		Map<GeneralDate, ChangeDailyAttendance> changedDailyAttendance = new HashMap<>();
		
		//パラメータ。期間の日数分をループする
		for(GeneralDate date : period.datesBetween()) {
			if(empCalAndSumExecLogId.isPresent()) {
                //ドメインモデル「就業計算と集計実行ログ」を取得し、実行状況を確認する
				Optional<EmpCalAndSumExeLog> logOptional = this.empCalAndSumExeLogRepository
						.getByEmpCalAndSumExecLogID(empCalAndSumExecLogId.get());
				if (logOptional.isPresent() && logOptional.get().getExecutionStatus().isPresent()
						&& logOptional.get().getExecutionStatus().get() == ExeStateOfCalAndSum.START_INTERRUPTION) {
					return new CreateDailyOuput(listIntegrationOfDaily, ReflectedAtr.SUSPENDED, listError, changedDailyAttendance);
				}
            }
			
			//「実行タイプ」をチェックする
			if(executionType == ExecutionTypeDaily.DELETE_ACHIEVEMENTS) {
				//日別実績の前データを削除する
            	deleteWorkInfor.deleteWorkInfor(companyId, employeeId, date);
			}
			//一日の日別実績の作成処理（New）
			OutputCreateDailyOneDay outputCreateDailyOneDay = createDailyOneDay.createDailyOneDay(companyId, employeeId,
					date, executionType, employeeGeneralInfoImport, periodInMasterList);
			
			if(outputCreateDailyOneDay.getListErrorMessageInfo().isEmpty()) {
				//日別実績一覧、エラー一覧、日別勤怠の何が変更されたか一覧に入れる
				listIntegrationOfDaily.add(outputCreateDailyOneDay.getIntegrationOfDaily());
				changedDailyAttendance.put(date, outputCreateDailyOneDay.getChangeDailyAttendance());
			}
			listError.addAll(outputCreateDailyOneDay.getListErrorMessageInfo());
		}
		return new CreateDailyOuput(listIntegrationOfDaily, ReflectedAtr.NORMAL, listError, changedDailyAttendance);
	}

}













