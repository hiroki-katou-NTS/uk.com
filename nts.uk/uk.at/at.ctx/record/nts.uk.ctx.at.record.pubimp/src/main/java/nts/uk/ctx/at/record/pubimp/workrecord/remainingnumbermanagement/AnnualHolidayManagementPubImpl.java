package nts.uk.ctx.at.record.pubimp.workrecord.remainingnumbermanagement;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement.AnnualHolidayManagementPub;
import nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement.AttendRateAtNextHolidayExport;
import nts.uk.ctx.at.record.pub.workrecord.remainingnumbermanagement.NextAnnualLeaveGrantExport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.CalcAnnLeaAttendanceRate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.CalYearOffWorkAttendRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AttendanceRate;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.GetNextAnnualLeaveGrant;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;

@Stateless
public class AnnualHolidayManagementPubImpl implements AnnualHolidayManagementPub {
	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepository;
	
	@Inject
	private YearHolidayRepository yearHolidayRepository;
	
	@Inject
	private EmpEmployeeAdapter empEmployeeAdapter;
	
	@Inject
	private RecordDomRequireService requireService;
	
	/**
	 * RequestList210
	 * 次回年休付与日を取得する
	 * 
	 * @param cId
	 * @param sId
	 * @return
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<NextAnnualLeaveGrantExport> acquireNextHolidayGrantDate(String companyId, String employeeId, Optional<GeneralDate> referenceDate) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		
		// ドメインモデル「年休社員基本情報」を取得
		Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfo = annLeaEmpBasicInfoRepository.get(employeeId);
		
		if(!annualLeaveEmpBasicInfo.isPresent()) {
			return Collections.emptyList();
		}
		
		// 次回年休付与の計算範囲を作成
		Optional<DatePeriod> period = createCalRangeNextYearHdGrant(referenceDate);
		
		// 次回年休付与を計算
		List<NextAnnualLeaveGrantExport> result = calculateNextHolidayGrant(require, cacheCarrier, companyId, 
				employeeId, period, annualLeaveEmpBasicInfo);
		
		// 次回年休付与を返す
		return result;
	}
	
	/**
	 * 次回年休付与の計算範囲を作成
	 * 
	 * @param referenceDate
	 * @return
	 */
	private Optional<DatePeriod> createCalRangeNextYearHdGrant(Optional<GeneralDate> referenceDate) {
		Optional<DatePeriod> result = Optional.empty();
		
		// パラメータ「基準日」が存在するかチェック
		if(referenceDate.isPresent()) {	
			// 基準日から1年間の期間を返す
			result = Optional.of(new DatePeriod(referenceDate.get(), referenceDate.get().addYears(1))) ;
		}
		
		return result;
	}

	/**
	 * 次回年休付与を計算
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param period
	 * @param annualLeaveEmpBasicInfo
	 * @return
	 */
	private List<NextAnnualLeaveGrantExport> calculateNextHolidayGrant(
			RecordDomRequireService.Require require, 
			CacheCarrier cacheCarrier, String companyId, String employeeId, 
			Optional<DatePeriod> period, Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfo) {
		boolean isSingleDay = false;
		Optional<DatePeriod> periodDate = period;
		
		// Imported(就業)「社員」を取得する
		EmployeeImport employee = empEmployeeAdapter.findByEmpId(employeeId);
		
		// パラメータ「期間」をチェック
		if(!period.isPresent()) {
			isSingleDay = true;
			
			// 社員に対応する締め開始日を取得する
			Optional<GeneralDate> closureStartDate = GetClosureStartForEmployee.algorithm(require, cacheCarrier, employeeId);
			
			periodDate = Optional.ofNullable(new DatePeriod(GeneralDate.ymd(closureStartDate.get().year(), 
					closureStartDate.get().month(), closureStartDate.get().day()), GeneralDate.ymd(9999, 12, 31)));
		}
		
		// ドメインモデル「年休付与テーブル設定」を取得する
		Optional<GrantHdTblSet> grantHdTblSet = yearHolidayRepository.findByCode(companyId, annualLeaveEmpBasicInfo.get().getGrantRule().getGrantTableCode().v());
		
		if(!grantHdTblSet.isPresent()) {
			return Collections.emptyList();
		}
		
		// 次回年休付与を取得する
		List<NextAnnualLeaveGrantExport> nextAnnualLeaveGrantData = GetNextAnnualLeaveGrant.algorithm(require, cacheCarrier, 
				companyId, grantHdTblSet.get().getYearHolidayCode().v(), employee.getEntryDate(), 
				annualLeaveEmpBasicInfo.get().getGrantRule().getGrantStandardDate(), periodDate.get(), isSingleDay)
				.stream().map(x -> new NextAnnualLeaveGrantExport(
						x.getGrantDate(), 
						x.getGrantDays(),
						x.getTimes(),
						x.getTimeAnnualLeaveMaxDays(),
						x.getTimeAnnualLeaveMaxTime(),
						x.getHalfDayAnnualLeaveMaxTimes()))
				.collect(Collectors.toList());
		
		// List<次回年休付与>を返す
		return nextAnnualLeaveGrantData;
	}
	
	/**
	 * RequestList323
	 * 次回年休付与時点の出勤率・出勤日数・所定日数・年間所定日数を取得する
	 * 
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @return 次回年休付与時点出勤率
	 */

	@Override
	public Optional<AttendRateAtNextHolidayExport> getDaysPerYear(String companyId, String employeeId) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		
		AttendRateAtNextHolidayExport result = null;
		
		// 「年休社員基本情報」を取得
		Optional<AnnualLeaveEmpBasicInfo> basicInfoOpt = this.annLeaEmpBasicInfoRepository.get(employeeId);
		if (!basicInfoOpt.isPresent()) return Optional.empty();
		AnnualLeaveEmpBasicInfo basicInfo = basicInfoOpt.get();
		
		// 次回年休付与を計算
		List<NextAnnualLeaveGrant> nextAnnLeaGrantList = CalcNextAnnualLeaveGrantDate.algorithm(require, cacheCarrier,
				companyId, employeeId, Optional.empty(),
				Optional.empty(), basicInfoOpt, Optional.empty(), Optional.empty());
		
		// List先頭の次回年休付与を出力用クラスにセット
		if (nextAnnLeaGrantList.size() <= 0) return Optional.empty();
		NextAnnualLeaveGrant nextAnnualLeaveGrant = nextAnnLeaGrantList.get(0);

		// 年休出勤率を計算する
		Optional<CalYearOffWorkAttendRate> attendanceRateOpt = CalcAnnLeaAttendanceRate.algorithm(require, cacheCarrier,
				companyId, employeeId, nextAnnualLeaveGrant.getGrantDate(), Optional.empty());
		Double attendanceRate = 0.0;
		Double attendanceDays = 0.0;
		Double predeterminedDays = 0.0;
		if (attendanceRateOpt.isPresent()) {
			attendanceRate = attendanceRateOpt.get().getAttendanceRate();
			attendanceDays = attendanceRateOpt.get().getWorkingDays();
			predeterminedDays = attendanceRateOpt.get().getPrescribedDays();
		}
		
		// 年休社員基本情報から年間所定日数をセット
		Double annualPerYearDays = 0.0;
		if (basicInfo.getWorkingDaysPerYear().isPresent()) {
			annualPerYearDays = basicInfo.getWorkingDaysPerYear().get().v().doubleValue();
		}
		
		// 次回年休付与時点出勤率を返す
		result = new AttendRateAtNextHolidayExport(
				nextAnnualLeaveGrant.getGrantDate(),
				nextAnnualLeaveGrant.getGrantDays(),
				new AttendanceRate(attendanceRate),
				new AttendanceDaysMonth(attendanceDays),
				new AttendanceDaysMonth(predeterminedDays),
				new AttendanceDaysMonth(annualPerYearDays));
		return Optional.ofNullable(result);
	}
	
	
	
}
