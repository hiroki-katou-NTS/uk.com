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
	
	
	@Test
	public void testCreate_fail() {
		
		/**
		 * useWorkExpectationAtr == USE
		 * shiftTableSetting.isEmpty
		 */
		NtsAssert.systemError(() -> {
			ShiftTableRule.create( 
					NotUseAtr.USE, // 公開運用区分	
					NotUseAtr.USE, // 勤務希望運用区分
					Optional.empty(), // シフト表の設定
					Collections.emptyList(), // 勤務希望の指定できる方法リスト
					Optional.empty()); //	何日前に通知するかの日数	
		});
		
		
		/**
		 * useWorkExpectationAtr == USE
		 * expectationAssignMethodList.isEmpty
		 */
		NtsAssert.businessException("Msg_1937", () -> {
			
			ShiftTableRule.create(
					NotUseAtr.USE, // 公開運用区分	
					NotUseAtr.USE, // 勤務希望運用区分
					Optional.of(ShiftTableDateSettingHelper.defaultCreate()), // シフト表の設定
					Collections.emptyList(), // 勤務希望の指定できる方法リスト
					Optional.empty()); // 何日前に通知するかの日数	
		});
		
		/**
		 * useWorkExpectationAtr == USE
		 * fromNoticeDays.isEmpty
		 */
		NtsAssert.businessException("Msg_1938", () -> {
			
			ShiftTableRule.create( 
					NotUseAtr.USE, // 公開運用区分	
					NotUseAtr.USE, // 勤務希望運用区分
					Optional.of(ShiftTableDateSettingHelper.defaultCreate()), // シフト表の設定
					Arrays.asList(AssignmentMethod.HOLIDAY), // 勤務希望の指定できる方法リスト
					Optional.empty()); // 何日前に通知するかの日数	
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
		assertThat( rule.getExpectationAssignMethodList()).containsOnly(AssignmentMethod.HOLIDAY);
		assertThat( rule.getFromNoticeDays().get()).isEqualTo(days);
	}
	
	@Test
	public void testIsTodayTheNotify_NOT_USE() {
		
		ShiftTableRule rule = ShiftTableRule.create( NotUseAtr.USE, NotUseAtr.NOT_USE, 
				Optional.empty(), Collections.emptyList(), Optional.empty());
		
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
		ShiftTableSetting setting = ShiftTableDateSettingHelper.defaultCreate();
		ShiftTableRule rule = ShiftTableRule.create( NotUseAtr.USE, NotUseAtr.USE, 
				Optional.of( setting), 
				Arrays.asList(AssignmentMethod.HOLIDAY), 
				Optional.of(new FromNoticeDays(3)));
		
		// Mock
		DeadlineAndPeriodOfExpectation ruleInfo = new DeadlineAndPeriodOfExpectation(GeneralDate.ymd(2020, 10, 10), 
				new DatePeriod(GeneralDate.ymd(2020, 10, 16), GeneralDate.ymd(2020, 11, 15)));
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 10, 6, 0, 0, 0); // TODAY = 2020/10/6
		
		new Expectations(setting, NotificationInfo.class) {
            {
            	setting.getCorrespondingDeadlineAndPeriod( (GeneralDate) any);
            	result = ruleInfo;
            	
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
		ShiftTableSetting setting = ShiftTableDateSettingHelper.defaultCreate();
		ShiftTableRule rule = ShiftTableRule.create( NotUseAtr.USE, NotUseAtr.USE, 
				Optional.of( setting), 
				Arrays.asList(AssignmentMethod.HOLIDAY), 
				Optional.of(new FromNoticeDays(3)));
		
		// Mock
		DeadlineAndPeriodOfExpectation ruleInfo = new DeadlineAndPeriodOfExpectation(GeneralDate.ymd(2020, 10, 10), 
				new DatePeriod(GeneralDate.ymd(2020, 10, 16), GeneralDate.ymd(2020, 11, 15)));
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 10, 7, 0, 0, 0); // TODAY = 2020/10/7
		
		new Expectations(setting, NotificationInfo.class) {
            {
            	setting.getCorrespondingDeadlineAndPeriod( (GeneralDate) any);
            	result = ruleInfo;
            	
            	NotificationInfo.createNotification(ruleInfo);
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
		ShiftTableSetting setting = ShiftTableDateSettingHelper.defaultCreate();
		ShiftTableRule rule = ShiftTableRule.create( NotUseAtr.USE, NotUseAtr.USE, 
				Optional.of( setting), 
				Arrays.asList(AssignmentMethod.HOLIDAY), 
				Optional.of(new FromNoticeDays(3)));
		
		// Mock
		DeadlineAndPeriodOfExpectation ruleInfo = new DeadlineAndPeriodOfExpectation(GeneralDate.ymd(2020, 10, 10), 
				new DatePeriod(GeneralDate.ymd(2020, 10, 16), GeneralDate.ymd(2020, 11, 15)));
		GeneralDateTime.FAKED_NOW = GeneralDateTime.ymdhms(2020, 10, 10, 0, 0, 0); // TODAY = 2020/10/10
		
		new Expectations(setting, NotificationInfo.class) {
            {
            	setting.getCorrespondingDeadlineAndPeriod( (GeneralDate) any);
            	result = ruleInfo;
            	
            	NotificationInfo.createNotification(ruleInfo);
                times = 1; 
            }
        };

        // Act
        rule.getTodayNotificationInfo();
        // when execute getTodayNotificationInfo(), NotificationInfo.createNotification() must to call 1 times
	}

}
