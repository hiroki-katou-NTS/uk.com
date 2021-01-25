package nts.uk.ctx.at.record.dom.dailyprocess.calc.errorcheck;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.SystemFixedErrorAlarm;

/**
 * 申告エラーアラーム作成サービス
 * @author shuichi_ishida
 */
public interface DeclareErrorCheckService {

	/**
	 * 申告エラーチェック
	 * @param integrationOfDaily 日別実績(Work)
	 * @param fixedErrorAlarmCode エラーコード
	 * @return 社員の日別実績エラー一覧
	 */
	public List<EmployeeDailyPerError> errorCheck(IntegrationOfDaily integrationOfDaily, SystemFixedErrorAlarm fixedErrorAlarmCode);
}
