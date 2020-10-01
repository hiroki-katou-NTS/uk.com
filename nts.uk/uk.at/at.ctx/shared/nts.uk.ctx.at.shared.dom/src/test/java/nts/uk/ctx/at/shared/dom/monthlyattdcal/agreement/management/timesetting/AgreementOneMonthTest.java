package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreement.management.timesetting;

import nts.arc.enums.EnumAdaptor;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneMonth;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

public class AgreementOneMonthTest {
    @Test
    public void getters() {
        AgreementOneMonth agreementsOneYear = new AgreementOneMonth(
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20)),
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20))
        );
        NtsAssert.invokeGetters(agreementsOneYear);
    }

    @Test
    public void checkErrorTimeExceededTest() {

        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth(
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20)),
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20))
        );
        AgreementOneMonthTime agreementOneMonthTime = new AgreementOneMonthTime(20);
        Pair<Boolean, AgreementOneMonthTime> result = agreementsOneMonth.checkErrorTimeExceeded(agreementOneMonthTime);

        Assert.assertEquals(false, result.getLeft());
        Assert.assertEquals(new AgreementOneMonthTime(20).v(), result.getRight().v());
    }

    @Test
    public void calculateAlarmTimeTest() {

        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth(
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20)),
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20))
        );
        AgreementOneMonthTime agreementOneMonthTime = new AgreementOneMonthTime(20);

        AgreementOneMonthTime result = agreementsOneMonth.calculateAlarmTime(agreementOneMonthTime);

        Assert.assertEquals(new AgreementOneMonthTime(20).v(), result.v());
    }

    @Test
    public void checkError_01() {
        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth(
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20)),
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20))
        );
        AttendanceTimeMonth agreementTargetTime = new AttendanceTimeMonth(10);
        AttendanceTimeMonth hoursSubjectToLegalUpperLimit = new AttendanceTimeMonth(20);
        OneMonthErrorAlarmTime applicationTime = new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20));

        AgreementTimeStatusOfMonthly rs = agreementsOneMonth.checkError(agreementTargetTime, hoursSubjectToLegalUpperLimit, applicationTime);

        Assert.assertEquals(EnumAdaptor.valueOf(AgreementTimeStatusOfMonthly.NORMAL_SPECIAL.value, AgreementTimeStatusOfMonthly.class), rs);

    }

    @Test
    public void checkError_02() {
        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth(
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(30), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20)),
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20))
        );
        AttendanceTimeMonth agreementTargetTime = new AttendanceTimeMonth(10);
        AttendanceTimeMonth hoursSubjectToLegalUpperLimit = new AttendanceTimeMonth(25);
        OneMonthErrorAlarmTime applicationTime = new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20));

        AgreementTimeStatusOfMonthly rs = agreementsOneMonth.checkError(agreementTargetTime, hoursSubjectToLegalUpperLimit, applicationTime);

        Assert.assertEquals(EnumAdaptor.valueOf(AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM.value, AgreementTimeStatusOfMonthly.class), rs);

    }

    @Test
    public void checkError_03() {
        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth(
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(26), new AgreementOneMonthTime(25))
                        , new AgreementOneMonthTime(28)),
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(26), new AgreementOneMonthTime(25))
                        , new AgreementOneMonthTime(28))
        );
        AttendanceTimeMonth agreementTargetTime = new AttendanceTimeMonth(10);
        AttendanceTimeMonth hoursSubjectToLegalUpperLimit = new AttendanceTimeMonth(27);
        OneMonthErrorAlarmTime applicationTime = new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20));

        AgreementTimeStatusOfMonthly rs = agreementsOneMonth.checkError(agreementTargetTime, hoursSubjectToLegalUpperLimit, applicationTime);

        Assert.assertEquals(EnumAdaptor.valueOf(AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR.value, AgreementTimeStatusOfMonthly.class), rs);

    }

    @Test
    public void checkError_04() {
        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth(
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(26), new AgreementOneMonthTime(25))
                        , new AgreementOneMonthTime(26)),
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(26), new AgreementOneMonthTime(25))
                        , new AgreementOneMonthTime(26))
        );
        AttendanceTimeMonth agreementTargetTime = new AttendanceTimeMonth(10);
        AttendanceTimeMonth hoursSubjectToLegalUpperLimit = new AttendanceTimeMonth(27);
        OneMonthErrorAlarmTime applicationTime = new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20));

        AgreementTimeStatusOfMonthly rs = agreementsOneMonth.checkError(agreementTargetTime, hoursSubjectToLegalUpperLimit, applicationTime);

        Assert.assertEquals(EnumAdaptor.valueOf(AgreementTimeStatusOfMonthly.EXCESS_BG_GRAY.value, AgreementTimeStatusOfMonthly.class), rs);

    }

    @Test
    public void checkError_05() {
        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth(
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20)),
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20))
        );
        AttendanceTimeMonth agreementTargetTime = new AttendanceTimeMonth(10);
        AttendanceTimeMonth hoursSubjectToLegalUpperLimit = new AttendanceTimeMonth(20);

        AgreementTimeStatusOfMonthly rs = agreementsOneMonth.checkError(agreementTargetTime, hoursSubjectToLegalUpperLimit, null);

        Assert.assertEquals(EnumAdaptor.valueOf(AgreementTimeStatusOfMonthly.NORMAL.value, AgreementTimeStatusOfMonthly.class), rs);

    }

    @Test
    public void checkError_06() {
        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth(
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20)),
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(25), new AgreementOneMonthTime(10))
                        , new AgreementOneMonthTime(10))
        );
        AttendanceTimeMonth agreementTargetTime = new AttendanceTimeMonth(25);
        AttendanceTimeMonth hoursSubjectToLegalUpperLimit = new AttendanceTimeMonth(20);

        AgreementTimeStatusOfMonthly rs = agreementsOneMonth.checkError(agreementTargetTime, hoursSubjectToLegalUpperLimit, null);

        Assert.assertEquals(EnumAdaptor.valueOf(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR.value, AgreementTimeStatusOfMonthly.class), rs);

    }
    @Test
    public void checkError_07() {
        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth(
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(27), new AgreementOneMonthTime(24))
                        , new AgreementOneMonthTime(20)),
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(25), new AgreementOneMonthTime(10))
                        , new AgreementOneMonthTime(10))
        );
        AttendanceTimeMonth agreementTargetTime = new AttendanceTimeMonth(25);
        AttendanceTimeMonth hoursSubjectToLegalUpperLimit = new AttendanceTimeMonth(20);

        AgreementTimeStatusOfMonthly rs = agreementsOneMonth.checkError(agreementTargetTime, hoursSubjectToLegalUpperLimit, null);

        Assert.assertEquals(EnumAdaptor.valueOf(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM.value, AgreementTimeStatusOfMonthly.class), rs);

    }
    @Test
    public void checkError_08() {
        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth(
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(27), new AgreementOneMonthTime(24))
                        , new AgreementOneMonthTime(29)),
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(25), new AgreementOneMonthTime(29))
                        , new AgreementOneMonthTime(29))
        );
        AttendanceTimeMonth agreementTargetTime = new AttendanceTimeMonth(28);
        AttendanceTimeMonth hoursSubjectToLegalUpperLimit = new AttendanceTimeMonth(20);

        AgreementTimeStatusOfMonthly rs = agreementsOneMonth.checkError(agreementTargetTime, hoursSubjectToLegalUpperLimit, null);

        Assert.assertEquals(EnumAdaptor.valueOf(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR.value, AgreementTimeStatusOfMonthly.class), rs);

    }
    @Test
    public void checkError_09() {
        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth(
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(27), new AgreementOneMonthTime(24))
                        , new AgreementOneMonthTime(29)),
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(25), new AgreementOneMonthTime(29))
                        , new AgreementOneMonthTime(29))
        );
        AttendanceTimeMonth agreementTargetTime = new AttendanceTimeMonth(28);
        AttendanceTimeMonth hoursSubjectToLegalUpperLimit = new AttendanceTimeMonth(20);
        OneMonthErrorAlarmTime applicationTime = new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20));

        AgreementTimeStatusOfMonthly rs = agreementsOneMonth.checkError(agreementTargetTime, hoursSubjectToLegalUpperLimit, applicationTime);

        Assert.assertEquals(EnumAdaptor.valueOf(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP.value, AgreementTimeStatusOfMonthly.class), rs);

    }
    @Test
    public void checkError_10() {
        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth(
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(27), new AgreementOneMonthTime(24))
                        , new AgreementOneMonthTime(29)),
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(25), new AgreementOneMonthTime(29))
                        , new AgreementOneMonthTime(29))
        );
        AttendanceTimeMonth agreementTargetTime = new AttendanceTimeMonth(27);
        AttendanceTimeMonth hoursSubjectToLegalUpperLimit = new AttendanceTimeMonth(20);
        OneMonthErrorAlarmTime applicationTime = new OneMonthErrorAlarmTime(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20));

        AgreementTimeStatusOfMonthly rs = agreementsOneMonth.checkError(agreementTargetTime, hoursSubjectToLegalUpperLimit, applicationTime);

        Assert.assertEquals(EnumAdaptor.valueOf(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM_SP.value, AgreementTimeStatusOfMonthly.class), rs);

    }
    @Test
    public void checkError_11() {
        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth(
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(27), new AgreementOneMonthTime(24))
                        , new AgreementOneMonthTime(29)),
                new OneMonthTime(new OneMonthErrorAlarmTime(new AgreementOneMonthTime(26), new AgreementOneMonthTime(29))
                        , new AgreementOneMonthTime(29))
        );
        AttendanceTimeMonth agreementTargetTime = new AttendanceTimeMonth(28);
        AttendanceTimeMonth hoursSubjectToLegalUpperLimit = new AttendanceTimeMonth(20);

        AgreementTimeStatusOfMonthly rs = agreementsOneMonth.checkError(agreementTargetTime, hoursSubjectToLegalUpperLimit, null);

        Assert.assertEquals(EnumAdaptor.valueOf(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR.value, AgreementTimeStatusOfMonthly.class), rs);

    }
}
