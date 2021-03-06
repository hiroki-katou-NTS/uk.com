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
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodAgree36Yearly;
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
    public static class ???????????????????????????????????? {
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
        private CheckingPeriodAgree36Yearly periodYearlyAgreement;
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
                    // ??????????????????
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
         * ????????????????????????????????????
         * 1) ???????????????????????????????????????????????????????????????????????????????????????????????????????????????
         * 2) ????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
         * 3) ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
         * 4) 1) ???3) ??????????????????????????????????????????????????????????????????????????????
         * 5) 1) ???4) ???????????????????????????
         */
        @Test
        public void test_1?????????????????????????????????????????????????????????() {
            // ?????????600?????????????????? 500????????????????????? 400??????
            AgreementOneYear threshold = this.buildThreshold(36000, 30000, 24000);

            new Expectations() {
                {
                    basicAgreementSetting.getOneYear();
                    returns(threshold);
                }
                {
                    //??????30????????????????????????1?????????360???????????????
                    require.agreementTimeOfManagePeriod(anyString, (YearMonth)any);
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 4), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(1800), (OneMonthTime)any), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(1800), (OneMonthTime)any), (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
            };

            Agree36YearlyCheckerByEmployee checker = null;

            CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);

            Iterator<AlarmRecordByEmployee> expected = checker.check(require, context).iterator();

            //Verify ??????????????????????????????
            Assert.assertTrue(!expected.hasNext());
        }

        @Test
        public void test_1?????????????????????????????????????????????????????????????????????() {
            // ?????????600?????????????????? 500????????????????????? 400??????
            AgreementOneYear threshold = this.buildThreshold(36000, 30000, 24000);

            new Expectations() {
                {
                    basicAgreementSetting.getOneYear();
                    returns(threshold);
                }
                {
                    //??????40????????????????????????1?????????480???????????????
                    require.agreementTimeOfManagePeriod(anyString, (YearMonth)any);
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 4), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(2400), (OneMonthTime)any), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(2400), (OneMonthTime)any), (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
            };

            Agree36YearlyCheckerByEmployee checker = null;

            CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);

            //Exercise
            Iterator<AlarmRecordByEmployee> iterator = checker.check(require, context).iterator();
            AlarmRecordByEmployee expected = iterator.next();

            //Verify 2022/3 ???????????????????????????????????????
            Assert.assertEquals("2021", expected.getDateInfo().getFormatted());
            Assert.assertEquals("????????????", expected.getMessage());
            // ??????????????????????????????
            Assert.assertTrue(!iterator.hasNext());
        }

        @Test//????????????????????????
        public void test_1????????????????????????????????????????????????????????????????????????????????????() {
            // ?????????600?????????????????? 500????????????????????? 400??????
            AgreementOneYear threshold = this.buildThreshold(36000, 30000, 24000);

            new Expectations() {
                {
                    basicAgreementSetting.getOneYear();
                    returns(threshold);
                }
                {
                    //??????40????????????????????????1?????????480???????????????
                    require.agreementTimeOfManagePeriod(anyString, (YearMonth)any);
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 4), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(2400), (OneMonthTime)any), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), (OneMonthTime)any), (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
            };

            //???????????? - 100????????????
            Agree36YearlyCheckerByEmployee checker = null;

            CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);

            //Exercise
            Iterator<AlarmRecordByEmployee> iterator = checker.check(require, context).iterator();
            AlarmRecordByEmployee expected = iterator.next();

            //Verify 2022/3 ???????????????????????????????????????
            Assert.assertEquals("2021", expected.getDateInfo().getFormatted());
            Assert.assertEquals("????????????", expected.getMessage());
            // ??????????????????????????????
            Assert.assertTrue(!iterator.hasNext());
        }
 
        private AgreementOneYear buildThreshold(int limit, int error, int alart) {
            OneYearTime specConditionLimit = OneYearTime.createWithCheck(OneYearErrorAlarmTime.of(new AgreementOneYearTime(error), new AgreementOneYearTime(alart)), new AgreementOneYearTime(limit));
            OneYearErrorAlarmTime basic = OneYearErrorAlarmTime.of(new AgreementOneYearTime(error), new AgreementOneYearTime(alart));
            return new AgreementOneYear(basic, specConditionLimit);
        }
    }
    
    @RunWith(JMockit.class)
    public static class ????????????????????? {
        @Capturing
        private AlarmListCheckerByEmployee.Require require;
        @Mocked
        private CheckingPeriod period;
        @Mocked
        private CheckingPeriodAgree36Yearly periodYearlyAgreement;
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
         * ??????????????????????????????????????????
         * ??????????????????????????????????????????
         * ??????
         */
        @Test
        public void test_????????????????????????????????????() {
            //???????????????45??????
            OneMonthTime threshold = this.buildThreshold(2700, 0, 0);
            
            new Expectations() {
                {
                    // 50??????
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 4));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 4), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 50??????
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 5));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 5), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 50??????
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 6));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 6), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 50??????
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 7));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 7), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 45??????
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 8));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 8), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(2400), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 40??????
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 9));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 9), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(2400), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 40??????
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 10));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 10), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(2400), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
            };
            
            //?????????????????????
            Agree36YearlyCheckerByEmployee checker = null;
            
            CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);
            
            //Exercise
            Iterator<AlarmRecordByEmployee> expected = checker.check(require, context).iterator();

            //Verify ??????????????????????????????
            Assert.assertTrue(!expected.hasNext());
        }
        
        @Test
        public void test_????????????????????????????????????() {
            //???????????????45??????
            OneMonthTime threshold = this.buildThreshold(2700, 0, 0);
            
            new Expectations() {
                {
                    // 50??????
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 4));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 4), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 50??????
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 5));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 5), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 50??????
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 6));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 6), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 50??????
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 7));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 7), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 50??????
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 8));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 8), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 50??????
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 9));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 9), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(3000), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
                {
                    // 40??????
                    require.agreementTimeOfManagePeriod(anyString, YearMonth.of(2021, 10));
                    returns(Optional.of(AgreementTimeOfManagePeriod.of(anyString, YearMonth.of(2021, 10), AgreementTimeOfMonthly.of(new AttendanceTimeMonth(2400), threshold), (AgreementTimeOfMonthly)any, (AgreementTimeBreakdown)any, (AgreementTimeStatusOfMonthly)any)));
                }
            };
            
            //?????????????????????
            Agree36YearlyCheckerByEmployee checker = null;
            
            CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);
            
            //Exercise
            Iterator<AlarmRecordByEmployee> iterator = checker.check(require, context).iterator();
            AlarmRecordByEmployee expected = iterator.next();

            //Verify 2022/3 ???????????????????????????????????????
            Assert.assertEquals("2021", expected.getDateInfo().getFormatted());
            Assert.assertEquals("???????????????????????????????????????", expected.getMessage());
            // ??????????????????????????????
            Assert.assertTrue(!iterator.hasNext());
        }
        
        private OneMonthTime buildThreshold(int limit, int error, int alart) {
            return OneMonthTime.createWithCheck(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(error), new AgreementOneMonthTime(alart)), new AgreementOneMonthTime(limit));
        }
    }
}
