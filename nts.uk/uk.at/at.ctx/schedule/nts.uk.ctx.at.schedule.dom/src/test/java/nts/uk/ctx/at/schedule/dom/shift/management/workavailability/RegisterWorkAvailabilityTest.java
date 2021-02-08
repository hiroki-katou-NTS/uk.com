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
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.DeadlineAndPeriodOfWorkAvailability;
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

		this.workOneDays = Helper.createWorkAvaiOfOneDays(workRequire, GeneralDate.ymd(2021, 02, 04), shiftMasterCode);
		
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
	 * ケース1：	締め切り < 期間.開始日
	 * 期間 =		[2021/2/1, 2021/2/28]
	 * 締切日=	2021/1/31
	 * 期待:	Msg_2050
	 */
	@Test
	public void testCheckError_throw_Msg_2050_deadline_less_than_startDate(@Injectable DatePeriod datePeriod) {
		
		new Expectations() {
			{			
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
				
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
				
				setting.getCorrespondingDeadlineAndPeriod((GeneralDate) any);
				result = new DeadlineAndPeriodOfWorkAvailability(GeneralDate.ymd(2021, 1, 31), datePeriod);
			}
		};

		NtsAssert.businessException("Msg_2050",
				() -> RegisterWorkAvailability.register(require, "sid", this.datePeriod, workOneDays));
	}
	
	/**
	 * ケース2：	締め切り = 期間.開始日
	 * 期間 		= (2021/2/1, 2021/2/28)
	 * 締め切り	= 2021/2/1
	 * 期待:		エラーではない
	 */
	@Test
	public void deadline_equals_startDate() {
		
		val shiftMasterCode = new ShiftMasterCode("001");
		
		new Expectations() {
			{			
				workRequire.shiftMasterIsExist(shiftMasterCode);
				result = true;
				
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
				
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
				
				setting.getCorrespondingDeadlineAndPeriod((GeneralDate) any);
				result = new DeadlineAndPeriodOfWorkAvailability(GeneralDate.ymd(2021, 2, 1), datePeriod);
			}
		};

		val workOneDays = Helper.createWorkAvaiOfOneDays(workRequire, GeneralDate.ymd(2021, 02, 01), shiftMasterCode);
		
		NtsAssert.atomTask(
				() -> RegisterWorkAvailability.register(require, "sid", this.datePeriod, workOneDays), 
				any -> require.deleteAllWorkAvailabilityOfOneDay(any.get(), any.get()),
				any -> require.insertAllWorkAvailabilityOfOneDay(any.get()));
	}
	
	/**
	 * ケース3：	締め切り > 期間.開始日
	 * 期間 		= [2021/2/1, 2021/2/28]
	 * 締め切り	= 2021/2/2
	 * 期待: 	エラーではない
	 */
	@Test
	public void deadline_more_than_startDate() {
		
		val workOneDays = Helper.createWorkAvaiHolidays(workRequire, GeneralDate.ymd(2021, 2, 2));
		
		new Expectations() {
			{							
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
				
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
				
				setting.getCorrespondingDeadlineAndPeriod((GeneralDate) any);
				result = new DeadlineAndPeriodOfWorkAvailability(GeneralDate.ymd(2021, 2, 2), datePeriod);
				
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
	 * ケース:	希望日 < 締め切り
	 * 期間 		= [2021/2/1, 2021/2/28]
	 * 締め切り	= 2021/2/2
	 * 希望日	= 2021/2/1 
	 * 期待		エラーではない
	 */
	@Test
	public void test_aspiration_date_less_than_deadline() {
		
		val workOneDays = Helper.createWorkAvaiHolidays(workRequire, GeneralDate.ymd(2021, 02, 01));
		
		new Expectations() {{
			service.get(require, (String) any, (GeneralDate) any);
			result = Optional.of(shiftRule);
				
		    shiftRule.getShiftTableSetting();
			result = Optional.of(setting);
		
			setting.getCorrespondingDeadlineAndPeriod((GeneralDate) any);
			result = new DeadlineAndPeriodOfWorkAvailability(GeneralDate.ymd(2021, 2, 2), datePeriod);
		}};

		NtsAssert.atomTask(
				() -> RegisterWorkAvailability.register(require, "sid", this.datePeriod, workOneDays), 
				any -> require.deleteAllWorkAvailabilityOfOneDay(any.get(), any.get()),
				any -> require.insertAllWorkAvailabilityOfOneDay(any.get()));
	}	
	
	/**
	 * ケース:	希望日 = 締め切り
	 * 期間 		= [2021/2/1, 2021/2/28]
	 * 締め切り	= 2021/2/1 
	 * 希望日	= 2021/2/1 
	 * 期待		エラーではない
	 */
	@Test
	public void test_aspiration_date_equals_deadline() {
		
		val workOneDays = Helper.createWorkAvaiHolidays(workRequire, GeneralDate.ymd(2021, 2, 1));
		
		new Expectations() {
			{			
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
				
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
				
				setting.getCorrespondingDeadlineAndPeriod((GeneralDate) any);
				result = new DeadlineAndPeriodOfWorkAvailability(GeneralDate.ymd(2021, 2, 1), datePeriod);
			}
		};
		
		NtsAssert.atomTask(
				() -> RegisterWorkAvailability.register(require, "sid", this.datePeriod, workOneDays), 
				any -> require.deleteAllWorkAvailabilityOfOneDay(any.get(), any.get()),
				any -> require.insertAllWorkAvailabilityOfOneDay(any.get()));
	}
	
	/**
	 * ケース		希望日 > 締め切り
	 * 期間		= [2021/2/1, 2021/2/28]
	 * 締め切り	= 2021/2/2
	 * 希望日	= 2021/2/3
	 * 期待		Msg_2050
	 */
	@Test
	public void test_aspiration_date_more_than_deadline() {
		
		val shiftMasterCode = new ShiftMasterCode("001");
		
		new Expectations() {
			{			
				workRequire.shiftMasterIsExist(shiftMasterCode);
				result = true;
				
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
				
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
				
				setting.getCorrespondingDeadlineAndPeriod((GeneralDate) any);
				result = new DeadlineAndPeriodOfWorkAvailability(GeneralDate.ymd(2021, 2, 2), datePeriod);
			}
		};
		
		val workOneDays = Helper.createWorkAvaiOfOneDays(workRequire, GeneralDate.ymd(2021, 2, 3), shiftMasterCode);
		
		NtsAssert.businessException("Msg_2050",
				() -> RegisterWorkAvailability.register(require, "sid", this.datePeriod, workOneDays));
	}
	
	/**
	 * シフト表の設定.休日日数の上限日数を超えているか = true
	 * 期待: Msg_2051
	 */
	@Test
	public void testCheckError_throw_Msg_2051_over_number_holiday_true() {
		
		val workOneDays = Helper.createWorkAvaiHolidays(workRequire, GeneralDate.ymd(2021, 2, 2));
		
		new Expectations() {
			{			
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
				
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
				
				setting.getCorrespondingDeadlineAndPeriod((GeneralDate) any);
				result = new DeadlineAndPeriodOfWorkAvailability(GeneralDate.ymd(2021, 2, 2), datePeriod);
				
				setting.isOverHolidayMaxDays(require, workOneDays);
				result = true;
			}
		};
		
		NtsAssert.businessException("Msg_2051",
				() -> RegisterWorkAvailability.register(require, "sid", this.datePeriod, workOneDays));
	}
	
	/**
	 * シフト表の設定.休日日数の上限日数を超えているか = false
	 * 期待: エラーではない
	 */
	@Test
	public void testCheckError_throw_Msg_2051_over_number_holiday_false() {
		
		val workOneDays = Helper.createWorkAvaiHolidays(workRequire, GeneralDate.ymd(2021, 2, 2));
		
		new Expectations() {
			{			
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
				
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
				
				setting.getCorrespondingDeadlineAndPeriod((GeneralDate) any);
				result = new DeadlineAndPeriodOfWorkAvailability(GeneralDate.ymd(2021, 2, 2), datePeriod);
			
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
	 * input:
	 * ケース：			締切日 < 期間.終了日
	 * 期間			= [2021/2/1, 2021/2/28]
	 * 締め切り		= 2021/2/27
	 * 登録対象の期間	= [2021/2/1, 2021/2/2]
	 */
	@Test
	public void testRegister_case_delete_endDate_more_than_deadline() {
		
		val targetRegister = new DatePeriod(GeneralDate.ymd(2021, 2, 1), GeneralDate.ymd(2021, 2, 27));
		
		new Expectations() {
			{				
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
					
			    shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
			
				setting.getCorrespondingDeadlineAndPeriod((GeneralDate) any);
				result = new DeadlineAndPeriodOfWorkAvailability(GeneralDate.ymd(2021, 2, 27), datePeriod);
			}
		};
		
		NtsAssert.atomTask(
				() -> RegisterWorkAvailability.register(require, "sid", this.datePeriod, Collections.emptyList()), 
				any -> require.deleteAllWorkAvailabilityOfOneDay("sid", targetRegister));
	}
	
	/**
	 * input:
	 * 締切日 		= 期間.終了日
	 * 期待値：		登録対象の期間 :期間.開始日, 締切日
	 * 期間			= [2021/2/1, 2021/2/28]
	 * 締め切り		= 2021/2/28
	 * 登録対象の期間	= [2021/2/1, 2021/2/28]
	 * 登録する: delete
	 */
	@Test
	public void testRegister_case_delete_endDate_equals_deadline() {
		
		val targetRegister = new DatePeriod(GeneralDate.ymd(2021, 2, 1), GeneralDate.ymd(2021, 2, 28));
		
		new Expectations() {
			{				
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
					
			    shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
			
				setting.getCorrespondingDeadlineAndPeriod((GeneralDate) any);
				result = new DeadlineAndPeriodOfWorkAvailability(GeneralDate.ymd(2021, 2, 28), datePeriod);
			}
		};
		
		NtsAssert.atomTask(
				() -> RegisterWorkAvailability.register(require, "sid", this.datePeriod, Collections.emptyList()), 
				any -> require.deleteAllWorkAvailabilityOfOneDay("sid", targetRegister));
	}
	/**
	 * input:
	 * 締切日 	> 期間.終了日
	 * 期待値：		登録対象の期間 :期間
	 * 期間			= [2021/2/1, 2021/2/28]
	 * 締め切り		= 2021/3/1
	 * 登録対象の期間　= [2021/2/1, 2021/2/28]
	 * 登録する: delete
	 */
	@Test
	public void testRegister_case_delete_endDate_less_deadline() {
		
		val targetRegister = new DatePeriod(GeneralDate.ymd(2021, 2, 1), GeneralDate.ymd(2021, 2, 28));
		
		new Expectations() {
			{				
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
					
			    shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
			
				setting.getCorrespondingDeadlineAndPeriod((GeneralDate) any);
				result = new DeadlineAndPeriodOfWorkAvailability(GeneralDate.ymd(2021, 3, 1), datePeriod);
			}
		};
		
		NtsAssert.atomTask(
				() -> RegisterWorkAvailability.register(require, "sid", this.datePeriod, Collections.emptyList()), 
				any -> require.deleteAllWorkAvailabilityOfOneDay("sid", targetRegister));
	}
	
	/**
	 * 一日分の勤務希望リスト　not empty
	 * 期待: 削除する
	 */
	@Test
	public void testRegister_workavailabilities_not_empty() {
		
		new Expectations() {
			{				
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
					
			    shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
			
				setting.getCorrespondingDeadlineAndPeriod((GeneralDate) any);
				result = new DeadlineAndPeriodOfWorkAvailability(GeneralDate.ymd(2021, 2, 14), datePeriod);
				
			}
		};
		
		NtsAssert.atomTask(
				() -> RegisterWorkAvailability.register(require, "sid", this.datePeriod, workOneDays), 
				any -> require.deleteAllWorkAvailabilityOfOneDay(any.get(), any.get()),
				any -> require.insertAllWorkAvailabilityOfOneDay(any.get()));
	}
	
	/**
	 * 一日分の勤務希望リスト 　empty
	 * 期待: 削除する
	 */
	@Test
	public void testRegister_workavailabilities_empty() {
		
		new Expectations() {
			{				
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
					
			    shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
			
				setting.getCorrespondingDeadlineAndPeriod((GeneralDate) any);
				result = new DeadlineAndPeriodOfWorkAvailability(GeneralDate.ymd(2021, 2, 14), datePeriod);
				
			}
		};
		
		NtsAssert.atomTask(
				() -> RegisterWorkAvailability.register(require, "sid", this.datePeriod, Collections.emptyList()), 
				any -> require.deleteAllWorkAvailabilityOfOneDay(any.get(), any.get()));
	}
	

	public static class Helper {
		/**
		 * 希望＝シフト
		 * @param require
		 * @param ymd 希望日
		 * @param shiftMasterCode　シフトコード
		 * @return
		 */
		public static List<WorkAvailabilityOfOneDay> createWorkAvaiOfOneDays(@Injectable WorkAvailability.Require require, GeneralDate ymd, ShiftMasterCode shiftMasterCode) {
			return Arrays.asList(
					WorkAvailabilityOfOneDay.create(require, "sid", ymd, new WorkAvailabilityMemo("memo"),
							AssignmentMethod.SHIFT, Arrays.asList(shiftMasterCode), Collections.emptyList()));
		}
		
		/**
		 * 希望＝休日
		 * @return
		 */
		public static List<WorkAvailabilityOfOneDay> createWorkAvaiHolidays(@Injectable WorkAvailability.Require require, GeneralDate ymd) {
			return Arrays.asList(
					WorkAvailabilityOfOneDay.create(require, "sid", ymd, new WorkAvailabilityMemo("memo"),
							AssignmentMethod.HOLIDAY, Collections.emptyList(), Collections.emptyList()));
		}

	}
}
