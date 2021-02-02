package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.createcode;

import java.time.MonthDay;
import java.util.List;
import java.util.Optional;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.ot.frame.NotUseAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.enums.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainData;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayName;
import nts.uk.ctx.at.shared.dom.specialholiday.TargetItem;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.AgeBaseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.AgeLimit;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.AgeRange;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.AgeStandard;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.GenderCls;
import nts.uk.ctx.at.shared.dom.specialholiday.grantcondition.SpecialLeaveRestriction;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.FixGrantDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantRegular;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantedDays;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.PeriodGrantDate;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.RegularGrantDays;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.TypeTime;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.GrantDeadline;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.LimitAccumulationDays;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.LimitCarryoverDays;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.SpecialVacationDeadline;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.SpecialVacationMonths;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.SpecialVacationYears;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.TimeLimitSpecification;
import nts.uk.shr.com.primitive.Memo;

public class TestCode {

	public void test()
	{
		SpecialHolidayRemainData c = new SpecialHolidayRemainData();



		/** 特別休暇月別残数データ.社員ID */
		c.getSid();

		/** 特別休暇月別残数データ.年月 */
		c.getYm();
		// !!クラスファイルが見つかりません。（YearMonth
		/** 特別休暇月別残数データ.締めID */
		c.getClosureId();

		/** 特別休暇月別残数データ.締め期間 */
		c.getClosurePeriod();
		// !!クラスファイルが見つかりません。（DatePeriod
		/** 特別休暇月別残数データ.締め処理状態 */
		c.getClosureStatus();
		/** 特別休暇月別残数データ.締め日付 */
		c.getClosureDate();
		// !!クラスファイルが見つかりません。（ClosureDate
		/** 特別休暇月別残数データ.特別休暇コード */
		c.getSpecialHolidayCd();

		/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.合計.使用日数.使用日数 */
		c.getActualSpecial().getUsedNumberInfo().getUsedNumber().getUseDays();
		if (c.getActualSpecial().getUsedNumberInfo().getUsedNumber().getUseTimes().isPresent()){
			/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.合計.使用時間.private UseNumber useNumber; */
			c.getActualSpecial().getUsedNumberInfo().getUsedNumber().getUseTimes().get().getUseTimes();
		}

		/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.付与前.使用日数.使用日数 */
		c.getActualSpecial().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseDays();
		if (c.getActualSpecial().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseTimes().isPresent()){
			/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.付与前.使用時間.private UseNumber useNumber; */
			c.getActualSpecial().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseTimes().get().getUseTimes();
		}

		/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.特休使用回数 （1日2回使用した場合２回でカウント） */
		c.getActualSpecial().getUsedNumberInfo().getSpecialLeaveUsedTimes();
		/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.特休使用日数 （1日2回使用した場合１回でカウント） */
		c.getActualSpecial().getUsedNumberInfo().getSpecialLeaveUsedDayTimes();
		if (c.getActualSpecial().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent()){
			/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.付与後.使用日数.使用日数 */
			c.getActualSpecial().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseDays();
			if (c.getActualSpecial().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().isPresent()){
				/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.付与後.使用時間.private UseNumber useNumber; */
				c.getActualSpecial().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().get().getUseTimes();
			}

		}

		/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.合計.合計残日数 */
		c.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getDayNumberOfRemain();
		if (c.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().isPresent()){
			/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.合計.合計残時間 */
			c.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().get();
		}

		/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.合計.明細 */
		c.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getDetails();

		// !!リストの対応が必要

		/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与前.合計残日数 */
		c.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDayNumberOfRemain();
		if (c.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().isPresent()){
			/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与前.合計残時間 */
			c.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().get();
		}

		/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与前.明細 */
		c.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDetails();

		// !!リストの対応が必要

		if (c.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()){
			/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与後.合計残日数 */
			c.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain();
			if (c.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent()){
				/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与後.合計残時間 */
				c.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().get();
			}

			/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与後.明細 */
			c.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDetails();

			// !!リストの対応が必要

		}

		/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.合計.使用日数.使用日数 */
		c.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseDays();
		if (c.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseTimes().isPresent()){
			/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.合計.使用時間.private UseNumber useNumber; */
			c.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseTimes().get().getUseTimes();
		}

		/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与前.使用日数.使用日数 */
		c.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseDays();
		if (c.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseTimes().isPresent()){
			/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与前.使用時間.private UseNumber useNumber; */
			c.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseTimes().get().getUseTimes();
		}

		/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.特休使用回数 （1日2回使用した場合２回でカウント） */
		c.getSpecialLeave().getUsedNumberInfo().getSpecialLeaveUsedTimes();
		/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.特休使用日数 （1日2回使用した場合１回でカウント） */
		c.getSpecialLeave().getUsedNumberInfo().getSpecialLeaveUsedDayTimes();
		if (c.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent()){
			/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与後.使用日数.使用日数 */
			c.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseDays();
			if (c.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().isPresent()){
				/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与後.使用時間.private UseNumber useNumber; */
				c.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().get().getUseTimes();
			}

		}

		/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.合計.合計残日数 */
		c.getSpecialLeave().getRemainingNumberInfo().getRemainingNumber().getDayNumberOfRemain();
		if (c.getSpecialLeave().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().isPresent()){
			/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.合計.合計残時間 */
			c.getSpecialLeave().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().get();
		}

		/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.合計.明細 */
		c.getSpecialLeave().getRemainingNumberInfo().getRemainingNumber().getDetails();

		// !!リストの対応が必要

		/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与前.合計残日数 */
		c.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDayNumberOfRemain();
		if (c.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().isPresent()){
			/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与前.合計残時間 */
			c.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().get();
		}

		/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与前.明細 */
		c.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDetails();

		// !!リストの対応が必要

		if (c.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()){
			/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与後.合計残日数 */
			c.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain();
			if (c.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent()){
				/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与後.合計残時間 */
				c.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().get();
			}

			/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与後.明細 */
			c.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDetails();

			// !!リストの対応が必要

		}

		/** 特別休暇月別残数データ.付与区分 */
		c.isGrantAtr();

		/** 特別休暇月別残数データ.未消化数.日数 */
		c.getUnDegestionNumber().getDays();
		if (c.getUnDegestionNumber().getTimes().isPresent()){
			/** 特別休暇月別残数データ.未消化数.時間 */
			c.getUnDegestionNumber().getTimes().get();
		}

		if (c.getGrantDays().isPresent()){
			/** 特別休暇月別残数データ.特別休暇付与情報: 付与日数 */
			c.getGrantDays().get();
		}



	}


	public void test2() {

//		/** 会社ID */
//		String  val_companyId1;
//		/** 特別休暇コード */
//		SpecialHolidayCode  val_specialHolidayCode2;
//		/** 特別休暇名称 */
//		SpecialHolidayName  val_specialHolidayName3;
//		/** 付与・期限情報.付与するタイミングの種類 */
//		TypeTime  val_typeTime4;
//		/** 付与・期限情報.付与基準日 */
//		Optional<GrantDate>  val_grantDate5;
//		/** 付与・期限情報.指定日付与.付与日数.付与日数 */
//		GrantedDays  val_grantDays6;
//		/** 付与・期限情報.指定日付与.期限.会社ID */
//		String  val_companyId7;
//		/** 付与・期限情報.指定日付与.期限.特別休暇コード */
//		SpecialHolidayCode  val_specialHolidayCode8;
//		/** 付与・期限情報.指定日付与.期限.期限指定方法 */
//		TimeLimitSpecification  val_timeSpecifyMethod9;
//		/** 付与・期限情報.指定日付与.期限.有効期限.月数 */
//		SpecialVacationMonths  val_months10;
//		/** 付与・期限情報.指定日付与.期限.有効期限.年数 */
//		SpecialVacationYears  val_years11;
//		/** 付与・期限情報.指定日付与.期限.蓄積上限.蓄積上限日数を制限する */
//		boolean  val_limit12;
//		/** 付与・期限情報.指定日付与.期限.蓄積上限.繰越上限日数 */
//		Optional<LimitCarryoverDays>  val_limitCarryoverDays13;
//		/** 付与・期限情報.指定日付与.付与月日. */
//		int  val_month14;
//		/** 付与・期限情報.指定日付与.付与月日. */
//		int  val_day15;
//		/** 付与・期限情報.付与日テーブル参照付与.会社ID */
//		String  val_companyId16;
//		/** 付与・期限情報.付与日テーブル参照付与.特別休暇コード */
//		SpecialHolidayCode  val_specialHolidayCode17;
//		/** 付与・期限情報.付与日テーブル参照付与.期限指定方法 */
//		TimeLimitSpecification  val_timeSpecifyMethod18;
//		/** 付与・期限情報.付与日テーブル参照付与.有効期限.月数 */
//		SpecialVacationMonths  val_months19;
//		/** 付与・期限情報.付与日テーブル参照付与.有効期限.年数 */
//		SpecialVacationYears  val_years20;
//		/** 付与・期限情報.付与日テーブル参照付与.蓄積上限.蓄積上限日数を制限する */
//		boolean  val_limit21;
//		/** 付与・期限情報.付与日テーブル参照付与.蓄積上限.繰越上限日数 */
//		Optional<LimitCarryoverDays>  val_limitCarryoverDays22;
//		/** 付与・期限情報.期間付与.期間 */
//		DatePeriod  val_period23;
//		/** 付与・期限情報.期間付与.付与日数.付与日数 */
//		GrantedDays  val_grantDays24;
//		/** 特別休暇利用条件.会社ID */
//		String  val_companyId25;
//		/** 特別休暇利用条件.特別休暇コード */
//		SpecialHolidayCode  val_specialHolidayCode26;
//		/** 特別休暇利用条件.分類条件 */
//		UseAtr  val_restrictionCls27;
//		/** 特別休暇利用条件.年齢条件 */
//		UseAtr  val_ageLimit28;
//		/** 特別休暇利用条件.性別条件 */
//		UseAtr  val_genderRest29;
//		/** 特別休暇利用条件.雇用条件 */
//		UseAtr  val_restEmp30;
//		/** 特別休暇利用条件.分類一覧 */
//		List<String>  val_listCls31;
//		/** 特別休暇利用条件.年齢基準.年齢基準年区分 */
//		AgeBaseYear  val_ageCriteriaCls32;
//		/** 特別休暇利用条件.年齢基準.年齢基準日 */
//		MonthDay  val_ageBaseDate33;
//		/** 特別休暇利用条件.年齢範囲.年齢下限 */
//		AgeLimit  val_ageLowerLimit34;
//		/** 特別休暇利用条件.年齢範囲.年齢上限 */
//		AgeLimit  val_ageHigherLimit35;
//		/** 特別休暇利用条件.性別 */
//		GenderCls  val_gender36;
//		/** 特別休暇利用条件.雇用一覧 */
//		List<String>  val_listEmp37;
//		/** 対象項目.対象の欠勤枠 */
//		List<Integer>  val_absenceFrameNo38;
//		/** 対象項目.対象の特別休暇枠 */
//		List<Integer>  val_frameNo39;
//		/** 自動付与区分 */
//		NotUseAtr  val_autoGrant40;
//		/** メモ */
//		Memo  val_memo41;
//
//			SpecialHoliday.of(
//				/** 会社ID */
//				 val_companyId1
//				/** 特別休暇コード */
//				, val_specialHolidayCode2
//				/** 特別休暇名称 */
//				, val_specialHolidayName3
//				/** 付与・期限情報 */
//				,GrantRegular.of(
//					/** 付与・期限情報.付与するタイミングの種類 */
//					 val_typeTime4
//					/** 付与・期限情報.付与基準日 */
//					, val_grantDate5
//		)			/** 付与・期限情報.指定日付与 */
//					,Optional.of(FixGrantDate.of(
//						/** 付与・期限情報.指定日付与.付与日数 */
//						RegularGrantDays.of(
//							/** 付与・期限情報.指定日付与.付与日数.付与日数 */
//							 val_grantDays6
//
//						)
//						/** 付与・期限情報.指定日付与.期限 */
//						,GrantDeadline.of(
//							/** 付与・期限情報.指定日付与.期限.会社ID */
//							 val_companyId7
//							/** 付与・期限情報.指定日付与.期限.特別休暇コード */
//							, val_specialHolidayCode8
//							/** 付与・期限情報.指定日付与.期限.期限指定方法 */
//							, val_timeSpecifyMethod9
//							/** 付与・期限情報.指定日付与.期限.有効期限 */
//							,Optional.of(SpecialVacationDeadline.of(
//								/** 付与・期限情報.指定日付与.期限.有効期限.月数 */
//								 val_months10
//								/** 付与・期限情報.指定日付与.期限.有効期限.年数 */
//								, val_years11
//
//							)
//							/** 付与・期限情報.指定日付与.期限.蓄積上限 */
//							,Optional.of(LimitAccumulationDays.of(
//								/** 付与・期限情報.指定日付与.期限.蓄積上限.蓄積上限日数を制限する */
//								 val_limit12
//								/** 付与・期限情報.指定日付与.期限.蓄積上限.繰越上限日数 */
//								, val_limitCarryoverDays13
//		)
//							)
//
//						)
//						/** 付与・期限情報.指定日付与.付与月日 */
//						,Optional.of(MonthDay.of(
//							/** 付与・期限情報.指定日付与.付与月日. */
//							 val_month14
//							/** 付与・期限情報.指定日付与.付与月日. */
//							, val_day15
//
//						)
//
//					)
//					/** 付与・期限情報.付与日テーブル参照付与 */
//					,Optional.of(GrantDeadline.of(
//						/** 付与・期限情報.付与日テーブル参照付与.会社ID */
//						 val_companyId16
//						/** 付与・期限情報.付与日テーブル参照付与.特別休暇コード */
//						, val_specialHolidayCode17
//						/** 付与・期限情報.付与日テーブル参照付与.期限指定方法 */
//						, val_timeSpecifyMethod18
//						/** 付与・期限情報.付与日テーブル参照付与.有効期限 */
//						,Optional.of(SpecialVacationDeadline.of(
//							/** 付与・期限情報.付与日テーブル参照付与.有効期限.月数 */
//							 val_months19
//							/** 付与・期限情報.付与日テーブル参照付与.有効期限.年数 */
//							, val_years20
//
//						)
//						/** 付与・期限情報.付与日テーブル参照付与.蓄積上限 */
//						,Optional.of(LimitAccumulationDays.of(
//							/** 付与・期限情報.付与日テーブル参照付与.蓄積上限.蓄積上限日数を制限する */
//							 val_limit21
//							/** 付与・期限情報.付与日テーブル参照付与.蓄積上限.繰越上限日数 */
//							, val_limitCarryoverDays22
//		)
//						)
//
//					)
//					/** 付与・期限情報.期間付与 */
//					,Optional.of(PeriodGrantDate.of(
//						/** 付与・期限情報.期間付与.期間 */
//						 val_period23
//						// !!クラスファイルが見つかりません。→　DatePeriod
//
//						/** 付与・期限情報.期間付与.付与日数 */
//						,RegularGrantDays.of(
//							/** 付与・期限情報.期間付与.付与日数.付与日数 */
//							 val_grantDays24
//
//						)
//
//					)
//
//				)
//				/** 特別休暇利用条件 */
//				,SpecialLeaveRestriction.of(
//					/** 特別休暇利用条件.会社ID */
//					 val_companyId25
//					/** 特別休暇利用条件.特別休暇コード */
//					, val_specialHolidayCode26
//					/** 特別休暇利用条件.分類条件 */
//					, val_restrictionCls27
//					/** 特別休暇利用条件.年齢条件 */
//					, val_ageLimit28
//					/** 特別休暇利用条件.性別条件 */
//					, val_genderRest29
//					/** 特別休暇利用条件.雇用条件 */
//					, val_restEmp30
//					/** 特別休暇利用条件.分類一覧 */
//					, val_listCls31
//					/** 特別休暇利用条件.年齢基準 */
//					,AgeStandard.of(
//						/** 特別休暇利用条件.年齢基準.年齢基準年区分 */
//						 val_ageCriteriaCls32
//						/** 特別休暇利用条件.年齢基準.年齢基準日 */
//						, val_ageBaseDate33
//						// !!クラスファイルが見つかりません。→　MonthDay
//
//
//					)
//					/** 特別休暇利用条件.年齢範囲 */
//					,AgeRange.of(
//						/** 特別休暇利用条件.年齢範囲.年齢下限 */
//						 val_ageLowerLimit34
//						/** 特別休暇利用条件.年齢範囲.年齢上限 */
//						, val_ageHigherLimit35
//
//					)
//					/** 特別休暇利用条件.性別 */
//					, val_gender36
//					/** 特別休暇利用条件.雇用一覧 */
//					, val_listEmp37
//
//				)
//				/** 対象項目 */
//				,TargetItem.of(
//					/** 対象項目.対象の欠勤枠 */
//					 val_absenceFrameNo38
//					/** 対象項目.対象の特別休暇枠 */
//					, val_frameNo39
//
//				)
//				/** 自動付与区分 */
//				, val_autoGrant40
//				// !!クラスファイルが見つかりません。→　NotUseAtr
//
//				/** メモ */
//				, val_memo41
//				// !!クラスファイルが見つかりません。→　Memo
//
//
//		);
	}
}
