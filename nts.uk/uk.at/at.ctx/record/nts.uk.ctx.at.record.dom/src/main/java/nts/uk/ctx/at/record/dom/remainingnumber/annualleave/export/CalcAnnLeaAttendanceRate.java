package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.CalYearOffWorkAttendRate;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;

/**
 * 年休出勤率を計算する
 * @author shuichu_ishida
 */
public interface CalcAnnLeaAttendanceRate {

	/**
	 * 年休出勤率を計算する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param grantDate 付与日
	 * @param grantNum 付与回数
	 * @return 出勤率計算結果
	 */
	Optional<CalYearOffWorkAttendRate> algorithm(String companyId, String employeeId, GeneralDate grantDate,
			Optional<Integer> grantNum);

	/**
	 * 年休出勤率を計算する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param grantDate 付与日
	 * @param grantNum 付与回数
	 * @param annualLeaveSetOpt 年休設定
	 * @param employeeOpt 社員
	 * @param annualLeaveEmpBasicInfoOpt 年休社員基本情報
	 * @param grantHdTblSetOpt 年休付与テーブル設定
	 * @param lengthServiceTblsOpt 勤続年数テーブル
	 * @param operationStartSetParam 日別実績の運用開始設定
	 * @return 出勤率計算結果
	 */
	Optional<CalYearOffWorkAttendRate> algorithm(String companyId, String employeeId, GeneralDate grantDate,
			Optional<Integer> grantNum,
			Optional<AnnualPaidLeaveSetting> annualLeaveSetOpt,
			Optional<EmployeeImport> employeeOpt,
			Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfoOpt,
			Optional<GrantHdTblSet> grantHdTblSetOpt,
			Optional<List<LengthServiceTbl>> lengthServiceTblsOpt,
			Optional<OperationStartSetDailyPerform> operationStartSetParam);
}
