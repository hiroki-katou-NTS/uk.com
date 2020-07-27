package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.serialize.binary.ObjectBinaryFile;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.test.CalcAnnLeaAttendanceRateRequireM3;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveRemainingHistory;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;

/**
 * テストコード　Require
 * Requireインターフェースを実装。Binaryファイルからデータを読み込む
 * @author masaaki_jinno
 *
 */
public class CalcAnnLeaAttendanceRateRequireM3Test implements GetAnnLeaRemNumWithinPeriodProc.RequireM3 {
	
	public CalcAnnLeaAttendanceRateRequireM3Test(){
		binaryData 
			= ObjectBinaryFile.read(CalcAnnLeaAttendanceRateRequireM3.destionationFile);	
	}
	
	/** バイナリデータ */
	HashMap<String, Object> binaryData;
	
	/** 社員 */
	@Override
	public EmployeeImport employee(CacheCarrier cacheCarrier, String empId) {
		//return empEmployeeAdapter.findByEmpIdRequire(cacheCarrier, empId);
		
		List<EmployeeImport> list = (List<EmployeeImport>) binaryData.get(EmployeeImport.class.toString());
//		List<EmployeeImport> listFilter
//			= list.stream().filter(c -> c.getEmployeeId().equals(empId)).collect(Collectors.toList());
//		if ( listFilter == null || listFilter.size() == 0 ){
//			return null;
//		}
		return list.stream().filter(c -> c.getEmployeeId().equals(empId)).findFirst().orElse(null);
	}

	/** 年休設定 */
	@Override
	public AnnualPaidLeaveSetting annualPaidLeaveSetting(String companyId) {
		//return annualPaidLeaveSettingRepo.findByCompanyId(companyId);
		
		List<AnnualPaidLeaveSetting> list 
			= (List<AnnualPaidLeaveSetting>) binaryData.get(AnnualPaidLeaveSetting.class.toString());
		return list.stream().filter(c -> c.getCompanyId().equals(companyId)).findFirst().orElse(null);
	}

	/** 年休社員基本情報 */
	@Override
	public Optional<AnnualLeaveEmpBasicInfo> employeeAnnualLeaveBasicInfo(String employeeId) {
		// return annLeaEmpBasicInfoRepo.get(employeeId);
		
		List<AnnualLeaveEmpBasicInfo> list
			= (List<AnnualLeaveEmpBasicInfo>) binaryData.get(AnnualLeaveEmpBasicInfo.class.toString());
		AnnualLeaveEmpBasicInfo annualLeaveEmpBasicInfo
			= list.stream().filter(c -> c.getEmployeeId().equals(employeeId)).findFirst().orElse(null);
		
		return Optional.ofNullable(annualLeaveEmpBasicInfo);
	}

	/** 年休付与テーブル設定 */
	@Override
	public Optional<GrantHdTblSet> grantHdTblSet(String companyId, String yearHolidayCode) {
		// return yearHolidayRepo.findByCode(companyId, yearHolidayCode);
		
		List<GrantHdTblSet> list
			= (List<GrantHdTblSet>) binaryData.get(GrantHdTblSet.class.toString());
		GrantHdTblSet grantHdTblSet
			= list.stream()
				.filter(c -> c.getCompanyId().equals(companyId))
				.filter(c -> c.getYearHolidayCode().equals(yearHolidayCode))
				.findFirst().orElse(null);
		
		return Optional.ofNullable(grantHdTblSet);
	}

	/** 勤続年数テーブル */
	@Override
	public List<LengthServiceTbl> lengthServiceTbl(String companyId, String yearHolidayCode) {
		// return lengthServiceRepository.findByCode(companyId, yearHolidayCode);
		
		List<LengthServiceTbl> list 
			= (List<LengthServiceTbl>) binaryData.get(LengthServiceTbl.class.toString());
		List<LengthServiceTbl> listFilter
			= list.stream()
					.filter(c -> c.getCompanyId().equals(companyId))
					.filter(c -> c.getYearHolidayCode().equals(yearHolidayCode))
					.collect(Collectors.toList());
		
		return listFilter;
	}

	@Override
	public List<AnnLeaRemNumEachMonth> annLeaRemNumEachMonth(String employeeId, DatePeriod closurePeriod) {
		//return annLeaRemNumEachMonthRepo.findByClosurePeriod(employeeId, closurePeriod);
//		private static final String FIND_BY_CLOSURE_PERIOD = "SELECT a FROM KrcdtMonRemain a "
//				+ "WHERE a.krcdtMonRemainPk.employeeId = :employeeId "
//				+ "AND a.startDate >= :startDate "
//				+ "AND a.endDate <= :endDate "
//				+ "AND a.closureStatus = 1 "
//				+ "ORDER BY a.startDate ";
		List<AnnLeaRemNumEachMonth> list 
			= (List<AnnLeaRemNumEachMonth>) binaryData.get(AnnLeaRemNumEachMonth.class.toString());
		List<AnnLeaRemNumEachMonth> listFilter
			= list.stream()
				.filter(c -> c.getEmployeeId().equals(employeeId))
				.filter(c -> c.getClosurePeriod().start().afterOrEquals(closurePeriod.start())) // ooooo
				.filter(c -> c.getClosurePeriod().end().beforeOrEquals(closurePeriod.end())) // ooooo
				.filter(c -> c.getClosureStatus().equals(1))
				.sorted((a1, a2) -> a1.getClosurePeriod().start().compareTo(a2.getClosurePeriod().start()))
				.collect(Collectors.toList());

		return listFilter;
	}

	@Override
	public List<WorkInfoOfDailyPerformance> dailyWorkInfos(String employeeId, DatePeriod datePeriod) {
		//return workInformationRepo.findByPeriodOrderByYmd(employeeId, datePeriod);
		//	"select * from KRCDT_WORK_SCHEDULE_TIME where SID = ? and YMD >= ? and YMD <= ? order by YMD ")) {
		
		List<WorkInfoOfDailyPerformance> list 
			= (List<WorkInfoOfDailyPerformance>)binaryData.get(WorkInfoOfDailyPerformance.class.toString());
		List<WorkInfoOfDailyPerformance> listFilter
			= list.stream()
				.filter(c -> c.getEmployeeId().equals(employeeId))
				.filter(c -> c.getYmd().afterOrEquals(datePeriod.start()))
				.filter(c -> c.getYmd().beforeOrEquals(datePeriod.end()))
				.sorted((a1, a2) -> a1.getYmd().compareTo(a2.getYmd()))
				.collect(Collectors.toList());

		return listFilter;
	}
	
	@Override
	public Optional<WorkType> workType(String companyId, String workTypeCd) {
		// return workTypeRepo.findByPK(companyId, workTypeCd);
		List<WorkType> list
			= (List<WorkType>) binaryData.get(WorkType.class.toString());
		WorkType workType
			= list.stream()
				.filter(c -> c.getWorkTypeCode().equals(workTypeCd))
				.findFirst().orElse(null);
		
		return Optional.ofNullable(workType);
	}

	/** 日別実績の運用開始設定　*/
	@Override
	public Optional<OperationStartSetDailyPerform> dailyOperationStartSet(CompanyId companyId) {
//		return operationStartSetDailyPerformRepo.findByCid(companyId);
		List<OperationStartSetDailyPerform> list
			= (List<OperationStartSetDailyPerform>) binaryData.get(OperationStartSetDailyPerform.class.toString());
		OperationStartSetDailyPerform operationStartSetDailyPerform
			= list.stream()
				.filter(c -> c.getCompanyId().equals(companyId))
				.findFirst().orElse(null);
		
		return Optional.ofNullable(operationStartSetDailyPerform);
	}

	@Override
	public List<InterimRemain> interimRemains(String employeeId, DatePeriod datePeriod, RemainType remainType) {
//		return interimRemainRepo.getRemainBySidPriod(employeeId, dateData, remainType);
		List<InterimRemain> list 
			= (List<InterimRemain>)binaryData.get(InterimRemain.class.toString());
		List<InterimRemain> listFilter
			= list.stream()
				.filter(c -> c.getSID().equals(employeeId))
				.filter(c -> c.getYmd().afterOrEquals(datePeriod.start()))
				.filter(c -> c.getYmd().beforeOrEquals(datePeriod.end()))
				.filter(c -> c.getRemainType().equals(remainType))
				.collect(Collectors.toList());
	
		return listFilter;
	}

	@Override
	public Optional<TmpAnnualHolidayMng> tmpAnnualHolidayMng(String mngId) {
//		List<TmpAnnualHolidayMng> list 
//			= (List<TmpAnnualHolidayMng>)binaryData.get(TmpAnnualHolidayMng.class.toString());
//		List<TmpAnnualHolidayMng> listFilter
//			= list.stream()
//				.filter(c -> c.get().equals(employeeId))
//				.filter(c -> c.getYmd().afterOrEquals(datePeriod.start()))
//				.filter(c -> c.getYmd().beforeOrEquals(datePeriod.end()))
//				.filter(c -> c.getRemainType().equals(remainType))
//				.collect(Collectors.toList());
//
//			return listFilter;
	
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
		return null;
	}

	/** 月別実績の勤怠時間 */
	@Override
	public List<AttendanceTimeOfMonthly> attendanceTimeOfMonthly(String employeeId, DatePeriod period) {
		// return attendanceTimeOfMonthlyRepo.findByYearMonthOrderByStartYmd(employeeId, yearMonth);
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
//        return null;
        
//        FIND_BY_PERIOD_INTO_END = "SELECT a FROM KrcdtMonMerge a "
//    			+ "WHERE a.krcdtMonMergePk.employeeId = :employeeId "
//    			+ "AND a.endYmd >= :startDate "
//    			+ "AND a.endYmd <= :endDate "
//    			+ "ORDER BY a.startYmd ";
        List<AttendanceTimeOfMonthly> list 
			= (List<AttendanceTimeOfMonthly>)binaryData.get(AttendanceTimeOfMonthly.class.toString());
		List<AttendanceTimeOfMonthly> listFilter
			= list.stream()
				.filter(c -> c.getEmployeeId().equals(employeeId))
				.filter(c -> c.getDatePeriod().end().afterOrEquals(period.start()))
				.filter(c -> c.getDatePeriod().end().beforeOrEquals(period.end()))
				.collect(Collectors.toList());

		return listFilter;
	}

	/** 雇用に紐づく就業締め */
	@Override
	public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {
		// return closureEmploymentRepo.findByEmploymentCD(companyID, employmentCD);
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
		return null;
	}

	/** 社員の雇用履歴 */
	@Override
	public List<SharedSidPeriodDateEmploymentImport> employmentHistory(CacheCarrier cacheCarrier, List<String> sids,
			DatePeriod datePeriod) {
		// shareEmploymentAdapter.getEmpHistBySidAndPeriodRequire(cacheCarrier, sids, datePeriod);
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
		return null;
	}

	/** 締め */
	@Override
	public List<Closure> closure(String companyId) {
		// return closureRepo.findAll(companyId);
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
		return null;
	}

	/** 年休付与テーブル */
	@Override
	public Optional<GrantHdTbl> grantHdTbl(String companyId, int conditionNo, String yearHolidayCode, int grantNum) {
		// return grantYearHolidayRepo.find(companyId, conditionNo, yearHolidayCode, grantNum);
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
		return null;
	}

	/** 年休付与残数履歴データ */
	@Override
	public List<AnnualLeaveRemainingHistory> annualLeaveRemainingHistory(String sid, YearMonth ym) {
		// return annualLeaveRemainHistRepo.getInfoBySidAndYM(sid, ym);
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
		return null;
	}

	/** 締め状態管理 */
	@Override
	public Optional<ClosureStatusManagement> latestClosureStatusManagement(String employeeId) {
		// return closureStatusManagementRepo.getLatestByEmpId(employeeId);
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
		return null;
	}

	/** 年休上限データ */
	@Override
	public Optional<AnnualLeaveMaxData> annualLeaveMaxData(String employeeId) {
		// return annLeaMaxDataRepo.get(employeeId);
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
		return null;
	}

	/** 年休付与残数データ */
	@Override
	public List<AnnualLeaveGrantRemainingData> annualLeaveGrantRemainingData(String employeeId) {
		// return annLeaGrantRemDataRepo.find(employeeId, grantDate);
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
		return null;
	}

}
