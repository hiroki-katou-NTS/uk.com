package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnLeaGrantRemDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnLeaMaxDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveManagement;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：期間中の年休残数を取得
 * @author shuichu_ishida
 */
@Stateless
public class GetAnnLeaRemNumWithinPeriodImpl implements GetAnnLeaRemNumWithinPeriod {

	/** 年休設定 */
	@Inject
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSet;
	/** 年休付与残数データ */
	@Inject
	private AnnLeaGrantRemDataRepository annLeaGrantRemDataRepo;
	/** 年休上限データ */
	@Inject
	private AnnLeaMaxDataRepository annLeaMaxDataRepo;
	/** 社員に対応する締め開始日を取得する */
	@Inject
	private GetClosureStartForEmployee getClosureStartForEmployee;
	/** 次回年休付与日を計算 */
	@Inject
	private CalcNextAnnualLeaveGrantDate calcNextAnnualLeaveGrantDate;
	/** 暫定年休管理データを作成する */
	@Inject
	private CreateTempAnnualLeaveManagement createTempAnnualLeaveMng;
	/** 期間中の年休残数を取得 */
	@Inject
	private GetAnnLeaRemNumWithinPeriod getAnnLeaRemNumWithinPeriod;
	
	/** 期間中の年休残数を取得 */
	@Override
	public Optional<AggrResultOfAnnualLeave> algorithm(
			String companyId, String employeeId, DatePeriod aggrPeriod, TempAnnualLeaveMngMode mode,
			GeneralDate criteriaDate, boolean isGetNextMonthData, boolean isCalcAttendanceRate,
			Optional<Boolean> isOverWriteOpt, Optional<List<TempAnnualLeaveManagement>> forOverWriteListOpt,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeaveOpt) {

		GetAnnLeaRemNumWithinPeriodProc proc = new GetAnnLeaRemNumWithinPeriodProc(
				this.annualPaidLeaveSet,
				this.annLeaGrantRemDataRepo,
				this.annLeaMaxDataRepo,
				this.getClosureStartForEmployee,
				this.calcNextAnnualLeaveGrantDate,
				this.createTempAnnualLeaveMng,
				this.getAnnLeaRemNumWithinPeriod);
		return proc.algorithm(companyId, employeeId, aggrPeriod, mode, criteriaDate,
				isGetNextMonthData, isCalcAttendanceRate,
				isOverWriteOpt, forOverWriteListOpt, prevAnnualLeaveOpt);
	}
}
