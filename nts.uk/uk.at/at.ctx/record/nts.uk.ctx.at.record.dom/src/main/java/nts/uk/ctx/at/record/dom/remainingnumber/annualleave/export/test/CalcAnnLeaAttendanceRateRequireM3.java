package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
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
 * テスト用バイナリファイル作成クラス
 * 「CalcAnnLeaAttendanceRate」クラスの「RequireM3」インターフェースに対応
 * @author masaaki_jinno
 *
 */
@Stateless
public class CalcAnnLeaAttendanceRateRequireM3 {

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
	private LengthServiceRepository lengthServiceRepository;
	
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
	
	/**
	 * バイナリファイル作成
	 */
	public void createBinaryFile(){

		// バイナリマップ
		Map<String,Object> toBinaryMap = new HashMap<String,Object>();
		
		// ※CSVファイルから変数を作成
		
		// 会社IDリスト
		ArrayList<String> companyIds = new ArrayList<String>();
		
		// 社員IDリスト
		ArrayList<String> employeeIds = new ArrayList<String>();
		
		
		
		// 参照不可。保留
		// EmpEmployeeAdapterImpl　EmpEmployeeAdapterImpl1 = new EmpEmployeeAdapterImpl();
		
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
		val yearHolidayCodes = new ArrayList<String>(); // ooooo　要実装追加
		
		List<LengthServiceTbl> lengthServiceTblList = new ArrayList<LengthServiceTbl>();
		for(String companyId: companyIds){
			List<LengthServiceTbl> lengthServiceTblListTmp
				= lengthServiceRepository.findByCode(companyId, yearHolidayCodes); // ooooo　要実装追加
			
			lengthServiceTblList.addAll(lengthServiceTblListTmp);
		}
		toBinaryMap.put(GrantHdTblSet.class.toString(), grantHdTblSetList);
		
		// 年休月別残数データ
		List<YearMonth> yearMonths = new ArrayList<YearMonth>(); // ooooo 要実装追加
		val annLeaRemNumEachMonthList
				= annLeaRemNumEachMonthRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		toBinaryMap.put(AnnLeaRemNumEachMonth.class.toString(), annLeaRemNumEachMonthList);
		
		// ??
		// ooooo 要確認
		// DatePeriod datePeriod = new DatePeriod(GeneralDate.ymd(1900, 1, 1), GeneralDate.ymd(9999, 1, 1));
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
		
		
		
		
		
		
	}
	
	public void testService(){
		
		System.out.println("CalcAnnLeaAttendanceRateRequireM3::testService() ");
		
	}
	
}
