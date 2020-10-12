package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.shift.management.workexpect.AssignmentMethod;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class ShiftTableRuleTest {
	
	private ShiftTableRule defaultCreate() {
		
		return ShiftTableRule.create(
				NotUseAtr.NOT_USE, 
				NotUseAtr.NOT_USE, 
				Optional.empty(), 
				Collections.emptyList(), 
				Optional.empty());
	}
	
	@Test
	public void getters() {
		
		ShiftTableRule target = defaultCreate();
		
		NtsAssert.invokeGetters(target);  
	}
	
	/**
	 * useWorkExpectationAtr == USE
	 * shiftTableSetting.isEmpty
	 */
	@Test
	public void testCreate_shiftTableSetting_isEmpty() {
		
		NtsAssert.systemError(() -> {
			ShiftTableRule.create( NotUseAtr.USE, NotUseAtr.USE, 
					Optional.empty(), Collections.emptyList(), Optional.empty());
		});
	}
	
	/**
	 * useWorkExpectationAtr == USE
	 * expectationAssignMethodList.isEmpty
	 */
	@Test
	public void testCreate_expectationAssignMethodList_isEmpty() {
		
		NtsAssert.businessException("Msg_1937", () -> {
			
			ShiftTableRule.create( NotUseAtr.USE, NotUseAtr.USE, 
					Optional.of(ShiftTableDateSettingHelper.defaultCreate()), 
					Collections.emptyList(), 
					Optional.empty());
		});
	}
	
	/**
	 * useWorkExpectationAtr == USE
	 * fromNoticeDays.isEmpty
	 */
	@Test
	public void testCreate_fromNoticeDays_isEmpty() {
	
		NtsAssert.businessException("Msg_1938", () -> {
			
			ShiftTableRule.create( NotUseAtr.USE, NotUseAtr.USE, 
					Optional.of(ShiftTableDateSettingHelper.defaultCreate()), 
					Arrays.asList(AssignmentMethod.HOLIDAY), 
					Optional.empty());
		});
	}
	
	@Test
	public void testCreateSuccess() {
		
		ShiftTableSetting setting = ShiftTableDateSettingHelper.defaultCreate();
		FromNoticeDays days = new FromNoticeDays(3);
		
		ShiftTableRule rule = ShiftTableRule.create( NotUseAtr.USE, NotUseAtr.USE, 
				Optional.of( setting), 
				Arrays.asList(AssignmentMethod.HOLIDAY), 
				Optional.of(days));
		
		assertThat( rule.getUsePublicAtr()).isEqualTo( NotUseAtr.USE);
		assertThat( rule.getUseWorkExpectationAtr()).isEqualTo( NotUseAtr.USE);
		assertThat( rule.getShiftTableSetting().get()).isEqualTo(setting);
		assertThat( rule.getExpectationAssignMethodList()).contains(AssignmentMethod.HOLIDAY);
		assertThat( rule.getFromNoticeDays().get()).isEqualTo(days);
	}
	
	@Test
	public void testIsTodayTheNotify_NOT_USE() {
		
		ShiftTableRule rule = ShiftTableRule.create( NotUseAtr.USE, NotUseAtr.NOT_USE, 
				Optional.empty(), Collections.emptyList(), Optional.empty());
		
		NotificationInfo result = rule.getTodayNotificationInfo();
		
		assertThat( result.isNotify()).isFalse();
		assertThat( result.getDeadlineAndPeriod()).isEmpty();
	}
	
	@Test
	public void testIsTodayTheNotify_false() {
		
		// Arrange
		ShiftTableSetting setting = ShiftTableDateSettingHelper.defaultCreate();
		ShiftTableRule rule = ShiftTableRule.create( NotUseAtr.USE, NotUseAtr.USE, 
				Optional.of( setting), 
				Arrays.asList(AssignmentMethod.HOLIDAY), 
				Optional.of(new FromNoticeDays(3)));
		
		// Mock
		DeadlineAndPeriodOfExpectation ruleInfo = new DeadlineAndPeriodOfExpectation(GeneralDate.ymd(2020, 10, 10), 
				new DatePeriod(GeneralDate.ymd(2020, 10, 16), GeneralDate.ymd(2020, 11, 15)));
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 10, 6, 0, 0, 0); // TODAY = 2020/10/6
		
		new Expectations(setting) {
            {
            	setting.getCorrespondingDeadlineAndPeriod( (GeneralDate) any);
            	result = ruleInfo;
            }
        };

        // Act
        NotificationInfo result = rule.getTodayNotificationInfo();
        
        // Assert
        assertThat( result.isNotify()).isFalse();
		assertThat( result.getDeadlineAndPeriod()).isEmpty();
	}
	
	@Test
	public void testIsTodayTheNotify_true_startNotify() {
		
		// Arrange
		ShiftTableSetting setting = ShiftTableDateSettingHelper.defaultCreate();
		ShiftTableRule rule = ShiftTableRule.create( NotUseAtr.USE, NotUseAtr.USE, 
				Optional.of( setting), 
				Arrays.asList(AssignmentMethod.HOLIDAY), 
				Optional.of(new FromNoticeDays(3)));
		
		// Mock
		DeadlineAndPeriodOfExpectation ruleInfo = new DeadlineAndPeriodOfExpectation(GeneralDate.ymd(2020, 10, 10), 
				new DatePeriod(GeneralDate.ymd(2020, 10, 16), GeneralDate.ymd(2020, 11, 15)));
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 10, 7, 0, 0, 0); // TODAY = 2020/10/7
		
		new Expectations(setting) {
            {
            	setting.getCorrespondingDeadlineAndPeriod( (GeneralDate) any);
            	result = ruleInfo;
            }
        };

        // Act
        NotificationInfo result = rule.getTodayNotificationInfo();
        
        // Assert
        assertThat( result.isNotify()).isTrue();
		assertThat( result.getDeadlineAndPeriod().get().getDeadline()).isEqualTo(GeneralDate.ymd(2020, 10, 10));
		assertThat( result.getDeadlineAndPeriod().get().getPeriod().start()).isEqualTo(GeneralDate.ymd(2020, 10, 16));
		assertThat( result.getDeadlineAndPeriod().get().getPeriod().end()).isEqualTo(GeneralDate.ymd(2020, 11, 15));
	}
	
	@Test
	public void testIsTodayTheNotify_true_endNotify() {
		
		// Arrange
		ShiftTableSetting setting = ShiftTableDateSettingHelper.defaultCreate();
		ShiftTableRule rule = ShiftTableRule.create( NotUseAtr.USE, NotUseAtr.USE, 
				Optional.of( setting), 
				Arrays.asList(AssignmentMethod.HOLIDAY), 
				Optional.of(new FromNoticeDays(3)));
		
		// Mock
		DeadlineAndPeriodOfExpectation ruleInfo = new DeadlineAndPeriodOfExpectation(GeneralDate.ymd(2020, 10, 10), 
				new DatePeriod(GeneralDate.ymd(2020, 10, 16), GeneralDate.ymd(2020, 11, 15)));
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 10, 10, 0, 0, 0); // TODAY = 2020/10/10
		
		new Expectations(setting) {
            {
            	setting.getCorrespondingDeadlineAndPeriod( (GeneralDate) any);
            	result = ruleInfo;
            }
        };

        // Act
        NotificationInfo result = rule.getTodayNotificationInfo();
        
        // Assert
        assertThat( result.isNotify()).isTrue();
		assertThat( result.getDeadlineAndPeriod().get().getDeadline()).isEqualTo(GeneralDate.ymd(2020, 10, 10));
		assertThat( result.getDeadlineAndPeriod().get().getPeriod().start()).isEqualTo(GeneralDate.ymd(2020, 10, 16));
		assertThat( result.getDeadlineAndPeriod().get().getPeriod().end()).isEqualTo(GeneralDate.ymd(2020, 11, 15));
	}

}
