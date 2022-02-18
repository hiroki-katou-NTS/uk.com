package nts.uk.ctx.at.record.dom.remainingnumber.childcare;

import static nts.arc.time.GeneralDate.ymd;
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
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.CareManagementDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimitSplit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareTargetChanged;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.FamilyInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NumberOfCaregivers;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.TimeCareNursingSet;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.MonthDay;

/**
 * 介護看護休暇設定
 ****** 1.家族情報から対象人数を履歴で求める
 ****** 2.次回起算日を求める
 ****** 3.上限日数を求める
 ****** 4.上限日数分割日を求める
 ****** 5.本年起算日を求める
 */
@RunWith(JMockit.class)
public class NursingLeaveSettingTest {

	/**
	 * 1.家族情報から対象人数を履歴で求める
	 */
	@Injectable
	private NursingLeaveSetting.RequireM7 require;

	@Test
	// 看護の場合
	// 期間の子の看護対象人数を求める
	public void testChildNursing() {
		String employeeId = "000001";
		DatePeriod period = new DatePeriod(ymd(2020, 10, 16),ymd(2020, 11, 15));
		GeneralDate criteriaDate = ymd(2020, 4, 1);
		new Expectations() {
			{
				require.familyInfo(employeeId); // 社員IDが一致する家族情報を取得（社員ID）
				result = new FamilyInfo();
			}
		};

		val childCare = createChildCare(NursingCategory.ChildNursing);
		val splitDateList = childCare.getHistoryCountFromFamilyInfo(employeeId, period, criteriaDate, require);


		// 上限日数分割日
		val expect = limitSplit();
		assertThat(splitDateList.get(0).getLimitDays()).isEqualTo(expect.get(0).getLimitDays());
		assertThat(splitDateList.get(0).getYmd()).isEqualTo(expect.get(0).getYmd());
	}

	@Test
	// 介護の場合
	// 介護対象人数を履歴で求める
	public void testNursing() {
		String employeeId = "000001";
		String familyID = "900001";
		DatePeriod period = new DatePeriod(ymd(2020, 10, 16),ymd(2020, 11, 15));
		GeneralDate criteriaDate = ymd(2020, 10, 16);
		new Expectations() {
			{
				require.familyInfo(employeeId); // 社員IDが一致する家族情報を取得（社員ID）
				result = familyInfo(); //dummy

				require.careData(familyID); // 介護対象管理データ（家族ID）
				result = careManagementDate(); //dummy
			}
		};

		val childCare = createChildCare(NursingCategory.Nursing);

		val splitDateList = childCare.getHistoryCountFromFamilyInfo(employeeId, period, criteriaDate, require);

		// 上限日数分割日
		val expect = limitSplitCare();
		assertThat(splitDateList.get(0).getLimitDays()).isEqualTo(expect.get(0).getLimitDays());
		assertThat(splitDateList.get(0).getYmd()).isEqualTo(expect.get(0).getYmd());

	}


	// 上限日数分割日List（看護）
	private List<ChildCareNurseUpperLimitSplit> limitSplit() {
		return  Arrays.asList(
				ChildCareNurseUpperLimitSplit.of(new ChildCareNurseUpperLimit(5), ymd(2020, 10, 16)),
				ChildCareNurseUpperLimitSplit.of(new ChildCareNurseUpperLimit(10), ymd(2020, 10, 16)));

	}

	// 上限日数分割日List（介護）
	private List<ChildCareNurseUpperLimitSplit> limitSplitCare() {
		return  Arrays.asList(
				ChildCareNurseUpperLimitSplit.of(new ChildCareNurseUpperLimit(0), ymd(2020, 10, 16)),
				ChildCareNurseUpperLimitSplit.of(new ChildCareNurseUpperLimit(5), ymd(2020, 10, 16)));

	}

	// 介護看護休暇上限人数設定
	private List<MaxPersonSetting> maxPersonSetting() {
		return Arrays.asList(MaxPersonSetting.of(new ChildCareNurseUpperLimit(5), new NumberOfCaregivers(1)),
				MaxPersonSetting.of(new ChildCareNurseUpperLimit(10), new NumberOfCaregivers(2)));
	}

	// 介護看護休暇設定
	private NursingLeaveSetting createChildCare(NursingCategory nursingCategory) {
		return NursingLeaveSetting.of(
				"0001", // companyId
				ManageDistinct.YES, //manageType,
				nursingCategory, //nursingCategory,
				new MonthDay(4, 1), //startMonthDay,
				maxPersonSetting(), //maxPersonSetting,
				Optional.empty(), //specialHolidayFrame,
				Optional.empty()); // workAbsence
	}

	// 家族情報
	private List<FamilyInfo> familyInfo() {
		return Arrays.asList(FamilyInfo.of("900001", ymd(1944, 6, 25), Optional.empty()),
				FamilyInfo.of("900002", ymd(1949, 5, 5), Optional.empty()),
				FamilyInfo.of("900003", ymd(1950, 3, 31), Optional.empty()));
	}

	// 介護対象管理データ
	Optional<CareManagementDate> careManagementDate() {
		return Optional.of(
				CareManagementDate.of(
						"900001",
						true,
						new DateHistoryItem("", new DatePeriod(GeneralDate.min(), GeneralDate.max()))));

	}

	/**
	 * 2.次回起算日を求める
	 */
	@Test
	// 基準日の年で次回起算日を求める
	public void testNextStartMonthDay1() {
		val childCare = createChildCare(new MonthDay(4,1));

		val startMonthDay = childCare.getNextStartMonthDay(ymd(2020, 4, 1));

		assertThat(startMonthDay).isEqualTo(ymd(2020, 4, 1));
	}

	@Test
	// 基準日の年に＋１年し次回起算日を求める
	public void testNextStartMonthDay2() {

		val childCare = createChildCare(new MonthDay(4,1));

		val startMonthDay = childCare.getNextStartMonthDay( ymd(2020, 10, 15)); //基準日

		assertThat(startMonthDay).isEqualTo(ymd(2021, 4, 1));
	}

	// 介護看護休暇設定
	private NursingLeaveSetting createChildCare(MonthDay startMonthDay) {
		return NursingLeaveSetting.of("0001", ManageDistinct.YES, NursingCategory.ChildNursing, startMonthDay,
				new ArrayList<>(),
				Optional.empty(), Optional.empty());
	}

	/**
	 * 3.上限日数を求める
	 */
	@Test
	// 人数=0の場合
	public void testNumPerson() {

		NumberOfCaregivers numPerson = new NumberOfCaregivers(0); //人数

		val childCare = createChildCare(NursingCategory.Nursing);

		val person = childCare.nursingNumberPerson(numPerson);

		assertThat(person).isEqualTo(new ChildCareNurseUpperLimit(0));
	}
	@Test
	// 人数=1の場合
	public void testNumPerson1() {

		NumberOfCaregivers numPerson = new NumberOfCaregivers(1); //人数

		val childCare = createChildCare(NursingCategory.Nursing);

		val days = childCare.nursingNumberPerson(numPerson);

		assertThat(days).isEqualTo(new ChildCareNurseUpperLimit(5));
	}
	@Test
	// 人数>=2の場合
	public void testNumPerson2() {

		NumberOfCaregivers numPerson = new NumberOfCaregivers(3); //人数

		val childCare = createChildCare(NursingCategory.Nursing);

		val days = childCare.nursingNumberPerson(numPerson);

		assertThat(days).isEqualTo(new ChildCareNurseUpperLimit(10));
	}

	/**
	 * 4.上限日数分割日を求める
	 */
	@Test
	// 年月日が一番大きい上限日数分割日の上限日数と比較
	// true：
	//    年月日が一番大きい上限日数分割日の上限日数と求めた上限日数が違う or 上限日数分割日が0件
	public void testUpperLimitSplit1() {

		// 上限日数を求める
		NumberOfCaregivers numPerson = new NumberOfCaregivers(1); //人
		val childCare = createChildCare(NursingCategory.Nursing);
		val days = childCare.nursingNumberPerson(numPerson); // 介護看護休暇日数
		assertThat(days).isEqualTo(new ChildCareNurseUpperLimit(5));

		// 上限日数分割日を求める
		val upperLimitSplit2= childCare.upperLimitSplit(targetChanged()); // 看護介護対象人数変更日
		val expect = upperLimitSplit();
		assertThat(upperLimitSplit2.get(0).getLimitDays()).isEqualTo(expect.get(0).getLimitDays());
		assertThat(upperLimitSplit2.get(0).getYmd()).isEqualTo(expect.get(0).getYmd());
	}

	@Test
	// 年月日が一番大きい上限日数分割日の上限日数と比較
	// false：
	// 年月日が一番大きい上限日数分割日の上限日数と求めた上限日数が同じ
	public void testUpperLimitSplit2() {

		// 上限日数を求める
		NumberOfCaregivers numPerson = new NumberOfCaregivers(2); //人数
		val childCare = createChildCare(NursingCategory.Nursing);
		val days = childCare.nursingNumberPerson(numPerson); // 介護看護休暇日数
		assertThat(days).isEqualTo(new ChildCareNurseUpperLimit(10));

		// 上限日数分割日を求める
		val upperLimitSplit2= childCare.upperLimitSplit(targetChanged2()); // 看護介護対象人数変更日
		val expect = upperLimitSplit2();
		assertThat(upperLimitSplit2.get(0).getLimitDays()).isEqualTo(expect.get(0).getLimitDays());
		assertThat(upperLimitSplit2.get(0).getYmd()).isEqualTo(expect.get(0).getYmd());
	}

	// 看護介護対象人数変更日(List)
	private List<ChildCareTargetChanged> targetChanged(){
		return Arrays.asList(
				ChildCareTargetChanged.of(new NumberOfCaregivers(1), ymd(2019, 5, 12)),
				ChildCareTargetChanged.of(new NumberOfCaregivers(0), ymd(2020, 1, 20)),
				ChildCareTargetChanged.of(new NumberOfCaregivers(2), ymd(2021, 4, 1)));
	}

	// 看護介護対象人数変更日(List)
	private List<ChildCareTargetChanged> targetChanged2(){
		return Arrays.asList(
				ChildCareTargetChanged.of(new NumberOfCaregivers(1), ymd(2019, 4, 1)),
				ChildCareTargetChanged.of(new NumberOfCaregivers(0), ymd(2020, 4, 1)),
				ChildCareTargetChanged.of(new NumberOfCaregivers(2), ymd(2021, 4, 1)));
	}

	// 上限日数分割日
	private List<ChildCareNurseUpperLimitSplit> upperLimitSplit() {
		return Arrays.asList(
				ChildCareNurseUpperLimitSplit.of(new ChildCareNurseUpperLimit(5), ymd(2019, 5, 12)),
				ChildCareNurseUpperLimitSplit.of(new ChildCareNurseUpperLimit(0), ymd(2020, 1, 20)),
				ChildCareNurseUpperLimitSplit.of(new ChildCareNurseUpperLimit(10), ymd(2021, 4, 1)));
	}

	// 上限日数分割日
	private List<ChildCareNurseUpperLimitSplit> upperLimitSplit2() {
		return Arrays.asList(
				ChildCareNurseUpperLimitSplit.of(new ChildCareNurseUpperLimit(5), ymd(2019, 4, 1)),
				ChildCareNurseUpperLimitSplit.of(new ChildCareNurseUpperLimit(10), ymd(2020, 4, 1)),
				ChildCareNurseUpperLimitSplit.of(new ChildCareNurseUpperLimit(5), ymd(2021, 6, 1)));
	}

	/**
	 * 5.本年起算日を求める
	 */
	@Test
	// 基準日の年で本年起算日を求める
	public void testThisYearStartMonthDay1() {
		val childCare = createChildCare(new MonthDay(4, 1));

		val startMonthDay = childCare.getThisYearStartMonthDay(ymd(2020, 4, 2));

		// 本年起算日 =｛年：基準日．年、月：起算日．月、日：起算日．日｝
		assertThat(startMonthDay).isEqualTo(ymd(2020, 4, 1));
	}

	@Test
	// 基準日の年に-1年して本年起算日を求める
	public void testThisYearStartMonthDay2() {

		val childCare = createChildCare(new MonthDay(4, 15));

		val startMonthDay = childCare.getThisYearStartMonthDay(ymd(2020, 4, 1)); //基準日

		// 本年起算日 =｛年：基準日．年　-　１、月：起算日．月、日：起算日．日｝
		assertThat(startMonthDay).isEqualTo(ymd(2019, 4, 15));
	}
	 
	@Test
	// Test [9] 対応する日次の勤怠項目を取得する
	public void testGetCorrespondDailyAttendanceItems() {
		// 介護看護区分 = 介護
		NursingLeaveSetting nursingLeaveSetting = NursingLeaveSettingHelper
				.createNursingLeaveSetting(NursingCategory.Nursing);
		List<Integer> lstId = nursingLeaveSetting.getCorrespondDailyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(1126, 1130, 1134, 1138, 1141, 1143);
		
		// 介護看護区分 != 介護
		nursingLeaveSetting = NursingLeaveSettingHelper
				.createNursingLeaveSetting(NursingCategory.ChildNursing);
		lstId = nursingLeaveSetting.getCorrespondDailyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(1125, 1129, 1133, 1137, 1140, 1142);
	}
	
	@Test
	// Test [10] 対応する月次の勤怠項目を取得する
	public void testGetCorrespondMonthlyAttendanceItems() {
		// 介護看護区分 = 介護
		NursingLeaveSetting nursingLeaveSetting = NursingLeaveSettingHelper
				.createNursingLeaveSetting(NursingCategory.Nursing);
		List<Integer> lstId = nursingLeaveSetting.getCorrespondMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(1673, 1674, 2254, 2255, 1279, 1280, 2252, 2253);
		
		// 介護看護区分 != 介護
		nursingLeaveSetting = NursingLeaveSettingHelper
				.createNursingLeaveSetting(NursingCategory.ChildNursing);
		lstId = nursingLeaveSetting.getCorrespondMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(1671, 1672, 2250, 2251, 1275, 1276, 2248, 2249);
	}
	
	@Test
	// Test [11] 利用できない日次の勤怠項目を取得する
	public void testGetDailyAttendanceItems() {
		// 管理区分 = 管理しない && 介護看護区分 = 介護 
		NursingLeaveSetting nursingLeaveSetting = NursingLeaveSettingHelper
				.createNursingLeaveSetting(ManageDistinct.NO, NursingCategory.Nursing);
		List<Integer> lstId = nursingLeaveSetting.getDailyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(1126, 1130, 1134, 1138, 1141, 1143);
		
		// 管理区分 = 管理しない && 介護看護区分 != 介護 
		nursingLeaveSetting = NursingLeaveSettingHelper
				.createNursingLeaveSetting(ManageDistinct.NO, NursingCategory.ChildNursing);
		lstId = nursingLeaveSetting.getDailyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(1125, 1129, 1133, 1137, 1140, 1142);
		
		// 管理区分 = 管理する
		nursingLeaveSetting = NursingLeaveSettingHelper
				.createNursingLeaveSetting(ManageDistinct.YES, NursingCategory.ChildNursing);
		lstId = nursingLeaveSetting.getDailyAttendanceItems();
		assertThat(lstId.isEmpty()).isTrue();
	}
	
	@Test
	// Test [12] 利用できない月次の勤怠項目を取得する
	public void testGetMonthlyAttendanceItems() {
		// 管理区分 = 管理しない && 介護看護区分 = 介護 
		NursingLeaveSetting nursingLeaveSetting = NursingLeaveSettingHelper
				.createNursingLeaveSetting(ManageDistinct.NO, NursingCategory.Nursing);
		List<Integer> lstId = nursingLeaveSetting.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(1673, 1674, 2254, 2255, 1279, 1280, 2252, 2253);
		
		// 管理区分 = 管理しない && 介護看護区分 != 介護 
		nursingLeaveSetting = NursingLeaveSettingHelper
				.createNursingLeaveSetting(ManageDistinct.NO, NursingCategory.ChildNursing);
		lstId = nursingLeaveSetting.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(1671, 1672, 2250, 2251, 1275, 1276, 2248, 2249);
		
		// 管理区分 = 管理する && 介護看護区分 = 介護  && 時間介護看護設定.管理区分 = 管理しない 
		TimeCareNursingSet timeCareNursingSetting = NursingLeaveSettingHelper.createTimeCareNursingSet(ManageDistinct.NO);
		nursingLeaveSetting = NursingLeaveSettingHelper
				.createNursingLeaveSetting(ManageDistinct.YES, NursingCategory.Nursing, timeCareNursingSetting);
		lstId = nursingLeaveSetting.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(1673, 1674, 2254, 2255);
		
		// 管理区分 = 管理する && 介護看護区分 != 介護  && 時間介護看護設定.管理区分 = 管理しない 
		nursingLeaveSetting = NursingLeaveSettingHelper
				.createNursingLeaveSetting(ManageDistinct.YES, NursingCategory.ChildNursing, timeCareNursingSetting);
		lstId = nursingLeaveSetting.getMonthlyAttendanceItems();
		assertThat(lstId).extracting(d -> d).containsExactly(1671, 1672, 2250, 2251);
		
		// 管理区分 = 管理する && 時間介護看護設定.管理区分 = 管理する
		timeCareNursingSetting = NursingLeaveSettingHelper.createTimeCareNursingSet(ManageDistinct.YES);
		nursingLeaveSetting = NursingLeaveSettingHelper
				.createNursingLeaveSetting(ManageDistinct.YES, NursingCategory.ChildNursing, timeCareNursingSetting);
		lstId = nursingLeaveSetting.getMonthlyAttendanceItems();
		assertThat(lstId.isEmpty()).isTrue();
	}
}
