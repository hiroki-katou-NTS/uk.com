package nts.uk.ctx.at.shared.dom.dailyprocess.calc;

import java.util.List;
import java.util.function.Consumer;

import lombok.AllArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;
/**
 * Interface này để chuyển method này từ record sang shared
 * @author phongtq
 *
 */
public interface CalculateDailyRecordServiceCenterNew {
		/**
		 * 社員の日別実績を計算
		 * @param asyncContext 同期コマンドコンテキスト
		 * @param companyId 会社ID
		 * @param employeeId 社員ID
		 * @param datePeriod 期間
		 * @param counter 
		 * @param counter 
		 * @param reCalcAtr 
		 * @param empCalAndSumExecLogID 
		 * @param empCalAndSumExecLogID 就業計算と集計実行ログID
		 * @param executionType 実行種別　（通常、再実行）
		 * @param companyCommonSetting 
		 * @return 排他エラーが発生したフラグ
		 */
		@SuppressWarnings("rawtypes")
		List<Boolean> calculate(List<String> employeeId,DatePeriod datePeriod, ExecutionType reCalcAtr, String empCalAndSumExecLogID, Boolean isCalWhenLock );
		@AllArgsConstructor
		/**
		 * 正常終了 : 0 中断 : 1
		 */
		public enum ProcessState {
			/* 中断 */
			INTERRUPTION(0),

			/* 正常終了 */
			SUCCESS(1);

			public final int value;
		}
}
