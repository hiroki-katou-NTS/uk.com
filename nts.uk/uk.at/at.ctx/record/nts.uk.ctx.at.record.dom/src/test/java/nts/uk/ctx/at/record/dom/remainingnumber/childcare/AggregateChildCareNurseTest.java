package nts.uk.ctx.at.record.dom.remainingnumber.childcare;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import static nts.arc.time.GeneralDate.*;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggregateChildCareNurse;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggregateChildCareNurseWork;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseAggrPeriodInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseCalcResultWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseErrors;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseRemainingNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseStartdateInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.NextDayAfterPeriodEndWork;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.YearAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;

/**
 * 子の看護介護集計期間
 ****** 1.エラー情報をセットする
 ****** 2.期間終了日の翌日時点使用数にセットする値を判断する
 ****** 3.起算日からの休暇情報にセットする値を判断する
 ****** 4.起算日を含む期間かどうかを判断する
 ****** 5.作成
 ****** 6.集計期間の休暇情報にセットする値を判断する
 ****** 7.集計結果を作成する
 */
@RunWith(JMockit.class)
public class AggregateChildCareNurseTest {

	/**
	 * 1.エラー情報をセットする
	 */
	@Test
	public void testSetErrosInfo() {

		val childCare = createChildCare();

		val errors = childCare.setErrosInfo();

		assertThat(errors).hasSize(2);
	}

	/**
	 * 2.期間終了日の翌日時点使用数にセットする値を判断する
	 */
	@Test
	// 終了日の期間の「本年か翌年か」と終了日の翌日の期間の「本年か翌年か」を比較→同じ場合
	public void testNextPeriodEndUsedNumber1() {

		val childCare = createChildCare(YearAtr.THIS_YEAR, 3.0, null, 0, 0, 0); //使用日数3.0日、使用時間なし、上限日数0、時間休暇使用回数0、時間休暇使用日数0
		val usedNumber = childCare.nextPeriodEndUsedNumber();

		val expect = usedNumber(3.0, null); //期待値 子の看護介護使用日数3.0日、使用時間なし

		assertThat(usedNumber.getUsedDay()).isEqualTo(expect.getUsedDay());
		assertThat(usedNumber.getUsedTimes()).isEqualTo(expect.getUsedTimes());
	}

	@Test
	// 終了日の期間の「本年か翌年か」と終了日の翌日の期間の「本年か翌年か」を比較→違う場合
	public void testNextPeriodEndUsedNumber2() {

		val childCare = createChildCare(YearAtr.NEXT_YEAR, 0, 0, 0, 0, 0);//使用日数0.0日、使用時間0:00、上限日数0、時間休暇使用回数0、時間休暇使用日数0
		val usedNumber = childCare.nextPeriodEndUsedNumber();

		val expect = usedNumber(0.0, 0); //期待値 子の看護介護使用日数0.0日、使用時間なし
		assertThat(usedNumber.getUsedDay()).isEqualTo(expect.getUsedDay());
		assertThat(usedNumber.getUsedTimes()).isEqualTo(expect.getUsedTimes());
	}

	// 子の看護介護集計期間
	private AggregateChildCareNurse createChildCare(YearAtr secondYear, double useDay, Integer usedTimes, int upperLimit, int usedCount, int usedDays) {
		return AggregateChildCareNurse.of(
				Arrays.asList(AggregateChildCareNurseWork.of(new DatePeriod(ymd(2020,4,1), ymd(2020,4,30)),
										new ArrayList<>(),
										NextDayAfterPeriodEndWork.of(true,false),
										YearAtr.THIS_YEAR,
										Finally.of(ChildCareNurseCalcResultWithinPeriod.of(new ArrayList<>(),
												startdateInfo(useDay, usedTimes, upperLimit),
												periodInfo(usedCount, usedDays, useDay, upperLimit)))),
									AggregateChildCareNurseWork.of(new DatePeriod(ymd(2020,4,1), ymd(2020,4,30)),
											new ArrayList<>(),
											NextDayAfterPeriodEndWork.of(false,true),
											secondYear,
											Finally.of(ChildCareNurseCalcResultWithinPeriod.of(new ArrayList<>(),
													startdateInfo(useDay, usedTimes, upperLimit),
													periodInfo(usedCount, usedDays, useDay, upperLimit))))));
	}

	// 子の看護介護使用数
	private ChildCareNurseUsedNumber usedNumber(double useDay, Integer usedTimes) {
		return ChildCareNurseUsedNumber.of(new DayNumberOfUse(useDay),
				//Optional.of(new TimeOfUse(useTime)));
				usedTimes == null ? Optional.empty() : Optional.of(new TimeOfUse(usedTimes)));
	}


	/**
	 * 3.起算日からの休暇情報にセットする値を判断する
	 */
	@Test
	public void testGetHolidayInfoStartMonthDay() {
		val childCare = createChildCare(0, 0, 0, null, 0); //使用日数0.0日、使用時間0:00、上限日数0、時間休暇使用回数なし、時間休暇使用日数0
		val holidayInfo = childCare.getHolidayInfoStartMonthDay();

		val expect = thisYear(0.0, null, 0, 0, 0);	 // 本年：使用日数0.0日、使用時間0:00、上限日数0、時間休暇使用回数0、時間休暇使用日数0
		val expect2 = nextYear(0.0, null, 0, 0, 0); // 翌年：使用日数0.0日、使用時間0:00、上限日数0、時間休暇使用回数0、時間休暇使用日数0

		assertThat(holidayInfo.getThisYear().getUsedDays().getUsedDay()).isEqualTo(expect.getAggrResultOfChildCareNurse().get().getStartdateInfo().getUsedDays().getUsedDay());
		assertThat(holidayInfo.getThisYear().getUsedDays().getUsedTimes()).isEqualTo(expect.getAggrResultOfChildCareNurse().get().getStartdateInfo().getUsedDays().getUsedTimes());
		assertThat(holidayInfo.getThisYear().getRemainingNumber().getUsedDays()).isEqualTo(expect.getAggrResultOfChildCareNurse().get().getStartdateInfo().getRemainingNumber().getUsedDays());
		assertThat(holidayInfo.getThisYear().getRemainingNumber().getUsedTime()).isEqualTo(expect.getAggrResultOfChildCareNurse().get().getStartdateInfo().getRemainingNumber().getUsedTime());
		assertThat(holidayInfo.getThisYear().getLimitDays()).isEqualTo(expect.getAggrResultOfChildCareNurse().get().getStartdateInfo().getLimitDays());

		assertThat(holidayInfo.getNextYear()).isPresent();
		assertThat(holidayInfo.getNextYear().get().getUsedDays().getUsedDay()).isEqualTo(expect2.getAggrResultOfChildCareNurse().get().getStartdateInfo().getUsedDays().getUsedDay());
		assertThat(holidayInfo.getNextYear().get().getUsedDays().getUsedTimes()).isEqualTo(expect2.getAggrResultOfChildCareNurse().get().getStartdateInfo().getUsedDays().getUsedTimes());
		assertThat(holidayInfo.getNextYear().get().getRemainingNumber().getUsedDays()).isEqualTo(expect2.getAggrResultOfChildCareNurse().get().getStartdateInfo().getRemainingNumber().getUsedDays());
		assertThat(holidayInfo.getNextYear().get().getRemainingNumber().getUsedTime()).isEqualTo(expect2.getAggrResultOfChildCareNurse().get().getStartdateInfo().getRemainingNumber().getUsedTime());
		assertThat(holidayInfo.getNextYear().get().getLimitDays()).isEqualTo(expect2.getAggrResultOfChildCareNurse().get().getStartdateInfo().getLimitDays());
	}


	//  子の看護介護休暇 集計期間
	private AggregateChildCareNurse createChildCare(int usedCount, int usedDays, double usedDay, Integer usedTimes, int upperLimit) {
		return AggregateChildCareNurse.of(
				Arrays.asList(AggregateChildCareNurseWork.of(new DatePeriod(ymd(2021,4,1), ymd(2021,4,30)),
						new ArrayList<>(),
						NextDayAfterPeriodEndWork.of(false, false),
						YearAtr.THIS_YEAR,
						Finally.of(ChildCareNurseCalcResultWithinPeriod.of(new ArrayList<>(),
								startdateDaysInfo(usedDay, usedTimes, upperLimit),
								aggrPeriodInfo(usedCount, usedDays, usedDay, upperLimit)))),
				AggregateChildCareNurseWork.of(new DatePeriod(ymd(2021,4,1), ymd(2021,4,30)),
						new ArrayList<>(),
						NextDayAfterPeriodEndWork.of(false, false),
						YearAtr.NEXT_YEAR,
						Finally.of(ChildCareNurseCalcResultWithinPeriod.of(new ArrayList<>(),
								startdateDaysInfo(usedDay, usedTimes, upperLimit),
								aggrPeriodInfo(usedCount, usedDays, usedDay, upperLimit))))));
	}

	// 起算日からの子の看護介護休暇情報
	private ChildCareNurseStartdateInfo startdateDaysInfo (double usedDay,Integer usedTimes, int upperLimit) {
			return ChildCareNurseStartdateInfo.of(ChildCareNurseUsedNumber.of(new DayNumberOfUse(usedDay),
						usedTimes == null ? Optional.empty() : Optional.of(new TimeOfUse(usedTimes))),
				ChildCareNurseRemainingNumber.of(new DayNumberOfUse(usedDay),
						usedTimes == null ? Optional.empty() : Optional.of(new TimeOfUse(usedTimes))),
				new ChildCareNurseUpperLimit(upperLimit));
	}

	// 集計期間の子の看護介護休暇情報
	private ChildCareNurseAggrPeriodInfo aggrPeriodInfo(int usedCount, int usedDays, double usedDay, Integer usedTimes) {
		return ChildCareNurseAggrPeriodInfo.of(new UsedTimes(usedCount),
				new UsedTimes(usedDays),
				ChildCareNurseUsedNumber.of(new DayNumberOfUse(usedDay),
						usedTimes == null ? Optional.empty() : Optional.of(new TimeOfUse(usedTimes))));
	}


	// 本年の期間の「子の看護介護集計期間WORK」を取得する
	private AggregateChildCareNurseWork thisYear(double usedDay, Integer usedTimes, int upperLimit, int usedCount, int usedDays) {
		return AggregateChildCareNurseWork.of(new DatePeriod(ymd(2020,8,1), ymd(2020,8,31)),
				new ArrayList<>(),
				NextDayAfterPeriodEndWork.of(false, false),
				YearAtr.THIS_YEAR,
				Finally.of(ChildCareNurseCalcResultWithinPeriod.of(new ArrayList<>(),
						startdateDaysInfo(usedDay, usedTimes, upperLimit),
						aggrPeriodInfo(usedCount, usedDays, usedDay, upperLimit))));
	}

	// 翌年の期間の「子の看護介護集計期間WORK」を取得する
	private AggregateChildCareNurseWork nextYear(double usedDay,Integer usedTimes, int upperLimit, int usedCount, int usedDays) {
		return AggregateChildCareNurseWork.of(new DatePeriod(ymd(2021,4,1), ymd(2021,4,30)),
				new ArrayList<>(),
				NextDayAfterPeriodEndWork.of(false, false),
				YearAtr.NEXT_YEAR,
				Finally.of(ChildCareNurseCalcResultWithinPeriod.of(new ArrayList<>(),
						startdateDaysInfo(usedDay, usedTimes, upperLimit),
						aggrPeriodInfo(usedCount, usedDays, usedDay, upperLimit))));
	}

	/**
	 * 4.起算日を含む期間かどうかを判断する
	 */
	// 起算日を含む場合：true
	@Test
	public void testJudgePeriodNextYear() {

		val childCare = createChildCareNextYear();

		val judgePeriod = childCare.judgePeriod();

		assertThat(judgePeriod).isTrue();

	}
	// 起算日を含まない場合：false
	@Test
	public void testJudgePeriodThisYear() {

		val childCare = CreateChildCareThisYear();

		val judgePeriod = childCare.judgePeriod();

		assertThat(judgePeriod).isFalse();

	}

	// 子の看護介護集計期間 翌年
	private AggregateChildCareNurse createChildCareNextYear() {

		return AggregateChildCareNurse.of(Arrays.asList(AggregateChildCareNurseWork.of(null, null, null, YearAtr.NEXT_YEAR, null)));
	}


	// 子の看護介護集計期間 本年
	private AggregateChildCareNurse CreateChildCareThisYear() {

		return AggregateChildCareNurse.of(Arrays.asList(AggregateChildCareNurseWork.of(null, null, null, YearAtr.THIS_YEAR, null)));
	}

	/**
	 * 5.作成
	 */

	/**
	 * 6.集計期間の休暇情報にセットする値を判断する
	 */
	@Test
	public void testGetHolidayInfoAggrPeriod() {
		val childCare = createChildCare(0, 0, 0.0, 0, 0); //時間休暇使用回数0、時間休暇使用日数0、使用日数0.0日、使用時間0:00、上限日数0、
		val holidayInfo = childCare.getHolidayInfoAggrPeriod();

		val expect = aggrChildCareNurseWork(YearAtr.THIS_YEAR, 0.0, 0, 0); 	 // 期待値：本年、使用日数0.0日、使用時間0:00、上限日数0日
		val expect2 = aggrChildCareNurseWork(YearAtr.NEXT_YEAR, 0.0, 0, 0); // 期待値：翌年、使用日数0.0日、使用時間0:00、上限日数0日

		assertThat(holidayInfo.getThisYear().getUsedCount()).isEqualTo(expect.getAggrResultOfChildCareNurse().get().getAggrPeriodInfo().getUsedCount());
		assertThat(holidayInfo.getThisYear().getUsedDays()).isEqualTo(expect.getAggrResultOfChildCareNurse().get().getAggrPeriodInfo().getUsedDays());
		assertThat(holidayInfo.getThisYear().getAggrPeriodUsedNumber().getUsedDay()).isEqualTo(expect.getAggrResultOfChildCareNurse().get().getAggrPeriodInfo().getAggrPeriodUsedNumber().getUsedDay());
		assertThat(holidayInfo.getThisYear().getAggrPeriodUsedNumber().getUsedTimes()).isEqualTo(expect.getAggrResultOfChildCareNurse().get().getAggrPeriodInfo().getAggrPeriodUsedNumber().getUsedTimes());

		assertThat(holidayInfo.getNextYear()).isPresent();
		assertThat(holidayInfo.getNextYear().get().getUsedCount()).isEqualTo(expect2.getAggrResultOfChildCareNurse().get().getAggrPeriodInfo().getUsedCount());
		assertThat(holidayInfo.getNextYear().get().getUsedCount()).isEqualTo(expect2.getAggrResultOfChildCareNurse().get().getAggrPeriodInfo().getUsedDays());
		assertThat(holidayInfo.getNextYear().get().getAggrPeriodUsedNumber().getUsedDay()).isEqualTo(expect2.getAggrResultOfChildCareNurse().get().getAggrPeriodInfo().getAggrPeriodUsedNumber().getUsedDay());
		assertThat(holidayInfo.getNextYear().get().getAggrPeriodUsedNumber().getUsedTimes()).isEqualTo(expect2.getAggrResultOfChildCareNurse().get().getAggrPeriodInfo().getAggrPeriodUsedNumber().getUsedTimes());

	}

	// 起算日から子の看護介護休暇情報
	private ChildCareNurseStartdateInfo startdateInfo(double useDay, Integer usedTimes, int upperLimit) {
		return ChildCareNurseStartdateInfo.of(
				ChildCareNurseUsedNumber.of(new DayNumberOfUse(useDay),
						usedTimes == null ? Optional.empty() : Optional.of(new TimeOfUse(usedTimes))),
				ChildCareNurseRemainingNumber.of(new DayNumberOfUse(useDay),
						usedTimes == null ? Optional.empty() : Optional.of(new TimeOfUse(usedTimes))),
				new ChildCareNurseUpperLimit(upperLimit));
	}


	// 集計期間の子の看護介護休暇情報
	private ChildCareNurseAggrPeriodInfo periodInfo(int usedCount, int usedDays, double useDay, Integer usedTimes) {
		return ChildCareNurseAggrPeriodInfo.of(new UsedTimes(usedCount), new UsedTimes(usedDays),
				ChildCareNurseUsedNumber.of(new DayNumberOfUse(useDay),
						usedTimes == null ? Optional.empty() : Optional.of(new TimeOfUse(usedTimes))));
	}

	// 本年の期間の「子の看護介護集計期間WORK」を取得する
	// 翌年の期間の「子の看護介護集計期間WORK」を取得する
	private AggregateChildCareNurseWork aggrChildCareNurseWork(YearAtr secondYear, double usedDay, Integer usedTime, int upperLimit) {
		return AggregateChildCareNurseWork.of(new DatePeriod(ymd(2020,10,16), ymd(2020,11,15)),
				new ArrayList<>(),
				NextDayAfterPeriodEndWork.of(false, false),
				secondYear,
				Finally.of(ChildCareNurseCalcResultWithinPeriod.of(new ArrayList<>(),
						startdateInfo(usedDay, usedTime, upperLimit),
						periodInfo(0, 0, 0, 0))));
	}

	/**
	 * 7.集計結果を作成する
	 */



	// 子の看護介護休暇 集計期間
	private AggregateChildCareNurse createChildCare() {

		return AggregateChildCareNurse.of(
				Arrays.asList(AggregateChildCareNurseWork.of(null, null, null, null,
												Finally.of(ChildCareNurseCalcResultWithinPeriod.of(Arrays.asList(createError(today(), 1d, 0, 2)), null, null))),
										AggregateChildCareNurseWork.of(null, null, null, null,
												Finally.of(ChildCareNurseCalcResultWithinPeriod.of(Arrays.asList(createError(today(), 2d, 0, 3)), null, null)))));
	}

	//	子の看護介護エラー情報
	private ChildCareNurseErrors createError(GeneralDate ymd, double useDay, int useTime, int limitDays) {

		return ChildCareNurseErrors.of(
							ChildCareNurseUsedNumber.of(new DayNumberOfUse(useDay),
																				Optional.of(new TimeOfUse(useTime))),
							new ChildCareNurseUpperLimit(limitDays), ymd);
	}


}
