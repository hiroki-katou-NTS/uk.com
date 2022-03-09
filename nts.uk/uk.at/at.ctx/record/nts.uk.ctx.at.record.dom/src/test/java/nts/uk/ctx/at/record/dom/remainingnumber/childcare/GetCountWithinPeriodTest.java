package nts.uk.ctx.at.record.dom.remainingnumber.childcare;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseTargetCountWork;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareTargetChanged;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NumberOfCaregivers;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.shr.com.time.calendar.MonthDay;

@RunWith(JMockit.class)
public class GetCountWithinPeriodTest {

	@Injectable
	private NursingLeaveSetting.RequireM7 require;
	/**
	 * 期間の対象人数を求める
	 *
	 */
	@Test
	// 期間に次回起算日が含まれている
	// 本年と次回の2件作成
	// 1.変更日←パラメータ「期間．開始日」
	//    人数←本年と翌年の対象人数．本年対象人数
	// 2.変更日←次回起算日
	//    人数←本年と次回の対象人数．次回対象人数
	public void testGetCountWithinPeriod1() {

		DatePeriod period = new DatePeriod(GeneralDate.ymd(2020, 10, 16),GeneralDate.ymd(2020, 11, 15));
		val targetCountWork = targetCountWork(1, 2); // 本年と次回の対象人数
		val nextStartMonthDay = GeneralDate.ymd(2020, 11, 15); //次回起算日

		val childCare = createChildCare();
		val count = childCare.getCountWithinPeriod(period, targetCountWork, nextStartMonthDay);

		assertThat(count).hasSize(2);
		// 本年
		val expect = targetChanged(2,GeneralDate.ymd(2020, 10, 16));
		assertThat(count.get(0).getNumPerson()).isEqualTo(expect.getNumPerson());
		assertThat(count.get(0).getYmd()).isEqualTo(expect.getYmd());

		// 次回
		val expect2 = targetChanged(1,GeneralDate.ymd(2020, 11, 15));
		assertThat(count.get(1).getNumPerson()).isEqualTo(expect2.getNumPerson());
		assertThat(count.get(1).getYmd()).isEqualTo(expect2.getYmd());

	}

	@Test
	// 期間に次回起算日が含まれていない
	// 変更日←パラメータ「期間．開始日」
	// 人数←本年と次回の対象人数．本年対象人数
	public void testGetCountWithinPeriod2() {

		DatePeriod period = new DatePeriod(GeneralDate.ymd(2020, 10, 16),GeneralDate.ymd(2020, 11, 15));
		val targetCountWork = targetCountWork(1, 2); // 本年と次回の対象人数
		val nextStartMonthDay = GeneralDate.ymd(2020, 11, 16); //次回起算日

		val childCare = createChildCare();
		val count = childCare.getCountWithinPeriod(period, targetCountWork, nextStartMonthDay);

		assertThat(count).hasSize(1);
		// 本年
		val expect = targetChanged(2,GeneralDate.ymd(2020, 10, 16));
		assertThat(count.get(0).getNumPerson()).isEqualTo(expect.getNumPerson());
		assertThat(count.get(0).getYmd()).isEqualTo(expect.getYmd());
	}

	// 介護看護休暇設定
	private NursingLeaveSetting createChildCare() {
		return NursingLeaveSetting.of(
				"0001", // companyId
				ManageDistinct.YES, //manageType,
				NursingCategory.ChildNursing, //nursingCategory,
				new MonthDay(4, 1), //startMonthDay,
				new ArrayList<>(), //maxPersonSetting,
				Optional.empty(), //specialHolidayFrame,
				Optional.empty(), // workAbsence
				new TimeVacationDigestUnit(ManageDistinct.YES, TimeDigestiveUnit.OneHour));
	}

	// 本年と次回の対象人数
	private ChildCareNurseTargetCountWork targetCountWork(int nextTargetCount, int thisTargetCount) {
		return ChildCareNurseTargetCountWork.of(
				new NumberOfCaregivers(nextTargetCount), // 次回対象人数
				new NumberOfCaregivers(thisTargetCount)); // 本年対象人数
	}

	// 看護介護対象人数変更日
	private ChildCareTargetChanged targetChanged(int number, GeneralDate ymd) {
		return ChildCareTargetChanged.of(new NumberOfCaregivers(number), ymd);
	}
}
