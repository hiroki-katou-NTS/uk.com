package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.YearMonthDayHolder.Difference;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.company.AffComHistItemImport;
import nts.uk.ctx.at.record.dom.adapter.company.AffCompanyHistImport;
import nts.uk.ctx.at.record.dom.adapter.company.SyCompanyRecordAdapter;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.specialholiday.calculateremainnum.RemainSpecialHoidayCalculation;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.specialholiday.updateremainnum.RemainSpecialHolidayUpdating;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.SClsHistImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.GrantNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveAppSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.ErrorFlg;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.GrantDaysInfor;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.GrantDaysInforByDates;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.GrantDaysInforByDatesInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.InforSpecialLeaveOfEmployeeSevice.RequireM1;
//import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.SpecialLeaveManagementService;
//import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service.InforSpecialLeaveOfEmployeeSevice.RequireM2;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.export.NextSpecialLeaveGrant;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.AgeBaseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.GenderCls;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.SpecialLeaveRestriction;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYearMonthTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantElapseYearMonth;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.MonthDay;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.TypeTime;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.YearMonth;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.TimeLimitSpecification;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantDays;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantNum;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.shr.com.security.audittrail.correction.content.pereg.TargetDataKey.CalendarKeyType;

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

		// List「次回特別休暇付与」を空で作成
		List<NextSpecialLeaveGrant> nextSpecialLeaveGrantList = new ArrayList<NextSpecialLeaveGrant>();

		// 特別休暇使用区分をチェックする
		if ( specialLeaveBasicInfoOpt.isPresent() ){
			if ( specialLeaveBasicInfoOpt.get().getUsed().isUse() ){ // 使用するとき

				// 次回特別休暇付与を計算
				nextSpecialLeaveGrantList
					= algorithm１(require, cacheCarrier, companyId, employeeId, spLeaveCD, period );

			} else { // 使用しないとき
				// List「次回特別休暇付与」を空で作成
			}
		}

		return nextSpecialLeaveGrantList;
	}

	/**
	 * 次回特別休暇付与を計算
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
			if (paramPeriod.end().before(GeneralDate.max())){addEnd = 1;}
			targetPeriod = new DatePeriod(paramPeriod.start().addDays(1), paramPeriod.end().addDays(addEnd));
		}

		// 付与日数情報を取得する
		List<NextSpecialLeaveGrant> nextSpecialLeaveGrantList
			= getSpecialLeaveGrantInfo(require, cacheCarrier, companyId, employeeId, spLeaveCD, period);

		return nextSpecialLeaveGrantList;
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

					if ( grantDate.isPresent() ){
						// 指定日の付与日一覧を求める
						List<NextSpecialLeaveGrant> nextSpecialLeaveGrantList
							= getSpecifyGrantDateList(
								require,
								cacheCarrier,
								companyId,
								employeeId,
								spLeaveCD,
								period,
								grantDate.get());
					}

				} else if (typeTime.equals(TypeTime.GRANT_PERIOD)){ // 期間で付与する

					if ( grantDate.isPresent() ){
						// 期間の付与日一覧を求める
						List<NextSpecialLeaveGrant> nextSpecialLeaveGrantList
							= getPeriodSpecialLeaveGrantInfo(
								require,
								cacheCarrier,
								companyId,
								employeeId,
								spLeaveCD,
								CALL_FROM.PERIOD,
								period,
								grantDate);
					}

				} else if (typeTime.equals(TypeTime.REFER_GRANT_DATE_TBL)){ // 付与テーブルを参照して付与する

					// 付与テーブルの付与日一覧を求める
					List<NextSpecialLeaveGrant> nextSpecialLeaveGrantList
						= getTableSpecialLeaveGrantInfo(
							require,
							cacheCarrier,
							companyId,
							employeeId,
							spLeaveCD,
							CALL_FROM.PERIOD, // ooooo 要確認
							period,
							grantDate,
							specialHolidays.get()
							);

//					SpecialLeaveManagementService.RequireM5 require,
//					CacheCarrier cacheCarrier,
//					String companyId,
//					String employeeId,
//					int spLeaveCD,
//					CALL_FROM callFrom,
//					Optional<DatePeriod> period,
//					Optional<GeneralDate> grantDateOpt,
//					SpecialLeaveBasicInfo specialLeaveBasicInfo,
//					SpecialHoliday specialHoliday) {

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

			ArrayList<String> sids = new ArrayList<String>();
			sids.add(employeeId);

			if ( grantDate.isPresent() ){
				if ( grantDate.get().equals(GrantDate.EMP_GRANT_DATE)){ // 入社日を付与基準日とする

					// 社員ID（List）と指定期間から所属会社履歴項目を取得 【Request：No211】
					Optional<AffCompanyHistImport> affCompanyHistImportOpt = Optional.empty();

					// ドメインモデル「所属会社履歴（社員別）」を全て取得する
					List<AffCompanyHistImport> listAffCompanyHistImport
						= require.listAffCompanyHistImport(sids, period.get());

					// 社員で絞込み
					List<AffCompanyHistImport> listAffCompanyHistImport_employeeId
						= listAffCompanyHistImport.stream()
							.filter(x -> x.getEmployeeId().equals(employeeId))
							.collect(Collectors.toList());

					if ( !listAffCompanyHistImport_employeeId.isEmpty() ){

						// 開始日＜＝パラメータ．期間．終了日 (startdate< = param. period. finishdate)
						// AND パラメータ．期間．開始日 ＜＝ 終了日 (param. period. startdate <= finish date)
						AffCompanyHistImport affCompanyHistImport
							= listAffCompanyHistImport_employeeId.stream().findFirst().get();

						List<AffComHistItemImport> listAffCompanyHistImportList
							= affCompanyHistImport.getLstAffComHistItem().stream()
								.filter(x -> x.getDatePeriod().start().beforeOrEquals(period.get().start()))
								.filter(x -> x.getDatePeriod().end().afterOrEquals(period.get().end()))
								.collect(Collectors.toList());

						if ( !listAffCompanyHistImportList.isEmpty()){
							affCompanyHistImportOpt = Optional.of(new AffCompanyHistImport());
							affCompanyHistImportOpt.get().setEmployeeId(employeeId);
							affCompanyHistImportOpt.get().setLstAffComHistItem(listAffCompanyHistImportList);
						}
					}

					// 所得した一番大きい「入社日」をパラメータ「付与基準日」にセットする
					if ( affCompanyHistImportOpt.isPresent() ){
						Optional<AffComHistItemImport> affComHistItemImport
							= affCompanyHistImportOpt.get().getLstAffComHistItem().stream()
								.sorted((a,b)->b.getDatePeriod().start().compareTo(a.getDatePeriod().start()))
								.findFirst();
						if (affComHistItemImport.isPresent()){
							grantStandardDate = Optional.of(affComHistItemImport.get().getDatePeriod().start());
						}
					}

	//				if (listAffCompanyHistImport.isEmpty() || listAffCompanyHistImport.stream()
	//						.flatMap(x -> x.getLstAffComHistItem().stream()).collect(Collectors.toList()).isEmpty()) {
	//					oneMonthApprovalStatusDto.setMessageID("Msg_875");
	//					return oneMonthApprovalStatusDto;
	//				}

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
//				= specialHolidays.get().getGrantRegular().getFixGrantDate().getGrantMonthDay();
				= specialHolidays.get().getGrantRegular().getFixGrantDate().get().getGrantMonthDay();

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

			// 定期の付与日一覧を求める
			GrantDaysInforByDatesInfo grantDaysInforByDatesInfo
				= getSpecialLeaveGrantInfo(
						require,
						cacheCarrier,
						companyId,
						employeeId,
						spLeaveCD,
						CALL_FROM.FIX,
						period,
						Optional.ofNullable(grantDate));


			// 期限日を求める
			List<NextSpecialLeaveGrant> nextSpecialLeaveGrantList
				= getExpireDate(
						require,
						cacheCarrier,
						companyId,
						employeeId,
						spLeaveCD,
						grantDaysInforByDatesInfo.getLstGrantDaysInfor(),
						grantDaysInforByDatesInfo.getGrantDate());

			return nextSpecialLeaveGrantList;
		}

		// 空のリストを返す
		return new ArrayList<NextSpecialLeaveGrant>();
	}

	/**
	 * 呼び出し元
	 * @author masaaki_jinno
	 *
	 */
	public enum CALL_FROM {
		/**
		 * 指定日付与
		 */
		FIX(1),
		/**
		 * 期間付与
		 */
		PERIOD(2);

		public int value;

		CALL_FROM(int type){
			this.value = type;
		}

		public static CALL_FROM toEnum(int value){
			return EnumAdaptor.valueOf(value, CALL_FROM.class);
		}
	}

	/**
	 * 定期の付与日一覧を求める
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param int spLeaveCD 特別休暇コード
	 * @param callFrom 呼び出し元（指定日付与または期間付与）
	 * @param period 期間
	 * @param grantDate 付与基準日
	 * @return 次回特休付与リスト
	 */
	private static GrantDaysInforByDatesInfo getSpecialLeaveGrantInfo(
			SpecialLeaveManagementService.RequireM5 require,
			CacheCarrier cacheCarrier,
			String companyId,
			String employeeId,
			int spLeaveCD,
			CALL_FROM callFrom,
			Optional<DatePeriod> period,
			Optional<GeneralDate> grantDateOpt) {

		//　アウトプット
		GrantDaysInforByDatesInfo ｇrantDaysInforByDatesInfo
			= new GrantDaysInforByDatesInfo();

		// 「特別休暇」を取得する
		Optional<SpecialHoliday> specialHolidays = require.specialHoliday(companyId, spLeaveCD);

		// パラメータ「付与基準日」がNULLかどうかチェックする
		if ( !grantDateOpt.isPresent()
				|| !period.isPresent() ){ // Nullのとき

			// 「List＜次回特別休暇付与＞」を空で作成
			List<NextSpecialLeaveGrant> listNoData = new ArrayList<NextSpecialLeaveGrant>();
			ｇrantDaysInforByDatesInfo.setLstGrantDaysInfor(listNoData);
			return ｇrantDaysInforByDatesInfo;
		}

		// 「List＜次回特別休暇付与＞」を作成
		List<NextSpecialLeaveGrant> nextSpecialLeaveGrantList = new ArrayList<NextSpecialLeaveGrant>();

		// パラメータ「付与日」←パラメータ「付与基準日」
		GeneralDate grantDate = GeneralDate.localDate(grantDateOpt.get().localDate());

		// ループ
		while (true){

			//　パラメータ「付与日」とパラメータ「期間」を比較する

			// 「付与日」＜「期間．開始日」
			if ( grantDate.before(period.get().start()) ){
				// 何もしない
			}
			// 「期間．開始日」≦「付与日」≦「期間．終了日」
			else if (period.get().start().beforeOrEquals(grantDate)
					&& grantDate.beforeOrEquals(period.get().start())){

				// 利用条件をチェックする
				boolean useCondition = checkUseCondition(
						require, cacheCarrier, companyId, employeeId, spLeaveCD, grantDate);

				// 状態　＝　「利用可能」
				if ( useCondition ){
					NextSpecialLeaveGrant nextSpecialLeaveGrant = new NextSpecialLeaveGrant();

					// 特別休暇基本情報を取得
					Optional<SpecialLeaveBasicInfo> specialLeaveBasicInfoOpt
						= require.specialLeaveBasicInfo(employeeId, spLeaveCD, UseAtr.USE);

					// 年月日←パラメータ「付与日」
					nextSpecialLeaveGrant.setGrantDate(grantDate);


					if ( specialLeaveBasicInfoOpt.isPresent() ){

						// 「特別休暇基本情報．適用設定≠所定の条件を適用する」　の場合
						if ( !specialLeaveBasicInfoOpt.get().getApplicationSet().equals(
								SpecialLeaveAppSetting.PRESCRIBED) ){
							// 付与日数　←　特別休暇基本情報．付与設定．付与日数
							Optional<GrantNumber> grantNumberOpt
								= specialLeaveBasicInfoOpt.get().getGrantSetting().getGrantDays();
							if ( grantNumberOpt.isPresent() ){
								int grantDays = grantNumberOpt.get().v();
								nextSpecialLeaveGrant.setGrantDays(new GrantDays((double)grantDays));
							}
						}
						// 「特別休暇基本情報．適用設定＝所定の条件を適用する」　の場合
						else if ( specialLeaveBasicInfoOpt.get().getApplicationSet().equals(
								SpecialLeaveAppSetting.PRESCRIBED)){

							double tmpGrantDays = 0.0;

							if ( specialHolidays.isPresent() ){

								// 引数の付与日数をセット

								// 呼び出し元が「指定日付与」
								if ( callFrom.equals(CALL_FROM.FIX) ){
									// 付与日数　←　ドメインモデル「特別休暇．付与情報．指定日付与．付与日数」
									// tmpGrantDays = specialHolidays.get().getGrantRegular().getFixGrantDate().getGrantDays().getGrantDays().v();
									if ( specialHolidays.get().getGrantRegular().getFixGrantDate().isPresent() ) {
										tmpGrantDays = specialHolidays.get().getGrantRegular().getFixGrantDate().get().getGrantDays().getGrantDays().v();
									}
								}
								// 呼び出し元が「期間付与」
								else if ( callFrom.equals(CALL_FROM.PERIOD) ){
									// 付与日数　←　ドメインモデル「特別休暇．付与情報．期間付与．付与日数」
									if (specialHolidays.get().getGrantRegular().getPeriodGrantDate().isPresent()) {
										tmpGrantDays = specialHolidays.get().getGrantRegular().getPeriodGrantDate().get().getGrantDays().getGrantDays().v();
									}
								}
							}
							nextSpecialLeaveGrant.setGrantDays(new GrantDays(tmpGrantDays));
						}

						// 回数←期間中に付与された回数
						nextSpecialLeaveGrant.setTimes(new GrantNum(nextSpecialLeaveGrantList.size()+1));

						// リストに追加
						nextSpecialLeaveGrantList.add(nextSpecialLeaveGrant);
					}
				}
			}

			// 「期間．終了日」＜「付与日」
			else if (period.get().end().before(grantDate)){
				ｇrantDaysInforByDatesInfo.setLstGrantDaysInfor(nextSpecialLeaveGrantList);
				return ｇrantDaysInforByDatesInfo;
			}

			// パラメータ「付与日」←パラメータ「付与日」＋1年
			grantDate = grantDate.addYears(1);
		}
	}

	/**
	 * 期間の付与日一覧を求める
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param int spLeaveCD 特別休暇コード
	 * @param callFrom 呼び出し元（指定日付与または期間付与）
	 * @param period 期間
	 * @param grantDate 付与基準日
	 * @return 次回特休付与リスト
	 */
	private static List<NextSpecialLeaveGrant> getPeriodSpecialLeaveGrantInfo(
			SpecialLeaveManagementService.RequireM5 require,
			CacheCarrier cacheCarrier,
			String companyId,
			String employeeId,
			int spLeaveCD,
			CALL_FROM callFrom,
			Optional<DatePeriod> period,
			Optional<GeneralDate> grantDateOpt) {

		List<NextSpecialLeaveGrant> nextSpecialLeaveGrantList = new ArrayList<NextSpecialLeaveGrant>();
		if ( !grantDateOpt.isPresent() ){
			return nextSpecialLeaveGrantList;
		}

		if ( !period.isPresent() ){
			return nextSpecialLeaveGrantList;
		}

		// 付与日から期限日内に入社日があるかチェック
		//期限に何を渡すかを確認すること
		//UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.特別休暇管理.アルゴリズム.Query.[No.373]社員の特別休暇情報を取得する.付与日数情報を取得する.期間の付与日一覧を求める.期間の付与日一覧を求める
		GeneralDate expireDate = grantDateOpt.get();

		// 求めた「付与日」をパラメータ「付与基準日」にセットする
		GeneralDate grantDateNew = getPeriodSpecialLeaveGrantInfo(
				require,
				cacheCarrier,
				companyId,
				employeeId,
				grantDateOpt.get(),
				expireDate);

		// 定期の付与日一覧を求める
		GrantDaysInforByDatesInfo grantDaysInforByDatesInfo
			= getSpecialLeaveGrantInfo(
					require,
					cacheCarrier,
					companyId,
					employeeId,
					spLeaveCD,
					CALL_FROM.PERIOD,
					period,
					Optional.ofNullable(grantDateNew));

		// 期限日を求める
		List<NextSpecialLeaveGrant> nextSpecialLeaveGrantListNew
			= getSpecialLeaveGrantInfo(
					require, cacheCarrier, companyId, employeeId,
					spLeaveCD, grantDaysInforByDatesInfo.getLstGrantDaysInfor(),
					grantDaysInforByDatesInfo.getGrantDate());


		return nextSpecialLeaveGrantListNew;
	}

	/**
	 * 期限日を求める（期間の付与日）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param spLeaveCD 特別休暇コード
	 * @param nextSpecialLeaveGrantList 次回特休付与リスト
	 * @param nextGrantDateAfterPeriod 次回期間外特休付与日
	 * @return 次回特休付与リスト
	 */
	private static List<NextSpecialLeaveGrant> getSpecialLeaveGrantInfo(
			SpecialLeaveManagementService.RequireM5 require,
			CacheCarrier cacheCarrier,
			String companyId,
			String employeeId,
			int spLeaveCD,
			List<NextSpecialLeaveGrant> nextSpecialLeaveGrantList,
			GeneralDate nextGrantDateAfterPeriod) {

		// 「特別休暇」を取得する
		Optional<SpecialHoliday> specialHolidays = require.specialHoliday(companyId, spLeaveCD);
		if ( specialHolidays.isPresent() ){

			// パラメータ「次回特別休暇付与」を取得
			// 次回特別休暇付与でループ
			nextSpecialLeaveGrantList.forEach(c->{

				// パラメータ「次回特別休暇付与」の期限を求める
				// ・期限日　←　パラメータ「次回特別休暇付与．付与年月日」の年　+ 期限付与.期間.終了
				if (specialHolidays.get().getGrantRegular().getPeriodGrantDate().isPresent()) {
					GeneralDate endDate = specialHolidays.get().getGrantRegular().getPeriodGrantDate().get().getPeriod().end();
					GeneralDate deadline = GeneralDate.ymd(c.getGrantDate().year(), endDate.month(), endDate.day());

					// ※付与日から期限日の間で年を跨ぐ場合、期限日に1年加算
					if ( c.getGrantDate().before(deadline) ){
						deadline = deadline.addYears(1);
					}
					c.setDeadLine(deadline);
				}

			});
		}
		return nextSpecialLeaveGrantList;
	}

	/**
	 * 付与テーブルの付与日一覧を求める
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param int spLeaveCD 特別休暇コード
	 * @param callFrom 呼び出し元（指定日付与または期間付与）
	 * @param period 期間
	 * @param grantDate 付与日
	 * @param
	 * @param specialHoliday 特別休暇
	 * @return 次回特休付与リスト
	 */
	private static List<NextSpecialLeaveGrant> getTableSpecialLeaveGrantInfo(
			SpecialLeaveManagementService.RequireM5 require,
			CacheCarrier cacheCarrier,
			String companyId,
			String employeeId,
			int spLeaveCD,
			CALL_FROM callFrom,
			Optional<DatePeriod> period,
			Optional<GeneralDate> grantDateOpt,
			SpecialHoliday specialHoliday) {

		List<NextSpecialLeaveGrant> nextSpecialLeaveGrantList
			= new ArrayList<NextSpecialLeaveGrant>();
		if ( !grantDateOpt.isPresent() ){
			return nextSpecialLeaveGrantList;
		}

		// 特別休暇基本情報を取得
		Optional<SpecialLeaveBasicInfo> specialLeaveBasicInfoOpt
			= require.specialLeaveBasicInfo(employeeId, spLeaveCD, UseAtr.USE);

		if ( specialLeaveBasicInfoOpt.isPresent() ){

			// テーブルに基づいた付与日数一覧を求める
			GrantDaysInforByDates grantDaysInforByDates
				= askGrantdaysFromtable(
						require,
						cacheCarrier,
						companyId,
						employeeId,
						spLeaveCD,
						period.get(),
						grantDateOpt.get(),
						specialLeaveBasicInfoOpt.get(),
						specialHoliday
						);

			// 期限日を求める
			List<NextSpecialLeaveGrant> nextSpecialLeaveGrant
				= getExpireDate(
						require,
						cacheCarrier,
						companyId,
						employeeId,
						spLeaveCD,
						grantDaysInforByDates.getNextSpecialLeaveGrant(),
						grantDaysInforByDates.getGrantDate().get());

			return nextSpecialLeaveGrant;
		}

		return nextSpecialLeaveGrantList;

//		List<NextSpecialLeaveGrant> getExpireDate(
//				SpecialLeaveManagementService.RequireM5 require,
//				CacheCarrier cacheCarrier,
//				String companyId,
//				String employeeId,
//				int spLeaveCD,
//				List<NextSpecialLeaveGrant> nextSpecialLeaveGrantList,
//				GeneralDate grantDateAfterPeriod)

//		GrantDaysInforByDates askGrantdaysFromtable(
//				RequireM1 require,
//				CacheCarrier cacheCarrier,
//				String cid,
//				String sid,
//				DatePeriod period,
//				GeneralDate granDate,
//				SpecialLeaveBasicInfo basicInfor,
//				SpecialHoliday speHoliday)



//		// 「特別休暇」を取得する
//		Optional<SpecialHoliday> specialHolidays = require.specialHoliday(companyId, spLeaveCD);
//
//
//		// パラメータ「付与基準日」がNULLかどうかチェックする
//		if ( !grantDateOpt.isPresent()
//				|| !period.isPresent() ){ // Nullのとき
//
//			// 「List＜次回特別休暇付与＞」を空で作成
//			List<NextSpecialLeaveGrant> listNoData = new ArrayList<NextSpecialLeaveGrant>();
//			return listNoData;
//		}
//
//		// 「List＜次回特別休暇付与＞」を作成
//		List<NextSpecialLeaveGrant> nextSpecialLeaveGrantList = new ArrayList<NextSpecialLeaveGrant>();
//
//		// パラメータ「付与日」←パラメータ「付与基準日」
//		GeneralDate grantDate = GeneralDate.localDate(grantDateOpt.get().localDate());
//
//		// ループ
//		while (true){
//
//			//　パラメータ「付与日」とパラメータ「期間」を比較する
//
//			// 「付与日」＜「期間．開始日」
//			if ( grantDate.before(period.get().start()) ){
//				// 何もしない
//			}
//			// 「期間．開始日」≦「付与日」≦「期間．終了日」
//			else if (period.get().start().beforeOrEquals(grantDate)
//					&& grantDate.beforeOrEquals(period.get().start())){
//
//				// 利用条件をチェックする
//				boolean useCondition = checkUseCondition(
//						require, cacheCarrier, companyId, employeeId, spLeaveCD, grantDate);
//
//				// 状態　＝　「利用可能」
//				if ( useCondition ){
//					NextSpecialLeaveGrant nextSpecialLeaveGrant = new NextSpecialLeaveGrant();
//
//					// 特別休暇基本情報を取得
//					Optional<SpecialLeaveBasicInfo> specialLeaveBasicInfoOpt
//						= require.specialLeaveBasicInfo(employeeId, spLeaveCD, UseAtr.USE);
//
//					if ( specialLeaveBasicInfoOpt.isPresent() ){
//
//						// 「特別休暇基本情報．適用設定≠所定の条件を適用する」　の場合
//						if ( !specialLeaveBasicInfoOpt.get().getApplicationSet().equals(
//								SpecialLeaveAppSetting.PRESCRIBED) ){
//							// 付与日数　←　特別休暇基本情報．付与設定．付与日数
//							Optional<GrantNumber> grantNumberOpt
//								= specialLeaveBasicInfoOpt.get().getGrantSetting().getGrantDays();
//							if ( grantNumberOpt.isPresent() ){
//								int grantDays = grantNumberOpt.get().v();
//								nextSpecialLeaveGrant.setGrantDays(new GrantDays((double)grantDays));
//							}
//						}
//						// 「特別休暇基本情報．適用設定＝所定の条件を適用する」　の場合
//						else if ( specialLeaveBasicInfoOpt.get().getApplicationSet().equals(
//								SpecialLeaveAppSetting.PRESCRIBED)){
//
//							double tmpGrantDays = 0.0;
//
//							if ( specialHolidays.isPresent() ){
//
//								// 引数の付与日数をセット
//
//								// 呼び出し元が「指定日付与」
//								if ( callFrom.equals(CALL_FROM.FIX) ){
//									// 付与日数　←　ドメインモデル「特別休暇．付与情報．指定日付与．付与日数」
//									tmpGrantDays = specialHolidays.get().getGrantRegular().getFixGrantDate().getGrantDays().getGrantDays().v();
//								}
//								// 呼び出し元が「期間付与」
//								else if ( callFrom.equals(CALL_FROM.PERIOD) ){
//									// 付与日数　←　ドメインモデル「特別休暇．付与情報．期間付与．付与日数」
//									tmpGrantDays = specialHolidays.get().getGrantRegular().getPeriodGrantDate().getGrantDays().getGrantDays().v();
//								}
//							}
//							nextSpecialLeaveGrant.setGrantDays(new GrantDays(tmpGrantDays));
//							nextSpecialLeaveGrantList.add(nextSpecialLeaveGrant);
//						}
//					}
//				}
//			}
//
//			// 「期間．終了日」＜「付与日」
//			else if (period.get().end().before(grantDate)){
//
//				// 「List＜次回特別休暇付与＞」を返す
//				return nextSpecialLeaveGrantList;
//			}
//
//			// パラメータ「付与日」←パラメータ「付与日」＋1年
//			grantDate = grantDate.addYears(1);
//		}
	}

	/**
	 * テーブルに基づいた付与日数一覧を求める
	 * @param cid　会社ID
	 * @param sid　社員ID
	 * @param period　期間
	 * @param granDate　付与基準日
	 * @param basicInfor　特別休暇基本情報
	 * @param speHoliday　特別休暇
	 * @return　付与日数一覧
	 */
	public static GrantDaysInforByDates askGrantdaysFromtable(
			SpecialLeaveManagementService.RequireM5 require,
			CacheCarrier cacheCarrier,
			String cid,
			String sid,
			int spLeaveCD,
			DatePeriod period,
			GeneralDate granDate,
			SpecialLeaveBasicInfo basicInfor,
			SpecialHoliday speHoliday) {

		// ドメインモデル「特別休暇付与経過年数テーブル」を取得する ooooo 要require対応
		List<ElapseYear> elapseYearList = new ArrayList<ElapseYear>();

		List<NextSpecialLeaveGrant> lstOutput = new ArrayList<>();
		GeneralDate outputDate = null;

		if ( elapseYearList.isEmpty() ){ // 取得できなかった場合
			return new GrantDaysInforByDates(Optional.ofNullable(outputDate), lstOutput);
		}
		ElapseYear elapseYear = elapseYearList.get(0);

		// ドメインモデル「特別休暇付与日数テーブル」を取得する ooooo 要require対応
		// 【条件】
		// ・会社ID：パラメータ「会社ID」
		// ・特別休暇コード：パラメータ「特別休暇コード」
		List<GrantDateTbl> grantDateTblList = new ArrayList<GrantDateTbl>();

		// ◆特別休暇基本情報．適用設定≠所定の条件を適用する　の場合
		// 取得している「特別休暇基本情報．付与設定．付与テーブルコード」　
		if(basicInfor.getApplicationSet() != SpecialLeaveAppSetting.PRESCRIBED
				&& basicInfor.getGrantSetting().getGrantTable().isPresent()) {

			grantDateTblList = grantDateTblList.stream()
				.filter(c->c.getGrantDateCode().v().equals(speHoliday.getSpecialHolidayCode().v()))
				.collect(Collectors.toList());
		}
		// ◆特別休暇基本情報．適用設定＝所定の条件を適用する　の場合
		// 規定のテーブルとする＝TRUE
		else {
			grantDateTblList = grantDateTblList.stream()
					.filter(c->c.isSpecified())
					.collect(Collectors.toList());
		}

		if ( grantDateTblList.isEmpty() ){ // 取得できなかった場合
			return new GrantDaysInforByDates(Optional.ofNullable(outputDate), lstOutput);
		}

		// パラメータ「付与基準日」がNULLかどうかチェックする
		if ( granDate == null ){ // 要Optional対応
			return new GrantDaysInforByDates(Optional.ofNullable(outputDate), lstOutput);
		}

		GrantDateTbl grantDateTbl = grantDateTblList.get(0);

		// 経過年数に対する付与日数
		List<GrantElapseYearMonth> grantElapseYearMonthList
			= grantDateTbl.getElapseYear();

		// ソート
		// 付与回数　ASC
		grantElapseYearMonthList
			= grantElapseYearMonthList
				.stream()
				.sorted((a,b)->Integer.compare(a.getElapseNo(),b.getElapseNo()))
				.collect(Collectors.toList());

		// 経過年数テーブル
		List<ElapseYearMonthTbl> elapseYearMonthTblList = elapseYear.getElapseYearMonthTblList();

		// 付与日を求める
		GeneralDate grantDateTmp = GeneralDate.localDate(granDate.localDate());

		// 「期間．終了日」＞=「付与日」の間 ループ
		for(GrantElapseYearMonth grantElapseYearMonth : grantElapseYearMonthList){

			// 経過年数（付与回数が同じもの）
			List<ElapseYearMonthTbl> elapseYearMonthTblListTmp
				= elapseYearMonthTblList.stream()
				.filter(d->d.getGrantCnt() == grantElapseYearMonth.getElapseNo())
				.collect(Collectors.toList());

			// 付与日数
			double grantDays = 0;

			// 【経過年数が設定されている間】
			if ( !elapseYearMonthTblListTmp.isEmpty() ){
				ElapseYearMonthTbl elapseYearMonthTbl = elapseYearMonthTblListTmp.get(0);

				// 付与日←パラメータ「付与基準日」＋特別休暇付与経過年数テーブル．経過年数テーブル．経過年数
				// ※ループ毎に経過年数を大きいものに変更する
				grantDateTmp = grantDateTmp
						.addYears(elapseYearMonthTbl.getElapseYearMonth().getYear())
						.addMonths(elapseYearMonthTbl.getElapseYearMonth().getMonth());
				// 付与日数
				grantDays = grantElapseYearMonth.getGrantedDays().v();
			}
			// 【経過年数が設定されていない】
			else {

				// 【経過年数の設定がないかつ付与周期が設定されている場合】
				// 付与日←付与日＋特別休暇付与経過年数テーブル．テーブル以降の付与周期．付与周期
				if ( elapseYear.getGrantCycleAfterTbl().isPresent() ){
					grantDateTmp = grantDateTmp
							.addYears(elapseYear.getGrantCycleAfterTbl().get().getElapseYearMonth().getYear())
							.addMonths(elapseYear.getGrantCycleAfterTbl().get().getElapseYearMonth().getMonth());
				}
				// 【経過年数の設定がないかつ付与周期が設定されていない場合】
				else {
					// 付与日←付与日＋1年
					grantDateTmp = grantDateTmp.addYears(1);
				}

				// 付与日数
				if ( grantDateTbl.getGrantedDays().isPresent() ){
					grantDays = grantDateTbl.getGrantedDays().get().v();
				}

				// テーブル以降の固定付与をおこなうをチェック
				// 【ループを抜ける条件】
				// 経過年数の設定がないかつ「特別休暇付与経過年数テーブル」．テーブル以降の固定付与をおこなうが「チェックなし」場合はループを抜ける
				if ( !elapseYear.isFixedAssign() ){
					break;
				}
			}

			// 利用条件をチェックする
			boolean checkUser = checkUseCondition(
					require, cacheCarrier, cid, sid, spLeaveCD, grantDateTmp);
			if(!checkUser) {

//				// パラメータ「付与日数一覧」を追加する
//				GrantDaysInfor outPut = new GrantDaysInfor(grantDateTmp, Optional.of(checkUser), 0);
//				lstOutput.add(outPut);

			} else { // 利用可能
				//　パラメータ「付与日数一覧」を追加する

				//　【追加する項目】
				//　・年月日←パラメータ「付与日」
				//　・回数←期間中に付与された回数
				int count = lstOutput.size();
				count++;
				//　・付与日数　←　
				//　【処理中の経過年数が存在する場合】
				//　処理中の「経過年数テーブル．付与回数」に対応する「付与日数」
				//　【処理中の経過年数が存在しない場合】
				//　「テーブル以降付与日数.付与日数」
				NextSpecialLeaveGrant outPut = new NextSpecialLeaveGrant();
				outPut.setGrantDate(grantDateTmp);
				outPut.setGrantDays(new GrantDays(grantDays));
				outPut.setTimes(new GrantNum(count));
				lstOutput.add(outPut);
			}
		}

		return new GrantDaysInforByDates(Optional.ofNullable(outputDate), lstOutput);
	}

	/**
	 * 付与日から期限日内に入社日があるかチェックする
	 * @param require
	 * @param cacheCarrier
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param grantDate 付与日
	 * @param expireDate 期限日
	 * @return 付与日
	 */
	private static GeneralDate getPeriodSpecialLeaveGrantInfo(
			SpecialLeaveManagementService.RequireM5 require,
			CacheCarrier cacheCarrier,
			String companyId,
			String employeeId,
			GeneralDate grantDate,
			GeneralDate expireDate) {


		// 所属期間の開始日がパラメータ「付与日」からパラメータ「期限日」と同じ所属履歴を取得する

		ArrayList<String> sIds = new ArrayList<String>();
		sIds.add(employeeId);

		DatePeriod period = new DatePeriod(grantDate, expireDate);

		// 社員ID（List）と指定期間から所属会社履歴項目を取得 【Request：No211】
		Optional<AffCompanyHistImport> affCompanyHistImportOpt = Optional.empty();

		// ドメインモデル「所属会社履歴（社員別）」を全て取得する
		List<AffCompanyHistImport> listAffCompanyHistImport
			= require.listAffCompanyHistImport(sIds, period);

		// 社員で絞込み
		List<AffCompanyHistImport> listAffCompanyHistImport_employeeId
			= listAffCompanyHistImport.stream()
				.filter(x -> x.getEmployeeId().equals(employeeId))
				.collect(Collectors.toList());

		if ( !listAffCompanyHistImport_employeeId.isEmpty() ){

			AffCompanyHistImport affCompanyHistImport
				= listAffCompanyHistImport_employeeId.stream().findFirst().get();

			// パラメータ「付与日」 <= 所属期間の開始日<=パラメータ「期限日」
			List<AffComHistItemImport> listAffCompanyHistImportList
				= affCompanyHistImport.getLstAffComHistItem().stream()
					.filter(x -> x.getDatePeriod().start().afterOrEquals(grantDate))
					.filter(x -> x.getDatePeriod().start().beforeOrEquals(expireDate))
					.collect(Collectors.toList());

			if ( !listAffCompanyHistImportList.isEmpty()){
				affCompanyHistImportOpt = Optional.of(new AffCompanyHistImport());
				affCompanyHistImportOpt.get().setEmployeeId(employeeId);
				affCompanyHistImportOpt.get().setLstAffComHistItem(listAffCompanyHistImportList);
			}
		}

		// 所得した一番大きい「入社日」をパラメータ「付与基準日」にセットする
		if ( affCompanyHistImportOpt.isPresent() ){
			Optional<AffComHistItemImport> affComHistItemImport
				= affCompanyHistImportOpt.get().getLstAffComHistItem().stream()
					.sorted((a,b)->b.getDatePeriod().start().compareTo(a.getDatePeriod().start()))
					.findFirst();
			if (affComHistItemImport.isPresent()){
				GeneralDate grantDateNew = GeneralDate.ymd(
						grantDate.year(),
						affComHistItemImport.get().getDatePeriod().start().month(),
						affComHistItemImport.get().getDatePeriod().start().day());
				return grantDateNew;
			}
			else {
				// 付与日←パラメータ「付与日」
				GeneralDate grantDateNew = GeneralDate.localDate(grantDate.localDate());
				return grantDateNew;
			}
		}

		// 付与日←パラメータ「付与日」
		return grantDate;
	}

//		// 「特別休暇」を取得する
//		Optional<SpecialHoliday> specialHolidays = require.specialHoliday(companyId, spLeaveCD);
//
//
//		// パラメータ「付与基準日」がNULLかどうかチェックする
//		if ( !grantDateOpt.isPresent()
//				|| !period.isPresent() ){ // Nullのとき
//
//			// 「List＜次回特別休暇付与＞」を空で作成
//			List<NextSpecialLeaveGrant> listNoData = new ArrayList<NextSpecialLeaveGrant>();
//			return listNoData;
//		}
//
//		// 「List＜次回特別休暇付与＞」を作成
//		List<NextSpecialLeaveGrant> nextSpecialLeaveGrantList = new ArrayList<NextSpecialLeaveGrant>();
//
//		// パラメータ「付与日」←パラメータ「付与基準日」
//		GeneralDate grantDate = GeneralDate.localDate(grantDateOpt.get().localDate());
//
//		// ループ
//		while (true){
//
//			//　パラメータ「付与日」とパラメータ「期間」を比較する
//
//			// 「付与日」＜「期間．開始日」
//			if ( grantDate.before(period.get().start()) ){
//				// 何もしない
//			}
//			// 「期間．開始日」≦「付与日」≦「期間．終了日」
//			else if (period.get().start().beforeOrEquals(grantDate)
//					&& grantDate.beforeOrEquals(period.get().start())){
//
//				// 利用条件をチェックする
//				boolean useCondition = checkUseCondition(
//						require, cacheCarrier, companyId, employeeId, spLeaveCD, grantDate);
//
//				// 状態　＝　「利用可能」
//				if ( useCondition ){
//					NextSpecialLeaveGrant nextSpecialLeaveGrant = new NextSpecialLeaveGrant();
//
//					// 特別休暇基本情報を取得
//					Optional<SpecialLeaveBasicInfo> specialLeaveBasicInfoOpt
//						= require.specialLeaveBasicInfo(employeeId, spLeaveCD, UseAtr.USE);
//
//					if ( specialLeaveBasicInfoOpt.isPresent() ){
//
//						// 「特別休暇基本情報．適用設定≠所定の条件を適用する」　の場合
//						if ( !specialLeaveBasicInfoOpt.get().getApplicationSet().equals(
//								SpecialLeaveAppSetting.PRESCRIBED) ){
//							// 付与日数　←　特別休暇基本情報．付与設定．付与日数
//							Optional<GrantNumber> grantNumberOpt
//								= specialLeaveBasicInfoOpt.get().getGrantSetting().getGrantDays();
//							if ( grantNumberOpt.isPresent() ){
//								int grantDays = grantNumberOpt.get().v();
//								nextSpecialLeaveGrant.setGrantDays(new GrantDays((double)grantDays));
//							}
//						}
//						// 「特別休暇基本情報．適用設定＝所定の条件を適用する」　の場合
//						else if ( specialLeaveBasicInfoOpt.get().getApplicationSet().equals(
//								SpecialLeaveAppSetting.PRESCRIBED)){
//
//							double tmpGrantDays = 0.0;
//
//							if ( specialHolidays.isPresent() ){
//
//								// 引数の付与日数をセット
//
//								// 呼び出し元が「指定日付与」
//								if ( callFrom.equals(CALL_FROM.FIX) ){
//									// 付与日数　←　ドメインモデル「特別休暇．付与情報．指定日付与．付与日数」
//									tmpGrantDays = specialHolidays.get().getGrantRegular().getFixGrantDate().getGrantDays().getGrantDays().v();
//								}
//								// 呼び出し元が「期間付与」
//								else if ( callFrom.equals(CALL_FROM.PERIOD) ){
//									// 付与日数　←　ドメインモデル「特別休暇．付与情報．期間付与．付与日数」
//									tmpGrantDays = specialHolidays.get().getGrantRegular().getPeriodGrantDate().getGrantDays().getGrantDays().v();
//								}
//							}
//							nextSpecialLeaveGrant.setGrantDays(new GrantDays(tmpGrantDays));
//							nextSpecialLeaveGrantList.add(nextSpecialLeaveGrant);
//						}
//					}
//				}
//			}
//
//			// 「期間．終了日」＜「付与日」
//			else if (period.get().end().before(grantDate)){
//
//				// 「List＜次回特別休暇付与＞」を返す
//				return nextSpecialLeaveGrantList;
//			}
//
//			// パラメータ「付与日」←パラメータ「付与日」＋1年
//			grantDate = grantDate.addYears(1);
//		}
//	}

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

	/**
	 * 期限を取得する （指定日、付与テーブル）
	 * @param require
	 * @param cacheCarrier
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param spLeaveCD 特別休暇コード
	 * @param nextSpecialLeaveGrantList 次回特別休暇付与リスト
	 * @param grantDateAfterPeriod 期間外次回付与日
	 * @return
	 */
	private static List<NextSpecialLeaveGrant> getExpireDate(
			SpecialLeaveManagementService.RequireM5 require,
			CacheCarrier cacheCarrier,
			String companyId,
			String employeeId,
			int spLeaveCD,
			List<NextSpecialLeaveGrant> nextSpecialLeaveGrantList,
			GeneralDate grantDateAfterPeriod) {

		// 「特別休暇」を取得する
		Optional<SpecialHoliday> specialHolidays = require.specialHoliday(companyId, spLeaveCD);
		if ( specialHolidays.isPresent() ){
			if ( specialHolidays.get().getGrantRegular().getGrantPeriodic().isPresent()) {

				// 取得している「特別休暇．期限情報．期限指定方法」をチェックする
				TimeLimitSpecification timeLimitSpecification
					= specialHolidays.get().getGrantRegular().getGrantPeriodic().get().getTimeSpecifyMethod();

				// 無期限
				if (timeLimitSpecification.equals(TimeLimitSpecification.INDEFINITE_PERIOD) ){

					// パラメータ「次回特別休暇付与」の期限を求める
					// 【条件】
					// ・付与日　←　パラメータ「次回特別休暇付与．付与「年月日」
					// ・付与日数　←　パラメータ「次回特別休暇付与．付与日数」
					// ・期限日　←　9999/12/31
					for( NextSpecialLeaveGrant nextSpecialLeaveGrant : nextSpecialLeaveGrantList ){
						nextSpecialLeaveGrant.setDeadLine(GeneralDate.ymd(9999,12,31));
					}
				}
				// 有効期限を指定する
				else if ( timeLimitSpecification.equals(TimeLimitSpecification.AVAILABLE_GRANT_DATE_DESIGNATE) ){

					// パラメータ「次回特別休暇付与」の期限を求める
					// 【条件】
					// ・付与日　←　パラメータ「次回特別休暇付与．付与年月日」
					// ・付与日数　←　パラメータ「次回特別休暇付与．付与日数」
					// ・期限日　←　パラメータ「次回特別休暇付与．付与年月日」
					// ├　年　＋　取得している「定期付与．付与日（定期）．特別休暇の期限．年数」
					// ├　月　＋　取得している「定期付与．付与日（定期）．特別休暇の期限．月数」
					// └　日　－　1日
					for( NextSpecialLeaveGrant nextSpecialLeaveGrant : nextSpecialLeaveGrantList ){

						// 付与日
						GeneralDate grantDateTmp = nextSpecialLeaveGrant.getGrantDate();

						if (specialHolidays.get().getGrantRegular().getFixGrantDate().isPresent()) {
								if ( specialHolidays.get().getGrantRegular().getFixGrantDate().get().getGrantPeriodic().getExpirationDate().isPresent()) {
								// 取得している「定期付与．付与日（定期）．特別休暇の期限．年数」
								//int years = specialHolidays.get().getGrantRegular().getFixGrantDate().getGrantPeriodic().getExpirationDate().getYears().v();
								int years = specialHolidays.get().getGrantRegular().getFixGrantDate().get().getGrantPeriodic().getExpirationDate().get().getYears().v();
								// 取得している「定期付与．付与日（定期）．特別休暇の期限．月数」
								int months = specialHolidays.get().getGrantRegular().getFixGrantDate().get().getGrantPeriodic().getExpirationDate().get().getMonths().v();

								GeneralDate deadline = grantDateTmp.addYears(years).addMonths(months).addDays(-1);
								nextSpecialLeaveGrant.setDeadLine(deadline);
							}
						}
					}
				}
				// 次回付与日まで使用可能
				else if ( timeLimitSpecification.equals(TimeLimitSpecification.AVAILABLE_UNTIL_NEXT_GRANT_DATE) ){

					// パラメータ「次回特別休暇付与」の期限を求める
					// 【条件】
					// ・付与日　←　パラメータ「次回特別休暇付与．付与年月日」
					// ・付与日数　←　パラメータ「次回特別休暇付与．付与日数」
					// ・期限日　←　パラメータ「次回特別休暇付与．付与年月日」の次の「次回特別休暇付与．付与年月日」
					//  ※最後の処理で次の「次回特別休暇付与．付与年月日」が存在しない場合
					//   →パラメータ「期間外次回付与日」の前日をセット

					// ループ1つ前の次回特別休暇付与を保持
					Optional<NextSpecialLeaveGrant> preNextSpecialLeaveGrant = Optional.empty();

					for( NextSpecialLeaveGrant nextSpecialLeaveGrant : nextSpecialLeaveGrantList ){
						if( preNextSpecialLeaveGrant.isPresent() ){
							preNextSpecialLeaveGrant.get().setDeadLine(
									nextSpecialLeaveGrant.getGrantDate().addDays(-1));
						}
						preNextSpecialLeaveGrant = Optional.of(nextSpecialLeaveGrant);
					}

					if( preNextSpecialLeaveGrant.isPresent() ){
						// 最後
						preNextSpecialLeaveGrant.get().setDeadLine(
							grantDateAfterPeriod.addDays(-1));
					}
				}
			}
		}
		return nextSpecialLeaveGrantList;
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

//	public static interface RequireM1 {
//
//		Optional<AnnualLeaveEmpBasicInfo> employeeAnnualLeaveBasicInfo(String employeeId);
//
//		EmployeeImport employee(CacheCarrier cacheCarrier, String empId);
//
//		List<AffCompanyHistImport> listAffCompanyHistImport(
//				ArrayList<String> sids, DatePeriod period);
//	}

//	public static interface RequireM2 extends RequireM1, GetClosureStartForEmployee.RequireM1, GetNextSpecialLeaveGrant.RequireM1 {
//
//	}
}



