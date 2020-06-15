package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.RepositoriesRequiredByRemNum;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.GetNextAnnualLeaveGrant;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;

/**
 * 次回年休付与日を計算
 * @author masaaki_jinno
 *
 */
public class TestCalcNextAnnualLeaveGrantDate_1 implements CalcNextAnnualLeaveGrantDate {

	/** 年休社員基本情報 */
	private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;
	
	/** 社員の取得 */
	private EmpEmployeeAdapter empEmployee;
	
	/** 社員に対応する締め開始日を取得する */
	private GetClosureStartForEmployee getClosureStartForEmployee;
	
	/** 次回年休付与を取得する */
	private GetNextAnnualLeaveGrant getNextAnnualLeaveGrant;
	
	/**
	 * コンストラクタ
	 */
	public TestCalcNextAnnualLeaveGrantDate_1(){
		
		/** 年休社員基本情報 */
		annLeaEmpBasicInfoRepo = TestAnnLeaEmpBasicInfoRepositoryFactory.create("1");
		
		/** 社員の取得 */
		empEmployee = TestEmpEmployeeAdapterImplFactory.create("1");
		
		/** 社員に対応する締め開始日を取得する */
		getClosureStartForEmployee = TestGetClosureStartForEmployeeFactory.create("1");
		
		/** 次回年休付与を取得する */
		getNextAnnualLeaveGrant = TestGetNextAnnualLeaveGrantFactory.create("1");
	}
	
	/** 次回年休付与日を計算 */
	@Override
	//@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<NextAnnualLeaveGrant> algorithm(
			Optional<RepositoriesRequiredByRemNum> repositoriesRequiredByRemNum,
			String companyId, String employeeId, Optional<DatePeriod> period){
		return this.algorithm(repositoriesRequiredByRemNum, 
				companyId, employeeId, period,
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
		
//			System.out.print("要実装");
//			final String className = Thread.currentThread().getStackTrace()[1].getClassName();
//		    System.out.println(className);
//		    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
//	        System.out.println(methodName);
//	        return null;
	        
	}
	
	/** 次回年休付与日を計算 */
	@Override
	//@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<NextAnnualLeaveGrant> algorithm(
			Optional<RepositoriesRequiredByRemNum> repositoriesRequiredByRemNum, 
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
			empBasicInfoOpt = this.annLeaEmpBasicInfoRepo.get(employeeId);
		}
		if (!empBasicInfoOpt.isPresent()) return nextAnnualLeaveGrantList;
		val empBasicInfo = empBasicInfoOpt.get();
	
		// 「社員」を取得する
		EmployeeImport employee = null;
		if (employeeOpt.isPresent()){
			employee = employeeOpt.get();
		}
		else {
			employee = this.empEmployee.findByEmpId(employeeId);
		}
		if (employee == null) return nextAnnualLeaveGrantList;
		
		// 「期間」をチェック
		DatePeriod targetPeriod = null;
		boolean isSingleDay = false;	// 単一日フラグ=false
		Optional<GeneralDate> closureStartOpt = Optional.empty();
		if (period.isPresent()){
			
			// 開始日、終了日を１日後にずらした期間
			val paramPeriod = period.get();
			int addEnd = 0;
			if (paramPeriod.end().before(GeneralDate.max())) addEnd = 1;
			targetPeriod = new DatePeriod(paramPeriod.start().addDays(1), paramPeriod.end().addDays(addEnd));
		}
		else {
			
			// 社員に対応する締め開始日を取得する
			closureStartOpt = this.getClosureStartForEmployee.algorithm(employeeId);
			if (!closureStartOpt.isPresent()) return nextAnnualLeaveGrantList;
			targetPeriod = new DatePeriod(closureStartOpt.get().addDays(1), GeneralDate.max());
			
			isSingleDay = true;			// 単一日フラグ=true
		}
		
		// 年休付与テーブル設定コードを取得する
		val grantRule = empBasicInfo.getGrantRule();
		val grantTableCode = grantRule.getGrantTableCode().v();
		
		// 次回年休付与を取得する
		nextAnnualLeaveGrantList = this.getNextAnnualLeaveGrant.algorithm(
				repositoriesRequiredByRemNum,
				companyId, grantTableCode, employee.getEntryDate(), grantRule.getGrantStandardDate(),
				targetPeriod, isSingleDay, grantHdTblSetOpt, lengthServiceTblsOpt, closureStartOpt);
		
		// 次回年休付与を返す
		return nextAnnualLeaveGrantList;
		
//		System.out.print("要実装");
//		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
//	    System.out.println(className);
//	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
//        System.out.println(methodName);
//        return null;
	}

	@Override
	public List<NextAnnualLeaveGrant> calNextHdGrantV2(
			Optional<RepositoriesRequiredByRemNum> repositoriesRequiredByRemNum, 
			String companyId, String employeeId, Optional<DatePeriod> period,
			Optional<EmployeeImport> empOp, Optional<AnnualLeaveEmpBasicInfo> annLeaEmpInfoOp,
			Optional<GrantHdTblSet> grantHdTblSetOpt, Optional<List<LengthServiceTbl>> lengthSvTblsOpt,
			Optional<GeneralDate> closureDate) {

//		List<NextAnnualLeaveGrant> nextAnnualLeaveGrantList = new ArrayList<>();
//		// 「年休社員基本情報」を取得
//		Optional<AnnualLeaveEmpBasicInfo> empBasicInfoOpt = Optional.empty();
//		if (annLeaEmpInfoOp.isPresent()){
//			empBasicInfoOpt = annLeaEmpInfoOp;
//		}
//		else {
//			empBasicInfoOpt = this.annLeaEmpBasicInfoRepo.get(employeeId);
//		}
//		if (!empBasicInfoOpt.isPresent()) return nextAnnualLeaveGrantList;
//		val empBasicInfo = empBasicInfoOpt.get();
//	
//		// 「社員」を取得する
//		EmployeeImport employee = null;
//		if (empOp.isPresent()){
//			employee = empOp.get();
//		}
//		else {
//			employee = this.empEmployee.findByEmpId(employeeId);
//		}
//		if (employee == null) return nextAnnualLeaveGrantList;
//		
//		// 「期間」をチェック
//		DatePeriod targetPeriod = null;
//		boolean isSingleDay = false;	// 単一日フラグ=false
//		if (period.isPresent()){
//			
//			// 開始日、終了日を１日後にずらした期間
//			val paramPeriod = period.get();
//			int addEnd = 0;
//			if (paramPeriod.end().before(GeneralDate.max())) addEnd = 1;
//			targetPeriod = new DatePeriod(paramPeriod.start().addDays(1), paramPeriod.end().addDays(addEnd));
//		}
//		else {
//			
//			// 社員に対応する締め開始日を取得する
//			if (!closureDate.isPresent()) return nextAnnualLeaveGrantList;
//			targetPeriod = new DatePeriod(closureDate.get().addDays(1), GeneralDate.max());
//			isSingleDay = true;			// 単一日フラグ=true
//		}
//		
//		// 年休付与テーブル設定コードを取得する
//		val grantRule = empBasicInfo.getGrantRule();
//		val grantTableCode = grantRule.getGrantTableCode().v();
//		
//		// 次回年休付与を取得する
//		nextAnnualLeaveGrantList = this.getNextAnnualLeaveGrant.algorithm(
//				repositoriesRequiredByRemNum,
//				companyId, grantTableCode, employee.getEntryDate(), grantRule.getGrantStandardDate(),
//				targetPeriod, isSingleDay, grantHdTblSetOpt, lengthSvTblsOpt, closureDate);
//		
//		// 次回年休付与を返す
//		return nextAnnualLeaveGrantList;
		System.out.print("要実装");
		final String className = Thread.currentThread().getStackTrace()[1].getClassName();
	    System.out.println(className);
	    final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName);
        return null;
	}
}

