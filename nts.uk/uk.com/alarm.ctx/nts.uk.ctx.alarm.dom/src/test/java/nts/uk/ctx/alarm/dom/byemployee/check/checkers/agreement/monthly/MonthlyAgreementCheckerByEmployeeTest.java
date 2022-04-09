/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.monthly;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import mockit.Capturing;
import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.time.YearMonth;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.AlarmListCheckerByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.checkers.agreement.TargetOfAlarm;
import nts.uk.ctx.alarm.dom.byemployee.check.context.CheckingContextByEmployee;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriod;
import nts.uk.ctx.alarm.dom.byemployee.check.context.period.CheckingPeriodMonthlyAgreement;
import nts.uk.ctx.alarm.dom.byemployee.result.AlarmRecordByEmployee;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ExcessState;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class MonthlyAgreementCheckerByEmployeeTest {    
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

    /**
     * テストせなあかんこと ・年月に対して管理期間の３６協定時間が取れなかったら、アラームはださん
     * 1)管理期間の３６協定時間が取れても、調整した結果の閾値に照らし合わせた結果、正常ならアラームはださん
     * 2)調整した結果の閾値に照らし合わせた結果、アラーム超過なら、それに対応するメッセージを返す
     * 3)調整した結果の閾値に照らし合わせた結果、エラー超過なら、それに対応するメッセージを返す
     * 4)調整した結果の閾値に照らし合わせた結果、法定上限超過なら、それに対応するメッセージを返す 5)上記
     * 2)から4)が限度時間と法定上限の２パターンある
     */
    
    //年月に対して管理期間の３６協定時間が取れなかったら、アラームはださん
    @Test
    public void test_管理期間の３６協定時間が取れなかったらアラームは出ない() {
        new Expectations() {
            {
                require.getAgeementTime(anyString, (YearMonth) any);
                returns(Optional.empty());
            }
        };

        MonthlyAgreementCheckerByEmployee checker = MonthlyAgreementCheckerByEmployee
                .getBuilder(TargetOfAlarm.AGREEMENT_36_TIME)
                .put(ExcessState.ALARM_OVER, "アラーム")
                .put(ExcessState.ERROR_OVER, "エラー")
                .put(ExcessState.UPPER_LIMIT_OVER, "法定上限越え").build();
        
        CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);
        Iterator<AlarmRecordByEmployee> expected = checker.check(require, context).iterator();

        //空だから次がないはず
        Assert.assertTrue(!expected.hasNext());
    }
    
    //管理期間の３６協定時間が取れても、調整した結果の閾値に照らし合わせた結果、正常ならアラームはださん
    @Test
    public void test_３６協定時間は正常であるためアラートなし_調整値はなし(
            @Mocked AgreementTimeOfManagePeriod agreementTimes,
            @Mocked AgreementTimeOfMonthly agreement36Times
    ) {
        //Setup
        OneMonthTime thresholdOfAgreement36 = this.buildThreshold(2700, 1800, 1200);
        
        new Expectations() {
            {
                //３月だけ取得できる
                require.getAgeementTime(anyString, YearMonth.of(2022, 3));
                returns(Optional.of(agreementTimes));
            }
            {
                agreementTimes.getAgreementTime();
                returns(agreement36Times);
            }
            {
                agreement36Times.getThreshold();
                returns(thresholdOfAgreement36);
            }
            {
                // 900 -> 15時間なので正常
                agreement36Times.getAgreementTime();
                returns(new AttendanceTimeMonth(900));
            }
        };
        
        MonthlyAgreementCheckerByEmployee checker = MonthlyAgreementCheckerByEmployee
                .getBuilder(TargetOfAlarm.AGREEMENT_36_TIME)
                .put(ExcessState.ALARM_OVER, "アラーム")
                .put(ExcessState.ERROR_OVER, "エラー")
                .put(ExcessState.UPPER_LIMIT_OVER, "法定上限越え").build();
        
        CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);
        
        //Exercise
        Iterator<AlarmRecordByEmployee> expected = checker.check(require, context).iterator();
        
        //Verify 空だから次がないはず
        Assert.assertTrue(!expected.hasNext());
    }
    
    @Test
    public void test_３６協定時間はエラー域を超えたためアラート出す_調整値はなし(
            @Mocked AgreementTimeOfManagePeriod agreementTimes,
            @Mocked AgreementTimeOfMonthly agreement36Times
    ) {
        
        //Setup
        OneMonthTime thresholdOfAgreement36 = this.buildThreshold(2700, 1800, 1200);
        
        new Expectations() {
            {
                //３月だけ取得できる
                require.getAgeementTime(anyString, YearMonth.of(2022, 3));
                returns(Optional.of(agreementTimes));
            }
            {
                agreementTimes.getAgreementTime();
                returns(agreement36Times);
            }
            {
                agreement36Times.getThreshold();
                returns(thresholdOfAgreement36);
            }
            {
                // 2100 -> 15時間なのでエラー
                agreement36Times.getAgreementTime();
                returns(new AttendanceTimeMonth(2100));
            }
        };
        
        MonthlyAgreementCheckerByEmployee checker = MonthlyAgreementCheckerByEmployee
                .getBuilder(TargetOfAlarm.AGREEMENT_36_TIME)
                .put(ExcessState.ALARM_OVER, "アラーム")
                .put(ExcessState.ERROR_OVER, "エラー")
                .put(ExcessState.UPPER_LIMIT_OVER, "法定上限越え").build();
        
        CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);
        
        //Exercise
        Iterator<AlarmRecordByEmployee> iterator = checker.check(require, context).iterator();
        AlarmRecordByEmployee expected = iterator.next();
        
        //Verify 2022/3 がエラーのメッセージで出る
        Assert.assertEquals("2022/3", expected.getDateInfo().getFormatted());
        Assert.assertEquals("エラー", expected.getMessage());
        // 一件しかとれないはず
        Assert.assertTrue(!iterator.hasNext());
    }
    
    @Test
    public void test_３６協定時間はエラー域を超えたためアラート出す_調整値あり(
            @Mocked AgreementTimeOfManagePeriod agreementTimes,
            @Mocked AgreementTimeOfMonthly agreement36Times
    ) {
        
        //Setup
        OneMonthTime thresholdOfAgreement36 = this.buildThreshold(2700, 1800, 1200);
        
        new Expectations() {
            {
                //３月だけ取得できる
                require.getAgeementTime(anyString, YearMonth.of(2022, 3));
                returns(Optional.of(agreementTimes));
            }
            {
                agreementTimes.getAgreementTime();
                returns(agreement36Times);
            }
            {
                agreement36Times.getThreshold();
                returns(thresholdOfAgreement36);
            }
            {
                // 1740 -> 29時間ぴったりなので、本来エラーにならない
                agreement36Times.getAgreementTime();
                returns(new AttendanceTimeMonth(1740));
            }
        };
        
        MonthlyAgreementCheckerByEmployee checker = MonthlyAgreementCheckerByEmployee
                .getBuilder(TargetOfAlarm.AGREEMENT_36_TIME)
                .put(ExcessState.ALARM_OVER, "アラーム")
                // 500 -> 5時間引いた数でエラーを出すように調整
                .put(ExcessState.ERROR_OVER, new AgreementOneMonthTime(300), "エラー")
                .put(ExcessState.UPPER_LIMIT_OVER, "法定上限越え").build();
        
        CheckingContextByEmployee context = new CheckingContextByEmployee("employee_id", period);
        
        //Exercise
        Iterator<AlarmRecordByEmployee> iterator = checker.check(require, context).iterator();
        AlarmRecordByEmployee expected = iterator.next();
        
        //Verify 2022/3 がエラーのメッセージで出る
        Assert.assertEquals("2022/3", expected.getDateInfo().getFormatted());
        Assert.assertEquals("エラー", expected.getMessage());
        // 一件しかとれないはず
        Assert.assertTrue(!iterator.hasNext());
    }
    
    
    private OneMonthTime buildThreshold(int limit, int error, int alart) {
        return OneMonthTime.createWithCheck(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(error), new AgreementOneMonthTime(alart)), new AgreementOneMonthTime(limit));
    }
}
