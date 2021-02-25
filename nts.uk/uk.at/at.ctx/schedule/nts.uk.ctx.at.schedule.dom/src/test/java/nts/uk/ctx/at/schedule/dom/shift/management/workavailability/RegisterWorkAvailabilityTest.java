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
	 * かりに締め日 = 2021/02/15だとしたら
	 * 期間 = (2021/02/01, 2021/02/28)
	 * 勤務希望リスト = [2021/02/16, 2021/02/17] -> 勤務希望が存在する
	 * 対象日 = 2021/02/16 -> 期間中に締切日を過ぎているか = true 
	 * 期待値: Msg_2050
	 */
	@Test
	public void  work_availability_date_over_deadline_and_exist_true() {
		val targetDay = GeneralDate.ymd(2021, 02, 16);
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
			
				setting.isOverDeadline(targetDay);
				result = true;
				
				require.existWorkAvailabilityOfOneDay((String) any, targetDay);
				result = true;
				
			}
		};
		
		val workOneDays = Arrays.asList(
					Helper.createWorkAvaiOfOneDay(workRequire, GeneralDate.ymd(2021, 02, 16), shiftMasterCode)
				,	Helper.createWorkAvaiOfOneDay(workRequire, GeneralDate.ymd(2021, 02, 17), shiftMasterCode));
		
		NtsAssert.businessException("Msg_2050"
				,	() -> RegisterWorkAvailability.register(require, "sid", datePeriod, workOneDays));
	}
	
	/**
	 * かりに締め日 = 2021/02/15だとしたら
	 * 期間 = (2021/02/01, 2021/02/28)
	 * 勤務希望リスト = [2021/02/16, 2021/02/17] -> 勤務希望が存在しない
	 * 対象日 = 2021/02/16 -> 期間中に締切日を過ぎているか = true 
	 * 期待値: 削除した、追加した
	 */	
	@Test
	public void  work_availability_date_over_deadline_and_exist_false() {
		val targetDay = GeneralDate.ymd(2021, 02, 16);
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
			
				setting.isOverDeadline(targetDay);
				result = true;
				
				require.existWorkAvailabilityOfOneDay((String) any, targetDay);
				result = false;
			}
		};
		
		val workOneDays = Arrays.asList(
						Helper.createWorkAvaiOfOneDay(workRequire, GeneralDate.ymd(2021, 02, 16), shiftMasterCode)
					,	Helper.createWorkAvaiOfOneDay(workRequire, GeneralDate.ymd(2021, 02, 17), shiftMasterCode));
		
		NtsAssert.atomTask(() -> RegisterWorkAvailability.register(require, "sid", datePeriod, workOneDays),
				any -> require.deleteAllWorkAvailabilityOfOneDay("sid", datePeriod),
				any -> require.insertAllWorkAvailabilityOfOneDay(workOneDays));
	}
	
	/**
	 * 締め日 = 2021/02/15
	 * 期間 = (2021/02/01, 2021/02/28)
	 * 勤務希望リスト = empty
	 * 対象日 = 2021/03/01
	 * -> 期間中に締切日を過ぎているか = true 
	 * -> 日分の勤務希望リスト .contains(対象日) = false
	 * 期待値: 削除した
	 */		
	@Test
	public void  work_availability_date_over_deadline_and_contains_false_1() {
		val targetDay = GeneralDate.ymd(2021, 02, 28);
		val workOneDays = new ArrayList<WorkAvailabilityOfOneDay>();
		val datePeriod = new DatePeriod(GeneralDate.ymd(2021, 2, 1), GeneralDate.ymd(2021, 2, 28));
		
		new Expectations() {
			{
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
					
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
			
				setting.isOverDeadline(targetDay);
				result = true;

			}
		};
		
		AtomTask persist = RegisterWorkAvailability.register(require, "sid", datePeriod, workOneDays);

		new Verifications() {{
			require.deleteAllWorkAvailabilityOfOneDay("sid", datePeriod);
			times = 0;  // まだ呼ばれていない
			
			require.insertAllWorkAvailabilityOfOneDay(workOneDays);
			times = 0;
		}};

		persist.run();  // AtomTaskを実行

		new Verifications() {{
			require.deleteAllWorkAvailabilityOfOneDay("sid", datePeriod);
			times = 1;  // 1回だけ呼ばれた
			
			require.insertAllWorkAvailabilityOfOneDay(workOneDays);
			times = 0;  //insertは実行されない
		}};
	}
	
	/**
	 * かりに締め日 = 2021/02/15だとしたら
	 * 期間 = (2021/02/01, 2021/02/28)
	 * 勤務希望リスト = [2021/03/01, 2021/03/02]
	 * 対象日 = 2021/02/16
	 * -> 期間中に締切日を過ぎているか = true 
	 * -> 日分の勤務希望リスト .contains(対象日) = false
	 * 期待値: 削除した, 追加した
	 */	
	@Test
	public void  work_availability_date_over_deadline_and_contains_false_2() {
		val targetDay = GeneralDate.ymd(2021, 02, 16);
		val datePeriod = new DatePeriod(GeneralDate.ymd(2021, 2, 1), GeneralDate.ymd(2021, 2, 28));
		
		new Expectations() {
			{
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
					
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
			
				setting.isOverDeadline(targetDay);
				result = true;

			}
		};
		
		List<WorkAvailabilityOfOneDay> workOneDays =  new ArrayList<WorkAvailabilityOfOneDay>(Arrays.asList(
					Helper.createWorkAvaiHoliday(workRequire, GeneralDate.ymd(2021, 03, 01))
				,	Helper.createWorkAvaiHoliday(workRequire, GeneralDate.ymd(2021, 03, 02))
				));
		
		NtsAssert.atomTask(() -> RegisterWorkAvailability.register(require, "sid", datePeriod, workOneDays),
				any -> require.deleteAllWorkAvailabilityOfOneDay("sid", datePeriod),
				any -> require.insertAllWorkAvailabilityOfOneDay(workOneDays));

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
				
				setting.isOverHolidayMaxDays(require, workOneDays);
				result = true;
			}
		};
		
		NtsAssert.businessException("Msg_2051",
				() -> RegisterWorkAvailability.register(require, "sid", this.datePeriod, workOneDays));
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
