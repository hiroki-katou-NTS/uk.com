package nts.uk.ctx.at.record.dom.monthly.agreement.monthlyresult.specialprovision;

import lombok.val;
import mockit.*;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.approver.MonthlyAppContent;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.AgreementExcessInfo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.*;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.oneyear.OneYearTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOverMaxTimes;
import nts.uk.shr.com.context.AppContexts;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

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
        ReasonsForAgreement reason = new ReasonsForAgreement("ReasonsForAgreement");
        MonthlyAppContent monthlyAppContent = new MonthlyAppContent("applicant", new YearMonth(202009),
                new AgreementOneMonthTime(1), Optional.of(new AgreementOneMonthTime(2)), reason);

        // Mock up
        val errCheckResult = new ImmutablePair<Boolean, AgreementOneMonthTime>(false, new AgreementOneMonthTime(0));
        new MockUp<AgreementOneMonth>() {
            @Mock
            public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {
                return errCheckResult;
            }
        };

        new Expectations() {{
            require.algorithm(require, monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = null;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        assertThat(data.size()).isEqualTo(0);
    }

    /**
     * チェックする TestCase 2
     * 1: [R-1] getMaxAverageMulti not null with errorClassification = TWO_MONTH_MAX_TIME
     * 2: [R-2] timeYear return null
     * 3: [R-3] algorithm return null
     */
    @Test
    public void getMaxAverageMultiTest_1() {
        ReasonsForAgreement reason = new ReasonsForAgreement("ReasonsForAgreement");
        MonthlyAppContent monthlyAppContent = new MonthlyAppContent("applicant", new YearMonth(202009),
                new AgreementOneMonthTime(1), Optional.of(new AgreementOneMonthTime(2)), reason);

        // Mock up
        val errCheckResult = new ImmutablePair<Boolean, AgreementOneMonthTime>(false, new AgreementOneMonthTime(0));
        new MockUp<AgreementOneMonth>() {
            @Mock
            public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {
                return errCheckResult;
            }
        };

        new Expectations() {{

            AgreMaxAverageTime agreMaxAverageTime = AgreMaxAverageTime.of(new YearMonthPeriod(new YearMonth(202008), new YearMonth(202009)), new AttendanceTimeYear(0), AgreMaxTimeStatusOfMonthly.ERROR_OVER);
            AgreMaxAverageTimeMulti agreMaxAverageTimeMulti = new AgreMaxAverageTimeMulti();
            agreMaxAverageTimeMulti.getAverageTimes().add(agreMaxAverageTime);

            val agreementTimes = new HashMap<YearMonth, AttendanceTimeMonth>();
            agreementTimes.put(monthlyAppContent.getYm(), new AttendanceTimeMonth(monthlyAppContent.getErrTime().v()));
            require.getMaxAverageMulti(monthlyAppContent.getApplicant(), GeneralDate.today(), monthlyAppContent.getYm(), agreementTimes);
            result = agreMaxAverageTimeMulti;

            require.algorithm(require, monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = null;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        assertThat(data.size()).isEqualTo(1);
        assertThat(data)
                .extracting(
                        d -> d.getErrorClassification(),
                        d -> d.getMaximumTimeMonth(),
                        d -> d.getMaximumTimeYear(),
                        d -> d.getExceedUpperLimit())
                .containsExactly(
                        tuple(ErrorClassification.TWO_MONTH_MAX_TIME, Optional.of(new AgreementOneMonthTime(0)),Optional.empty(), Optional.empty())
                );
    }

    /**
     * チェックする TestCase 2
     * 1: [R-1] getMaxAverageMulti not null with errorClassification = THREE_MONTH_MAX_TIME
     * 2: [R-2] timeYear return null
     * 3: [R-3] algorithm return null
     */
    @Test
    public void getMaxAverageMultiTest_2() {
        ReasonsForAgreement reason = new ReasonsForAgreement("ReasonsForAgreement");
        MonthlyAppContent monthlyAppContent = new MonthlyAppContent("applicant", new YearMonth(202009),
                new AgreementOneMonthTime(1), Optional.of(new AgreementOneMonthTime(2)), reason);

        // Mock up
        val errCheckResult = new ImmutablePair<Boolean, AgreementOneMonthTime>(false, new AgreementOneMonthTime(0));
        new MockUp<AgreementOneMonth>() {
            @Mock
            public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {
                return errCheckResult;
            }
        };

        new Expectations() {{

            AgreMaxAverageTime agreMaxAverageTime = AgreMaxAverageTime.of(new YearMonthPeriod(new YearMonth(202007), new YearMonth(202009)), new AttendanceTimeYear(0), AgreMaxTimeStatusOfMonthly.ERROR_OVER);
            AgreMaxAverageTimeMulti agreMaxAverageTimeMulti = new AgreMaxAverageTimeMulti();
            agreMaxAverageTimeMulti.getAverageTimes().add(agreMaxAverageTime);

            val agreementTimes = new HashMap<YearMonth, AttendanceTimeMonth>();
            agreementTimes.put(monthlyAppContent.getYm(), new AttendanceTimeMonth(monthlyAppContent.getErrTime().v()));
            require.getMaxAverageMulti(monthlyAppContent.getApplicant(), GeneralDate.today(), monthlyAppContent.getYm(), agreementTimes);
            result = agreMaxAverageTimeMulti;

            require.algorithm(require, monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = null;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        assertThat(data.size()).isEqualTo(1);
        assertThat(data)
                .extracting(
                        d -> d.getErrorClassification(),
                        d -> d.getMaximumTimeMonth(),
                        d -> d.getMaximumTimeYear(),
                        d -> d.getExceedUpperLimit())
                .containsExactly(
                        tuple(ErrorClassification.THREE_MONTH_MAX_TIME, Optional.of(new AgreementOneMonthTime(0)),Optional.empty(), Optional.empty())
                );
    }

    /**
     * チェックする TestCase 2
     * 1: [R-1] getMaxAverageMulti not null with errorClassification = FOUR_MONTH_MAX_TIME
     * 2: [R-2] timeYear return null
     * 3: [R-3] algorithm return null
     */
    @Test
    public void getMaxAverageMultiTest_3() {
        ReasonsForAgreement reason = new ReasonsForAgreement("ReasonsForAgreement");
        MonthlyAppContent monthlyAppContent = new MonthlyAppContent("applicant", new YearMonth(202009),
                new AgreementOneMonthTime(1), Optional.of(new AgreementOneMonthTime(2)), reason);

        // Mock up
        val errCheckResult = new ImmutablePair<Boolean, AgreementOneMonthTime>(false, new AgreementOneMonthTime(0));
        new MockUp<AgreementOneMonth>() {
            @Mock
            public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {
                return errCheckResult;
            }
        };

        new Expectations() {{

            AgreMaxAverageTime agreMaxAverageTime = AgreMaxAverageTime.of(new YearMonthPeriod(new YearMonth(202006), new YearMonth(202009)), new AttendanceTimeYear(0), AgreMaxTimeStatusOfMonthly.ERROR_OVER);
            AgreMaxAverageTimeMulti agreMaxAverageTimeMulti = new AgreMaxAverageTimeMulti();
            agreMaxAverageTimeMulti.getAverageTimes().add(agreMaxAverageTime);

            val agreementTimes = new HashMap<YearMonth, AttendanceTimeMonth>();
            agreementTimes.put(monthlyAppContent.getYm(), new AttendanceTimeMonth(monthlyAppContent.getErrTime().v()));
            require.getMaxAverageMulti(monthlyAppContent.getApplicant(), GeneralDate.today(), monthlyAppContent.getYm(), agreementTimes);
            result = agreMaxAverageTimeMulti;

            require.algorithm(require, monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = null;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        assertThat(data.size()).isEqualTo(1);
        assertThat(data)
                .extracting(
                        d -> d.getErrorClassification(),
                        d -> d.getMaximumTimeMonth(),
                        d -> d.getMaximumTimeYear(),
                        d -> d.getExceedUpperLimit())
                .containsExactly(
                        tuple(ErrorClassification.FOUR_MONTH_MAX_TIME, Optional.of(new AgreementOneMonthTime(0)),Optional.empty(), Optional.empty())
                );
    }

    /**
     * チェックする TestCase 2
     * 1: [R-1] getMaxAverageMulti not null with errorClassification = FIVE_MONTH_MAX_TIME
     * 2: [R-2] timeYear return null
     * 3: [R-3] algorithm return null
     */
    @Test
    public void getMaxAverageMultiTest_4() {
        ReasonsForAgreement reason = new ReasonsForAgreement("ReasonsForAgreement");
        MonthlyAppContent monthlyAppContent = new MonthlyAppContent("applicant", new YearMonth(202009),
                new AgreementOneMonthTime(1), Optional.of(new AgreementOneMonthTime(2)), reason);

        // Mock up
        val errCheckResult = new ImmutablePair<Boolean, AgreementOneMonthTime>(false, new AgreementOneMonthTime(0));
        new MockUp<AgreementOneMonth>() {
            @Mock
            public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {
                return errCheckResult;
            }
        };

        new Expectations() {{

            AgreMaxAverageTime agreMaxAverageTime = AgreMaxAverageTime.of(new YearMonthPeriod(new YearMonth(202005), new YearMonth(202009)), new AttendanceTimeYear(0), AgreMaxTimeStatusOfMonthly.ERROR_OVER);
            AgreMaxAverageTimeMulti agreMaxAverageTimeMulti = new AgreMaxAverageTimeMulti();
            agreMaxAverageTimeMulti.getAverageTimes().add(agreMaxAverageTime);

            val agreementTimes = new HashMap<YearMonth, AttendanceTimeMonth>();
            agreementTimes.put(monthlyAppContent.getYm(), new AttendanceTimeMonth(monthlyAppContent.getErrTime().v()));
            require.getMaxAverageMulti(monthlyAppContent.getApplicant(), GeneralDate.today(), monthlyAppContent.getYm(), agreementTimes);
            result = agreMaxAverageTimeMulti;

            require.algorithm(require, monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = null;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        assertThat(data.size()).isEqualTo(1);
        assertThat(data)
                .extracting(
                        d -> d.getErrorClassification(),
                        d -> d.getMaximumTimeMonth(),
                        d -> d.getMaximumTimeYear(),
                        d -> d.getExceedUpperLimit())
                .containsExactly(
                        tuple(ErrorClassification.FIVE_MONTH_MAX_TIME, Optional.of(new AgreementOneMonthTime(0)),Optional.empty(), Optional.empty())
                );
    }

    /**
     * チェックする TestCase 2
     * 1: [R-1] getMaxAverageMulti not null with errorClassification = SIX_MONTH_MAX_TIME
     * 2: [R-2] timeYear return null
     * 3: [R-3] algorithm return null
     */
    @Test
    public void getMaxAverageMultiTest_5() {
        ReasonsForAgreement reason = new ReasonsForAgreement("ReasonsForAgreement");
        MonthlyAppContent monthlyAppContent = new MonthlyAppContent("applicant", new YearMonth(202009),
                new AgreementOneMonthTime(1), Optional.of(new AgreementOneMonthTime(2)), reason);

        // Mock up
        val errCheckResult = new ImmutablePair<Boolean, AgreementOneMonthTime>(false, new AgreementOneMonthTime(0));
        new MockUp<AgreementOneMonth>() {
            @Mock
            public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {
                return errCheckResult;
            }
        };

        new Expectations() {{

            AgreMaxAverageTime agreMaxAverageTime = AgreMaxAverageTime.of(new YearMonthPeriod(new YearMonth(202004), new YearMonth(202009)), new AttendanceTimeYear(0), AgreMaxTimeStatusOfMonthly.ERROR_OVER);
            AgreMaxAverageTimeMulti agreMaxAverageTimeMulti = new AgreMaxAverageTimeMulti();
            agreMaxAverageTimeMulti.getAverageTimes().add(agreMaxAverageTime);

            val agreementTimes = new HashMap<YearMonth, AttendanceTimeMonth>();
            agreementTimes.put(monthlyAppContent.getYm(), new AttendanceTimeMonth(monthlyAppContent.getErrTime().v()));
            require.getMaxAverageMulti(monthlyAppContent.getApplicant(), GeneralDate.today(), monthlyAppContent.getYm(), agreementTimes);
            result = agreMaxAverageTimeMulti;

            require.algorithm(require, monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = null;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        assertThat(data.size()).isEqualTo(1);
        assertThat(data)
                .extracting(
                        d -> d.getErrorClassification(),
                        d -> d.getMaximumTimeMonth(),
                        d -> d.getMaximumTimeYear(),
                        d -> d.getExceedUpperLimit())
                .containsExactly(
                        tuple(ErrorClassification.SIX_MONTH_MAX_TIME, Optional.of(new AgreementOneMonthTime(0)),Optional.empty(), Optional.empty())
                );
    }

    /**
     * チェックする TestCase 2
     * 1: [R-1] getMaxAverageMulti not null with errorClassification = empty
     * 2: [R-2] timeYear return null
     * 3: [R-3] algorithm return null
     */
    @Test
    public void getMaxAverageMultiTest_6() {
        ReasonsForAgreement reason = new ReasonsForAgreement("ReasonsForAgreement");
        MonthlyAppContent monthlyAppContent = new MonthlyAppContent("applicant", new YearMonth(202009),
                new AgreementOneMonthTime(1), Optional.of(new AgreementOneMonthTime(2)), reason);

        // Mock up
        val errCheckResult = new ImmutablePair<Boolean, AgreementOneMonthTime>(false, new AgreementOneMonthTime(0));
        new MockUp<AgreementOneMonth>() {
            @Mock
            public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {
                return errCheckResult;
            }
        };
        new Expectations() {{

            AgreMaxAverageTime agreMaxAverageTime = AgreMaxAverageTime.of(new YearMonthPeriod(new YearMonth(202009), new YearMonth(202009)), new AttendanceTimeYear(0), AgreMaxTimeStatusOfMonthly.ERROR_OVER);
            AgreMaxAverageTimeMulti agreMaxAverageTimeMulti = new AgreMaxAverageTimeMulti();
            agreMaxAverageTimeMulti.getAverageTimes().add(agreMaxAverageTime);

            val agreementTimes = new HashMap<YearMonth, AttendanceTimeMonth>();
            agreementTimes.put(monthlyAppContent.getYm(), new AttendanceTimeMonth(monthlyAppContent.getErrTime().v()));
            require.getMaxAverageMulti(monthlyAppContent.getApplicant(), GeneralDate.today(), monthlyAppContent.getYm(), agreementTimes);
            result = agreMaxAverageTimeMulti;

            require.algorithm(require, monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = null;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        assertThat(data.size()).isEqualTo(0);
    }

    /**
     * チェックする TestCase 3
     * 1: [R-1] getMaxAverageMulti return null
     * 2: [R-2] timeYear return not null
     * 3: [R-3] algorithm return null
     */
    @Test
    public void check_3() {
        ReasonsForAgreement reason = new ReasonsForAgreement("ReasonsForAgreement");
        MonthlyAppContent monthlyAppContent = new MonthlyAppContent("applicant", new YearMonth(202009),
                new AgreementOneMonthTime(1), Optional.of(new AgreementOneMonthTime(2)), reason);

        // Mock up
        val errCheckResult = new ImmutablePair<Boolean, AgreementOneMonthTime>(false, new AgreementOneMonthTime(0));
        new MockUp<AgreementOneMonth>() {
            @Mock
            public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {
                return errCheckResult;
            }
        };

        new Expectations() {{
            AgreementTimeOfYear limitTime = AgreementTimeOfYear.of(new AgreementOneYearTime(10), new OneYearTime());
            AgreementTimeOfYear recordTime = AgreementTimeOfYear.of(new AgreementOneYearTime(20), new OneYearTime());
            AgreementTimeYear agreementTimeYear = AgreementTimeYear.of(limitTime, recordTime, AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR);

            val agreementTimes = new HashMap<YearMonth, AttendanceTimeMonth>();
            agreementTimes.put(monthlyAppContent.getYm(), new AttendanceTimeMonth(monthlyAppContent.getErrTime().v()));
            require.timeYear(monthlyAppContent.getApplicant(), GeneralDate.today(), new Year(monthlyAppContent.getYm().year()), agreementTimes);
            result = agreementTimeYear;

            require.algorithm(require, monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = null;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        assertThat(data.size()).isEqualTo(1);
        assertThat(data)
                .extracting(
                        d -> d.getErrorClassification(),
                        d -> d.getMaximumTimeMonth(),
                        d -> d.getMaximumTimeYear(),
                        d -> d.getExceedUpperLimit())
                .containsExactly(
                        tuple(ErrorClassification.OVERTIME_LIMIT_ONE_YEAR ,Optional.empty(), Optional.of(new AgreementOneYearTime(0)), Optional.empty())
                );
    }

    /**
     * チェックする TestCase 4
     * 1: [R-1] getMaxAverageMulti not null
     * 2: [R-2] timeYear return not null
     * 3: [R-3] algorithm return not null
     */
    @Test
    public void check_4() {
        ReasonsForAgreement reason = new ReasonsForAgreement("ReasonsForAgreement");
        MonthlyAppContent monthlyAppContent = new MonthlyAppContent("applicant", new YearMonth(202009),
                new AgreementOneMonthTime(1), Optional.of(new AgreementOneMonthTime(2)), reason);

        AgreMaxAverageTime agreMaxAverageTime = AgreMaxAverageTime.of(new YearMonthPeriod(new YearMonth(202008), new YearMonth(202009)), new AttendanceTimeYear(20), AgreMaxTimeStatusOfMonthly.ERROR_OVER);
        AgreMaxAverageTimeMulti agreMaxAverageTimeMulti = new AgreMaxAverageTimeMulti();
        agreMaxAverageTimeMulti.getAverageTimes().add(agreMaxAverageTime);

        // Mock up
        val errCheckResult = new ImmutablePair<Boolean, AgreementOneMonthTime>(false, new AgreementOneMonthTime(20));
        new MockUp<AgreementOneMonth>() {
            @Mock
            public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {
                return errCheckResult;
            }
        };

        new Expectations() {{

            val agreementTimes = new HashMap<YearMonth, AttendanceTimeMonth>();
            agreementTimes.put(monthlyAppContent.getYm(), new AttendanceTimeMonth(monthlyAppContent.getErrTime().v()));
            require.getMaxAverageMulti(monthlyAppContent.getApplicant(), GeneralDate.today(), monthlyAppContent.getYm(), agreementTimes);
            result = agreMaxAverageTimeMulti;

            AgreementTimeOfYear limitTime = AgreementTimeOfYear.of(new AgreementOneYearTime(10), new OneYearTime());
            AgreementTimeOfYear recordTime = AgreementTimeOfYear.of(new AgreementOneYearTime(20), new OneYearTime());
            AgreementTimeYear agreementTimeYear = AgreementTimeYear.of(limitTime, recordTime, AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR);
            require.timeYear(monthlyAppContent.getApplicant(), GeneralDate.today(), new Year(monthlyAppContent.getYm().year()), agreementTimes);
            result = agreementTimeYear;

            AgreementExcessInfo agreementExcessInfo = AgreementExcessInfo.of(0, 0, Arrays.asList(new YearMonth(201909)));
            require.algorithm(require, monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = agreementExcessInfo;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        assertThat(data.size()).isEqualTo(3);
        assertThat(data)
                .extracting(
                        d -> d.getErrorClassification(),
                        d -> d.getMaximumTimeMonth(),
                        d -> d.getMaximumTimeYear(),
                        d -> d.getExceedUpperLimit())
                .containsExactly(
                        tuple(ErrorClassification.TWO_MONTH_MAX_TIME, Optional.of(new AgreementOneMonthTime(0)), Optional.empty(), Optional.empty()),
                        tuple(ErrorClassification.OVERTIME_LIMIT_ONE_YEAR , Optional.empty(), Optional.of(new AgreementOneYearTime(0)), Optional.empty()),
                        tuple(ErrorClassification.EXCEEDING_MAXIMUM_NUMBER, Optional.empty(), Optional.empty(), Optional.of(AgreementOverMaxTimes.ZERO_TIMES))
                );
    }

    /**
     * チェックする TestCase 5
     * errorResult.getKey() = true
     */
    @Test
    public void check_5() {
        ReasonsForAgreement reason = new ReasonsForAgreement("ReasonsForAgreement");
        MonthlyAppContent monthlyAppContent = new MonthlyAppContent("applicant", new YearMonth(202009),
                new AgreementOneMonthTime(1), Optional.of(new AgreementOneMonthTime(2)), reason);

        // Mock up
        val errCheckResult = new ImmutablePair<Boolean, AgreementOneMonthTime>(true, new AgreementOneMonthTime(20));
        new MockUp<AgreementOneMonth>() {
            @Mock
            public Pair<Boolean, AgreementOneMonthTime> checkErrorTimeExceeded(AgreementOneMonthTime applicationTime) {
                return errCheckResult;
            }
        };

        new Expectations() {{
            require.algorithm(require, monthlyAppContent.getApplicant(), new Year(monthlyAppContent.getYm().year()));
            result = null;
        }};

        List<ExcessErrorContent> data = CheckErrorApplicationMonthService.check(require, monthlyAppContent);

        assertThat(data.size()).isEqualTo(1);
        assertThat(data)
                .extracting(
                        d -> d.getErrorClassification(),
                        d -> d.getMaximumTimeMonth().get().v(),
                        d -> d.getMaximumTimeYear(),
                        d -> d.getExceedUpperLimit())
                .containsExactly(
                        tuple(ErrorClassification.ONE_MONTH_MAX_TIME, 20, Optional.empty(), Optional.empty()));
    }

}
