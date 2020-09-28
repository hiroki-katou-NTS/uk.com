package nts.uk.ctx.at.shared.dom.monthlyattdcal;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.ErrorTimeInYear;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.hoursperyear.OneYearTime;
import nts.uk.ctx.at.shared.dom.standardtime.AgreementsOneYear;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class AgreementsOneYearTest {

    @Test
    public void getters() {
        AgreementsOneYear agreementsOneYear = new AgreementsOneYear(
                new OneYearTime(new ErrorTimeInYear(new AgreementOneYearTime(20),new AgreementOneYearTime(20))
                        ,new AgreementOneYearTime(20)),
                new OneYearTime(new ErrorTimeInYear(new AgreementOneYearTime(20),new AgreementOneYearTime(20))
                        ,new AgreementOneYearTime(20))
        );
        NtsAssert.invokeGetters(agreementsOneYear);
    }
    @Test
    public void checkErrorTimeExceededTest() {

        OneYearTime oneYearTime = OneYearTime.create(new ErrorTimeInYear(new AgreementOneYearTime(50),new AgreementOneYearTime(20))
                ,new AgreementOneYearTime(60));
        AgreementsOneYear agreementsOneYear = new AgreementsOneYear(
                oneYearTime,
                oneYearTime
        );
        Pair<Boolean, AgreementOneYearTime> result =  agreementsOneYear.checkErrorTimeExceeded(new AgreementOneYearTime(60));

        Assert.assertEquals(true,result.getLeft());
        Assert.assertEquals(new AgreementOneMonthTime(50).v(),result.getRight().v());
    }

    @Test
    public void calculateAlarmTimeTest() {

        OneYearTime oneYearTime = OneYearTime.create(new ErrorTimeInYear(new AgreementOneYearTime(20),new AgreementOneYearTime(20))
                ,new AgreementOneYearTime(20));
        AgreementsOneYear agreementsOneYear = new AgreementsOneYear(
                oneYearTime,
                oneYearTime
        );
        AgreementOneYearTime result =  agreementsOneYear.calculateAlarmTime(oneYearTime.getUpperLimitTime());

        Assert.assertEquals(new AgreementOneMonthTime(20).v(),result.v());
    }

}
