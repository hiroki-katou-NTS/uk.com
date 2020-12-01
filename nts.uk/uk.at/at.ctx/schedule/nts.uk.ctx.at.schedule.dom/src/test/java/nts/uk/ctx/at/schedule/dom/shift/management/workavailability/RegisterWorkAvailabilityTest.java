package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.GetUsingShiftTableRuleOfEmployeeService;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.GetUsingShiftTableRuleOfEmployeeService.Require;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.ShiftTableRule;
import nts.uk.ctx.at.schedule.dom.shift.management.shifttable.WorkAvailabilityRuleDateSetting;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;
@RunWith(JMockit.class)
public class RegisterWorkAvailabilityTest {
	
	@Injectable
	RegisterWorkAvailability.Require require;
	
	/**
	 * input : シフト表のルール = empty
	 * excepted: Msg_2049
	 */
	@Test
	public void testCheckError_throw_Msg_2049() {

		val baseDate = GeneralDate.today();

		List<WorkAvailabilityOfOneDay> workOneDays = Helper.createWorkAvaiOfOneDays();

		new MockUp<GetUsingShiftTableRuleOfEmployeeService>() {
			@Mock
			public Optional<ShiftTableRule> get(Require require, String sid, GeneralDate date ){
				 return Optional.empty();
			}
		};

		NtsAssert.businessException("Msg_2049",
				() -> RegisterWorkAvailability.register(require, workOneDays, baseDate));
	}

	/**
	 * input : シフト表のルール.勤務希望運用区分 ==しない									
	 * excepted: Msg_2052
	 */
	@Test
	public void testCheckError_throw_Msg_2052(@Mocked ShiftTableRule shiftRule) {

		val baseDate = GeneralDate.today();
		List<WorkAvailabilityOfOneDay> workOneDays = Helper.createWorkAvaiOfOneDays();
		
		new MockUp<GetUsingShiftTableRuleOfEmployeeService>() {
			@Mock
			public Optional<ShiftTableRule> get(Require require, String sid, GeneralDate date ){
				 return Optional.of(shiftRule);
			}
		};

		new Expectations() {{
				shiftRule.getUseWorkAvailabilityAtr();
				result = NotUseAtr.NOT_USE;
			}
		};

		NtsAssert.businessException("Msg_2052",
				() -> RegisterWorkAvailability.register(require, workOneDays, baseDate));
	}
	
	/**
	 * input : シフト表のルール.シフト表の設定.締切日を過ぎているか= true
	 * excepted: Msg_2050
	 */
	@Test
	public void testCheckError_throw_Msg_2050(@Mocked ShiftTableRule shiftMock, @Mocked WorkAvailabilityRuleDateSetting setting) {

		val baseDate = GeneralDate.today();

		List<WorkAvailabilityOfOneDay> workOneDays = Helper.createWorkAvaiOfOneDays();
		
		new MockUp<GetUsingShiftTableRuleOfEmployeeService>() {
			@Mock
			public Optional<ShiftTableRule> get(Require require, String sid, GeneralDate date ){
				 return Optional.of(shiftMock);
			}
		};

		new Expectations() {
			{					
				shiftMock.getShiftTableSetting();
				result = Optional.of(setting);
				
				setting.isOverDeadline(workOneDays.get(0).getWorkAvailabilityDate());
				result = true;
			}
		};

		NtsAssert.businessException("Msg_2050",
				() -> RegisterWorkAvailability.register(require, workOneDays, baseDate));
	}
	
	/**
	 * input: 希望＝休日,  フト表のルール.シフト表の設定.休日日数の上限日数を超えているか = true
	 * excepted: Msg_2051
	 */
	@Test
	public void testCheckError_throw_Msg_2051(@Mocked ShiftTableRule shiftMock, @Mocked WorkAvailabilityRuleDateSetting setting) {

		val baseDate = GeneralDate.today();

		List<WorkAvailabilityOfOneDay> workOneDays = Helper.createWorkAvaiHolidays();
		
		
		new MockUp<GetUsingShiftTableRuleOfEmployeeService>() {
			@Mock
			public Optional<ShiftTableRule> get(Require require, String sid, GeneralDate date ){
				 return Optional.of(shiftMock);
			}
		};
		
		new Expectations() {{
				
				shiftMock.getShiftTableSetting();
				result = Optional.of(setting);
			
				setting.isOverHolidayMaxDays(workOneDays);
				result = true;
				
			}
		};

		NtsAssert.businessException("Msg_2051",
				() -> RegisterWorkAvailability.register(require, workOneDays, baseDate));
	}
	
	
	@Test
	public void testRegister_success_1(@Mocked ShiftTableRule shiftMock, @Mocked WorkAvailabilityRuleDateSetting setting) {
		
		List<WorkAvailabilityOfOneDay> workOneDays = Helper.createWorkAvaiOfOneDays();
		
		new MockUp<GetUsingShiftTableRuleOfEmployeeService>() {
			@Mock
			public Optional<ShiftTableRule> get(Require require, String sid, GeneralDate date ){
				 return Optional.of(shiftMock);
			}
		};
		
		new Expectations() {
			{				
				shiftMock.getShiftTableSetting();
				result = Optional.of(setting);
				
				setting.isOverHolidayMaxDays(workOneDays);
				result = false;
			}
		};
		
		NtsAssert.atomTask(
				() -> RegisterWorkAvailability.register(
						require,
						workOneDays,
						GeneralDate.today()), 
				any -> require.deleteAll(workOneDays),
				any -> require.insertAll(workOneDays));
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
