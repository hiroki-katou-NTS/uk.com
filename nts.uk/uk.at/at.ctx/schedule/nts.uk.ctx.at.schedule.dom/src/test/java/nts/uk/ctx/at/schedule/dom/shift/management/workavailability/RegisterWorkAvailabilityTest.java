package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

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
	
	@Mocked 
	private ShiftTableRule shiftRule;
	
	@Mocked 
	private WorkAvailabilityRule setting;
	
	@Mocked
	private GetUsingShiftTableRuleOfEmployeeService service;
	
	private GeneralDate today;
	
	@Before
	public void generDate(){
		today = GeneralDate.today();
	}
	
	/**
	 * input : シフト表のルール = empty
	 * excepted: Msg_2049
	 */
	
	@Test
	public void testCheckError_throw_Msg_2049() {

		List<WorkAvailabilityOfOneDay> workOneDays = Helper.createWorkAvaiOfOneDays();
		
		new Expectations() {
			{
				service.get(require, (String) any, (GeneralDate) any);
			}
		};

		NtsAssert.businessException("Msg_2049",
				() -> RegisterWorkAvailability.register(require, workOneDays, today));
	}

	/**
	 * input : シフト表のルール.勤務希望運用区分 ==しない									
	 * excepted: Msg_2052
	 */
	@Test
	public void testCheckError_throw_Msg_2052() {

		val today = GeneralDate.today();
		List<WorkAvailabilityOfOneDay> workOneDays = Helper.createWorkAvaiOfOneDays();
		
		new Expectations() {{
			service.get(require, (String) any, today);
			result = Optional.of(shiftRule);
			
			shiftRule.getUseWorkAvailabilityAtr();
			result = NotUseAtr.NOT_USE;
		}};

		NtsAssert.businessException("Msg_2052",
				() -> RegisterWorkAvailability.register(require, workOneDays, today));
	}
	
	/**
	 * input : シフト表のルール.シフト表の設定.締切日を過ぎているか= true
	 * excepted: Msg_2050
	 */
	@Test
	public void testCheckError_throw_Msg_2050() {

		val today = GeneralDate.today();
		List<WorkAvailabilityOfOneDay> workOneDays = Helper.createWorkAvaiOfOneDays();
		
		new Expectations() {
			{			
				service.get(require, (String) any, today);
				result = Optional.of(shiftRule);
				
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
				
				setting.isOverDeadline(workOneDays.get(0).getWorkAvailabilityDate());
				result = true;
			}
		};

		NtsAssert.businessException("Msg_2050",
				() -> RegisterWorkAvailability.register(require, workOneDays, today));
	}
	
	/**
	 * input: 希望＝休日,  フト表のルール.シフト表の設定.休日日数の上限日数を超えているか = true
	 * excepted: Msg_2051
	 */
	@Test
	public void testCheckError_throw_Msg_2051() {
		List<WorkAvailabilityOfOneDay> workOneDays = Helper.createWorkAvaiHolidays();
		
		new Expectations() {{
			service.get(require, (String) any, today);
			result = Optional.of(shiftRule);
				
		    shiftRule.getShiftTableSetting();
			result = Optional.of(setting);
		
			setting.isOverHolidayMaxDays(workOneDays);
			result = true;
				
		}};

		NtsAssert.businessException("Msg_2051",
				() -> RegisterWorkAvailability.register(require, workOneDays, today));
	}
	
	
	@Test
	public void testRegister_success_1() {
		
		val workOneDays = Helper.createWorkAvaiOfOneDays();
		val datePeriod = new DatePeriod(GeneralDate.ymd(2021, 1, 1), GeneralDate.ymd(2021, 1, 31));
		
		new Expectations() {
			{				
				service.get(require, (String) any, (GeneralDate) any);
				result = Optional.of(shiftRule);
				
				shiftRule.getShiftTableSetting();
				result = Optional.of(setting);
				
				setting.isOverHolidayMaxDays(workOneDays);
				result = false;
				
				setting.getPeriodWhichIncludeAvailabilityDate((GeneralDate) any);
				result = datePeriod;
			}
		};
		
		NtsAssert.atomTask(
				() -> RegisterWorkAvailability.register(
						require,
						workOneDays,
						GeneralDate.today()), 
				any -> require.deleteAllWorkAvailabilityOfOneDay(workOneDays.get(0).getEmployeeId(), datePeriod),
				any -> require.insertAllWorkAvailabilityOfOneDay(workOneDays));
	}
	

	public static class Helper {
		/**
		 * 希望＝シフト
		 * @return
		 */
		public static List<WorkAvailabilityOfOneDay> createWorkAvaiOfOneDays() {
			return Arrays.asList(
					WorkAvailabilityOfOneDay.create("sid", GeneralDate.today(), new WorkAvailabilityMemo("memo"),
							AssignmentMethod.SHIFT, Arrays.asList((new ShiftMasterCode("001"))), Collections.emptyList()));
		}
		
		/**
		 *  希望＝休日
		 * @return
		 */
		public static List<WorkAvailabilityOfOneDay> createWorkAvaiHolidays() {
			return Arrays.asList(
					WorkAvailabilityOfOneDay.create("sid", GeneralDate.today(), new WorkAvailabilityMemo("memo"),
							AssignmentMethod.HOLIDAY, Collections.emptyList(), Collections.emptyList()));
		}

	}
}
