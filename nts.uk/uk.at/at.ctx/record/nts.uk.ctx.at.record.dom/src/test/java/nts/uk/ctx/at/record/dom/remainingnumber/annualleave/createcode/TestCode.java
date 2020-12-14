package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.createcode;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainData;

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
		c.getActualSpecial().getUsedNumberInfo().getUsedNumber().getUseDays().getUseDays();
		if (c.getActualSpecial().getUsedNumberInfo().getUsedNumber().getUseTimes().isPresent()){
			/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.合計.使用時間.private UseNumber useNumber; */
			c.getActualSpecial().getUsedNumberInfo().getUsedNumber().getUseTimes().get().getUseTimes();
		}

		/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.付与前.使用日数.使用日数 */
		c.getActualSpecial().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseDays().getUseDays();
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
			c.getActualSpecial().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseDays().getUseDays();
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
		c.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseDays().getUseDays();
		if (c.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseTimes().isPresent()){
			/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.合計.使用時間.private UseNumber useNumber; */
			c.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseTimes().get().getUseTimes();
		}

		/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与前.使用日数.使用日数 */
		c.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseDays().getUseDays();
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
			c.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseDays().getUseDays();
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


}
