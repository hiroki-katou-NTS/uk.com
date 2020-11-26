package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;


import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemDtoValue;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;
import nts.uk.ctx.at.function.dom.commonform.ClosureDateEmployment;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailAttItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.stream.Collectors;


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
     */
    @Test
    public void test_04() {
        List<String> listSid = new ArrayList<>(lstEmployee.keySet());
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId01",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));
        val listIds = outputSetting.getDailyOutputItemList().stream().filter(DailyOutputItemsAnnualWorkLedger::isPrintTargetFlag)
                .flatMap(x -> x.getSelectedAttendanceItemList().stream()
                        .map(OutputItemDetailAttItem::getAttendanceItemId))
                .distinct().collect(Collectors.toCollection(ArrayList::new));
        val listValue = Arrays.asList(new AttendanceResultDto(
                "eplId01",
                GeneralDate.today(),
                Arrays.asList(
                        new AttendanceItemDtoValue(1, 1, "25"),
                        new AttendanceItemDtoValue(2, 2, "2000"),
                        new AttendanceItemDtoValue(3, 3, "TEST 02"),
                        new AttendanceItemDtoValue(5, 4, "TEST 01")
                ))
        );
        new Expectations() {
            {
                require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
                result = listEmployeeStatus;
                require.getValueOf(Collections.singletonList(listEmployeeStatus.get(0).getEmployeeId()),
                        listEmployeeStatus.get(0).getListPeriod().get(0), listIds);
                result = listValue;

            }
        };


        val actual = CreateAnnualWorkLedgerContentDomainService.getData(require, datePeriod, lstEmployee,
                outputSetting, lstWorkplaceInfor, lstClosureDateEmployment);
        val expected = DumDataTest.expected;

    }
}
