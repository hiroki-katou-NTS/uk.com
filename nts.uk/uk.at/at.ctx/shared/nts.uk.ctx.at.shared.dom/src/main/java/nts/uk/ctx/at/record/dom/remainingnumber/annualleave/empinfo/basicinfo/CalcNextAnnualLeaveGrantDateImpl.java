package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.GetNextAnnualLeaveGrant;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

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
	
	/** 次回年休付与リスト */
	private List<NextAnnualLeaveGrant> nextAnnualLeaveGrantList;
	
	/**
	 * 次回年休付与を計算
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @return 次回年休付与リスト
	 */
	@Override
	public List<NextAnnualLeaveGrant> algorithm(String companyId, String employeeId, Optional<DatePeriod> period){
		
		this.nextAnnualLeaveGrantList = new ArrayList<>();

		// 「年休社員基本情報」を取得
		val annualLeaveEmpBasicInfoOpt = this.annLeaEmpBasicInfoRepo.get(employeeId);
		if (!annualLeaveEmpBasicInfoOpt.isPresent()) return this.nextAnnualLeaveGrantList;
		val annualLeaveEmpBasicInfo = annualLeaveEmpBasicInfoOpt.get();
	
		// 「社員」を取得する
		val employee = this.empEmployee.findByEmpId(employeeId);
		if (employee == null) return this.nextAnnualLeaveGrantList;
		
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
			val closureStartOpt = this.getClosureStartForEmployee.algorithm(employeeId);
			if (!closureStartOpt.isPresent()) return this.nextAnnualLeaveGrantList;
			targetPeriod = new DatePeriod(closureStartOpt.get().addDays(1), GeneralDate.max());
			
			isSingleDay = true;			// 単一日フラグ=true
		}
		
		// 年休付与テーブル設定コードを取得する
		val grantRule = annualLeaveEmpBasicInfo.getGrantRule();
		val grantTableCode = grantRule.getGrantTableCode().v();
		
		// 次回年休付与を取得する
		this.nextAnnualLeaveGrantList = this.getNextAnnualLeaveGrant.algorithm(
				companyId, grantTableCode, employee.getEntryDate(), grantRule.getGrantStandardDate(),
				targetPeriod, isSingleDay);
		
		// 次回年休付与を返す
		return this.nextAnnualLeaveGrantList;
	}
}
