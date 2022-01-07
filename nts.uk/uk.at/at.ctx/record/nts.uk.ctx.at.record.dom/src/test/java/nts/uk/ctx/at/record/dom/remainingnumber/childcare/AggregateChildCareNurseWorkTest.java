package nts.uk.ctx.at.record.dom.remainingnumber.childcare;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import static nts.arc.time.GeneralDate.*;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggregateChildCareNurseWork;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseCalcResultWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseCalcUsedNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseRemainingNumberCalcWork;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseStartdateInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.NextDayAfterPeriodEndWork;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.YearAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.children.service.ChildCareNurseErrors;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.UpperLimitSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.DayNumberOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.TimeOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseRemainingNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseUsedInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NumberOfCaregivers;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.shr.com.time.calendar.MonthDay;

/**
 * 子の看護介護集計期間WORK
 ****** 1.エラーチェック
 ****** 2.残数と使用数を計算する
 ****** 3.残数計算
 ****** 4.使用数計算
 ****** 5.子の看護介護残数を求める
 */
@RunWith(JMockit.class)
public class AggregateChildCareNurseWorkTest {

	@Injectable
	private AggregateChildCareNurseWork.Require require;

	private NursingCategory category = NursingCategory.ChildNursing;

	/**
	 *  1.エラーチェック
	 */
	@Test
	// 集計期間の翌日を集計する時は、処理は行わない
	// trueの場合：空のリストを返す
	public void testErrorCheck1() {

		String companyId = "0001";
		String employeeId = "000001";
		DatePeriod period = new DatePeriod(ymd(2020, 10, 16),ymd(2020, 11, 15));
		GeneralDate criteriaDate = ymd(2020,12, 1);
		ChildCareNurseUsedNumber startUsed = usedNumber(0.0, 0);

		val childCare = createChildCare11(0, 0, 0, null, 0, true); // 終了日の翌日の期間 = true;
		val nextPeriodEndAtr = childCare.errorInfo(companyId, employeeId, period, criteriaDate, startUsed, category, require);

		assertThat(nextPeriodEndAtr).isEmpty();
	}

	@Test
	// 集計期間の翌日を集計する時は、処理は行わない（暫定データ：本年）
	// falseの場合：子の看護介護エラー情報Listを返す
	public void testErrorCheck2() {

		String companyId = "0001";
		String employeeId = "000001";
		DatePeriod period = new DatePeriod(ymd(2020, 10, 16),ymd(2020, 11, 15));
		GeneralDate criteriaDate = ymd(2020,12, 1);
		ChildCareNurseUsedNumber startUsed = usedNumber(9.0, 120);//月初時点の使用数（日数、時間）

		new Expectations() {
			{
				require.employeeInfo(employeeId, category); // 子の看護・介護休暇基本情報を取得する
				result = childCareLeave(NursingCategory.ChildNursing, 5, 10); //本年度上限日数5日、翌年度上限日数10日

				require.nursingLeaveSetting(companyId, NursingCategory.ChildNursing); // 介護看護休暇設定を取得する（会社ID、介護看護区分）
				result = nursingLeaveSet(NursingCategory.ChildNursing);

			}
		};

		val childCare = createChildCare11(0, 0, 0, null, 0, false); // 終了日の翌日の期間 = false;
		val nextPeriodEndAtr = childCare.errorInfo(companyId, employeeId, period, criteriaDate, startUsed, category, require);

		val expect = createError(ymd(2020,10, 18), 10.0, 180, 5);	//	期待値：基準日、使用日数0日、使用時間3:00(=180)、上限日数5日
		assertThat(nextPeriodEndAtr.get(0).getUsedNumber().getUsedDay()).isEqualTo(expect.get(0).getUsedNumber().getUsedDay());
		assertThat(nextPeriodEndAtr.get(0).getUsedNumber().getUsedTimes()).isEqualTo(expect.get(0).getUsedNumber().getUsedTimes());
		assertThat(nextPeriodEndAtr.get(0).getLimitDays()).isEqualTo(expect.get(0).getLimitDays());
		assertThat(nextPeriodEndAtr.get(0).getYmd()).isEqualTo(expect.get(0).getYmd());
	}

	@Test
	// 集計期間の翌日を集計する時は、処理は行わない（暫定データ：翌年）
	// falseの場合：子の看護介護エラー情報Listを返す
	public void testErrorCheck3() {

		String companyId = "0001";
		String employeeId = "000001";
		DatePeriod period = new DatePeriod(ymd(2020, 10, 16),ymd(2020, 11, 15));
		GeneralDate criteriaDate = ymd(2020,12, 1);
		ChildCareNurseUsedNumber startUsed = usedNumber(9.0, 120);//月初時点の使用数（日数、時間）

		new Expectations() {
			{
				require.employeeInfo(employeeId, NursingCategory.ChildNursing); // 子の看護・介護休暇基本情報を取得する
				result = childCareLeave(NursingCategory.ChildNursing, 5, 10); //本年度上限日数5日、翌年度上限日数10日

				require.nursingLeaveSetting(companyId, NursingCategory.ChildNursing); // 介護看護休暇設定を取得する（会社ID、介護看護区分）
				result = nursingLeaveSet(NursingCategory.ChildNursing);

			}
		};

		val childCare = createChildCare11(0, 0, 0, null, 0, false); // 終了日の翌日の期間 = false;
		val nextPeriodEndAtr = childCare.errorInfo(companyId, employeeId, period, criteriaDate, startUsed, NursingCategory.ChildNursing, require);

		val expect = createError(ymd(2020,10, 18), 10.0, 180, 5);	//	期待値：基準日、使用日数0日、使用時間3:00(=180)、上限日数5日
		assertThat(nextPeriodEndAtr.get(0).getUsedNumber().getUsedDay()).isEqualTo(expect.get(0).getUsedNumber().getUsedDay());
		assertThat(nextPeriodEndAtr.get(0).getUsedNumber().getUsedTimes()).isEqualTo(expect.get(0).getUsedNumber().getUsedTimes());
		assertThat(nextPeriodEndAtr.get(0).getLimitDays()).isEqualTo(expect.get(0).getLimitDays());
		assertThat(nextPeriodEndAtr.get(0).getYmd()).isEqualTo(expect.get(0).getYmd());
	}

	// 子の看護介護計算使用数
	private ChildCareNurseCalcUsedNumber calcUsedNumber(double useDay, Integer useTime, int usedCount, int usedDays, double aggruseDay, Integer aggruseTime) {
		return ChildCareNurseCalcUsedNumber.of(usedNumber(useDay,useTime),//起算日からの使用数
				new UsedTimes(usedCount),			//時間休暇使用回数
				new UsedTimes(usedDays),			//時間休暇使用日数
				usedNumber(aggruseDay,aggruseTime));	//集計期間の使用数
	}

	//	子の看護介護エラー情報
	private List<ChildCareNurseErrors> createError(GeneralDate ymd, double useDay, int useTime, int limitDays) {
		return Arrays.asList(
				ChildCareNurseErrors.of(
							ChildCareNurseUsedNumber.of(new DayNumberOfUse(useDay),
																				Optional.of(new TimeOfUse(useTime))),
							new ChildCareNurseUpperLimit(limitDays), ymd));
	}

	/**
	 * 3.残数計算
	 */
	@Test
	// 3.残数計算
	// 集計期間の翌日を集計する時は、処理は行わない
	// trueの場合：子の看護介護計算残数を初期値で返す
	public void testCalcRemaining1() {

		String companyId = "0001";
		String employeeId = "000001";
		val childCare = createChildCare11(0, 0, 0, null, 0, true); // 終了日の翌日の期間 = true


		val nextPeriodEndAtr = childCare.calcRemaining(companyId, employeeId,
												new DatePeriod(ymd(2020, 10, 16),ymd(2020, 11, 15)),
												ymd(2020,10, 16),
												usedNumber(0.0, 0),	// 起算日からの使用数（使用日数、使用時間）
												calcUsedNumber(0.0 ,0 ,0 ,0 , 0.0, 0),// 子の看護介護計算使用数
												category,
												require);

		val expect = calcRemaining(0.0, null, 0);//期待値：計算残数（残日数、残時間、上限日数）
		assertThat(nextPeriodEndAtr.getRemainNumber().getRemainDay()).isEqualTo(expect.getRemainNumber().getRemainDay());
		assertThat(nextPeriodEndAtr.getRemainNumber().getRemainTimes()).isEqualTo(expect.getRemainNumber().getRemainTimes());
		assertThat(nextPeriodEndAtr.getUpperLimit()).isEqualTo(expect.getUpperLimit());
	}

	@Test
	// 集計期間の翌日を集計する時は、処理は行う
	// falseの場合：子の看護介護計算残数を返す
	public void testCalcRemaining2() {
		val companyId = "0001";
		val employeeId = "000001";
		val childCare = createChildCare11(0, 0, 0, null, 0, false);	// 終了日の翌日の期間 = false
		new Expectations() {
			{
				require.employeeInfo(employeeId, NursingCategory.ChildNursing); // 子の看護・介護休暇基本情報を取得する
				result = childCareLeave(NursingCategory.ChildNursing, 5, 10); //本年度上限日数5日、翌年度上限日数10日

				require.nursingLeaveSetting(companyId, NursingCategory.ChildNursing); // 介護看護休暇設定を取得する（会社ID、介護看護区分）
				result = nursingLeaveSet(NursingCategory.ChildNursing);
			}
		};

		val expect = calcRemaining(0.0, null, 0); //期待値：計算残数（残日数、残時間、上限日数）
		val calcRemaining = childCare.calcRemaining("0001", "000001",
				new DatePeriod(ymd(2020, 12, 1),ymd(2020, 12, 31)),
				ymd(2021,1, 10),
				usedNumber(0.5, 0),	// 起算日からの使用数
				calcUsedNumber(1.0 ,0 ,0 ,0 , 0.0 , 0), // 子の看護介護計算使用数
				category,
				require);
		assertThat(calcRemaining.getRemainNumber().getRemainDay()).isEqualTo(expect.getRemainNumber().getRemainDay());
		assertThat(calcRemaining.getRemainNumber().getRemainTimes()).isEqualTo(expect.getRemainNumber().getRemainTimes());
		assertThat(calcRemaining.getUpperLimit()).isEqualTo(expect.getUpperLimit());
	}

	// 子の看護介護休暇 集計期間WORK
	private AggregateChildCareNurseWork createChildCare11(int usedCount, int usedDays, double useDay, Integer usedTimes, int upperLimit, boolean nextPeriodEndAtr) {
		return AggregateChildCareNurseWork.of(new DatePeriod(ymd(2020, 10, 16),ymd(2020, 11, 15)),
																			provisionalDate(RemainType.CHILDCARE, 1.0, 60),// 暫定子の看護介護管理データ
																			NextDayAfterPeriodEndWork.of(false, nextPeriodEndAtr),
																			YearAtr.THIS_YEAR,
																			Finally.of(ChildCareNurseCalcResultWithinPeriod.of(
																					new ArrayList<>(),
																					startdateInfo(useDay, usedTimes, upperLimit),
																					periodInfo(usedCount,usedDays, useDay, usedTimes))));
	}

	// 暫定子の看護介護管理データ
	private List<TempChildCareNurseManagement> provisionalDate(RemainType remainType, double useDay, Integer usedTimes){
		return Arrays.asList(
				TempChildCareNurseManagement.of(
						"10f82569-6cfe-4992-9d5c-d9f3dd29b225",//remainManaID,
						"000001",	//sID,
						ymd(2020,10, 18),	//GeneralDate ymd,
						CreateAtr.RECORD,	//CreateAtr creatorAtr,
						remainType,	//RemainType remainType,
						usedNumber(useDay, usedTimes),
						Optional.empty()// DigestionHourlyTimeType
					));
	}

	// 子の看護介護残数
	private ChildCareNurseRemainingNumber remNum(double usedDays, Integer usedTime) {
		return ChildCareNurseRemainingNumber.of(
				new DayNumberOfRemain(usedDays),
				usedTime == null ? Optional.empty() : Optional.of(new TimeOfRemain(usedTime)));
	}

	// 起算日から子の看護介護休暇情報
	private ChildCareNurseStartdateInfo startdateInfo(double useDay, Integer usedTimes, int upperLimit) {
		return ChildCareNurseStartdateInfo.of(
				usedNumber(useDay, usedTimes),
				remNum(useDay, usedTimes),
				new ChildCareNurseUpperLimit(upperLimit));
	}

	// 集計期間の子の看護介護休暇情報
	private ChildCareNurseUsedInfo periodInfo(int usedCount, int usedDays, double useDay, Integer usedTimes) {
		return ChildCareNurseUsedInfo.of(
				usedNumber(useDay, usedTimes),
				new UsedTimes(usedCount),
				new UsedTimes(usedDays)
				);
	}

	// 子の看護介護計算残数
	private ChildCareNurseRemainingNumberCalcWork calcRemaining(double useDay, Integer usedTimes, int upperLimit) {
		return ChildCareNurseRemainingNumberCalcWork.of(
				remNum(useDay, usedTimes),
				new ChildCareNurseUpperLimit(upperLimit));
	}

	/**
	 * 4.使用数計算
	 */
	@Test
	// 集計期間の翌日を集計する時は、処理は行わない
	// trueの場合：ChildCareNurseRemainingNumberCalcWorkの初期値で返す
	public void testCalcUsed1() {
		val childCare = createChildCare11(0, 0, 0, null, 0, true);	//終了日の翌日の期間 = true
		val nextPeriodEndAtr = childCare.calcUsed("0001", "000001",
												ymd(2020,10, 16),
												usedNumber(0.0, 0),	// 起算日からの使用数
												require);

		val expect = calcUsedNumber(0.0, null, 0, 0, 0.0, null);		//期待値：起算日からの使用数、時間休暇使用回数、時間休暇使用日数、集計期間の使用数
		assertThat(nextPeriodEndAtr.getStartdateInfo().getUsedDay()).isEqualTo(expect.getStartdateInfo().getUsedDay());
		assertThat(nextPeriodEndAtr.getStartdateInfo().getUsedTimes()).isEqualTo(expect.getStartdateInfo().getUsedTimes());
		assertThat(nextPeriodEndAtr.getUsedCount()).isEqualTo(expect.getUsedCount());
		assertThat(nextPeriodEndAtr.getUsedDays()).isEqualTo(expect.getUsedDays());
		assertThat(nextPeriodEndAtr.getAggrPeriodUsedNumber().getUsedDay()).isEqualTo(expect.getAggrPeriodUsedNumber().getUsedDay());
		assertThat(nextPeriodEndAtr.getAggrPeriodUsedNumber().getUsedTimes()).isEqualTo(expect.getAggrPeriodUsedNumber().getUsedTimes());
	}

	@Test
	// 集計期間の翌日を集計する時は、処理は行わない
	// falseの場合：処理続行　
	public void testCalcUsed2() {
		val childCare = createChildCare11(0, 0, 0, null, 0, false); //終了日の翌日の期間 = false

		val expect = calcUsedNumber(4.0, 180, 1, 1, 1.0, 60);//期待値：起算日からの使用数、時間休暇使用回数、時間休暇使用日数、集計期間の使用数
		val calcUsed = childCare.calcUsed("0001", "000001",
				ymd(2020,10, 16),
				usedNumber(3.0, 120),	// 起算日からの使用数（日数、時間）
				require);

		assertThat(calcUsed.getStartdateInfo().getUsedDay()).isEqualTo(expect.getStartdateInfo().getUsedDay());
		assertThat(calcUsed.getStartdateInfo().getUsedTimes()).isEqualTo(expect.getStartdateInfo().getUsedTimes());
		assertThat(calcUsed.getUsedCount()).isEqualTo(expect.getUsedCount());
		assertThat(calcUsed.getUsedDays()).isEqualTo(expect.getUsedDays());
		assertThat(calcUsed.getAggrPeriodUsedNumber().getUsedDay()).isEqualTo(expect.getAggrPeriodUsedNumber().getUsedDay());
		assertThat(calcUsed.getAggrPeriodUsedNumber().getUsedTimes()).isEqualTo(expect.getAggrPeriodUsedNumber().getUsedTimes());
	}

	// 子の看護介護使用数
	private ChildCareNurseUsedNumber usedNumber(double useDay, Integer usedTimes) {
		return ChildCareNurseUsedNumber.of(
				new DayNumberOfUse(useDay),
				usedTimes == null ? Optional.empty() : Optional.of(new TimeOfUse(usedTimes)));
	}

	// 子の看護休暇基本情報
	private Optional<NursingCareLeaveRemainingInfo> childCareLeave(NursingCategory leaveType, Integer maxDayForThisFiscalYear , Integer maxDayForNextFiscalYear) {
		ChildCareLeaveRemainingInfo obj =ChildCareLeaveRemainingInfo.of(
				"000001",
				leaveType,
				true, // useClassification:使用区分
				UpperLimitSetting.PER_INFO_EVERY_YEAR,
				maxDayForThisFiscalYear == null ? Optional.empty() : Optional.of(new ChildCareNurseUpperLimit(maxDayForThisFiscalYear)),
				maxDayForNextFiscalYear == null ? Optional.empty() : Optional.of(new ChildCareNurseUpperLimit(maxDayForNextFiscalYear)));

		return Optional.of((NursingCareLeaveRemainingInfo)obj);
	}

	// 介護看護休暇上限人数設定
	private List<MaxPersonSetting> maxPersonSetting() {
		return Arrays.asList(MaxPersonSetting.of(new ChildCareNurseUpperLimit(5), new NumberOfCaregivers(1)),
				MaxPersonSetting.of(new ChildCareNurseUpperLimit(10), new NumberOfCaregivers(2)));
	}
	// 介護看護休暇設定
	private NursingLeaveSetting nursingLeaveSet(NursingCategory nursingCategory) {
		return NursingLeaveSetting.of(
				"0001", // companyId
				ManageDistinct.YES, //manageType,
				nursingCategory, //nursingCategory,
				new MonthDay(4, 1), //startMonthDay,
				maxPersonSetting(), //maxPersonSetting,
				Optional.empty(), //specialHolidayFrame,
				Optional.empty(), // workAbsence
				new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour));
	}
}
