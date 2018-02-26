package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.workrecord.log.enums.ExecutionType;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * ドメインサービス：日別計算　（社員の日別実績を計算）
 * @author shuichu_ishida
 */
@Stateless
public class DailyCalculationEmployeeServiceImpl implements DailyCalculationEmployeeService {

	/** ドメインサービス：日別実績計算処理　（勤務情報を取得して計算） */
	@Inject
	private CalculateDailyRecordService calculateDailtRecordService;
	
	//*****（未）　以下、日別実績の勤怠情報など、日別計算のデータ更新に必要なリポジトリを列記。
	/** リポジトリ：日別実績の勤怠情報 */
	@Inject
	private AttendanceTimeRepository attendanceTimeRepository;
	
	/**
	 * 社員の日別実績を計算
	 * @param asyncContext 同期コマンドコンテキスト
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param datePeriod 期間
	 * @param empCalAndSumExecLogID 就業計算と集計実行ログID
	 * @param executionType 実行種別　（通常、再実行）
	 */
	@Override
	public ProcessState calculate(AsyncCommandHandlerContext asyncContext, String employeeId,
			DatePeriod datePeriod, String empCalAndSumExecLogID, ExecutionType executionType) {
		
		ProcessState status = ProcessState.SUCCESS;
		val dataSetter = asyncContext.getDataSetter();
		
		// 日別実績を取得する
		//*****（未）　期間分をまとめて取得するリポジトリメソッド等をここで使い、読み込んだデータは、最終的にIntegrationへ入れる。
		//*****（未）　データがない日も含めて、毎日ごとに処理するなら、下のループをデータ単位→日単位に変え、Integrationへの取得はループ内で行う。
		List<IntegrationOfDaily> integrationOfDailys = new ArrayList<>();
		
		// 取得データ分ループ
		for (IntegrationOfDaily integrationOfDaily : integrationOfDailys) {
			
			// 中断処理　（中断依頼が出されているかチェックする）
			if (asyncContext.hasBeenRequestedToCancel()) {
				asyncContext.finishedAsCancelled();
				return ProcessState.INTERRUPTION;
			}
			
			// アルゴリズム「実績ロックされているか判定する」を実行する
			// ＞ロックされていれば、親に「中断」を返す
			//*****（未）　この判定が必要か、念のため確認要。
			//*****（未）　この処理は、共通処理として作る必要がある。現時点では、日別作成の中にprivateで作られているため、共有できない。
			
			// 対象個人の対象日時点の個人情報を読み込む
			//*****（未）　ここで、個人履歴の各リポジトリを使って、各コードを読み込む。（または、日別実績計算処理の中で、このコード類を取得する？）
			// 職場ID
			String placeId = "dummy";
			// 雇用コード
			String employmentCd = "dummy";
			
			// 計算処理　（勤務情報を取得して計算）
			val value = this.calculateDailtRecordService.calculate(integrationOfDaily);
			/*
			// 状態確認
			//*****（未）　IntegrationOfDailyの中に、boolean error;を置いて、処理内でのエラー有無を返し、ここで、エラー処理につなぐ。
			//*****（未）　メッセージも必要なら、同様に中にメッセージ用メンバを置いて、そこから受け取るのもアリ。
			//*****（未）　中断ボタンの判定は、ここで無くてもよいです。上で判定しているので。人数は、親処理で確認しているので、それも不要。
			if (value.isError()) {
				//*****（未）　画面側の仕様が不明だが、画面にエラーを表示するなら、このタイミングで、セション値として入れて返す。
				dataSetter.updateData("dailyCalculateHasError", "エラーあり");
				asyncContext.finishedAsCancelled();
				return ProcessState.INTERRUPTION;
			}
			*/
			
			// データ更新
			//*****（未）　日別実績の勤怠情報だけを更新する場合。まとめて更新するなら、integrationOfDailyを入出できるよう調整する。
			if(integrationOfDaily.getAttendanceTimeOfDailyPerformance().isPresent())
				this.registAttendanceTime(integrationOfDaily.getAttendanceTimeOfDailyPerformance().get());
		}
		return status;
	}
	
	/**
	 * データ更新
	 * @param attendanceTime 日別実績の勤怠時間
	 */
	private void registAttendanceTime(AttendanceTimeOfDailyPerformance attendanceTime){

		//*****（未）　この中で、必要なデータ更新処理を書く。下は、仮実装なので、正確な内容は別途確認する事。

		// キー値確認
		val employeeId = attendanceTime.getEmployeeId();
		val ymd = attendanceTime.getYmd();
		
		if (this.attendanceTimeRepository.find(employeeId, ymd).isPresent()){
			
			// 更新
			this.attendanceTimeRepository.update(attendanceTime);
		}
		else {
			
			// 追加
			//*****（未）　親のフローにより、読み込めないデータは計算しないはずなので、この処理は不要かもしれない。find確認自体不要かも。
			//this.attendanceTimeRepository.add(attendanceTime);
		}
	}
}
