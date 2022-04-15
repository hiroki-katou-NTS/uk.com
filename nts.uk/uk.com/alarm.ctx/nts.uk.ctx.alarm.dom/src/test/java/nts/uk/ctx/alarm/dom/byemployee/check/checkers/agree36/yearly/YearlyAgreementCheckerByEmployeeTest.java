/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agree36.yearly;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import mockit.Capturing;
import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodYearlyAgreement;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeBreakdown;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.StartingMonthType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSettingForCalc;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class YearlyAgreementCheckerByEmployeeTest {
    
    @RunWith(JMockit.class)
    public static class 限度時間のアラームテスト {
        @Capturing
        private AlarmListCheckerByEmployee.Require require;
        @Mocked
        private AgreementOperationSetting operationSetting;
        @Mocked
        private BasicAgreementSettingForCalc basicAgreementSettingForCalc;
        @Mocked
        private BasicAgreementSetting basicAgreementSetting;
        @Mocked
        private CheckingPeriod period;
        @Mocked
        private CheckingPeriodYearlyAgreement periodYearlyAgreement;
        @Mocked
        private AppContexts appContexts;
        
        @Before
        public void setUp() {
            new Expectations() {
                {
                    AppContexts.user().companyId();
                    returns(anyString);
                }
                {
                    require.agreementOperationSetting(anyString);
                    returns(Optional.of(operationSetting));
                }
                {
                    // 起算月は４月
                    operationSetting.getStartingMonth();
                    returns(StartingMonthType.APRIL);
                }
                {
                    basicAgreementSettingForCalc.getBasicSetting();
                    returns(basicAgreementSetting);
                }
                {
                    period.getYearlyAgreement();
                    returns(periodYearlyAgreement);
                }
                {
                    periodYearlyAgreement.calculatePeriod();
                    returns(Arrays.asList(new Year(2021)));
                }
            };
        }
        
        /**
         * テストしなきゃいかん内容
         * 1) 指定した年の起算月から１年間の内容を合計したものが正常ならアラームがでない
         * 2) 指定した年の起算月から１年間の内容を合計したものがアラーム超過なら、アラーム出る
         * 3) 指定した年の起算月から１年間の内容を合計したものがエラー超過なら、アラーム出る
         * 4) 1) ～3) を調整値から加味した状態でアラームを出すかを判断する
         * 5) 1) ～4) の特別条項のテスト
         */
        @Test
        public void test_1年間の内容を合計したものが正常であった() {
            // 上限　600時間　エラー 500時間　アラーム 400時間
            AgreementOneYear threshold = this.buildThreshold(36000, 30000, 24000);

            new Expectations() {
                {
                    basicAgreementSetting.getOneYear();
                    returns(threshold);
                }
                {
                    //常に30時間取れるので、1年間で360時間になる
                    require.agreementTimeOfManagePeriod(anyString, (YearMonth)any);
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 4), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(1800), (OneMonthTime)any), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(1800), (OneMonthTime)any), (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
            };

            Agree36YearlyCheckerByEmployee checker = null;

            CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);

            Iterator<AlarmRecordByEmployee> expected = checker.check(require, context).iterator();

            //Verify 空だから次がないはず
            Assert.assertTrue(!expected.hasNext());
        }

        @Test
        public void test_1年間の内容を合計したものがアラーム超過になった() {
            // 上限　600時間　エラー 500時間　アラーム 400時間
            AgreementOneYear threshold = this.buildThreshold(36000, 30000, 24000);

            new Expectations() {
                {
                    basicAgreementSetting.getOneYear();
                    returns(threshold);
                }
                {
                    //常に40時間取れるので、1年間で480時間になる
                    require.agreementTimeOfManagePeriod(anyString, (YearMonth)any);
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 4), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(2400), (OneMonthTime)any), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(2400), (OneMonthTime)any), (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
            };

            Agree36YearlyCheckerByEmployee checker = null;

            CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);

            //Exercise
            Iterator<AlarmRecordByEmployee> iterator = checker.check(require, context).iterator();
            AlarmRecordByEmployee expected = iterator.next();

            //Verify 2022/3 がエラーのメッセージで出る
            Assert.assertEquals("2021", expected.getDateInfo().getFormatted());
            Assert.assertEquals("アラーム", expected.getMessage());
            // 一件しかとれないはず
            Assert.assertTrue(!iterator.hasNext());
        }

        @Test//特定条項について
        public void test_1年間の内容を合計したものが調整した場合の法定上限を超えた() {
            // 上限　600時間　エラー 500時間　アラーム 400時間
            AgreementOneYear threshold = this.buildThreshold(36000, 30000, 24000);

            new Expectations() {
                {
                    basicAgreementSetting.getOneYear();
                    returns(threshold);
                }
                {
                    //常に40時間取れるので、1年間で480時間になる
                    require.agreementTimeOfManagePeriod(anyString, (YearMonth)any);
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 4), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(2400), (OneMonthTime)any), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), (OneMonthTime)any), (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
            };

            //それぞれ - 100時間する
            Agree36YearlyCheckerByEmployee checker = null;

            CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);

            //Exercise
            Iterator<AlarmRecordByEmployee> iterator = checker.check(require, context).iterator();
            AlarmRecordByEmployee expected = iterator.next();

            //Verify 2022/3 がエラーのメッセージで出る
            Assert.assertEquals("2021", expected.getDateInfo().getFormatted());
            Assert.assertEquals("法定上限", expected.getMessage());
            // 一件しかとれないはず
            Assert.assertTrue(!iterator.hasNext());
        }
 
        private AgreementOneYear buildThreshold(int limit, int error, int alart) {
            OneYearTime specConditionLimit = OneYearTime.createWithCheck(OneYearErrorAlarmTime.of(new AgreementOneYearTime(error), new AgreementOneYearTime(alart)), new AgreementOneYearTime(limit));
            OneYearErrorAlarmTime basic = OneYearErrorAlarmTime.of(new AgreementOneYearTime(error), new AgreementOneYearTime(alart));
            return new AgreementOneYear(basic, specConditionLimit);
        }
    }
    
    @RunWith(JMockit.class)
    public static class 超過回数テスト {
        @Capturing
        private AlarmListCheckerByEmployee.Require require;
        @Mocked
        private CheckingPeriod period;
        @Mocked
        private CheckingPeriodYearlyAgreement periodYearlyAgreement;
        @Mocked
        private AppContexts appContexts;
        
        @Before
        public void setUp() {
            new Expectations() {
                {
                    AppContexts.user().companyId();
                    returns(anyString);
                }
                {
                    require.agreementOperationSetting(anyString);
                    returns(Optional.of(new AgreementOperationSetting(anyString, StartingMonthType.APRIL, new ClosureDate(anyInt, true), true, true)));
                }
                {
                    period.getYearlyAgreement();
                    returns(periodYearlyAgreement);
                }
                {
                    periodYearlyAgreement.calculatePeriod();
                    returns(Arrays.asList(new Year(2021)));
                }
            };
        }
        
        /**
         * テストしなければならない内容
         * 閾値以下の場合アラームでない
         * 閾値
         */
        @Test
        public void test_超過回数が閾値未満である() {
            //法定上限　45時間
            OneMonthTime threshold = this.buildThreshold(2700, 0, 0);
            
            new Expectations() {
                {
                    // 50時間
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 4));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 4), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 50時間
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 5));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 5), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 50時間
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 6));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 6), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 50時間
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 7));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 7), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 45時間
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 8));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 8), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(2400), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 40時間
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 9));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 9), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(2400), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 40時間
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 10));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 10), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(2400), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
            };
            
            //閾値は５回以上
            Agree36YearlyCheckerByEmployee checker = null;
            
            CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);
            
            //Exercise
            Iterator<AlarmRecordByEmployee> expected = checker.check(require, context).iterator();

            //Verify 空だから次がないはず
            Assert.assertTrue(!expected.hasNext());
        }
        
        @Test
        public void test_超過回数が閾値以上である() {
            //法定上限　45時間
            OneMonthTime threshold = this.buildThreshold(2700, 0, 0);
            
            new Expectations() {
                {
                    // 50時間
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 4));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 4), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 50時間
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 5));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 5), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 50時間
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 6));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 6), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 50時間
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 7));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 7), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 50時間
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 8));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 8), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 50時間
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 9));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 9), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 40時間
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 10));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 10), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(2400), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
            };
            
            //閾値は５回以上
            Agree36YearlyCheckerByEmployee checker = null;
            
            CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);
            
            //Exercise
            Iterator<AlarmRecordByEmployee> iterator = checker.check(require, context).iterator();
            AlarmRecordByEmployee expected = iterator.next();

            //Verify 2022/3 がエラーのメッセージで出る
            Assert.assertEquals("2021", expected.getDateInfo().getFormatted());
            Assert.assertEquals("超過している回数が多いです", expected.getMessage());
            // 一件しかとれないはず
            Assert.assertTrue(!iterator.hasNext());
        }
        
        private OneMonthTime buildThreshold(int limit, int error, int alart) {
            return OneMonthTime.createWithCheck(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(error), new AgreementOneMonthTime(alart)), new AgreementOneMonthTime(limit));
        }
    }
}
