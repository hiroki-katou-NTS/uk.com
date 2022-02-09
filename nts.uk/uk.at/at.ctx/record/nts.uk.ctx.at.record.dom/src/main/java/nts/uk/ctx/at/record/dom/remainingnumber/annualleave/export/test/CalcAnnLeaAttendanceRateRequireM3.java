package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.serialize.binary.ObjectBinaryFile;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.test.LengthServiceTest;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthlyRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerform;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.OperationStartSetDailyPerformRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthOfService;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;

/**
 * テスト用バイナリファイル作成クラス
 * 「CalcAnnLeaAttendanceRate」クラスの「RequireM3」インターフェースに対応
 * @author masaaki_jinno
 *
 */
@Stateless
public class CalcAnnLeaAttendanceRateRequireM3 {
	
	/** バイナリファイル出力先 */
	static public Path destionationFile = Paths.get("C:\\jinno\\binaryFile.csv");

	/** 社員 */
	@Inject
	protected EmpEmployeeAdapter empEmployeeAdapter;
	
	/** 年休設定 */
	@Inject
	protected AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepo;
	
	/** 年休社員基本情報 */
	@Inject
	protected AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;

	/** 年休付与テーブル設定 */
	@Inject
	protected YearHolidayRepository yearHolidayRepo;
	
	/** 勤続年数テーブル */
	@Inject
	private LengthServiceTest lengthServiceRepository;
	
	/** 年休月別残数データ */
	@Inject
	private AnnLeaRemNumEachMonthRepository annLeaRemNumEachMonthRepo;
	
	/**  */
	@Inject
	private WorkInformationRepository workInformationRepo;
	
	/**  */
	@Inject
	protected WorkTypeRepository workTypeRepo;
	
	/** 日別実績の運用開始設定 */
	@Inject
	private OperationStartSetDailyPerformRepository operationStartSetDailyPerformRepo;
	
	/** 暫定年休管理データ */
	@Inject
	private TmpAnnualHolidayMngRepository tmpAnnualHolidayMngRepo;
	
	/** 月別実績の勤怠時間 */
	@Inject
	private AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo;
	
	/** 雇用に紐づく就業締め */
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	
	/**
	 * バイナリファイル作成
	 */
	public void createBinaryFile(){

		// バイナリマップ
		Map<String,Object> toBinaryMap = new HashMap<String,Object>();
		
		// ※CSVファイルから変数を作成
		
		
		// 会社IDリスト
		ArrayList<String> companyIds = new ArrayList<String>();
		companyIds.add("000000000000-0101");
		
		// 社員IDリスト
		ArrayList<String> employeeIds = new ArrayList<String>();
		employeeIds.add("ca294040-910f-4a42-8d90-2bd02772697c");
		
		// 期間
		DatePeriod datePeriodData = new DatePeriod(
				GeneralDate.ymd( 2019, 1, 1 ), GeneralDate.ymd( 2020, 12, 31 ));
		
		List<YearMonth> yearMonths = new ArrayList<YearMonth>(); // ooooo 要実装追加
		yearMonths.add(YearMonth.of(2019,1));
		yearMonths.add(YearMonth.of(2019,2));
		yearMonths.add(YearMonth.of(2019,3));
		yearMonths.add(YearMonth.of(2019,4));
		yearMonths.add(YearMonth.of(2019,5));
		yearMonths.add(YearMonth.of(2019,6));
		
		
		// 社員
//		EmpEmployeeAdapter　EmpEmployeeAdapterImpl1 = new EmpEmployeeAdapterImpl();
		List<EmployeeImport> employeeImportList = empEmployeeAdapter.findByEmpId(employeeIds);
		toBinaryMap.put(EmployeeImport.class.toString(), employeeImportList);

		// 年休設定
		val annualPaidLeaveSettingList = new ArrayList<AnnualPaidLeaveSetting>();
		for(String companyId: companyIds){
			AnnualPaidLeaveSetting annualPaidLeaveSetting
				= annualPaidLeaveSettingRepo.findByCompanyId(companyId);
			annualPaidLeaveSettingList.add(annualPaidLeaveSetting);
		}
		toBinaryMap.put(AnnualPaidLeaveSetting.class.toString(), annualPaidLeaveSettingList);
		
		//　年休社員基本情報
		val annualLeaveEmpBasicInfoList = new ArrayList<AnnualLeaveEmpBasicInfo>();
		for(String employeeId: employeeIds){
			Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfo
				= annLeaEmpBasicInfoRepo.get(employeeId);
			if ( annualLeaveEmpBasicInfo.isPresent() ){
				annualLeaveEmpBasicInfoList.add(annualLeaveEmpBasicInfo.get());
			}
		}
		toBinaryMap.put(AnnualLeaveEmpBasicInfo.class.toString(), annualLeaveEmpBasicInfoList);

		// 年休付与テーブル設定 
		//protected YearHolidayRepository yearHolidayRepo;
		List<GrantHdTblSet> grantHdTblSetList = new ArrayList<GrantHdTblSet>();
		for(String companyId: companyIds){
			List<GrantHdTblSet> annualPaidLeaveSettingListTmp
				= yearHolidayRepo.findAll(companyId);
			grantHdTblSetList.addAll(annualPaidLeaveSettingListTmp);
		}
		toBinaryMap.put(GrantHdTblSet.class.toString(), grantHdTblSetList);
		
		// 勤続年数テーブル
		val yearHolidayCodes = new ArrayList<String>();

		List<LengthOfService> lengthServiceTblList = new ArrayList<LengthOfService>();
		for(String companyId: companyIds){
			List<LengthOfService> lengthServiceTblListTmp
				= lengthServiceRepository.findByCompanyId(companyId)
				.stream().flatMap(c->c.getLengthOfService().stream()).collect(Collectors.toList());

			lengthServiceTblList.addAll(lengthServiceTblListTmp);
		}
		toBinaryMap.put(LengthServiceTbl.class.toString(), lengthServiceTblList);
		
		// 年休月別残数データ
		val annLeaRemNumEachMonthList
				= annLeaRemNumEachMonthRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		toBinaryMap.put(AnnLeaRemNumEachMonth.class.toString(), annLeaRemNumEachMonthList);
		
		// 
		val workInfoOfDailyPerformanceList
			= new ArrayList<WorkInfoOfDailyPerformance>();
		
		for(String employeeId: employeeIds){
			List<WorkInfoOfDailyPerformance> workInfoOfDailyPerformanceListTmp
				= workInformationRepo.findByEmployeeId(employeeId);
			workInfoOfDailyPerformanceList.addAll(workInfoOfDailyPerformanceListTmp);
		}
		toBinaryMap.put(WorkInfoOfDailyPerformance.class.toString(), workInfoOfDailyPerformanceList);
		
		/**  */ // ??
		val workTypeList = new ArrayList<WorkType>();
		for(String companyId: companyIds){
			List<WorkType> workTypeListListTmp
				= workTypeRepo.findByCompanyId(companyId);
			workTypeList.addAll(workTypeListListTmp);
		}
		toBinaryMap.put(WorkType.class.toString(), workTypeList);
		
		
		// 日別実績の運用開始設定
		val operationStartSetDailyPerformList = new ArrayList<OperationStartSetDailyPerform>();
		for(String companyId: companyIds){
			Optional<OperationStartSetDailyPerform> operationStartSetDailyPerformListTmp
				= operationStartSetDailyPerformRepo.findByCid(new CompanyId(companyId));
			if ( operationStartSetDailyPerformListTmp.isPresent() ){
				operationStartSetDailyPerformList.add(operationStartSetDailyPerformListTmp.get());
			}
		}
		toBinaryMap.put(OperationStartSetDailyPerform.class.toString(), operationStartSetDailyPerformList);
		
		// 暫定残数管理データ
//		val interimRemainList
//			= new ArrayList<InterimRemain>();
//	
//		for(String employeeId: employeeIds){
//			List<InterimRemain> interimRemainListTmp
//				=  tmpAnnualHolidayMngRepo.getBySidPeriod(employeeId, datePeriodData);
//			interimRemainList.addAll(interimRemainListTmp);
//		}
//		toBinaryMap.put(InterimRemain.class.toString(), interimRemainList);
	
		
		// 年休残数管理データ
		val tmpAnnualHolidayMngList
			= new ArrayList<TempAnnualLeaveMngs>();
	
		for(String employeeId: employeeIds){
			List<TempAnnualLeaveMngs> tmpAnnualHolidayMngListTmp
				= tmpAnnualHolidayMngRepo.getBySidPeriod(employeeId, datePeriodData);
			tmpAnnualHolidayMngList.addAll(tmpAnnualHolidayMngListTmp);
		}
		toBinaryMap.put(TempAnnualLeaveMngs.class.toString(), tmpAnnualHolidayMngList);
		
		
		// 月別実績の勤怠時間
		val attendanceTimeOfMonthlyList
				= attendanceTimeOfMonthlyRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		toBinaryMap.put(AttendanceTimeOfMonthly.class.toString(), attendanceTimeOfMonthlyList);
		
		// 雇用に紐づく就業締め
		// 保留
		
		
		
		
		
		
		
		
		// ファイルに書き込み
		ObjectBinaryFile.write(toBinaryMap, destionationFile);
				
	}
	
	
	public void testService(){
		System.out.println("CalcAnnLeaAttendanceRateRequireM3::testService() ");
		
	}
	
	
}
