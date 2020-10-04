package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.workplace.SWkpHistRcImported;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
@RunWith(JMockit.class)
public class GetWorkplaceApproveHistoryDomainServiceTest {
    @Injectable
    GetWorkplaceApproveHistoryDomainService.Require require;
    private static String workplaceId = "wid";
    private static DatePeriod period = DatePeriod.daysFirstToLastIn(YearMonth.of(202009));
    private static DatePeriod periodLast = DatePeriod.daysFirstToLastIn(YearMonth.of(202008));
    private static String employeeId = "eplId";
    private static String codeAndName = "codeName";
    private   static List<String> approverList =  Arrays.asList("abc","cba","efg");
    private   static List<String> confirmerList =  Arrays.asList("abc","cba","efg");
    private   static List<String> listWplId =  Arrays.asList("abc","cba","efg");
    private static Approver36AgrByWorkplace domain = new Approver36AgrByWorkplace(
            workplaceId,
            period,
            approverList,
            confirmerList
        );
    private static Approver36AgrByWorkplace domainLast = new Approver36AgrByWorkplace(
            workplaceId,
            periodLast,
            approverList,
            confirmerList
    );

    private static SWkpHistRcImported sWkpHistRcImported =  new SWkpHistRcImported(period,employeeId,workplaceId,codeAndName,codeAndName,codeAndName);
    @Test

    public void test_01() {
        val baseDate = GeneralDate.today();
        new Expectations() {{
            require.getYourWorkplace(employeeId, baseDate);
            result = Optional.empty();

        }};
        assertThat(GetWorkplaceApproveHistoryDomainService.getWorkplaceApproveHistory(require, employeeId))
                .isEqualTo(Optional.empty());

    }

    @Test
    public void test_02() {
        val wplSWkpHistRcImported = sWkpHistRcImported;
        val employId = employeeId;
        val baseDate = GeneralDate.today();
        val approve = new ApproverItem(domain.getApproverIds(), domain.getConfirmerIds());
        new Expectations() {{
            require.getYourWorkplace(employId, baseDate);
            result = Optional.of(wplSWkpHistRcImported);

            require.getApproveHistoryItem(wplSWkpHistRcImported.getWorkplaceId()
                    , baseDate);
            result = Optional.of(domain);
        }};

        val actual = GetWorkplaceApproveHistoryDomainService.getWorkplaceApproveHistory(require, employId);
        val expected = Optional.of(approve);
        assertThat(actual.get().getApproverList()).isEqualTo(expected.get().getApproverList());
        assertThat(actual.get().getConfirmerList()).isEqualTo(expected.get().getConfirmerList());
    }
    @Test
    public void test_03() {
        val wplSWkpHistRcImported = sWkpHistRcImported;
        val employId = employeeId;
        val baseDate = GeneralDate.today();
        new Expectations() {{
            require.getYourWorkplace(employId, baseDate);
            result = Optional.of(wplSWkpHistRcImported);

            require.getApproveHistoryItem(wplSWkpHistRcImported.getWorkplaceId()
                    , baseDate);
            result = Optional.empty();

            require.getUpperWorkplace(wplSWkpHistRcImported.getWorkplaceId(), baseDate);
            result = Collections.emptyList();
        }};

        val actual = GetWorkplaceApproveHistoryDomainService.getWorkplaceApproveHistory(require, employId);
        val expected = Optional.empty();
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void test_04() {
        val wplSWkpHistRcImported = sWkpHistRcImported;
        val employId = employeeId;
        val baseDate = GeneralDate.today();
        new Expectations() {{
            require.getYourWorkplace(employId, baseDate);
            result = Optional.of(wplSWkpHistRcImported);

            require.getApproveHistoryItem(wplSWkpHistRcImported.getWorkplaceId()
                    , baseDate);
            result = Optional.empty();


            require.getUpperWorkplace(wplSWkpHistRcImported.getWorkplaceId(), baseDate);
            result = listWplId;


            require.getApproveHistoryItem(listWplId.get(0),baseDate);
            result = Optional.of(domain);


        }};

        val actual = GetWorkplaceApproveHistoryDomainService.getWorkplaceApproveHistory(require, employId);
        assertThat(actual.get().getConfirmerList()).isEqualTo(domain.getConfirmerIds());
        assertThat(actual.get().getApproverList()).isEqualTo(domain.getApproverIds());
    }
    @Test
    public void test_05() {
        val wplSWkpHistRcImported = sWkpHistRcImported;
        val employId = employeeId;
        val baseDate = GeneralDate.today();

        new Expectations() {{
            require.getYourWorkplace(employId, baseDate);
            result = Optional.of(wplSWkpHistRcImported);

            require.getApproveHistoryItem(wplSWkpHistRcImported.getWorkplaceId()
                    , baseDate);
            result = Optional.empty();

            require.getUpperWorkplace(wplSWkpHistRcImported.getWorkplaceId(), baseDate);
            result = listWplId.get(0);


            require.getApproveHistoryItem(listWplId.get(0),baseDate);
            result = Optional.empty();
        }};

        val actual = GetWorkplaceApproveHistoryDomainService.getWorkplaceApproveHistory(require, employId);
        val expected = Optional.empty();
        assertThat(actual).isEqualTo(expected);
    }
}