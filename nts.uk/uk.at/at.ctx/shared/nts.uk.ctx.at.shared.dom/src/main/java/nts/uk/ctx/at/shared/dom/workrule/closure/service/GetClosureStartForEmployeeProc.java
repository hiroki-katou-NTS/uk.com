package nts.uk.ctx.at.shared.dom.workrule.closure.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.AffPeriodEmpCodeImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 処理：社員に対応する締め開始日を取得する
 * @author shuichi_ishida
 */
public class GetClosureStartForEmployeeProc {
	
	/**
	 * 社員に対応する締め開始日を取得する
	 * @param employeeId 社員ID
	 * @return 締め開始日
	 */
	public static Optional<GeneralDate> algorithm(RequireM1 require, CacheCarrier cacheCarrier, String employeeId){
		
		// ログインしている会社ID　取得
		LoginUserContext loginUserContext = AppContexts.user();
		String companyId = loginUserContext.companyId();
		
		// 全締めの当月と期間を取得する
		List<ClosureInfo> closureInfos = ClosureService.getAllClosureInfo(require);
		
		// チェック開始日・終了日を計算
		DatePeriod checkPeriod = calcCheckStartAndEnd(require, cacheCarrier, employeeId, closureInfos);
		GeneralDate checkStart = checkPeriod.start();
		GeneralDate checkEnd = checkPeriod.end();
		
		if (checkStart == null || checkEnd == null) return Optional.empty();

		// 社員IDからチェック開始日以後の期間が含まれる雇用履歴を取得
		List<String> employeeIds = new ArrayList<>();
		employeeIds.add(employeeId);
		DatePeriod empPeriod = new DatePeriod(checkStart, GeneralDate.max());	// チェック開始日～最大年月日
		List<SharedSidPeriodDateEmploymentImport> employmentList = require.employmentHistory(cacheCarrier, employeeIds, empPeriod);
		if (employmentList == null) return Optional.empty();
		if (employmentList.size() == 0) return Optional.empty();
		List<AffPeriodEmpCodeImport> empCodeList = employmentList.get(0).getAffPeriodEmpCodeExports();
		if (empCodeList.size() == 0) return Optional.empty();
		empCodeList.sort((a, b)->a.getPeriod().start().compareTo(b.getPeriod().start()));
		
		// 「検索開始日」←「チェック開始日」
		GeneralDate searchStart = checkStart;
		
		// 「チェック開始日」←該当する雇用履歴の最小開始日
		checkStart = empCodeList.get(0).getPeriod().start();
		
		while (true){
			
			// 検索開始日を基に、使用する雇用履歴を取得
			AffPeriodEmpCodeImport empCode = null;
			for (AffPeriodEmpCodeImport checkEmpCode : empCodeList) {
				if (checkEmpCode.getPeriod().start().after(searchStart)) break;
				if (checkEmpCode.getPeriod().contains(searchStart)) {
					empCode = checkEmpCode;
					break;
				}
			}
			if (empCode == null) break;
			String employmentCd = empCode.getEmploymentCode();
			
			// 雇用に紐づく締めを取得する
			Integer closureId = 1;
			val closureEmploymentOpt = require.employmentClosure(companyId, employmentCd);
			if (closureEmploymentOpt.isPresent()){
				val closureEmployment = closureEmploymentOpt.get();
				closureId = closureEmployment.getClosureId();
			}
			
			// 締め情報．期間を取得
			ClosureInfo targetClosureInfo = null;
			for (val closureInfo : closureInfos){
				if (closureInfo.getClosureId().value == closureId){
					targetClosureInfo = closureInfo;
					break;
				}
			}
			if (targetClosureInfo == null) break;
			
			// 既に締められた雇用履歴かチェック
			if (empCode.getPeriod().end().afterOrEquals(targetClosureInfo.getPeriod().start())){
				// 雇用履歴の途中まで締められたかチェック
				if (empCode.getPeriod().start().before(targetClosureInfo.getPeriod().start())){
					// チェック開始日　←　「締め情報．期間．開始日」
					checkStart = targetClosureInfo.getPeriod().start();
				}
			}
			else {
				// チェック開始日　←　「所属雇用履歴．期間．終了日」の翌日
				checkStart = empCode.getPeriod().end().addDays(1);
			}
			
			// チェック終了日までチェックが完了したかチェック
			if (checkEnd.beforeOrEquals(empCode.getPeriod().end())) break;
			
			// 検索開始日　←　所属雇用履歴．期間．終了日の翌日
			searchStart = empCode.getPeriod().end().addDays(1);
		}
		
		// チェック開始日を返す
		return Optional.ofNullable(checkStart);
	}
	
	/**
	 * チェック開始日・終了日を計算
	 */
	private static DatePeriod calcCheckStartAndEnd(RequireM2 require, CacheCarrier cacheCarrier, 
			String employeeId, List<ClosureInfo> closureInfos){

		GeneralDate checkStart = null;
		GeneralDate checkEnd = null;
		for (val closureInfo : closureInfos){
			
			// 締め情報のうち、一番早い「期間．開始日」を取得
			val targetPeriod = closureInfo.getPeriod();
			if (checkStart == null){
				checkStart = targetPeriod.start();
			}
			else if (checkStart.after(targetPeriod.start())){
				checkStart = targetPeriod.start();
			}
			
			// 締め情報のうち、一番遅い「期間．終了日」を取得
			if (checkEnd == null){
				checkEnd = closureInfo.getPeriod().end();
			}
			else if (checkEnd.before(targetPeriod.end())){
				checkEnd = closureInfo.getPeriod().end();
			}
		}
		
		// 「社員」を取得する
		val employee = require.employee(cacheCarrier, employeeId);
		if (employee != null){
			
			// 「チェック開始日」と「入社年月日」を比較
			if (checkStart.before(employee.getEntryDate())) checkStart = employee.getEntryDate();
		}
		
		return new DatePeriod(checkStart, checkEnd);
	}
	
	public static interface RequireM1 extends ClosureService.RequireM2, RequireM2 {
		
		Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD);
		
		List<SharedSidPeriodDateEmploymentImport> employmentHistory(CacheCarrier cacheCarrier, List<String> sids , DatePeriod datePeriod);
		
	}
	
	public static interface RequireM2 {
		
		EmployeeImport employee(CacheCarrier cacheCarrier, String empId);
	}
}
