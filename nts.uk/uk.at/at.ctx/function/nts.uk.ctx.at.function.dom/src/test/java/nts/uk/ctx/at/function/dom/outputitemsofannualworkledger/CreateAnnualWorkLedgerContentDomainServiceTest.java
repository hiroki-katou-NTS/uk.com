package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;


import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Map;


@RunWith(JMockit.class)
public class CreateAnnualWorkLedgerContentDomainServiceTest {

    @Injectable
    private  CreateAnnualWorkLedgerContentDomainService.Require require;

    private final DatePeriod datePeriod = new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1));
    public static Map<String, EmployeeBasicInfoImport> lstEmployee = DumDataTest.lstEmployee();

    /**
     * TEST CASE :Throw exception
     * - Fail  employee info
     */
    @Test
    public void test_02() {
        val mapSid = DumDataTest.lstWorkplaceInfor();

//        new Expectations() {
//            {
//                require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
//                result = listEmployeeStatus;
//
//            }
//        };
//
//        NtsAssert.businessException("Msg_1816", () -> {
//            CreateAnnualWorkLedgerContentDomainService.getData(require, datePeriod, lstEmployee,
//                    domainsStandard, workPlaceInfo);
//        });
    }
}
