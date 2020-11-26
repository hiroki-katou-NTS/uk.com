package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;


import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.commonform.ClosureDateEmployment;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;


@RunWith(JMockit.class)
public class CreateAnnualWorkLedgerContentDomainServiceTest {

    @Injectable
    private CreateAnnualWorkLedgerContentDomainService.Require require;

    private final DatePeriod datePeriod = new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1));
    private static Map<String, EmployeeBasicInfoImport> lstEmployee = DumDataTest.lstEmployee();
    private static Map<String, EmployeeBasicInfoImport> lstEmployeeFail = DumDataTest.lstEmployeeFail();
    private static Map<String, WorkplaceInfor> lstWorkplaceInfor = DumDataTest.lstWorkplaceInfor();
    private static Map<String, WorkplaceInfor> lstWorkplaceInforFail = DumDataTest.lstWorkplaceInforFail();
    private static Map<String, ClosureDateEmployment> lstClosureDateEmployment = DumDataTest.lstClosureDateEmployment();
    private static AnnualWorkLedgerOutputSetting outputSetting = DumDataTest.outputSetting;

    /**
     * TEST CASE :Throw exception
     * - [RQ 588] -> return empty;
     */
    @Test
    public void test_01() {
        List<String> listSid = new ArrayList<>(lstEmployee.keySet());

        new Expectations() {
            {
                require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
                result = Collections.emptyList();

            }
        };

        NtsAssert.businessException("Msg_1860", () -> {
            CreateAnnualWorkLedgerContentDomainService.getData(require, datePeriod, lstEmployee,
                    outputSetting, lstWorkplaceInfor, lstClosureDateEmployment);
        });
    }

    /**
     * TEST CASE :Throw exception
     * - Fail  workplace  info
     */
    @Test
    public void test_02() {
        List<String> listSid = new ArrayList<>(lstEmployee.keySet());
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId01",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));
        new Expectations() {
            {
                require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
                result = listEmployeeStatus;

            }
        };

        NtsAssert.businessException("Msg_1860", () -> {
            CreateAnnualWorkLedgerContentDomainService.getData(require, datePeriod, lstEmployee,
                    outputSetting, lstWorkplaceInforFail, lstClosureDateEmployment);
        });
    }

    /**
     * TEST CASE :Throw exception
     * - Fail  employee  info
     */
    @Test
    public void test_03() {
        List<String> listSid = new ArrayList<>(lstEmployeeFail.keySet());
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId01",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));
        new Expectations() {
            {
                require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
                result = listEmployeeStatus;

            }
        };

        NtsAssert.businessException("Msg_1860", () -> {
            CreateAnnualWorkLedgerContentDomainService.getData(require, datePeriod, lstEmployeeFail,
                    outputSetting, lstWorkplaceInfor, lstClosureDateEmployment);
        });
    }

    /**
     * TEST CASE :Throw exception
     * - Fail  employee  info
     */
    @Test
    public void test_04() {
        List<String> listSid = new ArrayList<>(lstEmployeeFail.keySet());
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId01",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));
        new Expectations() {
            {
                require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
                result = listEmployeeStatus;

            }
        };


        val actual = CreateAnnualWorkLedgerContentDomainService.getData(require, datePeriod, lstEmployeeFail,
                outputSetting, lstWorkplaceInfor, lstClosureDateEmployment);

    }
}
