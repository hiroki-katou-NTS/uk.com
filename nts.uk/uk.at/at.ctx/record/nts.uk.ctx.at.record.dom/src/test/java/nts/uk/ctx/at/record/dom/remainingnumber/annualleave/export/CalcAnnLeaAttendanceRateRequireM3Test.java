package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnLeaRemNumEachMonthRepository;
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
		return empEmployeeAdapter.findByEmpIdRequire(cacheCarrier, empId);
	}

	/** 年休設定 */
	@Override
	public AnnualPaidLeaveSetting annualPaidLeaveSetting(String companyId) {
		return annualPaidLeaveSettingRepo.findByCompanyId(companyId);
	}

	/** 年休社員基本情報 */
	@Override
	public Optional<AnnualLeaveEmpBasicInfo> employeeAnnualLeaveBasicInfo(String employeeId) {
		return annLeaEmpBasicInfoRepo.get(employeeId);
	}

	/** 年休付与テーブル設定 */
	@Override
	public Optional<GrantHdTblSet> grantHdTblSet(String companyId, String yearHolidayCode) {
		return yearHolidayRepo.findByCode(companyId, yearHolidayCode);
	}

	/** 勤続年数テーブル */
	@Override
	public List<LengthServiceTbl> lengthServiceTbl(String companyId, String yearHolidayCode) {
		return lengthServiceRepository.findByCode(companyId, yearHolidayCode);
	}

	@Override
	public List<AnnLeaRemNumEachMonth> annLeaRemNumEachMonth(String employeeId, DatePeriod closurePeriod) {
		return annLeaRemNumEachMonthRepo.findByClosurePeriod(employeeId, closurePeriod);
	}

	@Override
	public List<WorkInfoOfDailyPerformance> dailyWorkInfos(String employeeId, DatePeriod datePeriod) {
		return workInformationRepo.findByPeriodOrderByYmd(employeeId, datePeriod);
	}

	@Override
	public Optional<WorkType> workType(String companyId, String workTypeCd) {
		return workTypeRepo.findByPK(companyId, workTypeCd);
	}

	@Override
	public Optional<OperationStartSetDailyPerform> dailyOperationStartSet(CompanyId companyId) {
		return operationStartSetDailyPerformRepo.findByCid(companyId);
	}

}
