package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.company.AffCompanyHistImport;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.specialholiday.calculateremainnum.RemainSpecialHoidayCalculation;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.specialholiday.updateremainnum.RemainSpecialHolidayUpdating;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.InPeriodOfSpecialLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveManagementService;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.export.NextSpecialLeaveGrant;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.MonthDay;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.TypeTime;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;

/**
 * 次回特休付与を計算
 * @author masaaki_jinno
 *
 */
public class CalcNextSpecialLeaveGrantDate {

	/**
	 * 次回特休付与日を計算
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param　spLeaveCD　特別休暇コード
	 * @param period 期間
	 * @return 次回特休付与リスト
	 */
	public static List<NextSpecialLeaveGrant> algorithm(
			SpecialLeaveManagementService.RequireM5 require, 
			CacheCarrier cacheCarrier, 
			String companyId, 
			String employeeId, 
			int spLeaveCD, 
			Optional<DatePeriod> period) {
		
		// ドメインモデル「特別休暇社員基本情報」を取得
		Optional<SpecialLeaveBasicInfo> specialLeaveBasicInfoOpt
			= require.specialLeaveBasicInfo(employeeId, spLeaveCD, UseAtr.USE);
		
		// 特別休暇使用区分をチェックする
		if ( specialLeaveBasicInfoOpt.isPresent() ){
			if ( specialLeaveBasicInfoOpt.get().getUsed().isUse() ){ // 使用するとき
				
				
				
			} else { // 使用しないとき
				
				
				
			}
			
		}

		
		
		return algorithm(require, cacheCarrier, companyId, employeeId, period, 
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
	}
	
	/**
	 * 次回特休付与日を計算
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @return 次回特休付与リスト
	 */
	private static List<NextSpecialLeaveGrant> algorithm１(
			SpecialLeaveManagementService.RequireM5 require, 
			CacheCarrier cacheCarrier, 
			String companyId, 
			String employeeId, 
			int spLeaveCD, 
			Optional<DatePeriod> period) {
		
		// パラメータ「期間」を1日後ろにずらす
		DatePeriod targetPeriod = null;
		if (period.isPresent()){
			
			// 開始日、終了日を１日後にずらした期間
			val paramPeriod = period.get();
			int addEnd = 0;
			if (paramPeriod.end().before(GeneralDate.max())) addEnd = 1;
			targetPeriod = new DatePeriod(paramPeriod.start().addDays(1), paramPeriod.end().addDays(addEnd));
		}
		
		// 付与日数情報を取得する
		// ooooo
		
		
		
		
		return algorithm(require, cacheCarrier, companyId, employeeId, period, 
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
	}
	
	
	/**
	 * 付与日数情報を取得する
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * int spLeaveCD 特別休暇コード
	 * @param period 期間
	 * @return 次回特休付与リスト
	 */
	private static List<NextSpecialLeaveGrant> getSpecialLeaveGrantInfo(
			SpecialLeaveManagementService.RequireM5 require, 
			CacheCarrier cacheCarrier, 
			String companyId, 
			String employeeId, 
			int spLeaveCD, 
			Optional<DatePeriod> period) {
		
		// 「特別休暇」を取得する
		Optional<SpecialHoliday> specialHolidays = require.specialHoliday(companyId, spLeaveCD);
		if ( specialHolidays.isPresent() ){
			
			// 自動付与区分を確認
			if ( specialHolidays.get().getAutoGrant().equals(NotUseAtr.USE)){
				
				// 付与基準日を求める
				Optional<GeneralDate> grantDate = getSpecialLeaveGrantDate(
						require, cacheCarrier, companyId, employeeId, spLeaveCD, period);
				
				// 取得している「特別休暇．付与情報．付与するタイミングの種類」をチェックする
				TypeTime typeTime = specialHolidays.get().getGrantRegular().getTypeTime();
				
				if (typeTime.equals(TypeTime.GRANT_SPECIFY_DATE)){ // 指定日に付与する
					
					// 指定日の付与日一覧を求める
					
				
					
				} else if (typeTime.equals(TypeTime.GRANT_PERIOD)){ // 期間で付与する
				
					
					
				} else if (typeTime.equals(TypeTime.REFER_GRANT_DATE_TBL)){ // 付与テーブルを参照して付与する
				
					
				
				}
				
			}
		}
		
		return null;
	}
	
	/**
	 * 付与基準日を取得する
	 * @param employeeId 社員ID
	 * @param period 期間
	 * @return 付与基準日
	 */
	private static Optional<GeneralDate> getSpecialLeaveGrantDate(
			SpecialLeaveManagementService.RequireM5 require, 
			CacheCarrier cacheCarrier, 
			String companyId, 
			String employeeId, 
			int spLeaveCD, 
			Optional<DatePeriod> period) {
		
		// パラメータ 付与基準日
		Optional<GeneralDate> grantStandardDate = Optional.empty();
		
		// 「特別休暇」を取得する
		Optional<SpecialHoliday> specialHolidays = require.specialHoliday(companyId, spLeaveCD);
		if ( specialHolidays.isPresent() ){

			// ドメインモデル「特別休暇．付与情報．付与基準日」をチェックする
			Optional<GrantDate> grantDate = specialHolidays.get().getGrantRegular().getGrantDate();
			
			if ( grantDate.isPresent() ){
				if ( grantDate.get().equals(GrantDate.EMP_GRANT_DATE)){ // 入社日を付与基準日とする
					
					// ooooo
					
	//				// 社員ID（List）と指定期間から所属会社履歴項目を取得 【Request：No211】
	//				List<AffCompanyHistImport> listAffCompanyHistImport = this.syCompanyRecordAdapter
	//						.getAffCompanyHistByEmployee(new ArrayList<>(listAppId), period);
	////				if (listAffCompanyHistImport.isEmpty() || listAffCompanyHistImport.stream()
	////						.flatMap(x -> x.getLstAffComHistItem().stream()).collect(Collectors.toList()).isEmpty()) {
	////					oneMonthApprovalStatusDto.setMessageID("Msg_875");
	////					return oneMonthApprovalStatusDto;
	////				}
				
				} else if ( grantDate.get().equals(GrantDate.GRANT_BASE_HOLIDAY)){ // 年休付与基準日を付与基準日とする
					
					// ドメインモデル「年休社員基本情報」を取得する
					Optional<AnnualLeaveEmpBasicInfo> empBasicInfo
						= require.employeeAnnualLeaveBasicInfo(employeeId);
					
					if ( empBasicInfo.isPresent() ){
						// 所得したドメインモデル「年休社員基本情報．付与ルール．付与基準日」をパラメータ「付与基準日」にセットする
						grantStandardDate = Optional.of(empBasicInfo.get().getGrantRule().getGrantStandardDate());
					}
					
				} else if ( grantDate.get().equals(GrantDate.SPECIAL_LEAVE_DATE)){ // 特別休暇付与基準日を付与基準日とする
					
					// ドメインモデル「特別休暇社員基本情報」を取得
					Optional<SpecialLeaveBasicInfo> specialLeaveBasicInfoOpt
						= require.specialLeaveBasicInfo(employeeId, spLeaveCD, UseAtr.USE);
					if ( specialLeaveBasicInfoOpt.isPresent() ){
						// INPUT．「特別休暇基本情報．付与設定．付与基準日」をパラメータ「付与基準日」にセットする
						grantStandardDate = Optional.of(specialLeaveBasicInfoOpt.get().getGrantSetting().getGrantDate());
					}
				}
			}
		}
		
		return grantStandardDate;
	}
	
	/**
	 * 指定日の付与日一覧を求める
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param spLeaveCD 期間
	 * @param grantDate 付与基準日
	 * @return 次回特休付与リスト
	 */
	private static List<NextSpecialLeaveGrant> getSpecifyGrantDateList(
			SpecialLeaveManagementService.RequireM5 require, 
			CacheCarrier cacheCarrier, 
			String companyId, 
			String employeeId,
			int spLeaveCD, 
			Optional<DatePeriod> period,
			GeneralDate grantDate) {
		
		// 付与月日に値が入っているか
		
		// 「特別休暇」を取得する
		Optional<SpecialHoliday> specialHolidays = require.specialHoliday(companyId, spLeaveCD);
		if ( specialHolidays.isPresent() ){
			
			// 付与月日を取得
			Optional<MonthDay> grantMonthDay 
				= specialHolidays.get().getGrantRegular().getFixGrantDate().getGrantMonthDay();
			
			if ( grantMonthDay.isPresent() ){ // 付与月日に値が入っているとき
				
				if ( period.isPresent() ){
					// 期間開始日の年と指定日付与.付与日の月日をパラメータ「付与基準日」にセットする
					grantDate = GeneralDate.ymd(
							period.get().start().year(),
							grantMonthDay.get().getMonth(),
							grantMonthDay.get().getDay());
				}
				
			} else { // 付与月日に値が入っていないとき
				
				// 期間開始日の年と付与基準日の月日をパラメータ「付与基準日」にセットする
				grantDate = GeneralDate.ymd(
						period.get().start().year(),
						grantDate.month(),
						grantDate.day());
			}
			
			// 定期の付与日を求める
			
			
			
		}
		
			
			
			
			
	}
	
	/**
	 * 定期の付与日一覧を求める
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * int spLeaveCD 特別休暇コード
	 * @param period 期間
	 * @param grantDate 付与基準日
	 * @return 次回特休付与リスト
	 */
	private static List<NextSpecialLeaveGrant> getSpecialLeaveGrantInfo(
			SpecialLeaveManagementService.RequireM5 require, 
			CacheCarrier cacheCarrier, 
			String companyId, 
			String employeeId, 
			int spLeaveCD, 
			Optional<DatePeriod> period,
			Optional<GeneralDate> grantDateOpt) {
		
		// パラメータ「付与基準日」がNULLかどうかチェックする
		if ( ! grantDateOpt.isPresent() ){ // Nullのとき
			
			// 「List＜次回特別休暇付与＞」を空で作成
			List<NextSpecialLeaveGrant> list = new ArrayList<NextSpecialLeaveGrant>();
			return list;
		}
		
		// パラメータ「付与日」←パラメータ「付与基準日」
		GeneralDate grantDate = GeneralDate.localDate(grantDateOpt.get().localDate());
		
		
	}
	
	
	
	
}
	
	
	
	
	
	
	
	
	
	

//	/**
//	 * 次回特休付与を計算
//	 * @param companyId 会社ID
//	 * @param employeeId 社員ID
//	 * @param period 期間
//	 * @param employee 社員
//	 * @param annualLeaveEmpBasicInfo 特休社員基本情報
//	 * @param grantHdTblSet 特休付与テーブル設定
//	 * @param lengthServiceTbls 勤続年数テーブルリスト
//	 * @return 次回特休付与リスト
//	 */
//	public static List<NextSpecialLeaveGrant> algorithm(RequireM2 require, CacheCarrier cacheCarrier,
//			String companyId, String employeeId, Optional<DatePeriod> period,
//			Optional<EmployeeImport> employeeOpt, Optional<AnnualLeaveEmpBasicInfo> annualLeaveEmpBasicInfoOpt,
//			Optional<GrantHdTblSet> grantHdTblSetOpt, Optional<List<LengthServiceTbl>> lengthServiceTblsOpt) {
//		
//		List<NextSpecialLeaveGrant> nextAnnualLeaveGrantList = new ArrayList<>();
//
//		// 「特休社員基本情報」を取得
//		Optional<AnnualLeaveEmpBasicInfo> empBasicInfoOpt = Optional.empty();
//		if (annualLeaveEmpBasicInfoOpt.isPresent()){
//			empBasicInfoOpt = annualLeaveEmpBasicInfoOpt;
//		}
//		else {
//			empBasicInfoOpt = require.employeeAnnualLeaveBasicInfo(employeeId);
//		}
//		if (!empBasicInfoOpt.isPresent()) return nextAnnualLeaveGrantList;
//		val empBasicInfo = empBasicInfoOpt.get();
//	
//		// 「社員」を取得する
//		EmployeeImport employee = null;
//		if (employeeOpt.isPresent()){
//			employee = employeeOpt.get();
//		}
//		else {
//			employee = require.employee(cacheCarrier, employeeId);
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
//			val closureStartOpt = GetClosureStartForEmployee.algorithm(require, cacheCarrier, employeeId);
//			if (!closureStartOpt.isPresent()) return nextAnnualLeaveGrantList;
//			targetPeriod = new DatePeriod(closureStartOpt.get().addDays(1), GeneralDate.max());
//			
//			isSingleDay = true;			// 単一日フラグ=true
//		}
//		
//		// 特休付与テーブル設定コードを取得する
//		val grantRule = empBasicInfo.getGrantRule();
//		val grantTableCode = grantRule.getGrantTableCode().v();
//		
//		// 次回特休付与を取得する
//		nextAnnualLeaveGrantList = GetNextSpecialLeaveGrant.algorithm(require, cacheCarrier,
//				companyId, grantTableCode, employee.getEntryDate(), grantRule.getGrantStandardDate(),
//				targetPeriod, isSingleDay, grantHdTblSetOpt, lengthServiceTblsOpt);
//		
//		// 次回特休付与を返す
//		return nextAnnualLeaveGrantList;
//	}
//	
//	/**
//	 * 次回特休付与を計算
//	 * @param repositoriesRequiredByRemNum 残数処理 キャッシュデータ
//	 * @param companyId 会社ID
//	 * @param employeeId 社員ID
//	 * @param period 期間
//	 * @param employee 社員
//	 * @param annualLeaveEmpBasicInfo 特休社員基本情報
//	 * @param grantHdTblSet 特休付与テーブル設定
//	 * @param lengthServiceTbls 勤続年数テーブルリスト
//	 * @return 次回特休付与リスト
//	 */
//	public static List<NextSpecialLeaveGrant> calNextHdGrantV2(RequireM1 require, CacheCarrier cacheCarrier, 
//			String companyId, String employeeId, Optional<DatePeriod> period,
//			Optional<EmployeeImport> empOp, Optional<AnnualLeaveEmpBasicInfo> annLeaEmpInfoOp,
//			Optional<GrantHdTblSet> grantHdTblSetOpt, Optional<List<LengthServiceTbl>> lengthSvTblsOpt,
//			Optional<GeneralDate> closureDate) {
//
//		List<NextSpecialLeaveGrant> nextAnnualLeaveGrantList = new ArrayList<>();
//		// 「特休社員基本情報」を取得
//		Optional<AnnualLeaveEmpBasicInfo> empBasicInfoOpt = Optional.empty();
//		if (annLeaEmpInfoOp.isPresent()){
//			empBasicInfoOpt = annLeaEmpInfoOp;
//		}
//		else {
//			empBasicInfoOpt = require.employeeAnnualLeaveBasicInfo(employeeId);
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
//			employee = require.employee(cacheCarrier, employeeId);
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
//		// 特休付与テーブル設定コードを取得する
//		val grantRule = empBasicInfo.getGrantRule();
//		val grantTableCode = grantRule.getGrantTableCode().v();
//		
//		// 次回特休付与を取得する
//		nextAnnualLeaveGrantList = GetNextSpecialLeaveGrant.algorithm(require, cacheCarrier,
//				companyId, grantTableCode, employee.getEntryDate(), grantRule.getGrantStandardDate(),
//				targetPeriod, isSingleDay, grantHdTblSetOpt, lengthSvTblsOpt);
//		
//		// 次回特休付与を返す
//		return nextAnnualLeaveGrantList;
//	}
	
	public static interface RequireM1 {

		List<AffCompanyHistImport> listAffCompanyHistImport = this.syCompanyRecordAdapter
				.getAffCompanyHistByEmployee(new ArrayList<>(listAppId), period);
		
		
		Optional<AnnualLeaveEmpBasicInfo> employeeAnnualLeaveBasicInfo(String employeeId);
		
		EmployeeImport employee(CacheCarrier cacheCarrier, String empId);
	}
	
	public static interface RequireM2 extends RequireM1, GetClosureStartForEmployee.RequireM1, GetNextSpecialLeaveGrant.RequireM1 {
		
	}
}
