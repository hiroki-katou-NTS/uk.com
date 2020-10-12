package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.MonthlyAppContent;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgreementExcessInfo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.*;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearTime;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
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
                new AgreementOneMonthTime(1), Optional.of(new AgreementOneMonthTime(2)), reason);

        // Mock up
        new Expectations() {{

            require.algorithm(monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = null;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        assertThat(data.size()).isEqualTo(1);
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
                new AgreementOneMonthTime(1), Optional.of(new AgreementOneMonthTime(2)), reason);

        // Mock up
        new Expectations() {{

            AgreMaxAverageTime agreMaxAverageTime = AgreMaxAverageTime.of( new YearMonthPeriod(new YearMonth(202008), new YearMonth(202009)),new AttendanceTimeYear(0), AgreMaxTimeStatusOfMonthly.ERROR_OVER);
            AgreMaxAverageTimeMulti agreMaxAverageTimeMulti = new AgreMaxAverageTimeMulti();
            agreMaxAverageTimeMulti.getAverageTimes().add(agreMaxAverageTime);

            val agreementTimes = new HashMap<YearMonth, AgreementOneMonthTime>();
            agreementTimes.put(monthlyAppContent.getYm(), monthlyAppContent.getErrTime());
            require.getMaxAverageMulti(monthlyAppContent.getApplicant(), GeneralDate.today(), monthlyAppContent.getYm(), agreementTimes);
            result = agreMaxAverageTimeMulti;

            require.algorithm(monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = null;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        assertThat(data.size()).isEqualTo(2);
    }

    /**
     * チェックする TestCase 3
     * 1: [R-1] getMaxAverageMulti return null
     * 2: [R-2] timeYear return not null
     * 3: [R-3] algorithm return null
     */
    @Test
    public void check_3() {
        // SpecialProvisionsOfAgreement reason = SpecialProvisionsOfAgreementTest.createNewDomain();
		ReasonsForAgreement reason = new ReasonsForAgreement("ReasonsForAgreement");
        MonthlyAppContent monthlyAppContent = new MonthlyAppContent("applicant", new YearMonth(202009),
                new AgreementOneMonthTime(1), Optional.of(new AgreementOneMonthTime(2)), reason);

        // Mock up
        new Expectations() {{
            AgreementTimeOfYear limitTime = AgreementTimeOfYear.of(new AgreementOneYearTime(10),new OneYearTime());
            AgreementTimeOfYear recordTime = AgreementTimeOfYear.of(new AgreementOneYearTime(20),new OneYearTime());
            AgreementTimeYear agreementTimeYear = AgreementTimeYear.of( limitTime,recordTime, AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR);

            val agreementTimes = new HashMap<YearMonth, AgreementOneMonthTime>();
            agreementTimes.put(monthlyAppContent.getYm(), monthlyAppContent.getErrTime());
            require.timeYear(monthlyAppContent.getApplicant(), GeneralDate.today(), new Year(monthlyAppContent.getYm().year()), agreementTimes);
            result = agreementTimeYear;

            require.algorithm(monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = null;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        assertThat(data.size()).isEqualTo(2);
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
                new AgreementOneMonthTime(1), Optional.of(new AgreementOneMonthTime(2)), reason);

        // Mock up
        new Expectations() {{
            AgreMaxAverageTime agreMaxAverageTime = AgreMaxAverageTime.of( new YearMonthPeriod(new YearMonth(202008), new YearMonth(202009)),new AttendanceTimeYear(0), AgreMaxTimeStatusOfMonthly.ERROR_OVER);
            AgreMaxAverageTimeMulti agreMaxAverageTimeMulti = new AgreMaxAverageTimeMulti();
            agreMaxAverageTimeMulti.getAverageTimes().add(agreMaxAverageTime);

            val agreementTimes = new HashMap<YearMonth, AgreementOneMonthTime>();
            agreementTimes.put(monthlyAppContent.getYm(), monthlyAppContent.getErrTime());

            require.getMaxAverageMulti(monthlyAppContent.getApplicant(), GeneralDate.today(), monthlyAppContent.getYm(),agreementTimes);
            result = agreMaxAverageTimeMulti;

            AgreementTimeOfYear limitTime = AgreementTimeOfYear.of(new AgreementOneYearTime(10),new OneYearTime());
            AgreementTimeOfYear recordTime = AgreementTimeOfYear.of(new AgreementOneYearTime(20),new OneYearTime());
            AgreementTimeYear agreementTimeYear = AgreementTimeYear.of( limitTime,recordTime, AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR);
            require.timeYear(monthlyAppContent.getApplicant(), GeneralDate.today(), new Year(monthlyAppContent.getYm().year()), agreementTimes);
            result = agreementTimeYear;

            AgreementExcessInfo agreementExcessInfo = AgreementExcessInfo.of(0,0, Arrays.asList(new YearMonth(201909)));
            require.algorithm(monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = agreementExcessInfo;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        assertThat(data.size()).isEqualTo(4);
    }

}
