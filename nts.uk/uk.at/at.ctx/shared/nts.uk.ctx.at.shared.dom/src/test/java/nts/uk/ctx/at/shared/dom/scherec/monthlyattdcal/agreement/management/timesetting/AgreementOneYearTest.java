package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting;

import lombok.val;
import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ExcessState;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneYear;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class AgreementOneYearTest {
    @Test
    public void getters() {
        AgreementOneYear agreementOneYear = new AgreementOneYear(
                 OneYearErrorAlarmTime.of(new AgreementOneYearTime(20),new AgreementOneYearTime(20)),
                 OneYearTime.of( OneYearErrorAlarmTime.of(new AgreementOneYearTime(20),new AgreementOneYearTime(20))
                        ,new AgreementOneYearTime(20))
        );
        NtsAssert.invokeGetters(agreementOneYear);
    }
    @Test
    public void checkErrorTimeExceededTest() {

        AgreementOneYear agreementOneYear = new AgreementOneYear(
                 OneYearErrorAlarmTime.of(new AgreementOneYearTime(20),new AgreementOneYearTime(20)),
                 OneYearTime.of( OneYearErrorAlarmTime.of(new AgreementOneYearTime(20),new AgreementOneYearTime(20))
                        ,new AgreementOneYearTime(20))
        );
        Pair<Boolean, AgreementOneYearTime> result =  agreementOneYear.checkErrorTimeExceeded(new AgreementOneYearTime(60));

        Assert.assertEquals(true,result.getLeft());
        Assert.assertEquals(new AgreementOneYearTime(20).v(),result.getRight().v());
    }

    @Test
    public void calculateAlarmTimeTest() {

        AgreementOneYear agreementOneYear = new AgreementOneYear(
                 OneYearErrorAlarmTime.of(new AgreementOneYearTime(20),new AgreementOneYearTime(20)),
                 OneYearTime.of( OneYearErrorAlarmTime.of(new AgreementOneYearTime(20),new AgreementOneYearTime(20))
                        ,new AgreementOneYearTime(20))
        );
        AgreementOneYearTime result =  agreementOneYear.calculateAlarmTime(agreementOneYear.getBasic().getAlarm());

        Assert.assertEquals(new AgreementOneYearTime(20).v(),result.v());
    }
    @Test
    public void check_01() {
        AgreementOneYear agreementOneYear = new AgreementOneYear();
        val specConditionLimit=   agreementOneYear.getSpecConditionLimit();
        AgreementOneYearTime agreementTarget = new AgreementOneYearTime(10);
        AgreementOneYearTime legalLimitTarget = new AgreementOneYearTime(20);
        val rs = ExcessState.ALARM_OVER;
        new Expectations(OneYearTime.class){
            {
                specConditionLimit.check(legalLimitTarget);
                result = rs;
            }
        };
        Assert.assertEquals(AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM,
                agreementOneYear.check(agreementTarget,legalLimitTarget));
    }
    @Test
    public void check_02() {

        AgreementOneYear agreementOneYear = new AgreementOneYear();
        val specConditionLimit=   agreementOneYear.getSpecConditionLimit();
        AgreementOneYearTime agreementTarget = new AgreementOneYearTime(10);
        AgreementOneYearTime legalLimitTarget = new AgreementOneYearTime(20);
        val rs = ExcessState.ERROR_OVER;
        new Expectations(OneYearTime.class){
            {
                specConditionLimit.check(legalLimitTarget);
                result = rs;
            }
        };
        Assert.assertEquals(AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR,
                agreementOneYear.check(agreementTarget,legalLimitTarget));
    }
    @Test
    public void check_03() {

        AgreementOneYear agreementOneYear = new AgreementOneYear();
        val specConditionLimit=   agreementOneYear.getSpecConditionLimit();
        AgreementOneYearTime agreementTarget = new AgreementOneYearTime(10);
        AgreementOneYearTime legalLimitTarget = new AgreementOneYearTime(20);
        val rs = ExcessState.UPPER_LIMIT_OVER;
        new Expectations(OneYearTime.class){
            {
                specConditionLimit.check(legalLimitTarget);
                result = rs;
            }
        };
        Assert.assertEquals(AgreementTimeStatusOfMonthly.EXCESS_BG_GRAY,
                agreementOneYear.check(agreementTarget,legalLimitTarget));
    }
    @Test
    public void check_04() {

        AgreementOneYear agreementOneYear = new AgreementOneYear(
                OneYearErrorAlarmTime.of(new AgreementOneYearTime(20),new AgreementOneYearTime(20)),
                OneYearTime.of( OneYearErrorAlarmTime.of(new AgreementOneYearTime(20),new AgreementOneYearTime(20))
                        ,new AgreementOneYearTime(20))
        );
        val specConditionLimit=   agreementOneYear.getSpecConditionLimit();
        AgreementOneYearTime agreementTarget = new AgreementOneYearTime(50);
        AgreementOneYearTime legalLimitTarget = new AgreementOneYearTime(20);
        new Expectations(OneYearTime.class){
            {
                specConditionLimit.check(legalLimitTarget);
                result = null;
            }
        };
        Assert.assertEquals(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR,
                agreementOneYear.check(agreementTarget,legalLimitTarget));
    }
    @Test
    public void check_05() {

        AgreementOneYear agreementOneYear = new AgreementOneYear(
                OneYearErrorAlarmTime.of(new AgreementOneYearTime(21),new AgreementOneYearTime(20)),
                OneYearTime.of( OneYearErrorAlarmTime.of(new AgreementOneYearTime(20),new AgreementOneYearTime(20))
                        ,new AgreementOneYearTime(20))
        );
        val specConditionLimit=   agreementOneYear.getSpecConditionLimit();
        AgreementOneYearTime agreementTarget = new AgreementOneYearTime(16);
        AgreementOneYearTime legalLimitTarget = new AgreementOneYearTime(20);
        new Expectations(OneYearTime.class){
            {
                specConditionLimit.check(legalLimitTarget);
                result = null;
            }
        };
        Assert.assertEquals(AgreementTimeStatusOfMonthly.NORMAL,
                agreementOneYear.check(agreementTarget,legalLimitTarget));
    }
    @Test
    public void check_06() {

        AgreementOneYear agreementOneYear = new AgreementOneYear(
                OneYearErrorAlarmTime.of(new AgreementOneYearTime(22),new AgreementOneYearTime(15)),
                OneYearTime.of( OneYearErrorAlarmTime.of(new AgreementOneYearTime(20),new AgreementOneYearTime(20))
                        ,new AgreementOneYearTime(20))
        );
        val specConditionLimit=   agreementOneYear.getSpecConditionLimit();
        AgreementOneYearTime agreementTarget = new AgreementOneYearTime(16);
        AgreementOneYearTime legalLimitTarget = new AgreementOneYearTime(20);
        new Expectations(OneYearTime.class){
            {
                specConditionLimit.check(legalLimitTarget);
                result = null;
            }
        };
        Assert.assertEquals(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM,
                agreementOneYear.check(agreementTarget,legalLimitTarget));
    }
}
