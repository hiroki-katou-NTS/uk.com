package nts.uk.ctx.at.function.dom.supportworklist.outputsetting;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.supportworklist.SupportWorkDataImportHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class EmployeeExtractConditionTest {
    @Injectable
    SupportWorkOutputDataRequire require;

    @Test
    public void testGetSupportWorkDataByWorkplace1() {
        String companyId = "00000000003-0001";
        String employeeId = "employee-id-0001";
        String workplaceId = "workplace-id-0001";
        DatePeriod period = DatePeriod.oneDay(GeneralDate.today());
        List<String> workplaceIds = Arrays.asList(workplaceId);

        EmployeeExtractCondition cnd = EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES;

        new Expectations() {{
            require.getSupportWorkDataForWorkingEmployeeByWorkplace(companyId, period, workplaceIds);
            result = SupportWorkDataImportHelper.createDataImport(employeeId, workplaceId, "0001", workplaceId, "0001");
        }};

        assertThat(cnd.getSupportWorkDataByWorkplace(
                require,
                companyId,
                period,
                workplaceIds
        ).getAffiliationInforList().size()).isEqualTo(1);
    }

    @Test
    public void testGetSupportWorkDataByWorkplace2() {
        String companyId = "00000000003-0001";
        String employeeId = "employee-id-0001";
        String workplaceId = "workplace-id-0001";
        DatePeriod period = DatePeriod.oneDay(GeneralDate.today());
        List<String> workplaceIds = Arrays.asList(workplaceId);

        EmployeeExtractCondition cnd = EmployeeExtractCondition.EXTRACT_EMPLOYEES_GO_TO_SUPPORT;

        new Expectations() {{
            require.getSupportWorkDataForEmployeeGoToSupportByWorkplace(companyId, period, workplaceIds);
            result = SupportWorkDataImportHelper.createDataImport(employeeId, workplaceId, "0001", workplaceId, "0001");
        }};

        assertThat(cnd.getSupportWorkDataByWorkplace(
                require,
                companyId,
                period,
                workplaceIds
        ).getAffiliationInforList().size()).isEqualTo(1);
    }

    @Test
    public void testGetSupportWorkDataByWorkplace3() {
        String companyId = "00000000003-0001";
        String employeeId = "employee-id-0001";
        String workplaceId = "workplace-id-0001";
        DatePeriod period = DatePeriod.oneDay(GeneralDate.today());
        List<String> workplaceIds = Arrays.asList(workplaceId);

        EmployeeExtractCondition cnd = EmployeeExtractCondition.EXTRACT_EMPLOYEES_COME_TO_SUPPORT;

        new Expectations() {{
            require.getSupportWorkDataForEmployeeComeToSupportByWorkplace(companyId, period, workplaceIds);
            result = SupportWorkDataImportHelper.createDataImport(employeeId, workplaceId, "0001", workplaceId, "0001");
        }};

        assertThat(cnd.getSupportWorkDataByWorkplace(
                require,
                companyId,
                period,
                workplaceIds
        ).getAffiliationInforList().size()).isEqualTo(1);
    }

    @Test
    public void testGetSupportWorkDataByWorkLocation1() {
        String companyId = "00000000003-0001";
        String employeeId = "employee-id-0001";
        String workLocationCode = "0001";
        DatePeriod period = DatePeriod.oneDay(GeneralDate.today());
        List<String> workLocationCodes = Arrays.asList(workLocationCode);

        EmployeeExtractCondition cnd = EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES;

        new Expectations() {{
            require.getSupportWorkDataForWorkingEmployeeByWorkLocation(companyId, period, workLocationCodes);
            result = SupportWorkDataImportHelper.createDataImport(employeeId, "", workLocationCode, "", null);
        }};

        assertThat(cnd.getSupportWorkDataByWorkLocations(
                require,
                companyId,
                period,
                workLocationCodes
        ).getAffiliationInforList().size()).isEqualTo(1);
    }

    @Test
    public void testGetSupportWorkDataByWorkLocation2() {
        String companyId = "00000000003-0001";
        String employeeId = "employee-id-0001";
        String workLocationCode = "0001";
        DatePeriod period = DatePeriod.oneDay(GeneralDate.today());
        List<String> workLocationCodes = Arrays.asList(workLocationCode);

        EmployeeExtractCondition cnd = EmployeeExtractCondition.EXTRACT_EMPLOYEES_GO_TO_SUPPORT;

        new Expectations() {{
            require.getSupportWorkDataForEmployeeGoToSupportByWorkLocation(companyId, period, workLocationCodes);
            result = SupportWorkDataImportHelper.createDataImport(employeeId, "", workLocationCode, "", null);
        }};

        assertThat(cnd.getSupportWorkDataByWorkLocations(
                require,
                companyId,
                period,
                workLocationCodes
        ).getAffiliationInforList().size()).isEqualTo(1);
    }

    @Test
    public void testGetSupportWorkDataByWorkLocation3() {
        String companyId = "00000000003-0001";
        String employeeId = "employee-id-0001";
        String workLocationCode = "0001";
        DatePeriod period = DatePeriod.oneDay(GeneralDate.today());
        List<String> workLocationCodes = Arrays.asList(workLocationCode);

        EmployeeExtractCondition cnd = EmployeeExtractCondition.EXTRACT_EMPLOYEES_COME_TO_SUPPORT;

        new Expectations() {{
            require.getSupportWorkDataForEmployeeComeToSupportByWorkLocation(companyId, period, workLocationCodes);
            result = SupportWorkDataImportHelper.createDataImport(employeeId, "", workLocationCode, "", null);
        }};

        assertThat(cnd.getSupportWorkDataByWorkLocations(
                require,
                companyId,
                period,
                workLocationCodes
        ).getAffiliationInforList().size()).isEqualTo(1);
    }
}
