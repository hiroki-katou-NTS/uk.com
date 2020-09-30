package nts.uk.ctx.at.shared.dom.monthlyattdcal.agreement.management.timesetting;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneYear;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertEquals(new AgreementOneYearTime(50).v(),result.getRight().v());
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

}
