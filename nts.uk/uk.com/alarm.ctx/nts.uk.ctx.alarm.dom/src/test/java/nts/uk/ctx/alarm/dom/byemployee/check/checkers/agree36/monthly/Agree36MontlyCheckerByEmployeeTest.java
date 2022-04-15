/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agree36.monthly;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import mockit.Capturing;
import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.time.YearMonth;
import nts.uk.ctx.alarm.dom.AlarmListAlarmMessage;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodMonthlyAgreement;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.alarm.dom.conditionvalue.AlarmListConditionValue;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueComparison;
import nts.uk.ctx.alarm.dom.conditionvalue.ConditionValueExpression;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class Agree36MontlyCheckerByEmployeeTest {

    @Capturing
    private AlarmListCheckerByEmployee.Require require;
    @Mocked
    private CheckingPeriod period;
    @Mocked
    private CheckingPeriodMonthlyAgreement periodMonthlyAgreement;

    @Before
    public void setUp() {
        new Expectations() {
            {
                period.getMonthlyAgreement();
                returns(periodMonthlyAgreement);
            }

            {
                periodMonthlyAgreement.calculatePeriod();
                returns(Arrays.asList(YearMonth.of(2022, 1), YearMonth.of(2022, 2), YearMonth.of(2022, 3), YearMonth.of(2022, 4), YearMonth.of(2022, 5)));
            }
        };
    }

    @Test
    public void test_管理期間の３６協定時間が取れなかったらアラームは出ない() {
        new Expectations() {
            {
                require.getAgeementTime(anyString, (YearMonth) any);
                returns(Optional.empty());
            }
        };

        Agree36MontlyCheckerByEmployee checker = new Agree36MontlyCheckerByEmployee(
                "会社ID",
                null,
                "チェック条件の名前",
                Arrays.asList(
                        //条件はすべて有効　閾値は0
                        this.generateEnable(ConditionValueAgree36MonthlyCheckerByEmployee.月間限度時間, 0),
                        this.generateEnable(ConditionValueAgree36MonthlyCheckerByEmployee.特別条項による条件, 0)
                )
        );

        CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);
        Iterator<AlarmRecordByEmployee> expected = checker.check(require, context).iterator();

        //空だから次がないはず
        Assert.assertTrue(!expected.hasNext());
    }

    //管理期間の３６協定時間が取れても、調整した結果の閾値に照らし合わせた結果、正常ならアラームはださん
    @Test
    public void test_月間限度時間は正常であるためアラートなし(
            @Mocked AgreementTimeOfManagePeriod agreementTimes,
            @Mocked AgreementTimeOfMonthly 限度時間の対象値
    ) {
        new Expectations() {
            {
                //３月だけ取得できる
                require.getAgeementTime(anyString, YearMonth.of(2022, 3));
                returns(Optional.of(agreementTimes));
            }
            {
                agreementTimes.getAgreementTime();
                returns(限度時間の対象値);
            }
            {
                // 900 -> 15時間
                限度時間の対象値.getAgreementTime();
                returns(new AttendanceTimeMonth(900));
            }
        };


        Agree36MontlyCheckerByEmployee checker = new Agree36MontlyCheckerByEmployee(
                "会社ID",
                null,
                "チェック条件の名前",
                Arrays.asList(
                        //月間限度時間だけ有効　閾値は45時間なのでアラームは出ない
                        this.generateEnable(ConditionValueAgree36MonthlyCheckerByEmployee.月間限度時間, 1800.0),
                        this.generateDisable(ConditionValueAgree36MonthlyCheckerByEmployee.特別条項による条件, 0)
                )
        );

        CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);

        //Exercise
        Iterator<AlarmRecordByEmployee> expected = checker.check(require, context).iterator();

        //Verify 空だから次がないはず
        Assert.assertTrue(!expected.hasNext());
    }

    @Test
    public void test_月間限度時間は閾値を超えたのでアラームを出す(
            @Mocked AgreementTimeOfManagePeriod agreementTimes,
            @Mocked AgreementTimeOfMonthly 限度時間の対象値,
            @Mocked AgreementTimeOfMonthly 特別条項の対象値
    ) {
        new Expectations() {
            {
                //３月だけ取得できる
                require.getAgeementTime(anyString, YearMonth.of(2022, 3));
                returns(Optional.of(agreementTimes));
            }
            {
                agreementTimes.getAgreementTime();
                returns(限度時間の対象値);
            }
            {
                // 2100 -> 35時間
                限度時間の対象値.getAgreementTime();
                returns(new AttendanceTimeMonth(2100));
            }
        };

        Agree36MontlyCheckerByEmployee checker = new Agree36MontlyCheckerByEmployee(
                "会社ID",
                null,
                "チェック条件の名前",
                Arrays.asList(
                        //特別条項だけ有効　閾値は30時間
                        this.generateEnable(ConditionValueAgree36MonthlyCheckerByEmployee.月間限度時間, 1800.0),
                        this.generateDisable(ConditionValueAgree36MonthlyCheckerByEmployee.特別条項による条件, 0)
                )
        );


        CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);

        //Exercise
        Iterator<AlarmRecordByEmployee> iterator = checker.check(require, context).iterator();
        iterator.hasNext();
        AlarmRecordByEmployee expected = iterator.next();

        //Verify 2022/3 がエラーのメッセージで出る
        Assert.assertEquals("2022/3", expected.getDateInfo().getFormatted());
        Assert.assertEquals("アラームメッセージ", expected.getMessage().v());
        // 一件しかとれないはず
        Assert.assertTrue(!iterator.hasNext());
    }



    @Test
    public void test_特別条項に対応する時間は正常であるためアラートなし(
            @Mocked AgreementTimeOfManagePeriod agreementTimes,
            @Mocked AgreementTimeOfMonthly 特別条項の対象値
    ) {

        new Expectations() {
            {
                //３月だけ取得できる
                require.getAgeementTime(anyString, YearMonth.of(2022, 3));
                returns(Optional.of(agreementTimes));
            }
            {
                agreementTimes.getLegalMaxTime();
                returns(特別条項の対象値);
            }
            {
                // 2100 -> 35時間
                特別条項の対象値.getAgreementTime();
                returns(new AttendanceTimeMonth(2100));
            }
        };

        Agree36MontlyCheckerByEmployee checker = new Agree36MontlyCheckerByEmployee(
                "会社ID",
                null,
                "チェック条件の名前",
                Arrays.asList(
                        //月間限度時間だけ有効　閾値は50時間なのでアラームはでない
                        this.generateDisable(ConditionValueAgree36MonthlyCheckerByEmployee.月間限度時間, 0.0),
                        this.generateEnable(ConditionValueAgree36MonthlyCheckerByEmployee.特別条項による条件, 3000.0)
                )
        );

        CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);

         //Exercise
        Iterator<AlarmRecordByEmployee> expected = checker.check(require, context).iterator();

        //Verify 空だから次がないはず
        Assert.assertTrue(!expected.hasNext());
    }
    
    @Test
    public void test_特別条項に対応する時間は閾値を超えたのでアラームを出す(
            @Mocked AgreementTimeOfManagePeriod agreementTimes,
            @Mocked AgreementTimeOfMonthly 特別条項の対象値
    ) {

        new Expectations() {
            {
                //３月だけ取得できる
                require.getAgeementTime(anyString, YearMonth.of(2022, 3));
                returns(Optional.of(agreementTimes));
            }
            {
                agreementTimes.getLegalMaxTime();
                returns(特別条項の対象値);
            }
            {
                // 3300 -> 55時間
                特別条項の対象値.getAgreementTime();
                returns(new AttendanceTimeMonth(3300));
            }
        };

        Agree36MontlyCheckerByEmployee checker = new Agree36MontlyCheckerByEmployee(
                "会社ID",
                null,
                "チェック条件の名前",
                Arrays.asList(
                        //特別条項だけ有効　閾値は50時間
                        this.generateDisable(ConditionValueAgree36MonthlyCheckerByEmployee.月間限度時間, 0.0),
                        this.generateEnable(ConditionValueAgree36MonthlyCheckerByEmployee.特別条項による条件, 3000.0)
                )
        );

        CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);

        //Exercise
        Iterator<AlarmRecordByEmployee> iterator = checker.check(require, context).iterator();
        iterator.hasNext();
        AlarmRecordByEmployee expected = iterator.next();

        //Verify 2022/3 がエラーのメッセージで出る
        Assert.assertEquals("2022/3", expected.getDateInfo().getFormatted());
        Assert.assertEquals("アラームメッセージ", expected.getMessage().v());
        // 一件しかとれないはず
        Assert.assertTrue(!iterator.hasNext());
    }
    
    private AlarmListConditionValue<
        ConditionValueAgree36MonthlyCheckerByEmployee, ConditionValueAgree36MonthlyCheckerByEmployee.Context> generateEnable(ConditionValueAgree36MonthlyCheckerByEmployee logic, double threshold) {

        ConditionValueExpression expression = new ConditionValueExpression(
                ConditionValueComparison.GREATER_THAN,
                threshold,
                Optional.empty()
        );

        return new AlarmListConditionValue(logic, true, expression, new AlarmListAlarmMessage("アラームメッセージ"));
    }

    private AlarmListConditionValue<
        ConditionValueAgree36MonthlyCheckerByEmployee, ConditionValueAgree36MonthlyCheckerByEmployee.Context> generateDisable(ConditionValueAgree36MonthlyCheckerByEmployee logic, double threshold) {

        ConditionValueExpression expression = new ConditionValueExpression(
                ConditionValueComparison.GREATER_THAN,
                threshold,
                Optional.empty()
        );

        return new AlarmListConditionValue(logic, false, expression, new AlarmListAlarmMessage("アラームメッセージ"));
    }
}
