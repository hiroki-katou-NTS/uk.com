package nts.uk.ctx.at.shared.dom.monthlyattdcal;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.OverState;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.ErrorTimeInMonth;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hourspermonth.OneMonthTime;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public class OneMonthTimeTest {

    @Test
    public void getters() {
        OneMonthTime oneMonthTime = new OneMonthTime(new ErrorTimeInMonth(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        NtsAssert.invokeGetters(oneMonthTime);
    }

    @Test
    public void createTest_1() {
        NtsAssert.businessException("Msg_59", () -> {
            OneMonthTime.create(new ErrorTimeInMonth(new AgreementOneMonthTime(30),
                    new AgreementOneMonthTime(20)), new AgreementOneMonthTime(10));
        });
    }

    @Test
    public void createTest_2() {
        ErrorTimeInMonth errorTimeInMonth = new ErrorTimeInMonth(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30));

        OneMonthTime target = OneMonthTime.create(errorTimeInMonth,new AgreementOneMonthTime(50));

        Assert.assertEquals(errorTimeInMonth.getErrorTime(), target.getErrorTimeInMonth().getErrorTime());
        Assert.assertEquals(errorTimeInMonth.getAlarmTime(), target.getErrorTimeInMonth().getAlarmTime());
        Assert.assertEquals(new AgreementOneMonthTime(50), target.getUpperLimitTime());
    }

    @Test
    public void errorCheckTest_Normal() {
        OneMonthTime target = new OneMonthTime(new ErrorTimeInMonth(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        OverState result = target.errorCheck(new AttendanceTimeMonth(30));

        Assert.assertEquals(OverState.NORMAL.value,result.value);
    }

    @Test
    public void errorCheckTest_AlarmOver() {
        OneMonthTime target = new OneMonthTime(new ErrorTimeInMonth(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        OverState result = target.errorCheck(new AttendanceTimeMonth(40));

        Assert.assertEquals(OverState.ALARM_OVER.value,result.value);
    }

    @Test
    public void errorCheckTest_ErrorOver() {
        OneMonthTime target = new OneMonthTime(new ErrorTimeInMonth(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        OverState result = target.errorCheck(new AttendanceTimeMonth(50));

        Assert.assertEquals(OverState.ERROR_OVER.value,result.value);
    }

    @Test
    public void errorCheckTest_MaxTime() {
        OneMonthTime target = new OneMonthTime(new ErrorTimeInMonth(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        OverState result = target.errorCheck(new AttendanceTimeMonth(60));

        Assert.assertEquals( OverState.UPPER_LIMIT_OVER.value,result.value);
    }

    @Test
    public void checkErrorTimeExceededTest_1() {
        OneMonthTime target = new OneMonthTime(new ErrorTimeInMonth(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        Pair<Boolean, AgreementOneMonthTime> result = target.checkErrorTimeExceeded(new AgreementOneMonthTime(60));

        Assert.assertEquals(true,result.getLeft());
        Assert.assertEquals(new AgreementOneMonthTime(40),result.getRight());
    }

    @Test
    public void checkErrorTimeExceededTest_2() {
        OneMonthTime target = new OneMonthTime(new ErrorTimeInMonth(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        Pair<Boolean, AgreementOneMonthTime> result = target.checkErrorTimeExceeded(new AgreementOneMonthTime(35));

        Assert.assertEquals(false,result.getLeft());
        Assert.assertEquals(new AgreementOneMonthTime(40),result.getRight());
    }

    @Test
    public void calculateAlarmTimeTest_1() {
        OneMonthTime target = new OneMonthTime(new ErrorTimeInMonth(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        AgreementOneMonthTime result = target.calculateAlarmTime(new AgreementOneMonthTime(50));

        Assert.assertEquals(new AgreementOneMonthTime(40),result);
    }

    @Test
    public void calculateAlarmTimeTest_2() {
        OneMonthTime target = new OneMonthTime(new ErrorTimeInMonth(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        AgreementOneMonthTime result = target.calculateAlarmTime(new AgreementOneMonthTime(10));

        Assert.assertEquals(new AgreementOneMonthTime(0),result);
    }

}
