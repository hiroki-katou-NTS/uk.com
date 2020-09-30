package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreement.management.timesetting;


import nts.arc.testing.assertion.NtsAssert;
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
                 OneMonthTime.of( OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20)),
                 OneMonthTime.of( OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20))
        );
        NtsAssert.invokeGetters(agreementsOneYear);
    }

    @Test
    public void checkErrorTimeExceededTest() {

        AgreementOneMonth agreementsOneMonth = new AgreementOneMonth(
                 OneMonthTime.of( OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20)),
                 OneMonthTime.of( OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
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
                 OneMonthTime.of( OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20)),
                 OneMonthTime.of( OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(20), new AgreementOneMonthTime(20))
                        , new AgreementOneMonthTime(20))
        );
        AgreementOneMonthTime agreementOneMonthTime = new AgreementOneMonthTime(20);

        AgreementOneMonthTime result = agreementsOneMonth.calculateAlarmTime(agreementOneMonthTime);

        Assert.assertEquals(new AgreementOneMonthTime(20).v(), result.v());
    }

}
