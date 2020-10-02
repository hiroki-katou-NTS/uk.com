package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
@RunWith(JMockit.class)
public class GetWorkplaceApproveHistoryDomainServiceTest {
    @Injectable
    GetWorkplaceApproveHistoryDomainService.Require require;

    @Test
    public void test_01_R_Q_30_empty() {
        val employId = "eplId";
        val baseDate = GeneralDate.today();

        new Expectations() {{
            require.getYourWorkplace(CreateDomain.employeeId, baseDate);
            result = Optional.empty();

        }};

        assertThat(GetWorkplaceApproveHistoryDomainService.getWorkplaceApproveHistory(require, employId)).isEqualTo(Optional.empty());

    }

    @Test
    public void test_02_R_Q_30_not_empty_and_require_empty() {
        val wplSWkpHistRcImported = CreateDomain.createSWkpHistRcImported();
        val wplHistoryOfEmployees = CreateDomain.createApprover36AgrByWorkplace();
        val employId = CreateDomain.employeeId;
        val baseDate = GeneralDate.today();
        val approve = new ApproverItem(wplHistoryOfEmployees.getApproverIds(), wplHistoryOfEmployees.getConfirmerIds());
        new Expectations() {{
            require.getYourWorkplace(employId, baseDate);
            result = Optional.of(wplSWkpHistRcImported);

            require.getApproveHistoryItem(wplSWkpHistRcImported.getWorkplaceId()
                    , baseDate);
            result = Optional.of(approve);
        }};

        val actual = GetWorkplaceApproveHistoryDomainService.getWorkplaceApproveHistory(require, employId);
        val expected = Optional.of(approve);
        assertThat(actual).isEqualTo(expected);

    }
    @Test
    public void test_03_R_Q_30_not_empty_and_require_empty() {
        val wplSWkpHistRcImported = CreateDomain.createSWkpHistRcImported();
        val employId = CreateDomain.employeeId;
        val baseDate = GeneralDate.today();
        List myList = new ArrayList();
        new Expectations() {{
            require.getYourWorkplace(employId, baseDate);
            result = Optional.of(wplSWkpHistRcImported);

            require.getApproveHistoryItem(wplSWkpHistRcImported.getWorkplaceId()
                    , baseDate);
            result = Optional.empty();

            require.getUpperWorkplace(wplSWkpHistRcImported.getWorkplaceId(), baseDate);
            result = myList;
        }};

        val actual = GetWorkplaceApproveHistoryDomainService.getWorkplaceApproveHistory(require, employId);
        val expected = Optional.empty();
        assertThat(actual).isEqualTo(expected);
    }
    @Test
    public void test_04_R_Q_30_not_empty_and_require_not_empty() {
        val wplSWkpHistRcImported = CreateDomain.createSWkpHistRcImported();
        val employId = CreateDomain.employeeId;
        val baseDate = GeneralDate.today();
        val myList = CreateDomain.createStringListWithSize("check",3);
        val wplHistoryOfEmployees = CreateDomain.createApprover36AgrByWorkplace();
        val approve = new ApproverItem(wplHistoryOfEmployees.getApproverIds(), wplHistoryOfEmployees.getConfirmerIds());

        val approveList = new ArrayList<String>();
        val confirmedList = new ArrayList<String>();
        val expected = Optional.of(new ApproverItem(approveList,confirmedList));
        new Expectations() {{
            require.getYourWorkplace(employId, baseDate);
            result = Optional.of(wplSWkpHistRcImported);

            require.getApproveHistoryItem(wplSWkpHistRcImported.getWorkplaceId()
                    , baseDate);
            result = Optional.empty();

            require.getUpperWorkplace(wplSWkpHistRcImported.getWorkplaceId(), baseDate);
            result = myList;


            require.getApproveHistoryItem(myList.get(0),baseDate);
            result = Optional.of(approve);
            approveList.addAll(approve.getApproverList());
            confirmedList.addAll(approve.getConfirmerList());

            require.getApproveHistoryItem(myList.get(1),baseDate);
            result = Optional.of(approve);
            approveList.addAll(approve.getApproverList());
            confirmedList.addAll(approve.getConfirmerList());

            require.getApproveHistoryItem(myList.get(2),baseDate);
            result = Optional.of(approve);
            approveList.addAll(approve.getApproverList());
            confirmedList.addAll(approve.getConfirmerList());



        }};

        val actual = GetWorkplaceApproveHistoryDomainService.getWorkplaceApproveHistory(require, employId);

        assertThat(actual.get().getConfirmerList()).isEqualTo(expected.get().getConfirmerList());
        assertThat(actual.get().getApproverList()).isEqualTo(expected.get().getApproverList());
    }
    @Test
    public void test_05_R_Q_30_not_empty_and_require_not_empty() {
        val wplSWkpHistRcImported = CreateDomain.createSWkpHistRcImported();
        val employId = CreateDomain.employeeId;
        val baseDate = GeneralDate.today();
        val myList = CreateDomain.createStringListWithSize("check",3);

        val approveList = new ArrayList<String>();
        val confirmedList = new ArrayList<String>();
        val expected = Optional.of(new ApproverItem(approveList,confirmedList));
        new Expectations() {{
            require.getYourWorkplace(employId, baseDate);
            result = Optional.of(wplSWkpHistRcImported);

            require.getApproveHistoryItem(wplSWkpHistRcImported.getWorkplaceId()
                    , baseDate);
            result = Optional.empty();

            require.getUpperWorkplace(wplSWkpHistRcImported.getWorkplaceId(), baseDate);
            result = myList;


            require.getApproveHistoryItem(myList.get(0),baseDate);
            result = Optional.empty();
        }};


        val actual = GetWorkplaceApproveHistoryDomainService.getWorkplaceApproveHistory(require, employId);

        assertThat(actual.get().getConfirmerList()).isEqualTo(approveList);
        assertThat(actual.get().getApproverList()).isEqualTo(confirmedList);
    }
}