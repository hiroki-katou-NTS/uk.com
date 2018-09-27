package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord.dailymonthlyprocessing;

import java.util.List;

public interface TargetPersonRequestImport {
	/**
	 * 対象社員を取得
	 * @param empCalAndSumExecLogId 就業計算と集計実行ログID
	 * @return
	 */
	List<TargetPersonImport> getTargetPerson(String empCalAndSumExecLogId);
}
