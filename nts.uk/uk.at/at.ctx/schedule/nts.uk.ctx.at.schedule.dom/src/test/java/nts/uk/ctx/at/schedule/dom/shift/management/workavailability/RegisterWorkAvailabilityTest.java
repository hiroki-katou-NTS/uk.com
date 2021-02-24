package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.GetUsingShiftTableRuleOfEmployeeService;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRule;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.WorkAvailabilityRule;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;
@SuppressWarnings("static-access")
@RunWith(JMockit.class)
public class RegisterWorkAvailabilityTest {
	@Injectable
	RegisterWorkAvailability.Require require;	
	
	@Injectable
	WorkAvailability.Require workRequire;
	
	@Mocked 
	private ShiftTableRule shiftRule;
	
	@Mocked 
	private WorkAvailabilityRule setting;
	
	@Mocked
	private GetUsingShiftTableRuleOfEmployeeService service;
	
	private DatePeriod datePeriod;
	
	private List<WorkAvailabilityOfOneDay> workOneDays = new ArrayList<>();
	
	@Before
	public void createDatePeriod(){
		this.datePeriod = new DatePeriod(GeneralDate.ymd(2021, 2, 1), GeneralDate.ymd(2021, 2, 28));
	}
	
	@Before
	public void createWorkAvaiOfOneDays() {
		ShiftMasterCode shiftMasterCode = new ShiftMasterCode("001");
		
		new Expectations() {
			{
				workRequire.shiftMasterIsExist(shiftMasterCode);
				result = true;
			}
		};

		this.workOneDays = Arrays.asList(Helper.createWorkAvaiOfOneDay(workRequire, GeneralDate.ymd(2021, 02, 04), shiftMasterCode));
		
	}
	/**
	 * シフト表のルール = empty
	 * 期待: Msg_2049
	 */
	@Test
	public void testCheckError_throw_Msg_2049() {
		
		new Expectations() {
			{
				service.get(require, (String) any, (GeneralDate) any);
			}
		};

		NtsAssert.businessException("Msg_2049",
				() -> RegisterWorkAvailability.register(require, "sid", this.datePeriod, workOneDays));
	}

	/**
	 * シフト表のルール.勤務希望運用区分 ==　しない
	 * 期待: Msg_2052
	 */
	@Test
	public void testCheckError_throw_Msg_2052() {
		
		new Expectations() {
			{
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);

				shiftRule.getUseWorkAvailabilityAtr();
				result = NotUseAtr.NOT_USE;
			}
		};

		NtsAssert.businessException("Msg_2052",
				() -> RegisterWorkAvailability.register(require, "sid", this.datePeriod, workOneDays));
	}
	
	/**
	 * 期間中に対象日は締切日を過ぎているか = true
	 * 勤務希望リスト中に対象日(希望日)がある
	 * その対象日に勤務希望が存在するか = true
	 * 期待値: Msg_2050
	 */
	@Test
	public void  work_availability_date_over_deadline_and_exist() {
		val shiftMasterCode = new ShiftMasterCode("001");
		val datePeriod = new DatePeriod(GeneralDate.ymd(2021, 2, 1), GeneralDate.ymd(2021, 2, 28));
		
		new Expectations() {
			{
				workRequire.shiftMasterIsExist(shiftMasterCode);
				result = true;
				
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
					
			    shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
			
				setting.isOverDeadline((GeneralDate) any);
				result = true;
				
				require.existWorkAvailabilityOfOneDay((String) any, (GeneralDate) any);
				result = true;
				
			}
		};
		
		val workOneDays = Arrays.asList(
					Helper.createWorkAvaiOfOneDay(workRequire, GeneralDate.ymd(2021, 02, 04), shiftMasterCode)
				,	Helper.createWorkAvaiOfOneDay(workRequire, GeneralDate.ymd(2021, 02, 05), shiftMasterCode));
		
		NtsAssert.businessException("Msg_2050"
				,	() -> RegisterWorkAvailability.register(require, "sid", datePeriod, workOneDays));
	}
	
	/**
	 * 期間 = [2021/02/01 , 2021/02/28]
	 * 期間中に対象日は締切日を過ぎているか = true
	 * 勤務希望が存在するか = false
	 * 期待値: 削除した、追加した
	 */
	@Test
	public void  work_availability_date_over_deadline_and_exist_false() {
		val shiftMasterCode = new ShiftMasterCode("001");
		val datePeriod = new DatePeriod(GeneralDate.ymd(2021, 2, 1), GeneralDate.ymd(2021, 2, 28));
		
		new Expectations() {
			{
				workRequire.shiftMasterIsExist(shiftMasterCode);
				result = true;
				
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
					
			    shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
			
				setting.isOverDeadline((GeneralDate) any);
				result = true;
				
				require.existWorkAvailabilityOfOneDay((String) any, (GeneralDate) any);
				result = false;
			}
		};
		
		val workOneDays = Arrays.asList(
					Helper.createWorkAvaiOfOneDay(workRequire, GeneralDate.ymd(2021, 02, 03), shiftMasterCode));
		
		NtsAssert.atomTask(() -> RegisterWorkAvailability.register(require, "sid", datePeriod, workOneDays),
				any -> require.deleteAllWorkAvailabilityOfOneDay("sid", datePeriod),
				any -> require.insertAllWorkAvailabilityOfOneDay(workOneDays));
	}
	
	/**
	 * シフト表の設定.休日日数の上限日数を超えているか = false
	 * 期待: エラーではない
	 */
	@Test
	public void testCheckError_throw_Msg_2051_over_number_holiday_false() {
		
		val workOneDays = Arrays.asList(Helper.createWorkAvaiHoliday(workRequire, GeneralDate.ymd(2021, 2, 2)));
		
		new Expectations() {
			{			
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
				
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
			
				setting.isOverDeadline((GeneralDate) any);
				result = false;
				
				setting.isOverHolidayMaxDays(require, workOneDays);
				result = false;
			}
		};
		
		NtsAssert.atomTask(
				() -> RegisterWorkAvailability.register(require, "sid", this.datePeriod, workOneDays), 
				any -> require.deleteAllWorkAvailabilityOfOneDay(any.get(), any.get()),
				any -> require.insertAllWorkAvailabilityOfOneDay(any.get()));
	}
	
	/**
	 * ケース:	締切日 ＞ 期間.開始日 && 希望日 なし && 締切日 ＞ 期間.終了日
	 * 期間：		（2021/02/01, 2021/02/28）
	 * 締切日：	2021/03/1
	 * 希望日:	なし
	 * 期待値：　	登録対象の期間: (2021/02/1, 2021/02/28)
	 * Inputの期間開始日～締切日のデータがDelete実行されることをテスト		
	 * Insertは実行されないケース
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testRegister_deadline_more_than_endDate() {
		val datePeriod = new DatePeriod(GeneralDate.ymd(2021, 2, 1), GeneralDate.ymd(2021, 2, 28));
		val targetRegister = new DatePeriod(GeneralDate.ymd(2021, 2, 1), GeneralDate.ymd(2021, 2, 28));
		val workOneDays = new ArrayList<WorkAvailabilityOfOneDay>();
		new Expectations() {
			{				
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
					
			    shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
			
				setting.isOverDeadline((GeneralDate) any);
				result = false;
				
			}
		};
		
		AtomTask persist = RegisterWorkAvailability.register(require, "sid", datePeriod, workOneDays);

		new Verifications() {{
			require.deleteAllWorkAvailabilityOfOneDay("sid", targetRegister);
			times = 0;  // まだ呼ばれていない
			
			require.insertAllWorkAvailabilityOfOneDay((List<WorkAvailabilityOfOneDay>)any);
			times = 0;
		}};

		persist.run();  // AtomTaskを実行

		new Verifications() {{
			require.deleteAllWorkAvailabilityOfOneDay("sid", targetRegister);
			times = 1;  // 1回だけ呼ばれた
			
			require.insertAllWorkAvailabilityOfOneDay((List<WorkAvailabilityOfOneDay>)any);
			times = 0;  //insertは実行されない
		}};
	}
	
	public static class Helper {
		/**
		 * 希望＝休日
		 *  一日分の勤務希望を作る
		 * @param require
		 * @param ymd 希望日
		 * @return
		 */
		public static WorkAvailabilityOfOneDay createWorkAvaiHoliday(@Injectable WorkAvailability.Require require, GeneralDate ymd) {
			return WorkAvailabilityOfOneDay.create(require, "sid", ymd, new WorkAvailabilityMemo("memo"),
					AssignmentMethod.HOLIDAY, Collections.emptyList(), Collections.emptyList());
		}
		
		
		/**
		 * 希望＝シフト
		 * 一日分の勤務希望を作る
		 * @param require
		 * @param ymd 希望日
		 * @param shiftMasterCode　シフトコード
		 * @return
		 */
		public static WorkAvailabilityOfOneDay createWorkAvaiOfOneDay(
				@Injectable WorkAvailability.Require require, GeneralDate ymd, ShiftMasterCode shiftMasterCode) {
			return  WorkAvailabilityOfOneDay.create(require, "sid", ymd, new WorkAvailabilityMemo("memo"),
					AssignmentMethod.SHIFT, Arrays.asList(shiftMasterCode), Collections.emptyList());
		}
	}
}
