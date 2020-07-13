package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.serialize.binary.ObjectBinaryFile;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.test.CalcAnnLeaAttendanceRateRequireM3;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;

/**
 * テストコード　Require
 * Requireインターフェースを実装。Binaryファイルからデータを読み込む
 * @author masaaki_jinno
 *
 */
public class CalcAnnLeaAttendanceRateRequireM3Test implements CalcAnnLeaAttendanceRate.RequireM3 {
	
	public CalcAnnLeaAttendanceRateRequireM3Test(){
		binaryData 
			= ObjectBinaryFile.read(CalcAnnLeaAttendanceRateRequireM3.destionationFile);	
	}
	
	/** バイナリデータ */
	HashMap<String, Object> binaryData;
	
	protected EmpEmployeeAdapter empEmployeeAdapter;
	protected AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepo;
	protected AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;
	protected YearHolidayRepository yearHolidayRepo;
	private LengthServiceRepository lengthServiceRepository;
	private AnnLeaRemNumEachMonthRepository annLeaRemNumEachMonthRepo;
	private WorkInformationRepository workInformationRepo;
	protected WorkTypeRepository workTypeRepo;
	private OperationStartSetDailyPerformRepository operationStartSetDailyPerformRepo;
	
	/** 社員 */
	@Override
	public EmployeeImport employee(CacheCarrier cacheCarrier, String empId) {
		//return empEmployeeAdapter.findByEmpIdRequire(cacheCarrier, empId);
		List<EmployeeImport> list = (List<EmployeeImport>) binaryData.get(EmployeeImport.class.toString());
		List<EmployeeImport> listFilter
			= list.stream().filter(c -> c.getEmployeeId().equals(empId)).collect(Collectors.toList());
		if ( listFilter == null || listFilter.size() == 0 ){
			return null;
		}
		return listFilter.get(0);
	}

	/** 年休設定 */
	@Override
	public AnnualPaidLeaveSetting annualPaidLeaveSetting(String companyId) {
		//return annualPaidLeaveSettingRepo.findByCompanyId(companyId);
		List<AnnualPaidLeaveSetting> list 
			= (List<AnnualPaidLeaveSetting>) binaryData.get(AnnualPaidLeaveSetting.class.toString());
		List<AnnualPaidLeaveSetting> listFilter
			= list.stream().filter(c -> c.getCompanyId().equals(companyId)).collect(Collectors.toList());
		if ( listFilter == null || listFilter.size() == 0 ){
			return null;
		}
		return listFilter.get(0);
	}

	/** 年休社員基本情報 */
	@Override
	public Optional<AnnualLeaveEmpBasicInfo> employeeAnnualLeaveBasicInfo(String employeeId) {
		// return annLeaEmpBasicInfoRepo.get(employeeId);
		List<AnnualLeaveEmpBasicInfo> list
			= (List<AnnualLeaveEmpBasicInfo>) binaryData.get(AnnualLeaveEmpBasicInfo.class.toString());
		List<AnnualLeaveEmpBasicInfo> listFilter
			= list.stream().filter(c -> c.getEmployeeId().equals(employeeId)).collect(Collectors.toList());
		
		if ( listFilter == null || listFilter.size() == 0 ){
			return Optional.empty();
		}
		return Optional.of(listFilter.get(0));
	}

	/** 年休付与テーブル設定 */
	@Override
	public Optional<GrantHdTblSet> grantHdTblSet(String companyId, String yearHolidayCode) {
		// return yearHolidayRepo.findByCode(companyId, yearHolidayCode);
		List<GrantHdTblSet> list
			= (List<GrantHdTblSet>) binaryData.get(GrantHdTblSet.class.toString());
		List<GrantHdTblSet> listFilter
			= list.stream()
				.filter(c -> c.getCompanyId().equals(companyId))
				.filter(c -> c.getYearHolidayCode().equals(yearHolidayCode))
				.collect(Collectors.toList());
		
		if ( listFilter == null || listFilter.size() == 0 ){
			return Optional.empty();
		}
		return Optional.of(listFilter.get(0));
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
//				.sorted()) // ooooo
				.collect(Collectors.toList());

		return listFilter;
	}

	@Override
	public List<WorkInfoOfDailyPerformance> dailyWorkInfos(String employeeId, DatePeriod datePeriod) {
		//return workInformationRepo.findByPeriodOrderByYmd(employeeId, datePeriod);
//	"select * from KRCDT_WORK_SCHEDULE_TIME where SID = ? and YMD >= ? and YMD <= ? order by YMD ")) {
		List<WorkInfoOfDailyPerformance> list 
			= (List<WorkInfoOfDailyPerformance>) binaryData.get(WorkInfoOfDailyPerformance.class.toString());
		List<WorkInfoOfDailyPerformance> listFilter
			= list.stream()
				.filter(c -> c.getEmployeeId().equals(employeeId))
				.filter(c -> c.getYmd().afterOrEquals(datePeriod.start()))
				.filter(c -> c.getYmd().beforeOrEquals(datePeriod.end()))
//				.sorted()) // ooooo
				.collect(Collectors.toList());

		return listFilter;
	}
	
	@Override
	public Optional<WorkType> workType(String companyId, String workTypeCd) {
		// return workTypeRepo.findByPK(companyId, workTypeCd);
		List<WorkType> list
			= (List<WorkType>) binaryData.get(WorkType.class.toString());
		List<WorkType> listFilter
			= list.stream()
				.filter(c -> c.getWorkTypeCode().equals(workTypeCd))
				.collect(Collectors.toList());
		
		if ( listFilter == null || listFilter.size() == 0 ){
			return Optional.empty();
		}
		return Optional.of(listFilter.get(0));
	}

	/** 日別実績の運用開始設定　*/
	@Override
	public Optional<OperationStartSetDailyPerform> dailyOperationStartSet(CompanyId companyId) {
//		return operationStartSetDailyPerformRepo.findByCid(companyId);
		List<OperationStartSetDailyPerform> list
			= (List<OperationStartSetDailyPerform>) binaryData.get(OperationStartSetDailyPerform.class.toString());
		List<OperationStartSetDailyPerform> listFilter
			= list.stream()
				.filter(c -> c.getCompanyId().equals(companyId))
				.collect(Collectors.toList());
		
		if ( listFilter == null || listFilter.size() == 0 ){
			return Optional.empty();
		}
		return Optional.of(listFilter.get(0));
	}

}
