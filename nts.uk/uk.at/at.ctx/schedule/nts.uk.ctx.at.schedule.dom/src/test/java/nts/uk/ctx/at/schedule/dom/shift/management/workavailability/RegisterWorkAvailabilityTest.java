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
				() -> RegisterWorkAvailability.register(require, "cid", "sid", this.datePeriod, workOneDays));
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
				() -> RegisterWorkAvailability.register(require, "cid", "sid", this.datePeriod, workOneDays));
	}
	
	/**
	 * case : 削除対象日が、締切日を過ぎている場合
	 * 期待値: Msg_2050
	 */
	@Test
	public void  work_availability_date_over_deadline_and_exist_true() {
		
		new Expectations() {
			{
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
					
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
			
				setting.isOverDeadline(GeneralDate.ymd(2021, 02, 16));
				result = true;
				
				require.existWorkAvailabilityOfOneDay((String) any, GeneralDate.ymd(2021, 02, 16));
				result = true;
				
			}
		};
		
		NtsAssert.businessException("Msg_2050"
				,	() -> RegisterWorkAvailability.register(require, "cid", "sid", this.datePeriod, Collections.emptyList()));
	}
	
	/**
	 * case : 勤務希望を登録しようとしている日が締切日を過ぎている場合
	 * 期待値: Msg_2050
	 */	
	@Test
	public void  work_availability_date_over_deadline_and_exist_false() {
		val targetDay = GeneralDate.ymd(2021, 02, 16);
		val shiftMasterCode = new ShiftMasterCode("001");
		
		new Expectations() {
			{
				workRequire.shiftMasterIsExist(shiftMasterCode);
				result = true;
				
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
				
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
			
				setting.isOverDeadline(targetDay);
				result = true;
			}
		};
		
		val workOneDays = Arrays.asList(
						Helper.createWorkAvaiOfOneDay(workRequire, targetDay, shiftMasterCode)
					,	Helper.createWorkAvaiOfOneDay(workRequire, GeneralDate.ymd(2021, 02, 17), shiftMasterCode));
		
		NtsAssert.businessException("Msg_2050"
				,	() -> RegisterWorkAvailability.register(require, "cid", "sid", this.datePeriod, workOneDays));
	}
	
	/**
	 * case : 勤務希望を登録しようとしている日が締切日を過ぎていない && 削除対象日が締切日を過ぎていない
	 * 期待値: 登録できます
	 */	
	@Test
	public void work_availability_date_over_deadline_and_contains_false_exit_false() {
		
		new Expectations() {
			{
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
					
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
			
				// 2021/2/16は締切日を過ぎている
				setting.isOverDeadline(GeneralDate.ymd(2021, 02, 16));
				result = true;
				// 2021/2/17,18は締切日を過ぎていない
			}
		};
		
		// 2021/2/17、2021/2/18の勤務希望を登録する
		val workOneDays =  new ArrayList<WorkAvailabilityOfOneDay>(Arrays.asList(
					Helper.createWorkAvaiHoliday(workRequire, GeneralDate.ymd(2021, 02, 17))
				,	Helper.createWorkAvaiHoliday(workRequire, GeneralDate.ymd(2021, 02, 18))
				));
		
		NtsAssert.atomTask(
				() -> RegisterWorkAvailability.register(require, "cid", "sid", this.datePeriod, workOneDays),
				any -> require.deleteAllWorkAvailabilityOfOneDay(any.get(), any.get()),
				any -> require.insertAllWorkAvailabilityOfOneDay(any.get()));
	}
	
	/**
	 * 締め日: 2021/02/15
	 * 期間: (2021/02/01, 2021/02/28)
	 * 対象日: 2021/02/15
	 * シフト表の設定.休日日数の上限日数を超えているか = true
	 * 期待: Msg_2051
	 */
	@Test
	public void testCheckError_throw_Msg_2051_over_number_holiday_true() {
		val targetDate = GeneralDate.ymd(2021, 02, 15);
		val workOneDays = Arrays.asList(Helper.createWorkAvaiHoliday(workRequire, GeneralDate.ymd(2021, 2, 2)));
		
		new Expectations() {
			{			
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
				
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
				
				setting.isOverDeadline(targetDate);
				result = false;
				
				setting.isOverHolidayMaxDays(require, anyString, workOneDays);
				result = true;
			}
		};
		
		NtsAssert.businessException("Msg_2051",
				() -> RegisterWorkAvailability.register(require, "cid", "sid", this.datePeriod, workOneDays));
	}
	/**
	 * 締め日: 2021/02/15
	 * 期間: (2021/02/01, 2021/02/28)
	 * 対象日: 2021/02/14
	 * シフト表の設定.休日日数の上限日数を超えているか = false
	 * 期待: エラーではない
	 */
	@Test
	public void testCheckError_throw_Msg_2051_over_number_holiday_false() {
		val targetDate = GeneralDate.ymd(2021, 02, 14);
		val workOneDays = Arrays.asList(Helper.createWorkAvaiHoliday(workRequire, GeneralDate.ymd(2021, 2, 2)));
		
		new Expectations() {
			{			
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
				
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
			
				setting.isOverDeadline(targetDate);
				result = false;
				
				setting.isOverHolidayMaxDays(require, anyString, workOneDays);
				result = false;
			}
		};
		
		NtsAssert.atomTask(
				() -> RegisterWorkAvailability.register(require, "cid", "sid", this.datePeriod, workOneDays), 
				any -> require.deleteAllWorkAvailabilityOfOneDay(any.get(), any.get()),
				any -> require.insertAllWorkAvailabilityOfOneDay(any.get()));
	}
	
	/**
	 * ケース:	
	 * 期間：		（2021/02/01, 2021/02/28）
	 * 締切日：	2021/03/1
	 * 希望日:	なし
	 * 対象日: 2021/02/14
	 * 期待値：
	 * Inputの期間のデータがDelete実行されることをテスト		
	 * Insertは実行されないケース
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testRegister_work_availability_empty() {
		val targetDate = GeneralDate.ymd(2021, 02, 14);
		val datePeriod = new DatePeriod(GeneralDate.ymd(2021, 2, 1), GeneralDate.ymd(2021, 2, 28));
		val targetRegister = new DatePeriod(GeneralDate.ymd(2021, 2, 1), GeneralDate.ymd(2021, 2, 28));
		val workOneDays = new ArrayList<WorkAvailabilityOfOneDay>();
		new Expectations() {
			{				
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
					
			    shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
			
				setting.isOverDeadline(targetDate);
				result = false;
				
			}
		};
		
		AtomTask persist = RegisterWorkAvailability.register(require, "cid", "sid", datePeriod, workOneDays);

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
