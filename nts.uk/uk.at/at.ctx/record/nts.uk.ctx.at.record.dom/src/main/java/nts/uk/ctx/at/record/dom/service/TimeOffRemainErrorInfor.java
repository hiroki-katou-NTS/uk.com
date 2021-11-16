package nts.uk.ctx.at.record.dom.service;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.erroralarm.EmployeeMonthlyPerError;

public interface TimeOffRemainErrorInfor {
	/**
	 * 休暇残数エラーの取得
	 * @param param
	 * @return 社員の月別実績エラー一覧の情報
	 */
	List<EmployeeMonthlyPerError> getErrorInfor(TimeOffRemainErrorInputParam param);
}
