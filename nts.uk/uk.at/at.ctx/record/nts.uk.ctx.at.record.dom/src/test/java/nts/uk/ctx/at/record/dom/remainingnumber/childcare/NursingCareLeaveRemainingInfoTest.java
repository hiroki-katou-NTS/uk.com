package nts.uk.ctx.at.record.dom.remainingnumber.childcare;

import static org.assertj.core.api.Assertions.assertThat;

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
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.UpperLimitSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimitPeriod;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.FamilyInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NumberOfCaregivers;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.shr.com.time.calendar.MonthDay;

@RunWith(JMockit.class)
public class NursingCareLeaveRemainingInfoTest {
	/**
	 * 子の看護・介護休暇基本情報
	 ****** 1.期間ごとの上限日数を求める(= 期間の上限日数を取得する)
	 */
	@Injectable
	private NursingCareLeaveRemainingInfo.RequireM7 require;

	@Test
	// 期間に次回起算日がある
	// 上限日数を求める　－　家族情報を参照（子の看護）
	// ===期間．開始日を分割日に設定
	public void testNextStartMonthDay1() {

		String companyId = "0001";
		String employeeId = "000001";
		DatePeriod period = new DatePeriod(ymd(2020, 3, 1), ymd(2020, 3, 31));
		GeneralDate criteriaDate = ymd(2021, 4, 1); // 基準日

		new Expectations() {
			{
				require.nursingLeaveSetting(companyId, NursingCategory.ChildNursing); // 介護看護休暇設定を取得する
				result = nursingLeaveSet();

				require.familyInfo(employeeId); // 社員IDが一致する家族情報を取得（社員ID）
				result = familyInfo();
			}
		};

		val childCare = nursingInfo(NursingCategory.ChildNursing, UpperLimitSetting.PER_INFO_EVERY_YEAR); // 子の看護・介護休暇基本情報を取得

		val judgePeriod = childCare.childCareNurseUpperLimitPeriod(companyId, employeeId, period, criteriaDate, require);

		// 上限日数期間
		val expect2 = limitPeriod(new DatePeriod(ymd(2020, 3, 2), ymd(2020, 3, 31)), 10);//02
		assertThat(judgePeriod.get(0).getPeriod()).isEqualTo(expect2.getPeriod());
		assertThat(judgePeriod.get(0).getLimitDays()).isEqualTo(expect2.getLimitDays());
	}

	@Test
	// 期間に次回起算日がある
	// 上限日数を求める　－　家族情報を参照（介護）
	// ===期間．開始日を分割日に設定
	public void testNextStartMonthDay2() {

		String companyId = "0001";
		String employeeId = "000001";
//		String familyID = "10f82569-6cfe-4992-9d5c-d9f3dd29b225";
		DatePeriod period = new DatePeriod(ymd(2020, 3, 1), ymd(2020, 3, 31));
		GeneralDate criteriaDate = ymd(2021, 4, 1); // 基準日

		new Expectations() {
			{
				require.nursingLeaveSetting(companyId, NursingCategory.Nursing); // 介護看護休暇設定を取得する
				result = nursingLeaveSet();

				require.familyInfo(employeeId); // 社員IDが一致する家族情報を取得（社員ID）
				result = familyInfo();
			}
		};

		val childCare = nursingInfo(NursingCategory.Nursing, UpperLimitSetting.PER_INFO_EVERY_YEAR); // 子の看護・介護休暇基本情報を取得
		val judgePeriod = childCare.childCareNurseUpperLimitPeriod(companyId, employeeId, period, criteriaDate, require);

		// 上限日数期間
		val expect2 = limitPeriod(new DatePeriod(ymd(2020, 3, 2), ymd(2020, 3, 31)), 10);//02
		assertThat(judgePeriod.get(0).getPeriod()).isEqualTo(expect2.getPeriod());
		assertThat(judgePeriod.get(0).getLimitDays()).isEqualTo(expect2.getLimitDays());
	}

	@Test
	// 期間に次回起算日がある
	// 上限日数を求める　－　個人情報を参照（介護）
	// ===期間．開始日を分割日に設定
	public void testNextStartMonthDay3() {

		String companyId = "0001";
		String employeeId = "000001";
		DatePeriod period = new DatePeriod(ymd(2020, 3, 1), ymd(2020, 3, 31));
		GeneralDate criteriaDate = ymd(2021, 4, 1); // 基準日

		new Expectations() {
			{
				require.nursingLeaveSetting(companyId, NursingCategory.Nursing); // 介護看護休暇設定を取得する
				result = nursingLeaveSet();
			}
		};

		val childCare = nursingInfo(NursingCategory.Nursing, UpperLimitSetting.PER_INFO_EVERY_YEAR); // 子の看護・介護休暇基本情報を取得
		val judgePeriod = childCare.childCareNurseUpperLimitPeriod(companyId, employeeId, period, criteriaDate, require);

		// 上限日数期間
		val expect2 = limitPeriod(new DatePeriod(ymd(2020, 3, 2), ymd(2020, 3, 31)), 5);//02
		assertThat(judgePeriod.get(0).getPeriod()).isEqualTo(expect2.getPeriod());
		assertThat(judgePeriod.get(0).getLimitDays()).isEqualTo(expect2.getLimitDays());

	}
	@Test
	// 期間に次回起算日がない
	// 上限日数を求める　－　家族情報を参照（子の看護）
	// ①期間．開始日から次回起算日の前日の上限日数を取得
	// ②次回起算日から期間．終了日期間の上限日数を取得
	public void testNextStartMonthDay4() {

		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate criteriaDate = ymd(2021, 4, 1); // 基準日
		DatePeriod period = new DatePeriod(ymd(2020, 10, 16), ymd(2020, 11, 15));

		new Expectations() {
			{
				require.nursingLeaveSetting(companyId, NursingCategory.ChildNursing); // 介護看護休暇設定を取得する
				result = nursingLeaveSet();

				require.familyInfo(employeeId); // 社員IDが一致する家族情報を取得（社員ID）
				result = familyInfo2();
			}
		};

		val childCare = nursingInfo(NursingCategory.ChildNursing, UpperLimitSetting.PER_INFO_EVERY_YEAR); // 子の看護・介護休暇基本情報を取得

		val judgePeriod = childCare.childCareNurseUpperLimitPeriod(companyId, employeeId, period, criteriaDate, require);

		// 上限日数期間
		val expect2 = limitPeriod(new DatePeriod(ymd(2020, 10, 17), ymd(2020, 11, 16)), 5);
		assertThat(judgePeriod.get(0).getPeriod()).isEqualTo(expect2.getPeriod());
		assertThat(judgePeriod.get(0).getLimitDays()).isEqualTo(expect2.getLimitDays());

	}

	@Test
	// 期間に次回起算日がない
	// 上限日数を求める　－　家族情報を参照（介護）
	// ①期間．開始日から次回起算日の前日の上限日数を取得
	// ②次回起算日から期間．終了日期間の上限日数を取得
	public void testNextStartMonthDay5() {
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate criteriaDate = ymd(2021, 4, 1); // 基準日
		DatePeriod period = new DatePeriod(ymd(2020, 10, 16), ymd(2020, 11, 15));

		new Expectations() {
			{
				require.nursingLeaveSetting(companyId, NursingCategory.Nursing); // 介護看護休暇設定を取得する
				result = nursingLeaveSet();

				require.familyInfo(employeeId); // 社員IDが一致する家族情報を取得（社員ID）
				result = familyInfo2();
			}
		};

		val childCare = nursingInfo(NursingCategory.Nursing, UpperLimitSetting.PER_INFO_EVERY_YEAR); // 子の看護・介護休暇基本情報を取得

		val judgePeriod = childCare.childCareNurseUpperLimitPeriod(companyId, employeeId, period, criteriaDate, require);

		// 上限日数期間
		val expect2 = limitPeriod(new DatePeriod(ymd(2020, 10, 17), ymd(2020, 11, 16)), 5);
		assertThat(judgePeriod.get(0).getPeriod()).isEqualTo(expect2.getPeriod());
		assertThat(judgePeriod.get(0).getLimitDays()).isEqualTo(expect2.getLimitDays());
	}

	@Test
	// 期間に次回起算日がない
	// 上限日数を求める　－　個人情報を参照
	// ①期間．開始日から次回起算日の前日の上限日数を取得
	// ②次回起算日から期間．終了日期間の上限日数を取得
	public void testNextStartMonthDay6() {
		String companyId = "0001";
		String employeeId = "000001";
		GeneralDate criteriaDate = ymd(2021, 4, 1); // 基準日
		DatePeriod period = new DatePeriod(ymd(2020, 10, 16), ymd(2020, 11, 15));

		new Expectations() {
			{
				require.nursingLeaveSetting(companyId, NursingCategory.ChildNursing); // 介護看護休暇設定を取得する
				result = nursingLeaveSet();
			}
		};

		val childCare = nursingInfo(NursingCategory.ChildNursing, UpperLimitSetting.PER_INFO_EVERY_YEAR); // 子の看護・介護休暇基本情報を取得
		val judgePeriod = childCare.childCareNurseUpperLimitPeriod(companyId, employeeId, period, criteriaDate, require);

		// 上限日数期間
		val expect2 = limitPeriod(new DatePeriod(ymd(2020, 10, 17), ymd(2020, 11, 16)), 5);
		assertThat(judgePeriod.get(0).getPeriod()).isEqualTo(expect2.getPeriod());
		assertThat(judgePeriod.get(0).getLimitDays()).isEqualTo(expect2.getLimitDays());
	}

	// 子の看護・介護休暇基本情報
	private ChildCareLeaveRemainingInfo nursingInfo(NursingCategory leaveType, UpperLimitSetting upperlimitSetting) {
		return ChildCareLeaveRemainingInfo.of(
				"000001",
				leaveType, // 介護看護区分
				true,  // 使用区分
				upperlimitSetting, // 上限設定
				Optional.of(new ChildCareNurseUpperLimit(5)),  // 本年度上限日数
				Optional.of(new ChildCareNurseUpperLimit(10))); // 次年度上限日数
	}

	// 介護看護休暇上限人数設定
	private List<MaxPersonSetting> maxPersonSetting() {
		return Arrays.asList(
				MaxPersonSetting.of(new ChildCareNurseUpperLimit(5),new NumberOfCaregivers(1)),
				MaxPersonSetting.of(new ChildCareNurseUpperLimit(10),new NumberOfCaregivers(2)));
	}

	// 介護看護休暇設定
	private NursingLeaveSetting nursingLeaveSet() {
		return NursingLeaveSetting.of(
				"0001", // companyId
				ManageDistinct.YES, //manageType,
				NursingCategory.ChildNursing, //nursingCategory,
				new MonthDay(4, 1), //startMonthDay,
				maxPersonSetting(), //maxPersonSetting,
				Optional.empty(), //specialHolidayFrame,
				Optional.empty()); // workAbsence
	}

	// 上限日数期間
	private ChildCareNurseUpperLimitPeriod limitPeriod(DatePeriod period, int limitDays) {
		return ChildCareNurseUpperLimitPeriod.of(period, new ChildCareNurseUpperLimit(limitDays));
	}

	// 家族情報(Imported)
	private List<FamilyInfo> familyInfo() {
		return Arrays.asList(
				FamilyInfo.of("10f82569-6cfe-4992-9d5c-d9f3dd29b225", ymd(2014, 4, 2), Optional.empty()),
				FamilyInfo.of("14f82569-6cfe-4992-9d5c-d9f3dd29b225", ymd(2016, 5, 2), Optional.empty()),
				FamilyInfo.of("15f82569-6cfe-4992-9d5c-d9f3dd29b225", ymd(2016, 5, 2), Optional.empty()),
				FamilyInfo.of("16f82569-6cfe-4992-9d5c-d9f3dd29b225", ymd(2019, 6, 2), Optional.empty()));
	}
	// 家族情報(Imported)
	private List<FamilyInfo> familyInfo2() {
		return Arrays.asList(
				FamilyInfo.of("10f82569-6cfe-4992-9d5c-d9f3dd29b225", ymd(2014, 4, 2), Optional.empty()));
	}

//	// 介護対象管理データ
//	private List<CareManagementDate> careManagementDate(){
//		return Arrays.asList(
//				CareManagementDate.of("10f82569-6cfe-4992-9d5c-d9f3dd29b225", false,
//						new DateHistoryItem("10f82569-6cfe-4992-9d5c-d9f3dd29b225",
//						new DatePeriod(ymd(2010,4,16), ymd(2019,5,20)))),
//				CareManagementDate.of("14f82569-6cfe-4992-9d5c-d9f3dd29b225", true,
//						new DateHistoryItem("10f82569-6cfe-4992-9d5c-d9f3dd29b225",
//						new DatePeriod(ymd(2017,5,16), ymd(9999,12,31)))),
//				CareManagementDate.of("15f82569-6cfe-4992-9d5c-d9f3dd29b225", true,
//						new DateHistoryItem("10f82569-6cfe-4992-9d5c-d9f3dd29b225",
//						new DatePeriod(ymd(2021,6,1), ymd(9999,12,31)))));
//	}
//	// 介護対象管理データ
//	private List<CareManagementDate> careManagementDate2(){
//		return Arrays.asList(
//				CareManagementDate.of("10f82569-6cfe-4992-9d5c-d9f3dd29b225", false,
//						new DateHistoryItem("10f82569-6cfe-4992-9d5c-d9f3dd29b225",
//						new DatePeriod(ymd(2010,4,16), ymd(2019,5,20)))),
//				CareManagementDate.of("14f82569-6cfe-4992-9d5c-d9f3dd29b225", true,
//						new DateHistoryItem("10f82569-6cfe-4992-9d5c-d9f3dd29b225",
//						new DatePeriod(ymd(2017,5,16), ymd(9999,12,31)))),
//				CareManagementDate.of("15f82569-6cfe-4992-9d5c-d9f3dd29b225", true,
//						new DateHistoryItem("10f82569-6cfe-4992-9d5c-d9f3dd29b225",
//						new DatePeriod(ymd(2021,6,1), ymd(9999,12,31)))));
//	}
}
