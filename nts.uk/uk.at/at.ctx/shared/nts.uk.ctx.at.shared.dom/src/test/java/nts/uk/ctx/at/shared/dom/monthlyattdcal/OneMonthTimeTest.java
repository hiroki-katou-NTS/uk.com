package nts.uk.ctx.at.shared.dom.monthlyattdcal;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ExcessState;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public class OneMonthTimeTest {

    @Test
    public void getters() {
        OneMonthTime oneMonthTime = new OneMonthTime();
        NtsAssert.invokeGetters(oneMonthTime);
    }

    @Test
    public void createTest_1() {
        NtsAssert.businessException("Msg_59", () -> {
            OneMonthErrorAlarmTime oneMonthErrorAlarmTime = OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(30),
                    new AgreementOneMonthTime(20));
            OneMonthTime.of(oneMonthErrorAlarmTime, new AgreementOneMonthTime(10));
        });
    }

    @Test
    public void createTest_2() {
        OneMonthErrorAlarmTime errorTimeInMonth = OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30));

        OneMonthTime target = OneMonthTime.of(errorTimeInMonth,new AgreementOneMonthTime(50));

        Assert.assertEquals(errorTimeInMonth.getError(), target.getErAlTime().getError());
        Assert.assertEquals(errorTimeInMonth.getAlarm(), target.getErAlTime().getAlarm());
        Assert.assertEquals(new AgreementOneMonthTime(50), target.getUpperLimit());
    }

    @Test
    public void errorCheckTest_Normal() {
        OneMonthTime target = OneMonthTime.of(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        ExcessState result = target.check(new AttendanceTimeMonth(30));

        Assert.assertEquals(ExcessState.NORMAL.value,result.value);
    }

    @Test
    public void errorCheckTest_AlarmOver() {
        OneMonthTime target = OneMonthTime.of(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        ExcessState result = target.check(new AttendanceTimeMonth(40));

        Assert.assertEquals(ExcessState.ALARM_OVER.value,result.value);
    }

    @Test
    public void errorCheckTest_ErrorOver() {
        OneMonthTime target = OneMonthTime.of(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        ExcessState result = target.check(new AttendanceTimeMonth(50));

        Assert.assertEquals(ExcessState.ERROR_OVER.value,result.value);
    }

    @Test
    public void errorCheckTest_MaxTime() {
        OneMonthTime target = OneMonthTime.of(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        ExcessState result = target.check(new AttendanceTimeMonth(60));

        Assert.assertEquals( ExcessState.UPPER_LIMIT_OVER.value,result.value);
    }

    @Test
    public void checkErrorTimeExceededTest_1() {
        OneMonthTime target = OneMonthTime.of(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        Pair<Boolean, AgreementOneMonthTime> result = target.isErrorTimeOver(new AgreementOneMonthTime(60));

        Assert.assertEquals(true,result.getLeft());
        Assert.assertEquals(new AgreementOneMonthTime(40),result.getRight());
    }

    @Test
    public void checkErrorTimeExceededTest_2() {
        OneMonthTime target = OneMonthTime.of(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        Pair<Boolean, AgreementOneMonthTime> result = target.isErrorTimeOver(new AgreementOneMonthTime(35));

        Assert.assertEquals(false,result.getLeft());
        Assert.assertEquals(new AgreementOneMonthTime(40),result.getRight());
    }

    @Test
    public void calculateAlarmTimeTest_1() {
        OneMonthTime target = OneMonthTime.of(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        AgreementOneMonthTime result = target.calcAlarmTime(new AgreementOneMonthTime(50));

        Assert.assertEquals(new AgreementOneMonthTime(40),result);
    }

    @Test
    public void calculateAlarmTimeTest_2() {
        OneMonthTime target = OneMonthTime.of(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(40), new AgreementOneMonthTime(30)),
                new AgreementOneMonthTime(50));
        AgreementOneMonthTime result = target.calcAlarmTime(new AgreementOneMonthTime(10));

        Assert.assertEquals(new AgreementOneMonthTime(0),result);
    }

}
