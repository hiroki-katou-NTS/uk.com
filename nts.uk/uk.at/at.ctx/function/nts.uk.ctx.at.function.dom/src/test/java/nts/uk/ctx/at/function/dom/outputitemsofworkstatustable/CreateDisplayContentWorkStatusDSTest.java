package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemDtoValue;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.EmployeeInfor;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.WorkPlaceInfo;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.SettingClassificationCommon;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


@RunWith(JMockit.class)
public class CreateDisplayContentWorkStatusDSTest {

    @Injectable
    private CreateDisplayContentWorkStatusDService.Require require;

    private final DatePeriod datePeriod = new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1));
    private static final List<EmployeeInfor> employeeInfors = DumData.employeeInfors;
    private static final List<EmployeeInfor> employeeInforFail = DumData.employeeInforFail;
    private static final List<WorkPlaceInfo> workPlaceInfo = DumData.workPlaceInfo;
    private static final List<WorkPlaceInfo> workPlaceInfoFail = DumData.workPlaceInfoFail;
    private final OutputItemSettingCode code = new OutputItemSettingCode("ABC");
    private final OutputItemSettingName name = new OutputItemSettingName("CBA");
    private final String iD = "iD";
    private final String empId = "employeeId";
    private final SettingClassificationCommon settingCategoryStandard = SettingClassificationCommon.STANDARD_SELECTION;
    private final WorkStatusOutputSettings domainsStandard = DumData.dumDisplay(code, name, empId, iD, settingCategoryStandard);
    private final WorkStatusOutputSettings dumDomainNolistItem = DumData.dumDomainNolistItem(code, name, empId, iD, settingCategoryStandard);

    @Test
    public void test_01() {
        val listSid = employeeInfors.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId01",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));
        val listAttd = Arrays.asList(new AttendanceResultDto(
                "eplId01",
                GeneralDate.today(),
                Arrays.asList(
                        new AttendanceItemDtoValue(1, 1, "25"),
                        new AttendanceItemDtoValue(1, 8, "2000"),
                        new AttendanceItemDtoValue(5, 22, "TEST 02"),
                        new AttendanceItemDtoValue(5, 2, "TEST 01")
                ))
        );
        new Expectations() {
            {
                require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
                result = listEmployeeStatus;
                require.getValueOf(Collections.singletonList("eplId01"), new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1)),
                        Arrays.asList(22, 2, 1, 8));
                result = listAttd;
            }
        };
        val actual = CreateDisplayContentWorkStatusDService.displayContentsOfWorkStatus(require, datePeriod, employeeInfors,
                domainsStandard, workPlaceInfo);
        val expected = DumData.expected;

        assertThat(actual)
                .extracting(
                        DisplayContentWorkStatus::getEmployeeName,
                        DisplayContentWorkStatus::getEmployeeCode,
                        DisplayContentWorkStatus::getWorkPlaceCode,
                        DisplayContentWorkStatus::getWorkPlaceName)
                .containsExactly(
                        tuple(expected.get(0).getEmployeeName(),
                                expected.get(0).getEmployeeCode(),
                                expected.get(0).getWorkPlaceCode(),
                                expected.get(0).getWorkPlaceName()));

        assertThat(actual.get(0).getOutputItemOneLines())
                .extracting(
                        d -> d.getOutItemValue().size(),
                        OutputItemOneLine::getOutPutItemName,
                        OutputItemOneLine::getTotalOfOneLine)

                .containsExactly(
                        tuple(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().size(),
                                expected.get(0).getOutputItemOneLines().get(0).getOutPutItemName(),
                                expected.get(0).getOutputItemOneLines().get(0).getTotalOfOneLine()
                        ),
                        tuple(expected.get(0).getOutputItemOneLines().get(1).getOutItemValue().size(),
                                expected.get(0).getOutputItemOneLines().get(1).getOutPutItemName(),
                                expected.get(0).getOutputItemOneLines().get(1).getTotalOfOneLine()
                        )

                );

        assertThat(actual.get(0).getOutputItemOneLines().get(0).getOutItemValue())
                .extracting(
                        DailyValue::getDate,
                        DailyValue::getActualValue,
                        DailyValue::getCharacterValue,
                        DailyValue::getAttributes
                )
                .containsExactly(
                        tuple(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getDate(),
                                expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getActualValue(),
                                expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getCharacterValue(),
                                expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getAttributes()

                        )

                );
        assertThat(actual.get(0).getOutputItemOneLines().get(1).getOutItemValue())
                .extracting(
                        DailyValue::getDate,
                        DailyValue::getActualValue,
                        DailyValue::getCharacterValue,
                        DailyValue::getAttributes
                )
                .containsExactly(
                        tuple(expected.get(0).getOutputItemOneLines().get(1).getOutItemValue().get(0).getDate(),
                                expected.get(0).getOutputItemOneLines().get(1).getOutItemValue().get(0).getActualValue(),
                                expected.get(0).getOutputItemOneLines().get(1).getOutItemValue().get(0).getCharacterValue(),
                                expected.get(0).getOutputItemOneLines().get(1).getOutItemValue().get(0).getAttributes()

                        )

                );
    }

    /**
     * TEST CASE :Throw exception
     * - Fail  employee info
     */
    @Test
    public void test_02() {
        val listSid = employeeInforFail.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId05",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));
        new Expectations() {
            {
                require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
                result = listEmployeeStatus;

            }
        };

        NtsAssert.businessException("Msg_1816", () -> {
            CreateDisplayContentWorkStatusDService.displayContentsOfWorkStatus(require, datePeriod, employeeInfors,
                    domainsStandard, workPlaceInfo);
        });
    }

    /**
     * TEST CASE :Throw exception
     * <p>
     * - Fail  workplace info
     */
    @Test
    public void test_03() {
        val listSid = employeeInfors.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId05",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));
        new Expectations() {
            {
                require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
                result = listEmployeeStatus;

            }
        };
        NtsAssert.businessException("Msg_1816", () -> {
            CreateDisplayContentWorkStatusDService.displayContentsOfWorkStatus(require, datePeriod, employeeInfors,
                    domainsStandard, workPlaceInfoFail);
        });
    }

    /**
     * TEST CASE :Throw exception
     * - Fail employee info
     * - Fail  workplace info
     */
    @Test
    public void test_04() {
        val listSid = employeeInforFail.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
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
        NtsAssert.businessException("Msg_1816", () -> {
            CreateDisplayContentWorkStatusDService.displayContentsOfWorkStatus(require, datePeriod, employeeInforFail,
                    domainsStandard, workPlaceInfoFail);
        });
    }

    /**
     * TEST CASE : Value of list AttendanceId return null
     */
    @Test
    public void test_05() {
        val listSid = employeeInfors.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId01",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));
        new Expectations() {
            {
                require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
                result = listEmployeeStatus;

                require.getValueOf(Collections.singletonList("eplId01"), new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1)), Arrays.asList(22, 2, 1, 8));
                result = null;
            }
        };
        val itemValue = new ArrayList<DailyValue>();
        val listOutPut = new ArrayList<OutputItemOneLine>();
        listOutPut.add(new OutputItemOneLine(
                0D,
                "itemName01",
                itemValue));
        listOutPut.add(new OutputItemOneLine(
                0D,
                "itemName03",
                itemValue));


        val actual = CreateDisplayContentWorkStatusDService.displayContentsOfWorkStatus(require, datePeriod, employeeInfors,
                domainsStandard, workPlaceInfo);
        val expected = Arrays.asList(new DisplayContentWorkStatus(
                "eplCode01",
                "eplName01",
                "wplCode01",
                "wplName01",
                listOutPut
        ));
        assertThat(actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().size()).isEqualTo(0);
        assertThat(actual.get(0).getOutputItemOneLines().get(1).getOutItemValue().size()).isEqualTo(0);
        assertThat(actual)
                .extracting(
                        DisplayContentWorkStatus::getEmployeeName,
                        DisplayContentWorkStatus::getEmployeeCode,
                        DisplayContentWorkStatus::getWorkPlaceCode,
                        DisplayContentWorkStatus::getWorkPlaceName
                )
                .containsExactly(
                        tuple(expected.get(0).getEmployeeName(),
                                expected.get(0).getEmployeeCode(),
                                expected.get(0).getWorkPlaceCode(),
                                expected.get(0).getWorkPlaceName()));
    }
    /**
     * TEST CASE : List output : emptyList
     */
    @Test
    public void test_06() {
        val listSid = employeeInfors.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
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
        NtsAssert.businessException("Msg_1816", () -> {
            CreateDisplayContentWorkStatusDService.displayContentsOfWorkStatus(require, datePeriod, employeeInfors,
                    dumDomainNolistItem, workPlaceInfo);
        });

    }

}
