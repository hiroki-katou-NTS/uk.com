package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainOffMonthProcess;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainHistRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemainRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;

/**
 * 期間中の年休残数を取得　Require クラス
 * @author masaaki_jinno
 *
 */
@Getter
public class GetAnnLeaRemNumWithinPeriodRequire {
	/** 社員 */
	protected EmpEmployeeAdapter empEmployee;
	/** 年休社員基本情報 */
	protected AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;
	/** 労働条件 */
	protected WorkingConditionItemRepository workingConditionItemRepository;
	/** 年休付与テーブル設定 */
	protected YearHolidayRepository yearHolidayRepo;
	/** 勤続年数テーブル */
	protected LengthServiceRepository lengthServiceRepo;
	/** 年休設定 */
	protected AnnualPaidLeaveSettingRepository annualPaidLeaveSet;
	/** 年休付与残数データ */
	protected AnnLeaGrantRemDataRepository annLeaGrantRemDataRepo;
	/** 年休上限データ */
	protected AnnLeaMaxDataRepository annLeaMaxDataRepo;
	/** 社員に対応する締め開始日を取得する */
	protected GetClosureStartForEmployee getClosureStartForEmployee;
	/** 次回年休付与日を計算 */
	protected CalcNextAnnualLeaveGrantDate calcNextAnnualLeaveGrantDate;
	/** 月次処理用の暫定残数管理データを作成する */
	@SuppressWarnings("unused")
	protected InterimRemainOffMonthProcess interimRemOffMonth;
	/** 暫定年休管理データを作成する */
	private CreateInterimAnnualMngData createInterimAnnual;
	/** 暫定残数管理データ */
	protected InterimRemainRepository interimRemainRepo;
	/** 暫定年休管理データ */
	protected TmpAnnualHolidayMngRepository tmpAnnualLeaveMng;
	/** 月別実績の勤怠時間 */
	protected AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo;
	/** 期間中の年休残数を取得 */
	protected GetAnnLeaRemNumWithinPeriod getAnnLeaRemNumWithinPeriod;
	/** 締め状態管理 */
	protected ClosureStatusManagementRepository closureSttMngRepo;
	/** 年休出勤率を計算する */
	protected CalcAnnLeaAttendanceRate calcAnnLeaAttendanceRate;
	/** 年休付与テーブル */
	protected GrantYearHolidayRepository grantYearHolidayRepo;
	/** 日別実績の運用開始設定 */
	protected OperationStartSetDailyPerformRepository operationStartSetRepo;
	/** 年休付与残数履歴データ */
	protected AnnualLeaveRemainHistRepository annualLeaveRemainHistRepo;
	
}
