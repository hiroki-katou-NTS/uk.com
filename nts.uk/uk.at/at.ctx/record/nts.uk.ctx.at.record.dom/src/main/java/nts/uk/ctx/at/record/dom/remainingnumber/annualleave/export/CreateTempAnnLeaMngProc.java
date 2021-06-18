package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.basicschedule.BasicScheduleSidDto;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.GetVacationAddSet;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.VacationAddSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 処理：暫定年休管理データを作成する
 * @author shuichu_ishida
 */
@Getter
public class CreateTempAnnLeaMngProc {

	/** 会社ID */
	private String companyId;
	/** 社員ID */
	private String employeeId;
	/** 期間 */
	private DatePeriod period;
	/** モード */
	private InterimRemainMngMode mode;

	/** 月別集計で必要な会社別設定 */
	private MonAggrCompanySettings companySets;
	/** 月の計算中の日別実績データ */
	private MonthlyCalculatingDailys monthlyCalculatingDailys;

	/** 日別実績の勤務情報リスト */
	private Map<GeneralDate, WorkInfoOfDailyAttendance> workInfoOfDailys;
	/** 勤務予定基本情報リスト **/
	private Map<GeneralDate, BasicScheduleSidDto> basicSchedules;
	/** 暫定年休管理データリスト */
	private List<InterimRemain> tempAnnualLeaveMngs;

	private List<TempAnnualLeaveMngs> holidayMngs;
	/** 勤務種類リスト */
	private Map<String, WorkType> workTypeMap;
	/** 休暇加算設定 */
	private VacationAddSet vacationAddSet;

	public CreateTempAnnLeaMngProc() {

		this.workInfoOfDailys = new HashMap<>();
		this.basicSchedules = new HashMap<>();
		this.tempAnnualLeaveMngs = new ArrayList<>();
		this.workTypeMap = new HashMap<>();
	}

	@AllArgsConstructor
	@Getter
	public class AlgorithmResult {

		private List<InterimRemain> tempAnnualLeaveMngs;

		private AtomTask atomTask;
	}

	public static interface RequireM3 extends RequireM4, RequireM2, RequireM1,
		GetVacationAddSet.RequireM1 {
	}

	public static interface RequireM4 {

		Optional<WorkType> workType(String companyId, String workTypeCd);
	}

	public static interface RequireM2 {

		List<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, GeneralDate criteriaDate);

		Map<GeneralDate, WorkInfoOfDailyAttendance> dailyWorkInfos(String employeeId, DatePeriod datePeriod);
	}

	public static interface RequireM1 {

		List<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, GeneralDate criteriaDate);
	}
}
