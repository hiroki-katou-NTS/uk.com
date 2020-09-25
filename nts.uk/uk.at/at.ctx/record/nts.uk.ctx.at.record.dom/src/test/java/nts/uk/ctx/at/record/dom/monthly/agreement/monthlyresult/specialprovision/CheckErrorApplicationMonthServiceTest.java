package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.MonthlyAppContent;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgreementExcessInfo;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.*;
import nts.uk.ctx.at.shared.dom.monthlyattdcal.agreementresult.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneYear;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(JMockit.class)
public class CheckErrorApplicationMonthServiceTest {

    @Injectable
    CheckErrorApplicationMonthService.Require require;

    @Mocked
    AppContexts appContexts;

    /**
     * Init Appcontext
     */
    @Before
    public void init() {
        new Expectations() {{
            AppContexts.user().companyId();
            result = "CID";

            AppContexts.user().employeeId();
            result = "SID";
        }};
    }

    /**
     * チェックする TestCase 1
     * 1: [R-1] getMaxAverageMulti return null
     * 2: [R-2] timeYear return null
     * 3: [R-3] algorithm return null
     */
    @Test
    public void check_1() {
        // SpecialProvisionsOfAgreement reason = SpecialProvisionsOfAgreementTest.createNewDomain();
		ReasonsForAgreement reason = new ReasonsForAgreement("ReasonsForAgreement");
        MonthlyAppContent monthlyAppContent = new MonthlyAppContent("applicant", new YearMonth(202009),
                new AgreementOneMonthTime(1), new AgreementOneMonthTime(2), reason);

        // Mock up
        new Expectations() {{

            require.algorithm(monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = null;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        Assert.assertEquals(data.size(), 1);
    }

    /**
     * チェックする TestCase 2
     * 1: [R-1] getMaxAverageMulti not null
     * 2: [R-2] timeYear return null
     * 3: [R-3] algorithm return null
     */
    @Test
    public void check_2() {
        // SpecialProvisionsOfAgreement reason = SpecialProvisionsOfAgreementTest.createNewDomain();
		ReasonsForAgreement reason = new ReasonsForAgreement("ReasonsForAgreement");
        MonthlyAppContent monthlyAppContent = new MonthlyAppContent("applicant", new YearMonth(202009),
                new AgreementOneMonthTime(1), new AgreementOneMonthTime(2), reason);

        // Mock up
        new Expectations() {{

            AgreMaxAverageTime agreMaxAverageTime = AgreMaxAverageTime.of( new YearMonthPeriod(new YearMonth(202008), new YearMonth(202009)),new AttendanceTimeYear(0),AgreMaxTimeStatusOfMonthly.EXCESS_MAXTIME);
            AgreMaxAverageTimeMulti agreMaxAverageTimeMulti = new AgreMaxAverageTimeMulti();
            agreMaxAverageTimeMulti.getAverageTimeList().add(agreMaxAverageTime);
            require.getMaxAverageMulti("CID", "SID", GeneralDate.today(), monthlyAppContent.getYm());
            result = Optional.of(agreMaxAverageTimeMulti);

            require.algorithm(monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = null;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        Assert.assertEquals(data.size(), 2);
    }

    /**
     * チェックする TestCase 3
     * 1: [R-1] getMaxAverageMulti not null
     * 2: [R-2] timeYear return not null
     * 3: [R-3] algorithm return null
     */
    @Test
    public void check_3() {
        // SpecialProvisionsOfAgreement reason = SpecialProvisionsOfAgreementTest.createNewDomain();
		ReasonsForAgreement reason = new ReasonsForAgreement("ReasonsForAgreement");
        MonthlyAppContent monthlyAppContent = new MonthlyAppContent("applicant", new YearMonth(202009),
                new AgreementOneMonthTime(1), new AgreementOneMonthTime(2), reason);

        // Mock up
        new Expectations() {{

            AgreementTimeYear agreementTimeYear = AgreementTimeYear.of( new LimitOneYear(0),new AttendanceTimeYear(0), AgreTimeYearStatusOfMonthly.EXCESS_LIMIT);
            require.timeYear("CID", "SID", GeneralDate.today(), new Year(monthlyAppContent.getYm().year()));
            result = Optional.of(agreementTimeYear);

            require.algorithm(monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = null;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        Assert.assertEquals(data.size(), 2);
    }

    /**
     * チェックする TestCase 4
     * 1: [R-1] getMaxAverageMulti not null
     * 2: [R-2] timeYear return not null
     * 3: [R-3] algorithm return not null
     */
    @Test
    public void check_4() {
        // SpecialProvisionsOfAgreement reason = SpecialProvisionsOfAgreementTest.createNewDomain();
		ReasonsForAgreement reason = new ReasonsForAgreement("ReasonsForAgreement");
        MonthlyAppContent monthlyAppContent = new MonthlyAppContent("applicant", new YearMonth(202009),
                new AgreementOneMonthTime(1), new AgreementOneMonthTime(2), reason);

        // Mock up
        new Expectations() {{
            AgreementExcessInfo agreementExcessInfo = AgreementExcessInfo.of(0,0, Arrays.asList(new YearMonth(202009)));
            require.algorithm(monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = agreementExcessInfo;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        Assert.assertEquals(data.size(), 3);
    }

}
