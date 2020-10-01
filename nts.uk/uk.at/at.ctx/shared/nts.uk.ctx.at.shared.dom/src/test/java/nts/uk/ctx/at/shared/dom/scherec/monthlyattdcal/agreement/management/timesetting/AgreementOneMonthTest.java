package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting;


import lombok.val;
import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ExcessState;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class AgreementOneMonthTest {
    @Test
    public void getters() {
        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth();
        NtsAssert.invokeGetters(agreementsOneMonth);
    }

    @Test
    public void checkErrorTimeExceededTest() {

        AgreementOneMonth agreementsOneYear = new AgreementOneMonth(
                OneMonthTime.of( OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20)),
                OneMonthTime.of( OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20))
        );
        AgreementOneMonthTime agreementOneMonthTime = new AgreementOneMonthTime(20);
        Pair<Boolean, AgreementOneMonthTime> result = agreementsOneYear.checkErrorTimeExceeded(agreementOneMonthTime);

        Assert.assertEquals(false, result.getLeft());
        Assert.assertEquals(new AgreementOneMonthTime(20).v(), result.getRight().v());
    }

    @Test
    public void calculateAlarmTimeTest() {
        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth();

        AgreementOneMonthTime agreementOneMonthTime = new AgreementOneMonthTime(20);

        AgreementOneMonthTime result = agreementsOneMonth.calculateAlarmTime(agreementOneMonthTime);

        Assert.assertEquals(new AgreementOneMonthTime(20).v(), result.v());
    }
    @Test
    public void check_01() {
        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth();
        val specConditionLimit=   agreementsOneMonth.getSpecConditionLimit();
        AttendanceTimeMonth agreementTarget = new AttendanceTimeMonth(10);
        AttendanceTimeMonth legalLimitTarget = new AttendanceTimeMonth(20);
        val rs = ExcessState.ALARM_OVER;
        new Expectations(OneMonthTime.class){
            {
                specConditionLimit.check(legalLimitTarget);
                result = rs;
            }
        };
        Assert.assertEquals(AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM,
                agreementsOneMonth.check(agreementTarget,legalLimitTarget));
    }
    @Test
    public void check_02() {

        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth();
        val specConditionLimit=   agreementsOneMonth.getSpecConditionLimit();
        AttendanceTimeMonth agreementTarget = new AttendanceTimeMonth(10);
        AttendanceTimeMonth legalLimitTarget = new AttendanceTimeMonth(20);
        val rs = ExcessState.ERROR_OVER;
        new Expectations(OneMonthTime.class){
            {
                specConditionLimit.check(legalLimitTarget);
                result = rs;
            }
        };
        Assert.assertEquals(AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR,
                agreementsOneMonth.check(agreementTarget,legalLimitTarget));
    }
    @Test
    public void check_03() {

        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth();
        val specConditionLimit=   agreementsOneMonth.getSpecConditionLimit();
        AttendanceTimeMonth agreementTarget = new AttendanceTimeMonth(10);
        AttendanceTimeMonth legalLimitTarget = new AttendanceTimeMonth(20);
        val rs = ExcessState.UPPER_LIMIT_OVER;
        new Expectations(OneMonthTime.class){
            {
                specConditionLimit.check(legalLimitTarget);
                result = rs;
            }
        };
        Assert.assertEquals(AgreementTimeStatusOfMonthly.EXCESS_BG_GRAY,
                agreementsOneMonth.check(agreementTarget,legalLimitTarget));
    }
    @Test
    public void check_04() {

        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth();
        val specConditionLimit=   agreementsOneMonth.getSpecConditionLimit();
        val basic = agreementsOneMonth.getBasic();
        AttendanceTimeMonth agreementTarget = new AttendanceTimeMonth(10);
        AttendanceTimeMonth legalLimitTarget = new AttendanceTimeMonth(20);
        new Expectations(OneMonthTime.class){
            {
                specConditionLimit.check(legalLimitTarget);
                result = null;
                basic.check(agreementTarget);
                result = ExcessState.ALARM_OVER;
            }
        };
        Assert.assertEquals(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM,
                agreementsOneMonth.check(agreementTarget,legalLimitTarget));
    }
    @Test
    public void check_05() {

        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth();
        val specConditionLimit=   agreementsOneMonth.getSpecConditionLimit();
        val basic = agreementsOneMonth.getBasic();
        AttendanceTimeMonth agreementTarget = new AttendanceTimeMonth(10);
        AttendanceTimeMonth legalLimitTarget = new AttendanceTimeMonth(20);
        new Expectations(OneMonthTime.class){
            {
                specConditionLimit.check(legalLimitTarget);
                result = null;
                basic.check(agreementTarget);
                result = ExcessState.ERROR_OVER;
            }
        };
        Assert.assertEquals(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR,
                agreementsOneMonth.check(agreementTarget,legalLimitTarget));
    }
    @Test
    public void check_06() {

        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth();
        val specConditionLimit=   agreementsOneMonth.getSpecConditionLimit();
        val basic = agreementsOneMonth.getBasic();
        AttendanceTimeMonth agreementTarget = new AttendanceTimeMonth(10);
        AttendanceTimeMonth legalLimitTarget = new AttendanceTimeMonth(20);
        new Expectations(OneMonthTime.class){
            {
                specConditionLimit.check(legalLimitTarget);
                result = null;
                basic.check(agreementTarget);
                result = ExcessState.UPPER_LIMIT_OVER;
            }
        };
        Assert.assertEquals(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR,
                agreementsOneMonth.check(agreementTarget,legalLimitTarget));
    }
    @Test
    public void check_07() {

        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth();

        val specConditionLimit=   agreementsOneMonth.getSpecConditionLimit();
        val basic = agreementsOneMonth.getBasic();
        AttendanceTimeMonth agreementTarget = new AttendanceTimeMonth(10);
        AttendanceTimeMonth legalLimitTarget = new AttendanceTimeMonth(20);
        new Expectations(OneMonthTime.class){
            {
                specConditionLimit.check(legalLimitTarget);
                result = null;
                basic.check(agreementTarget);
                result = null;
            }
        };
        Assert.assertEquals(AgreementTimeStatusOfMonthly.NORMAL,
                agreementsOneMonth.check(agreementTarget,legalLimitTarget));
    }

}
