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
 * ???????????????????????????????????????????????????
 * ???CalcAnnLeaAttendanceRate??????????????????RequireM3????????????????????????????????????
 * @author masaaki_jinno
 *
 */
@Stateless
public class CalcAnnLeaAttendanceRateRequireM3 {
	
	/** ????????????????????????????????? */
	static public Path destionationFile = Paths.get("C:\\jinno\\binaryFile.csv");

	/** ?????? */
	@Inject
	protected EmpEmployeeAdapter empEmployeeAdapter;
	
	/** ???????????? */
	@Inject
	protected AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepo;
	
	/** ???????????????????????? */
	@Inject
	protected AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;

	/** ?????????????????????????????? */
	@Inject
	protected YearHolidayRepository yearHolidayRepo;
	
	/** ???????????????????????? */
	@Inject
	private LengthServiceTest lengthServiceRepository;
	
	/** ??????????????????????????? */
	@Inject
	private AnnLeaRemNumEachMonthRepository annLeaRemNumEachMonthRepo;
	
	/**  */
	@Inject
	private WorkInformationRepository workInformationRepo;
	
	/**  */
	@Inject
	protected WorkTypeRepository workTypeRepo;
	
	/** ????????????????????????????????? */
	@Inject
	private OperationStartSetDailyPerformRepository operationStartSetDailyPerformRepo;
	
	/** ??????????????????????????? */
	@Inject
	private TmpAnnualHolidayMngRepository tmpAnnualHolidayMngRepo;
	
	/** ??????????????????????????? */
	@Inject
	private AttendanceTimeOfMonthlyRepository attendanceTimeOfMonthlyRepo;
	
	/** ?????????????????????????????? */
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	
	/**
	 * ??????????????????????????????
	 */
	public void createBinaryFile(){

		// ?????????????????????
		Map<String,Object> toBinaryMap = new HashMap<String,Object>();
		
		// ???CSV?????????????????????????????????
		
		
		// ??????ID?????????
		ArrayList<String> companyIds = new ArrayList<String>();
		companyIds.add("000000000000-0101");
		
		// ??????ID?????????
		ArrayList<String> employeeIds = new ArrayList<String>();
		employeeIds.add("ca294040-910f-4a42-8d90-2bd02772697c");
		
		// ??????
		DatePeriod datePeriodData = new DatePeriod(
				GeneralDate.ymd( 2019, 1, 1 ), GeneralDate.ymd( 2020, 12, 31 ));
		
		List<YearMonth> yearMonths = new ArrayList<YearMonth>(); // ooooo ???????????????
		yearMonths.add(YearMonth.of(2019,1));
		yearMonths.add(YearMonth.of(2019,2));
		yearMonths.add(YearMonth.of(2019,3));
		yearMonths.add(YearMonth.of(2019,4));
		yearMonths.add(YearMonth.of(2019,5));
		yearMonths.add(YearMonth.of(2019,6));
		
		
		// ??????
//		EmpEmployeeAdapter???EmpEmployeeAdapterImpl1 = new EmpEmployeeAdapterImpl();
		List<EmployeeImport> employeeImportList = empEmployeeAdapter.findByEmpId(employeeIds);
		toBinaryMap.put(EmployeeImport.class.toString(), employeeImportList);

		// ????????????
		val annualPaidLeaveSettingList = new ArrayList<AnnualPaidLeaveSetting>();
		for(String companyId: companyIds){
			AnnualPaidLeaveSetting annualPaidLeaveSetting
				= annualPaidLeaveSettingRepo.findByCompanyId(companyId);
			annualPaidLeaveSettingList.add(annualPaidLeaveSetting);
		}
		toBinaryMap.put(AnnualPaidLeaveSetting.class.toString(), annualPaidLeaveSettingList);
		
		//???????????????????????????
		val annualLeaveEmpBasicInfoList = new ArrayList<AnnualLeaveEmpBasicInfo>();
		for(String employeeId: employeeIds){
			Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfo
				= annLeaEmpBasicInfoRepo.get(employeeId);
			if ( annualLeaveEmpBasicInfo.isPresent() ){
				annualLeaveEmpBasicInfoList.add(annualLeaveEmpBasicInfo.get());
			}
		}
		toBinaryMap.put(AnnualLeaveEmpBasicInfo.class.toString(), annualLeaveEmpBasicInfoList);

		// ?????????????????????????????? 
		//protected YearHolidayRepository yearHolidayRepo;
		List<GrantHdTblSet> grantHdTblSetList = new ArrayList<GrantHdTblSet>();
		for(String companyId: companyIds){
			List<GrantHdTblSet> annualPaidLeaveSettingListTmp
				= yearHolidayRepo.findAll(companyId);
			grantHdTblSetList.addAll(annualPaidLeaveSettingListTmp);
		}
		toBinaryMap.put(GrantHdTblSet.class.toString(), grantHdTblSetList);
		
		// ????????????????????????
		val yearHolidayCodes = new ArrayList<String>();

		List<LengthOfService> lengthOfServiceList = new ArrayList<LengthOfService>();
		for(String companyId: companyIds){
			List<LengthOfService> lengthOfServiceListTmp
				= lengthServiceRepository.findByCompanyId(companyId)
				.stream().flatMap(c->c.getLengthOfServices().stream()).collect(Collectors.toList());

			lengthOfServiceList.addAll(lengthOfServiceListTmp);
		}
		toBinaryMap.put(LengthServiceTbl.class.toString(), lengthOfServiceList);

		// ???????????????????????????
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
		
		
		// ?????????????????????????????????
		val operationStartSetDailyPerformList = new ArrayList<OperationStartSetDailyPerform>();
		for(String companyId: companyIds){
			Optional<OperationStartSetDailyPerform> operationStartSetDailyPerformListTmp
				= operationStartSetDailyPerformRepo.findByCid(new CompanyId(companyId));
			if ( operationStartSetDailyPerformListTmp.isPresent() ){
				operationStartSetDailyPerformList.add(operationStartSetDailyPerformListTmp.get());
			}
		}
		toBinaryMap.put(OperationStartSetDailyPerform.class.toString(), operationStartSetDailyPerformList);
		
		// ???????????????????????????
//		val interimRemainList
//			= new ArrayList<InterimRemain>();
//	
//		for(String employeeId: employeeIds){
//			List<InterimRemain> interimRemainListTmp
//				=  tmpAnnualHolidayMngRepo.getBySidPeriod(employeeId, datePeriodData);
//			interimRemainList.addAll(interimRemainListTmp);
//		}
//		toBinaryMap.put(InterimRemain.class.toString(), interimRemainList);
	
		
		// ???????????????????????????
		val tmpAnnualHolidayMngList
			= new ArrayList<TempAnnualLeaveMngs>();
	
		for(String employeeId: employeeIds){
			List<TempAnnualLeaveMngs> tmpAnnualHolidayMngListTmp
				= tmpAnnualHolidayMngRepo.getBySidPeriod(employeeId, datePeriodData);
			tmpAnnualHolidayMngList.addAll(tmpAnnualHolidayMngListTmp);
		}
		toBinaryMap.put(TempAnnualLeaveMngs.class.toString(), tmpAnnualHolidayMngList);
		
		
		// ???????????????????????????
		val attendanceTimeOfMonthlyList
				= attendanceTimeOfMonthlyRepo.findBySidsAndYearMonths(employeeIds, yearMonths);
		toBinaryMap.put(AttendanceTimeOfMonthly.class.toString(), attendanceTimeOfMonthlyList);
		
		// ??????????????????????????????
		// ??????
		
		
		
		
		
		
		
		
		// ???????????????????????????
		ObjectBinaryFile.write(toBinaryMap, destionationFile);
				
	}
	
	
	public void testService(){
		System.out.println("CalcAnnLeaAttendanceRateRequireM3::testService() ");
		
	}
	
	
}
