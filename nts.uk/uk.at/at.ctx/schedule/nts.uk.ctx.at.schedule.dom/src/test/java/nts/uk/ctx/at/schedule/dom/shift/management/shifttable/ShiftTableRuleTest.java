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
import nts.uk.ctx.at.schedule.dom.shift.management.workavailability.AssignmentMethod;
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
	
	private DeadlineAndPeriodOfWorkAvailability creatWithDeadline(GeneralDate deadline) {
		
		return new DeadlineAndPeriodOfWorkAvailability(deadline, 
				new DatePeriod(GeneralDate.min(), GeneralDate.max()));
	}
	
	@Test
	public void getters() {
		
		ShiftTableRule target = defaultCreate();
		
		NtsAssert.invokeGetters(target);  
	}
	
	
	@Test
	public void testCreate_shiftTableSetting_isEmpty() {
		
		/**
		 * useWorkExpectationAtr == USE
		 * shiftTableSetting.isEmpty
		 */
		NtsAssert.systemError(() -> {
			ShiftTableRule.create( 
					NotUseAtr.USE,
					NotUseAtr.USE, // 勤務希望運用区分
					Optional.empty(), // シフト表の設定
					Collections.emptyList(),
					Optional.empty()); 
		});
		
		/**
		 * useWorkExpectationAtr == NOT_USE
		 * shiftTableSetting.isEmpty
		 */
		ShiftTableRule.create( 
					NotUseAtr.USE,
					NotUseAtr.NOT_USE, // 勤務希望運用区分
					Optional.empty(), // シフト表の設定
					Collections.emptyList(),
					Optional.empty());
	}
	
	@Test
	public void testCreate_expectationAssignMethodList_isEmpty() {
		
		/**
		 * useWorkExpectationAtr == USE
		 * expectationAssignMethodList.isEmpty
		 */
		NtsAssert.businessException("Msg_1937", () -> {
			
			ShiftTableRule.create(
					NotUseAtr.USE,
					NotUseAtr.USE, // 勤務希望運用区分
					Optional.of(WorkAvailabilityRuleDateSettingHelper.defaultCreate()),
					Collections.emptyList(), // 勤務希望の指定できる方法リスト
					Optional.empty());
		});
		
		/**
		 * useWorkExpectationAtr == NOT_USE
		 * expectationAssignMethodList.isEmpty
		 */
		ShiftTableRule.create(
				NotUseAtr.USE,
				NotUseAtr.NOT_USE, // 勤務希望運用区分
				Optional.of(WorkAvailabilityRuleDateSettingHelper.defaultCreate()),
				Collections.emptyList(), // 勤務希望の指定できる方法リスト
				Optional.empty());
	}
	
	@Test
	public void testCreate_fromNoticeDays_isEmpty() {
		
		/**
		 * useWorkExpectationAtr == USE
		 * fromNoticeDays.isEmpty
		 */
		NtsAssert.businessException("Msg_1938", () -> {
			
			ShiftTableRule.create( 
					NotUseAtr.USE,
					NotUseAtr.USE, // 勤務希望運用区分
					Optional.of(WorkAvailabilityRuleDateSettingHelper.defaultCreate()),
					Arrays.asList(AssignmentMethod.HOLIDAY),
					Optional.empty()); // 何日前に通知するかの日数	
		});
		
		/**
		 * useWorkExpectationAtr == NOT_USE
		 * fromNoticeDays.isEmpty
		 */
		ShiftTableRule.create( 
				NotUseAtr.USE,
				NotUseAtr.NOT_USE, // 勤務希望運用区分
				Optional.of(WorkAvailabilityRuleDateSettingHelper.defaultCreate()),
				Arrays.asList(AssignmentMethod.HOLIDAY),
				Optional.empty()); // 何日前に通知するかの日数	
	}
	
	@Test
	public void testCreate_success_useWorkExpectationAtr_isTrue() {
		
		WorkAvailabilityRule setting = WorkAvailabilityRuleDateSettingHelper.defaultCreate();
		FromNoticeDays days = new FromNoticeDays(3);
		
		ShiftTableRule rule = ShiftTableRule.create( NotUseAtr.USE, NotUseAtr.USE, 
				Optional.of( setting), 
				Arrays.asList(AssignmentMethod.HOLIDAY), 
				Optional.of(days));
		
		assertThat( rule.getUsePublicAtr()).isEqualTo( NotUseAtr.USE);
		assertThat( rule.getUseWorkAvailabilityAtr()).isEqualTo( NotUseAtr.USE);
		assertThat( rule.getShiftTableSetting().get()).isEqualTo(setting);
		assertThat( rule.getAvailabilityAssignMethodList()).containsOnly(AssignmentMethod.HOLIDAY);
		assertThat( rule.getFromNoticeDays().get()).isEqualTo(days);
	}
	
	@Test
	public void testIsTodayTheNotify_NOT_USE() {
		
		// Arrange
		ShiftTableRule rule = ShiftTableRuleHelper.createWithParam( 
				NotUseAtr.NOT_USE, // 勤務希望運用
				Optional.empty(), 
				Optional.empty() );
		
		new Expectations(NotificationInfo.class) {
            {
            	NotificationInfo.createWithoutNotify();
                times = 1; 
            }
        };
        
        rule.getTodayNotificationInfo();
        // when execute getTodayNotificationInfo(), NotificationInfo.createWithoutNotify() must to call 1 times
	}
	
	@Test
	public void testIsTodayTheNotify_false() {
		
		// Arrange
		WorkAvailabilityRule setting = WorkAvailabilityRuleDateSettingHelper.defaultCreate();
		ShiftTableRule rule = ShiftTableRuleHelper.createWithParam( 
				NotUseAtr.USE, // 勤務希望運用
				Optional.of(setting), 
				Optional.of(new FromNoticeDays(3)) );
		
		// Mock
		DeadlineAndPeriodOfWorkAvailability deadlineAndPeriod = creatWithDeadline(GeneralDate.ymd(2020, 10, 10));
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 10, 6, 0, 0, 0); // TODAY = 2020/10/6
		
		new Expectations( setting , NotificationInfo.class) {
            {
            	setting.getCorrespondingDeadlineAndPeriod( (GeneralDate) any);
            	result = deadlineAndPeriod;
            	
            	NotificationInfo.createWithoutNotify();
                times = 1; 
            }
        };

        // Act
        rule.getTodayNotificationInfo();
        // when execute getTodayNotificationInfo(), NotificationInfo.createWithoutNotify() must to call 1 times
	}
	
	@Test
	public void testIsTodayTheNotify_true_startNotify() {
		
		// Arrange
		WorkAvailabilityRule setting = WorkAvailabilityRuleDateSettingHelper.defaultCreate();
		ShiftTableRule rule = ShiftTableRuleHelper.createWithParam( 
				NotUseAtr.USE, // 勤務希望運用
				Optional.of(setting), 
				Optional.of(new FromNoticeDays(3)) );
		
		// Mock
		DeadlineAndPeriodOfWorkAvailability deadlineAndPeriod = creatWithDeadline(GeneralDate.ymd(2020, 10, 10));
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 10, 7, 0, 0, 0); // TODAY = 2020/10/7
		
		new Expectations(setting, NotificationInfo.class) {
            {
            	setting.getCorrespondingDeadlineAndPeriod( (GeneralDate) any);
            	result = deadlineAndPeriod;
            	
            	NotificationInfo.createNotification(deadlineAndPeriod);
                times = 1; 
            }
        };

        // Act
        rule.getTodayNotificationInfo();
        // when execute getTodayNotificationInfo(), NotificationInfo.createNotification() must to call 1 times
	}
	
	@Test
	public void testIsTodayTheNotify_true_endNotify() {
		
		// Arrange
		WorkAvailabilityRule setting = WorkAvailabilityRuleDateSettingHelper.defaultCreate();
		ShiftTableRule rule = ShiftTableRuleHelper.createWithParam( 
				NotUseAtr.USE, // 勤務希望運用
				Optional.of(setting), 
				Optional.of(new FromNoticeDays(3)) );
		
		// Mock
		DeadlineAndPeriodOfWorkAvailability deadlineAndPeriod = creatWithDeadline(GeneralDate.ymd(2020, 10, 10));
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 10, 10, 0, 0, 0); // TODAY = 2020/10/10
		
		new Expectations(setting, NotificationInfo.class) {
            {
            	setting.getCorrespondingDeadlineAndPeriod( (GeneralDate) any);
            	result = deadlineAndPeriod;
            	
            	NotificationInfo.createNotification(deadlineAndPeriod);
                times = 1; 
            }
        };

        // Act
        rule.getTodayNotificationInfo();
        // when execute getTodayNotificationInfo(), NotificationInfo.createNotification() must to call 1 times
	}

}
