package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployeeImpl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.GetNextAnnualLeaveGrant;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.GetNextAnnualLeaveGrantImpl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 実装：次回年休付与日を計算
 * @author shuichu_ishida
 */
@Stateless
public class CalcNextAnnualLeaveGrantDateImpl implements CalcNextAnnualLeaveGrantDate {

	/** 年休社員基本情報 */
	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;
	/** 社員の取得 */
	@Inject
	private EmpEmployeeAdapter empEmployee;
	/** 社員に対応する締め開始日を取得する */
	@Inject
	private GetClosureStartForEmployee getClosureStartForEmployee;
	/** 次回年休付与を取得する */
	@Inject
	private GetNextAnnualLeaveGrant getNextAnnualLeaveGrant;
	/*require*/
	@Inject
	private YearHolidayRepository yearHolidayRepo;
	@Inject
	private LengthServiceRepository lengthServiceRepo;
	@Inject
	private GrantYearHolidayRepository grantYearHolidayRepo;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	@Inject
	private ClosureRepository closureRepository;
	/*require*/
	
	/** 次回年休付与を計算 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<NextAnnualLeaveGrant> algorithm(String companyId, String employeeId, Optional<DatePeriod> period){
		return this.algorithm(companyId, employeeId, period,
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
	}
	
	/** 次回年休付与を計算 */
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<NextAnnualLeaveGrant> algorithm(String companyId, String employeeId, Optional<DatePeriod> period,
			Optional<EmployeeImport> employeeOpt, Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfoOpt,
			Optional<GrantHdTblSet> grantHdTblSetOpt, Optional<List<LengthServiceTbl>> lengthServiceTblsOpt) {
		val require = createRequireImpl();
		val cacheCarrier = new CacheCarrier();
		return algorithmRequire(require, cacheCarrier, companyId, employeeId, period, 
				employeeOpt, annualLeaveEmpBasicInfoOpt, grantHdTblSetOpt, lengthServiceTblsOpt);
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<NextAnnualLeaveGrant> algorithmRequire(Require require, CacheCarrier cacheCarrier,
			String companyId, String employeeId, Optional<DatePeriod> period,
			Optional<EmployeeImport> employeeOpt, Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfoOpt,
			Optional<GrantHdTblSet> grantHdTblSetOpt, Optional<List<LengthServiceTbl>> lengthServiceTblsOpt) {
		
		List<NextAnnualLeaveGrant> nextAnnualLeaveGrantList = new ArrayList<>();

		// 「年休社員基本情報」を取得
		Optional<AnnualLeaveEmpBasicInfo> empBasicInfoOpt = Optional.empty();
		if (annualLeaveEmpBasicInfoOpt.isPresent()){
			empBasicInfoOpt = annualLeaveEmpBasicInfoOpt;
		}
		else {
			empBasicInfoOpt = require.get(employeeId);
		}
		if (!empBasicInfoOpt.isPresent()) return nextAnnualLeaveGrantList;
		val empBasicInfo = empBasicInfoOpt.get();
	
		// 「社員」を取得する
		EmployeeImport employee = null;
		if (employeeOpt.isPresent()){
			employee = employeeOpt.get();
		}
		else {
			employee = this.empEmployee.findByEmpIdRequire(cacheCarrier, employeeId);
		}
		if (employee == null) return nextAnnualLeaveGrantList;
		
		// 「期間」をチェック
		DatePeriod targetPeriod = null;
		boolean isSingleDay = false;	// 単一日フラグ=false
		if (period.isPresent()){
			
			// 開始日、終了日を１日後にずらした期間
			val paramPeriod = period.get();
			int addEnd = 0;
			if (paramPeriod.end().before(GeneralDate.max())) addEnd = 1;
			targetPeriod = new DatePeriod(paramPeriod.start().addDays(1), paramPeriod.end().addDays(addEnd));
		}
		else {
			
			// 社員に対応する締め開始日を取得する
			val closureStartOpt = this.getClosureStartForEmployee.algorithmRequire(require, cacheCarrier, employeeId);
			if (!closureStartOpt.isPresent()) return nextAnnualLeaveGrantList;
			targetPeriod = new DatePeriod(closureStartOpt.get().addDays(1), GeneralDate.max());
			
			isSingleDay = true;			// 単一日フラグ=true
		}
		
		// 年休付与テーブル設定コードを取得する
		val grantRule = empBasicInfo.getGrantRule();
		val grantTableCode = grantRule.getGrantTableCode().v();
		
		// 次回年休付与を取得する
		nextAnnualLeaveGrantList = this.getNextAnnualLeaveGrant.algorithmRequire(require, cacheCarrier,
				companyId, grantTableCode, employee.getEntryDate(), grantRule.getGrantStandardDate(),
				targetPeriod, isSingleDay, grantHdTblSetOpt, lengthServiceTblsOpt);
		
		// 次回年休付与を返す
		return nextAnnualLeaveGrantList;
	}

	@Override
	public List<NextAnnualLeaveGrant> calNextHdGrantV2(String companyId, String employeeId, Optional<DatePeriod> period,
			Optional<EmployeeImport> empOp, Optional<AnnualLeaveEmpBasicInfo> annLeaEmpInfoOp,
			Optional<GrantHdTblSet> grantHdTblSetOpt, Optional<List<LengthServiceTbl>> lengthSvTblsOpt,
			Optional<GeneralDate> closureDate) {

		List<NextAnnualLeaveGrant> nextAnnualLeaveGrantList = new ArrayList<>();
		// 「年休社員基本情報」を取得
		Optional<AnnualLeaveEmpBasicInfo> empBasicInfoOpt = Optional.empty();
		if (annLeaEmpInfoOp.isPresent()){
			empBasicInfoOpt = annLeaEmpInfoOp;
		}
		else {
			empBasicInfoOpt = this.annLeaEmpBasicInfoRepo.get(employeeId);
		}
		if (!empBasicInfoOpt.isPresent()) return nextAnnualLeaveGrantList;
		val empBasicInfo = empBasicInfoOpt.get();
	
		// 「社員」を取得する
		EmployeeImport employee = null;
		if (empOp.isPresent()){
			employee = empOp.get();
		}
		else {
			employee = this.empEmployee.findByEmpId(employeeId);
		}
		if (employee == null) return nextAnnualLeaveGrantList;
		
		// 「期間」をチェック
		DatePeriod targetPeriod = null;
		boolean isSingleDay = false;	// 単一日フラグ=false
		if (period.isPresent()){
			
			// 開始日、終了日を１日後にずらした期間
			val paramPeriod = period.get();
			int addEnd = 0;
			if (paramPeriod.end().before(GeneralDate.max())) addEnd = 1;
			targetPeriod = new DatePeriod(paramPeriod.start().addDays(1), paramPeriod.end().addDays(addEnd));
		}
		else {
			
			// 社員に対応する締め開始日を取得する
			if (!closureDate.isPresent()) return nextAnnualLeaveGrantList;
			targetPeriod = new DatePeriod(closureDate.get().addDays(1), GeneralDate.max());
			isSingleDay = true;			// 単一日フラグ=true
		}
		
		// 年休付与テーブル設定コードを取得する
		val grantRule = empBasicInfo.getGrantRule();
		val grantTableCode = grantRule.getGrantTableCode().v();
		
		// 次回年休付与を取得する
		nextAnnualLeaveGrantList = this.getNextAnnualLeaveGrant.algorithm(
				companyId, grantTableCode, employee.getEntryDate(), grantRule.getGrantStandardDate(),
				targetPeriod, isSingleDay, grantHdTblSetOpt, lengthSvTblsOpt);
		
		// 次回年休付与を返す
		return nextAnnualLeaveGrantList;
	}
	
	public static interface Require extends GetNextAnnualLeaveGrantImpl.Require, GetClosureStartForEmployeeImpl.Require{
//		this.annLeaEmpBasicInfoRepo.get(employeeId);
		Optional<AnnualLeaveEmpBasicInfo> get(String employeeId);
	}
	
	private Require createRequireImpl() {
		return new CalcNextAnnualLeaveGrantDateImpl.Require() {
			@Override
			public Optional<GrantHdTbl> find(String companyId, int conditionNo, String yearHolidayCode, int grantNum) {
				return grantYearHolidayRepo.find(companyId, 1, yearHolidayCode, grantNum);
			}
			@Override
			public Optional<GrantHdTblSet> findGrantHdTblSetByCode(String companyId, String yearHolidayCode) {
				return yearHolidayRepo.findByCode(companyId, yearHolidayCode);
			}
			@Override
			public List<LengthServiceTbl> findLengthServiceTblByCode(String companyId, String yearHolidayCode) {
				return lengthServiceRepo.findByCode(companyId, yearHolidayCode);
			}
			@Override
			public Optional<ClosureEmployment> findByEmploymentCD(String companyID, String employmentCD) {
				return closureEmploymentRepo.findByEmploymentCD(companyID, employmentCD);
			}
			@Override
			public List<Closure> findAllUse(String companyId) {
				return closureRepository.findAllUse(companyId);
			}
			@Override
			public Optional<AnnualLeaveEmpBasicInfo> get(String employeeId) {
				return annLeaEmpBasicInfoRepo.get(employeeId);
			}
			@Override
			public Optional<Closure> findClosureById(String companyId, int closureId) {
				return closureRepository.findById(companyId, closureId);
			}
		};
	}
}
