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
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.AggregateChildCareNurseWork;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareCheckOverUsedNumberWork;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareShortageRemainingNumberWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareNurseManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.UpperLimitSetting;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.DayNumberOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.TimeOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse.ChildCareNurseRemainingNumber;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimitPeriod;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NumberOfCaregivers;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.shr.com.time.calendar.MonthDay;

/**
 * 超過確認用使用数
 ****** 1.残数不足数を計算
 ****** 2.子の看護介護残数が残っているか
 ****** 3.上限超過チェック：checkOverUsedNumberWork
 */
@RunWith(JMockit.class)
public class ChildCareCheckOverUsedNumberWorkTest {
	/**
	 * 1.残数不足数を計算
	 */
	@Injectable
	private AggregateChildCareNurseWork.RequireM4 require;

	@Injectable
	private ChildCareCheckOverUsedNumberWork.RequireM3 requireM3;

	private NursingCategory category = NursingCategory.ChildNursing;

	/**
	 * 残数不足数を求める
	 */
	@Test
	// 翌年の場合
	// 超過確認用使用数．使用数．日数　＝　0
	// 超過確認用使用数．使用数．時間　＝　0
	public void testShortageRemNumNextYear() {

		String companyId = "0001";
		String employeeId = "000001";
		DatePeriod period = new DatePeriod(ymd(2020, 10, 16), ymd(2020, 11, 15));
		GeneralDate criteriaDate = ymd(2020, 10, 16); //基準日
		TempChildCareNurseManagement interimDate = new TempChildCareNurseManagement("000001", employeeId, criteriaDate, null, null);

		new Expectations() {
			{
				require.employeeInfo(employeeId, NursingCategory.ChildNursing); // 子の看護・介護休暇基本情報を取得する（社員ID）
				result = nursingInfo(NursingCategory.ChildNursing, UpperLimitSetting.PER_INFO_EVERY_YEAR);

//				require.contractTime(companyId, employeeId, criteriaDate); // 年休の契約時間を取得する（会社ID、社員ID、基準日）
//				result = new LaborContractTime(480); //8時間

				require.nursingLeaveSetting(companyId, NursingCategory.ChildNursing); // 介護看護休暇設定を取得する（会社ID、介護看護区分）
				result = nursingLeaveSet(NursingCategory.ChildNursing);
			}
		};

		// 子の看護介護残数が上限超過していないか
		// trueの場合：子の看護介護残数不足数．使用可能数＝暫定管理データの使用数、残数不足数←0　もセットする
		val childCare2 = checkOverUsedNumberWork(0.0, 0); //超過確認用使用数
		val shortRemNum = childCare2.calcShortageRemainingNumber(companyId, employeeId, period, criteriaDate, interimDate, category, require);

		val expect = shortageWork(0, 0, 0, null); //期待値：子の看護介護残数不足数
		assertThat(shortRemNum.getShortageRemNum().getRemainDay()).isEqualTo(expect.getShortageRemNum().getRemainDay());
		assertThat(shortRemNum.getShortageRemNum().getRemainTimes()).isEqualTo(expect.getShortageRemNum().getRemainTimes());
		assertThat(shortRemNum.getAvailable().getUsedDay()).isEqualTo(expect.getAvailable().getUsedDay());
		assertThat(shortRemNum.getAvailable().getUsedTimes()).isEqualTo(expect.getAvailable().getUsedTimes());

	}

	@Test
	// 本年の場合
	// 超過確認用使用数．使用数．日数　＝　INPUT．月初時点の使用数．使用日数
	// 超過確認用使用数．使用数．時間　＝　INPUT．月初時点の使用数．使用時間
	public void testShortageRemNumThisYear() {
		String companyId = "0001";
		String employeeId = "000001";
		DatePeriod period = new DatePeriod(ymd(2020, 10, 16),ymd(2020, 11, 15));
		GeneralDate criteriaDate = ymd(2020, 10, 16); //基準日
		TempChildCareNurseManagement interimDate = new TempChildCareNurseManagement("000001", employeeId, criteriaDate, null, null);


		new Expectations() {
			{
				require.employeeInfo(employeeId, NursingCategory.ChildNursing); // 子の看護・介護休暇基本情報を取得する（社員ID）
				result = nursingInfo(NursingCategory.ChildNursing, UpperLimitSetting.PER_INFO_EVERY_YEAR);

//				require.contractTime(companyId, employeeId, criteriaDate); // 年休の契約時間を取得する（会社ID、社員ID、基準日）
//				result = new LaborContractTime(480); //8時間

				require.nursingLeaveSetting(companyId, NursingCategory.ChildNursing); // 介護看護休暇設定を取得する（会社ID、介護看護区分）
				result = nursingLeaveSet(NursingCategory.ChildNursing);
			}
		};

		val childCare2 = checkOverUsedNumberWork(2.5, 0);//超過確認用使用数（日数、時間）
		val shortRemNum = childCare2.calcShortageRemainingNumber(companyId, employeeId, period, criteriaDate, interimDate, category, require);

		val expect = shortageWork(0, 0, 0, 0); //期待値：子の看護介護残数不足数
		assertThat(shortRemNum.getShortageRemNum().getRemainDay()).isEqualTo(expect.getShortageRemNum().getRemainDay());
		assertThat(shortRemNum.getShortageRemNum().getRemainTimes()).isEqualTo(expect.getShortageRemNum().getRemainTimes());
		assertThat(shortRemNum.getAvailable().getUsedDay()).isEqualTo(expect.getAvailable().getUsedDay());
		assertThat(shortRemNum.getAvailable().getUsedTimes()).isEqualTo(Optional.empty());
	}

	// 子の看護介護残数
	private ChildCareNurseRemainingNumber remainingNumber(double useDay, Integer usedTimes) {
		return ChildCareNurseRemainingNumber.of(
						new DayNumberOfRemain(useDay),
						usedTimes == null ? Optional.empty() : Optional.of(new TimeOfRemain(usedTimes)));
	}

	// 超過確認用使用数
	private ChildCareCheckOverUsedNumberWork checkOverUsedNumberWork(double useDay, Integer usedTimes) {
		return ChildCareCheckOverUsedNumberWork.of(
				usedNumber(useDay, usedTimes));
	}

	// 子の看護介護使用数
	private ChildCareNurseUsedNumber usedNumber(double useDay, Integer usedTimes) {
		return ChildCareNurseUsedNumber.of(new DayNumberOfUse(useDay),
						usedTimes == null ? Optional.empty() : Optional.of(new TimeOfUse(usedTimes)));
	}

	// 子の看護・介護休暇基本情報
	private Optional<ChildCareLeaveRemainingInfo> nursingInfo(NursingCategory leaveType, UpperLimitSetting upperlimitSetting) {
		return Optional.of(ChildCareLeaveRemainingInfo.of(
				"000001",
				leaveType, // 介護看護区分
				true,  // 使用区分
				upperlimitSetting, // 上限設定
				Optional.of(new ChildCareNurseUpperLimit(5)),  // 本年度上限日数
				Optional.of(new ChildCareNurseUpperLimit(10)))); // 次年度上限日数
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
				Optional.empty()); // workAbsence
	}

	// 子の看護介護残数不足数
	private ChildCareShortageRemainingNumberWork shortageWork(double remDay, Integer remTimes, double useDay, Integer usedTimes) {
		return ChildCareShortageRemainingNumberWork.of(
				remainingNumber(remDay, remTimes),
				usedNumber(useDay, usedTimes));
	}

	/**
	 * 2.子の看護介護残数が残っているか(=子の看護介護残数が上限超過していないか)
	 */
	@Test
	// Trueの場合：上限超過していない
	public void checkRemainingNumber() {

		String companyId = "0001";
		String employeeId = "000001";
		DatePeriod period = new DatePeriod(ymd(2020, 10, 16),ymd(2020, 11, 15));
		ChildCareNurseUpperLimit limitDays = new ChildCareNurseUpperLimit(10);
		GeneralDate criteriaDate = ymd(2020, 10, 16); //基準日

		val childCare = checkOverUsedNumberWork(0.0, 0);
		val shortRemNum = childCare.checkRemainingNumber(companyId, employeeId, upperLimitPeriod(period, limitDays), criteriaDate, requireM3);

		assertThat(shortRemNum).isTrue();

		val childCare2 = remNum(0, 0);
		val remNum = childCare2.checkOverUpperLimit();

		assertThat(remNum).isTrue();
	}
	@Test
	// Falseの場合：上限超過あり
	public void checkRemainingNumber2() {

		String companyId = "0001";
		String employeeId = "000001";
		DatePeriod period = new DatePeriod(ymd(2020, 10, 16),ymd(2020, 11, 15));
		ChildCareNurseUpperLimit limitDays = new ChildCareNurseUpperLimit(10);
		GeneralDate criteriaDate = ymd(2020, 10, 16); //基準日

		val childCare = checkOverUsedNumberWork(11.0, 180); // 超過確認用使用数(日数、時間)
		val shortRemNum = childCare.checkRemainingNumber(companyId, employeeId, upperLimitPeriod(period, limitDays), criteriaDate, requireM3);

		assertThat(shortRemNum).isFalse();

		val childCare2 = remNum(-11.0, 180);// 超過確認用使用数(日数、時間)
		val remNum = childCare2.checkOverUpperLimit();

		assertThat(remNum).isFalse();
	}
	// 上限日数期間
	private ChildCareNurseUpperLimitPeriod upperLimitPeriod (DatePeriod period, ChildCareNurseUpperLimit limitDays) {
		return ChildCareNurseUpperLimitPeriod.of(
				period,
				limitDays);
	}
	// 子の看護介護残数
	private ChildCareNurseRemainingNumber remNum(double usedDays, int usedTime) {
		return ChildCareNurseRemainingNumber.of(new DayNumberOfRemain(usedDays), Optional.of(new TimeOfRemain(usedTime)));
	}

}
