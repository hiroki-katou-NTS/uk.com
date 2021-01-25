package nts.uk.ctx.at.function.dom.commonform;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.workrule.closure.*;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * 基準日で社員の雇用と締め日を取得する
 */
@RunWith(JMockit.class)
public class GetClosureDateEmploymentDomainServiceTest {
    @Injectable
    private GetClosureDateEmploymentDomainService.Require require;

    public static Closure createClosure() {
        return new Closure(new ClosureGetMemento() {

            @Override
            public UseClassification getUseClassification() {
                return UseClassification.UseClass_Use;
            }

            @Override
            public CompanyId getCompanyId() {
                return new CompanyId("cid");
            }

            @Override
            public CurrentMonth getClosureMonth() {
                return new CurrentMonth(11);
            }

            @Override
            public ClosureId getClosureId() {
                return ClosureId.RegularEmployee;
            }

            @Override
            public List<ClosureHistory> getClosureHistories() {
                return Arrays.asList(new ClosureHistory(new ClosureHistoryGetMemento() {

                    @Override
                    public YearMonth getStartDate() {
                        return YearMonth.of(1900, 1);
                    }

                    @Override
                    public YearMonth getEndDate() {
                        return YearMonth.of(9999, 12);
                    }

                    @Override
                    public CompanyId getCompanyId() {
                        return new CompanyId("cid");
                    }

                    @Override
                    public ClosureName getClosureName() {
                        return new ClosureName("AA");
                    }

                    @Override
                    public ClosureId getClosureId() {
                        return ClosureId.RegularEmployee;
                    }

                    @Override
                    public ClosureDate getClosureDate() {
                        return new ClosureDate(1, true);
                    }
                }));
            }
        });
    }

    /**
     * Test: GetClosureDateEmploymentDomainService
     * require.getEmploymentInfor: return: list.size() = 1
     * require.getClosureDataByEmployee : return !=null
     */
    @Test
    public void testClosureDateEmploymentFull_01() {

        List<String> listSid = Arrays.asList("01","02","03");
        GeneralDate baseDate = GeneralDate.ymd(2020,10,10);
        DatePeriod datePeriod = new DatePeriod(GeneralDate.ymd(2020,10,1), GeneralDate.ymd(2020,10,30));
        BsEmploymentHistoryImport historyImport = new BsEmploymentHistoryImport("sid","code","name",datePeriod);

        Map<String, BsEmploymentHistoryImport> expectedList = new HashMap<>();
        expectedList.put("01",historyImport);

        Closure closure = createClosure();

        new Expectations() {
            {
                require.getEmploymentInfor(listSid, baseDate);
                result = expectedList;

                require.getClosureDataByEmployee("sid", baseDate);
                result = closure;
            }
        };

        List<ClosureDateEmployment> result = GetClosureDateEmploymentDomainService.get(require,baseDate,listSid);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getEmployeeId()).isEqualTo("sid");
        assertThat(result.get(0).getEmploymentCode()).isEqualTo("code");
        assertThat(result.get(0).getEmploymentName()).isEqualTo("name");
        assertThat(result.get(0).getClosure()).isEqualTo(closure);
    }

    /**
     * Test: GetClosureDateEmploymentDomainService
     * require.getEmploymentInfor: return: list.size() = 0
     */
    @Test
    public void testEmploymentInforEmpty_02() {
        List<String> listSid = Arrays.asList("01","02","03");
        GeneralDate baseDate = GeneralDate.ymd(2020,10,10);
        Map<String, BsEmploymentHistoryImport> expectedList = new HashMap<>();

        new Expectations() {
            {
                require.getEmploymentInfor(listSid, baseDate);
                result = expectedList;
            }
        };

        List<ClosureDateEmployment> result = GetClosureDateEmploymentDomainService.get(require,baseDate,listSid);
        assertThat(result.size()).isEqualTo(0);
    }
    /**
     * Test: GetClosureDateEmploymentDomainService
     * require.getEmploymentInfor: return: list.size() > 0
     * require.getClosureDataByEmployee : null
     */
    @Test
    public void testClosureDataByEmployeeNull_03() {
        List<String> listSid = Arrays.asList("01","02","03");
        GeneralDate baseDate = GeneralDate.ymd(2020,10,10);
        DatePeriod datePeriod = new DatePeriod(GeneralDate.ymd(2020,10,1), GeneralDate.ymd(2020,10,30));
        BsEmploymentHistoryImport historyImport = new BsEmploymentHistoryImport("sid","code","name",datePeriod);

        Map<String, BsEmploymentHistoryImport> expectedList = new HashMap<>();
        expectedList.put("01",historyImport);

        new Expectations() {
            {
                require.getEmploymentInfor(listSid, baseDate);
                result = expectedList;

                require.getClosureDataByEmployee("sid", baseDate);
                result = null;
            }
        };

        List<ClosureDateEmployment> result = GetClosureDateEmploymentDomainService.get(require,baseDate,listSid);
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getEmployeeId()).isEqualTo("sid");
        assertThat(result.get(0).getEmploymentCode()).isEqualTo("code");
        assertThat(result.get(0).getEmploymentName()).isEqualTo("name");
        assertThat(result.get(0).getClosure()).isEqualTo(null);
    }

    /**
     * Test: GetClosureDateEmploymentDomainService
     * require.getEmploymentInfor: return: list.size() > 1
     * require.getClosureDataByEmployee : return !=null
     */
    @Test
    public void testClosureDataByEmployeeFull_04() {
        List<String> listSid = Arrays.asList("01","02","03");
        GeneralDate baseDate = GeneralDate.ymd(2020,10,10);
        DatePeriod datePeriod = new DatePeriod(GeneralDate.ymd(2020,10,1), GeneralDate.ymd(2020,10,30));
        BsEmploymentHistoryImport historyImport = new BsEmploymentHistoryImport("sid","code","name",datePeriod);
        BsEmploymentHistoryImport historyImport1 = new BsEmploymentHistoryImport("sid1","code","name",datePeriod);

        Map<String, BsEmploymentHistoryImport> expectedList = new HashMap<>();
        expectedList.put("01",historyImport);
        expectedList.put("02",historyImport1);

        Closure closure = createClosure();

        new Expectations() {
            {
                require.getEmploymentInfor(listSid, baseDate);
                result = expectedList;

                require.getClosureDataByEmployee("sid", baseDate);
                result = closure;
                require.getClosureDataByEmployee("sid1", baseDate);
                result = closure;
            }
        };

        List<ClosureDateEmployment> result = GetClosureDateEmploymentDomainService.get(require,baseDate,listSid);
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getEmployeeId()).isEqualTo("sid");
        assertThat(result.get(0).getEmploymentCode()).isEqualTo("code");
        assertThat(result.get(0).getEmploymentName()).isEqualTo("name");
        assertThat(result.get(0).getClosure()).isEqualTo(closure);

        assertThat(result.get(1).getEmployeeId()).isEqualTo("sid1");
        assertThat(result.get(1).getEmploymentCode()).isEqualTo("code");
        assertThat(result.get(1).getEmploymentName()).isEqualTo("name");
        assertThat(result.get(1).getClosure()).isEqualTo(closure);
    }

}
