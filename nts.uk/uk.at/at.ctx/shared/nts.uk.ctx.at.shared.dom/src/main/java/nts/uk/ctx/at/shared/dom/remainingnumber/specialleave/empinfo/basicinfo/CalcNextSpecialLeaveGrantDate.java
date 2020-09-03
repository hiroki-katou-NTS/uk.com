package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonthDayHolder.Difference;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.company.AffCompanyHistImport;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.specialholiday.calculateremainnum.RemainSpecialHoidayCalculation;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.specialholiday.updateremainnum.RemainSpecialHolidayUpdating;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.SClsHistImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.ErrorFlg;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.InPeriodOfSpecialLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveManagementService;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.InforSpecialLeaveOfEmployeeSevice.RequireM2;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.export.NextSpecialLeaveGrant;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.AgeBaseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.GenderCls;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.SpecialLeaveRestriction;
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
			List<NextSpecialLeaveGrant> nextSpecialLeaveGrantList
				= getSpecialLeaveGrantInfo(
						require, 
						cacheCarrier, 
						companyId, 
						employeeId, 
						spLeaveCD,
						period,
						Optional.ofNullable(grantDate));
			
			
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
		
		// ループ
		while (true){
			
			//　パラメータ「付与日」とパラメータ「期間」を比較する
			if ( grantDate.before()) // 「付与日」＜「期間．開始日」
			
			// 利用条件をチェックする
			
		}
		
	}
	
	
	
	/**
	 * 利用条件をチェックする
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param spLeaveCD 特別休暇コード
	 * @param 基準日
	 * @return true：利用可能、false：利用不可
	 */
	private static boolean checkUseCondition(
			SpecialLeaveManagementService.RequireM5 require, 
			CacheCarrier cacheCarrier, 
			String companyId, 
			String employeeId,
			int spLeaveCD,
			GeneralDate ymd) {

		// 「特別休暇」を取得する
		Optional<SpecialHoliday> specialHolidaysOpt
			= require.specialHoliday(companyId, spLeaveCD);
		if ( specialHolidaysOpt.isPresent() ){
			SpecialLeaveRestriction specialLeaveRestriction
				= specialHolidaysOpt.get().getSpecialLeaveRestriction();
			
			SpecialHoliday specialHolidays = specialHolidaysOpt.get();
			
			ErrorFlg outData = new ErrorFlg(false, false, false, false);
			SpecialLeaveRestriction specialLeaveRestric 
				= specialHolidays.getSpecialLeaveRestriction();
			
			// Imported(就業)「社員」を取得する
			EmployeeRecordImport empInfor = require.employeeFullInfo(cacheCarrier, employeeId);
			
			// 取得しているドメインモデル「定期付与．特別休暇利用条件．性別条件」をチェックする
			if(specialLeaveRestric.getGenderRest().equals(UseAtr.USE)){ // 利用するとき
				
				// 性別が一致するかチェックする
				if(empInfor.getGender() == specialLeaveRestric.getGender().value) {
					// パラメータ「エラーフラグ．性別条件に一致しない」にFALSEをセットする
					outData.setGenderError(false);	
				} else {
					// パラメータ「エラーフラグ．性別条件に一致しない」にTRUEをセットする
					outData.setGenderError(true);				
				}
			} else {
				// パラメータ「エラーフラグ．性別条件に一致しない」にFALSEをセットする
				outData.setGenderError(false);
			}
			
			// 取得しているドメインモデル「定期付与．特別休暇利用条件．雇用条件」をチェックする
			if(specialLeaveRestric.getRestEmp().equals(UseAtr.USE)){ // 利用するとき
				
				// アルゴリズム「社員所属雇用履歴を取得」を実行する
				Optional<BsEmploymentHistoryImport> employmentHistory 
					= require.employmentHistory(cacheCarrier, companyId, employeeId, ymd);
				
				if(!employmentHistory.isPresent()) {
					// パラメータ「エラーフラグ．雇用条件に一致しない」にTRUEをセットする
					outData.setEmploymentError(true);
				} else {
					BsEmploymentHistoryImport lstEmployment = employmentHistory.get();
					List<String> listEmp = specialLeaveRestriction.getListEmp();
					if(listEmp != null && !listEmp.isEmpty()) {
						// 取得した雇用コードが取得しているドメインモデル「定期付与．特別休暇利用条件．雇用一覧」に存在するかチェックする
						if(listEmp.contains(lstEmployment.getEmploymentCode())) {
							// パラメータ「エラーフラグ．雇用条件に一致しない」にFALSEをセットする
							outData.setEmploymentError(false);
						} else {
							// パラメータ「エラーフラグ．雇用条件に一致しない」にTRUEをセットする
							outData.setEmploymentError(true);
						}
					}				
				}
			} else {
				// パラメータ「エラーフラグ．雇用条件条件に一致しない」にFALSEをセットする
				outData.setEmploymentError(false);
			}
			
			// ドメインモデル「特別休暇利用条件」．分類条件をチェックする
			if(specialLeaveRestric.getRestrictionCls().equals(UseAtr.USE)){ // 利用するとき
				
				// アルゴリズム「社員所属分類履歴を取得」を実行する
				List<String> emploeeIdList = new ArrayList<>();
				emploeeIdList.add(employeeId);
				List<SClsHistImport> clsHistList = require.employeeClassificationHistoires(
						cacheCarrier, companyId, emploeeIdList, new DatePeriod(ymd, ymd));		
				if(clsHistList.isEmpty()) {
					outData.setClassError(true);
				}
				// 取得した分類コードが取得しているドメインモデル「定期付与．特別休暇利用条件．分類一覧」に存在するかチェックする
				List<String> classCodeList = specialLeaveRestriction.getListCls();
				if(classCodeList != null && !classCodeList.isEmpty()) {
					boolean isExit = false;
					for (SClsHistImport classData : clsHistList) {
						if(classCodeList.contains(classData.getClassificationCode())) {
							isExit = true;
							break;
						}
					}
					if(isExit) { // 存在するとき
						// パラメータ「エラーフラグ．分類条件に一致しない」にFALSEをセットする
						outData.setClassError(false);
					} else {
						// パラメータ「エラーフラグ．分類条件に一致しない」にTRUEをセットする
						outData.setClassError(true);
					}	
				}
				else
				{
					// パラメータ「エラーフラグ．分類条件に一致しない」にTRUEをセットする
					outData.setClassError(true);
				}
				
			} else {
				// パラメータ「エラーフラグ．分類条件に一致しない」にFALSEをセットする
				outData.setClassError(false);
			}
			
			// ドメインモデル「特別休暇利用条件」．年齢条件をチェックする
			if(specialLeaveRestric.getAgeLimit().equals(UseAtr.USE)){ // 利用するとき
				
				GeneralDate ageBase = ymd;
				
				// 年齢基準日を求める
				nts.uk.shr.com.time.calendar.MonthDay ageBaseDate 
					= specialLeaveRestriction.getAgeStandard().getAgeBaseDate();
				
				int year = 0;
				
				// 取得しているドメインモデル「定期付与．特別休暇利用条件．年齢基準．年齢基準年区分」＝　「当年」の場合
				if(specialLeaveRestric.getAgeStandard().getAgeCriteriaCls() == AgeBaseYear.THIS_YEAR) {
					// 年齢基準日 = パラメータ「基準日．年」 + ドメインモデル「定期付与．特別休暇利用条件．年齢基準．年齢基準日」
					year = ageBaseDate != null ? ageBase.year() : 0;
				} else 
				// 取得しているドメインモデル「定期付与．特別休暇利用条件．年齢基準．年齢基準年区分」＝　「翌年」の場合
				if(specialLeaveRestric.getAgeStandard().getAgeCriteriaCls() == AgeBaseYear.NEXT_YEAR) { 
					// 年齢基準日 = パラメータ「基準日．年」 の翌年 + ドメインモデル「定期付与．特別休暇利用条件．年齢基準．年齢基準日」
					year = ageBaseDate != null ? ageBase.year() + 1 : 0;
				}
				
				if(year != 0
					&& ageBaseDate.getMonth() != 0
					&& ageBaseDate.getDay() != 0) {
					ageBase = GeneralDate.ymd(year, ageBaseDate.getMonth(), ageBaseDate.getDay());
				}
				
				// 求めた「年齢基準日」時点の年齢を求める
				Difference difYMD = ageBase.differenceFrom(empInfor.getBirthDay());
				
				//求めた「年齢」が年齢条件に一致するかチェックする
				if(specialLeaveRestric.getAgeRange().getAgeLowerLimit().v() > difYMD.years()
						|| specialLeaveRestric.getAgeRange().getAgeHigherLimit().v() < difYMD.years()) {
					outData.setAgeError(true);
				}
				else
				{
					outData.setAgeError(false);
				}
			}
			return outData.canUse(); 
		}
		
		return false;		
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
