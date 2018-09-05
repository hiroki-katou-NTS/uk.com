package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing;

import java.util.Optional;


public interface ExecutionLogRequestImport {
	/**
	 * 承認結果反映用の設定情報
	 * @param empCalAndSumExecLogID 就業計算と集計実行ログID＝パラメータ「実行ID」
	 * @param executionContent 実行内容＝”承認結果の反映”
	 * @return
	 */
	Optional<SetInforReflAprResultImport> optReflectResult(String empCalAndSumExecLogID, int executionContent);
}
