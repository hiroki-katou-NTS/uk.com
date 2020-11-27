package nts.uk.ctx.at.function.dom.outputitemsofannualworkledger;


import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.adapter.actualmultiplemonth.MonthlyRecordValueImport;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceItemDtoValue;
import nts.uk.ctx.at.function.dom.adapter.outputitemsofworkstatustable.AttendanceResultDto;
import nts.uk.ctx.at.function.dom.commonform.ClosureDateEmployment;
import nts.uk.ctx.at.function.dom.commonform.GetSuitableDateByClosureDateUtility;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.DisplayContentWorkStatus;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.OutputItemDetailAttItem;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.dto.StatusOfEmployee;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeBasicInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;


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
     * TEST CASE :
     */
    @Test
    public void test_04() {
        List<String> listSid = new ArrayList<>(lstEmployee.keySet());
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId01",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today().addMonths(1), GeneralDate.today().addDays(1).addMonths(1))
                        )));
        val listIds = outputSetting.getDailyOutputItemList().stream().filter(DailyOutputItemsAnnualWorkLedger::isPrintTargetFlag)
                .flatMap(x -> x.getSelectedAttendanceItemList().stream()
                        .map(OutputItemDetailAttItem::getAttendanceItemId))
                .distinct().collect(Collectors.toCollection(ArrayList::new));
        val listMonthlyIds = outputSetting.getMonthlyOutputItemList().stream()
                .flatMap(x -> x.getSelectedAttendanceItemList().stream()
                        .map(OutputItemDetailAttItem::getAttendanceItemId))
                .distinct().collect(Collectors.toCollection(ArrayList::new));
        val clDate = 1;
        YearMonthPeriod yearMonthPeriod = GetSuitableDateByClosureDateUtility.convertPeriod(listEmployeeStatus.get(0).getListPeriod().get(0), clDate);


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

        Map<String, List<MonthlyRecordValueImport>> listMap = new HashMap<>();
        val listValueM = Arrays.asList(
                MonthlyRecordValueImport.of(
                        new YearMonth(202012),
                        Arrays.asList(new ItemValue(
                                        20,
                                        ValueType.TIME,
                                        "123",
                                        1
                                ),
                                new ItemValue(
                                        "ABCFD",
                                        ValueType.DAYS,
                                        "123",
                                        5
                                ))
                )
        );
        listMap.put("eplId01", listValueM);


        new Expectations() {
            {
                require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
                result = listEmployeeStatus;
                require.getValueOf(Collections.singletonList(listEmployeeStatus.get(0).getEmployeeId()), listEmployeeStatus.get(0).getListPeriod().get(0), listIds);
                result = listValue;
                require.getActualMultipleMonth(Collections.singletonList(listEmployeeStatus.get(0).getEmployeeId()), yearMonthPeriod, listMonthlyIds);
                result = listMap;

            }
        };


        val actual = CreateAnnualWorkLedgerContentDomainService.getData(require, datePeriod, lstEmployee,
                outputSetting, lstWorkplaceInfor, lstClosureDateEmployment);
        val expected = DumDataTest.expected;

        assertThat(actual.get(0).getEmployeeCode()).isEqualTo(expected.get(0).getEmployeeCode());
        assertThat(actual.get(0).getEmployeeName()).isEqualTo(expected.get(0).getEmployeeName());
        assertThat(actual.get(0).getWorkplaceCode()).isEqualTo(expected.get(0).getWorkplaceCode());
        assertThat(actual.get(0).getWorkplaceName()).isEqualTo(expected.get(0).getWorkplaceName());
        assertThat(actual.get(0).getEmploymentCode()).isEqualTo(expected.get(0).getEmploymentCode());
        assertThat(actual.get(0).getEmploymentName()).isEqualTo(expected.get(0).getEmploymentName());

        assertThat(actual.get(0).getDailyData().getLeftAttribute()).isEqualTo(expected.get(0).getDailyData().getLeftAttribute());
        assertThat(actual.get(0).getDailyData().getRightAttribute()).isEqualTo(expected.get(0).getDailyData().getRightAttribute());
        assertThat(actual.get(0).getDailyData().getLeftColumnName()).isEqualTo(expected.get(0).getDailyData().getLeftColumnName());
        assertThat(actual.get(0).getDailyData().getRightColumnName()).isEqualTo(expected.get(0).getDailyData().getRightColumnName());
        assertThat(actual.get(0).getDailyData().getLstRightValue().size()).isEqualTo(expected.get(0).getDailyData().getLstRightValue().size());
        assertThat(actual.get(0).getDailyData().getLstLeftValue().size()).isEqualTo(expected.get(0).getDailyData().getLstLeftValue().size());
        assertThat(actual.get(0).getDailyData().getLstLeftValue().size()).isEqualTo(expected.get(0).getDailyData().getLstLeftValue().size());
        assertThat(actual.get(0).getLstMonthlyData().size()).isEqualTo(expected.get(0).getLstMonthlyData().size());

        val ex = expected.get(0).getDailyData().getLstLeftValue();
        val ac = actual.get(0).getDailyData().getLstLeftValue();
        assertThat(ac)
                .extracting(
                        DailyValue::getActualValue,
                        DailyValue::getCharacterValue,
                        DailyValue::getDate
                )
                .containsExactly(
                        tuple(
                                ex.get(0).getActualValue(),
                                ex.get(0).getCharacterValue(),
                                ex.get(0).getDate()));
        val ex1 = expected.get(0).getDailyData().getLstRightValue();
        val ac1 = actual.get(0).getDailyData().getLstRightValue();
        assertThat(ac1)
                .extracting(
                        DailyValue::getActualValue,
                        DailyValue::getCharacterValue,
                        DailyValue::getDate
                )
                .containsExactly(
                        tuple(
                                ex1.get(0).getActualValue(),
                                ex1.get(0).getCharacterValue(),
                                ex.get(0).getDate()));

        val exMonthly = expected.get(0).getLstMonthlyData();
        val acMonthly = actual.get(0).getLstMonthlyData();
        assertThat(acMonthly)
                .extracting(
                        d -> d.getAttribute().value,
                        MonthlyData::getOutputItemName,
                        d -> d.getLstMonthlyValue().size()
                )
                .containsExactly(
                        tuple(
                                exMonthly.get(0).getAttribute().value,
                                exMonthly.get(0).getOutputItemName(),
                                exMonthly.get(0).getLstMonthlyValue().size()
                        ),
                        tuple(
                                exMonthly.get(1).getAttribute().value,
                                exMonthly.get(1).getOutputItemName(),
                                exMonthly.get(1).getLstMonthlyValue().size()
                        ),
                        tuple(
                                exMonthly.get(2).getAttribute().value,
                                exMonthly.get(2).getOutputItemName(),
                                exMonthly.get(2).getLstMonthlyValue().size()
                        ),
                        tuple(
                                exMonthly.get(3).getAttribute().value,
                                exMonthly.get(3).getOutputItemName(),
                                exMonthly.get(3).getLstMonthlyValue().size()
                        ), tuple(
                                exMonthly.get(4).getAttribute().value,
                                exMonthly.get(4).getOutputItemName(),
                                exMonthly.get(4).getLstMonthlyValue().size()
                        ),
                        tuple(
                                exMonthly.get(5).getAttribute().value,
                                exMonthly.get(5).getOutputItemName(),
                                exMonthly.get(5).getLstMonthlyValue().size()
                        ), tuple(
                                exMonthly.get(6).getAttribute().value,
                                exMonthly.get(6).getOutputItemName(),
                                exMonthly.get(6).getLstMonthlyValue().size()
                        ),
                        tuple(
                                exMonthly.get(7).getAttribute().value,
                                exMonthly.get(7).getOutputItemName(),
                                exMonthly.get(7).getLstMonthlyValue().size()
                        ), tuple(
                                exMonthly.get(8).getAttribute().value,
                                exMonthly.get(8).getOutputItemName(),
                                exMonthly.get(8).getLstMonthlyValue().size()
                        ),
                        tuple(
                                exMonthly.get(9).getAttribute().value,
                                exMonthly.get(9).getOutputItemName(),
                                exMonthly.get(9).getLstMonthlyValue().size()
                        )
                );
        assertThat(acMonthly.get(0).getLstMonthlyValue())
                .extracting(
                        MonthlyValue::getActualValue,
                        MonthlyValue::getCharacterValue,
                        MonthlyValue::getDate
                ).containsExactly(
                tuple(
                        exMonthly.get(0).getLstMonthlyValue().get(0).getActualValue(),
                        exMonthly.get(0).getLstMonthlyValue().get(0).getCharacterValue(),
                        exMonthly.get(0).getLstMonthlyValue().get(0).getDate()
                )
        );
        assertThat(acMonthly.get(1).getLstMonthlyValue())
                .extracting(
                        MonthlyValue::getActualValue,
                        MonthlyValue::getCharacterValue,
                        MonthlyValue::getDate
                ).containsExactly(
                tuple(
                        exMonthly.get(1).getLstMonthlyValue().get(0).getActualValue(),
                        exMonthly.get(1).getLstMonthlyValue().get(0).getCharacterValue(),
                        exMonthly.get(1).getLstMonthlyValue().get(0).getDate()
                )
        );
        assertThat(acMonthly.get(2).getLstMonthlyValue())
                .extracting(
                        MonthlyValue::getActualValue,
                        MonthlyValue::getCharacterValue,
                        MonthlyValue::getDate
                ).containsExactly(
                tuple(
                        exMonthly.get(2).getLstMonthlyValue().get(0).getActualValue(),
                        exMonthly.get(2).getLstMonthlyValue().get(0).getCharacterValue(),
                        exMonthly.get(2).getLstMonthlyValue().get(0).getDate()
                )
        );
        assertThat(acMonthly.get(3).getLstMonthlyValue())
                .extracting(
                        MonthlyValue::getActualValue,
                        MonthlyValue::getCharacterValue,
                        MonthlyValue::getDate
                ).containsExactly(
                tuple(
                        exMonthly.get(3).getLstMonthlyValue().get(0).getActualValue(),
                        exMonthly.get(3).getLstMonthlyValue().get(0).getCharacterValue(),
                        exMonthly.get(3).getLstMonthlyValue().get(0).getDate()
                )
        );
        assertThat(acMonthly.get(4).getLstMonthlyValue())
                .extracting(
                        MonthlyValue::getActualValue,
                        MonthlyValue::getCharacterValue,
                        MonthlyValue::getDate
                ).containsExactly(
                tuple(
                        exMonthly.get(4).getLstMonthlyValue().get(0).getActualValue(),
                        exMonthly.get(4).getLstMonthlyValue().get(0).getCharacterValue(),
                        exMonthly.get(4).getLstMonthlyValue().get(0).getDate()
                )
        );
        assertThat(acMonthly.get(5).getLstMonthlyValue())
                .extracting(
                        MonthlyValue::getActualValue,
                        MonthlyValue::getCharacterValue,
                        MonthlyValue::getDate
                ).containsExactly(
                tuple(
                        exMonthly.get(5).getLstMonthlyValue().get(0).getActualValue(),
                        exMonthly.get(5).getLstMonthlyValue().get(0).getCharacterValue(),
                        exMonthly.get(5).getLstMonthlyValue().get(0).getDate()
                )
        );
        assertThat(acMonthly.get(6).getLstMonthlyValue())
                .extracting(
                        MonthlyValue::getActualValue,
                        MonthlyValue::getCharacterValue,
                        MonthlyValue::getDate
                ).containsExactly(
                tuple(
                        exMonthly.get(6).getLstMonthlyValue().get(0).getActualValue(),
                        exMonthly.get(6).getLstMonthlyValue().get(0).getCharacterValue(),
                        exMonthly.get(6).getLstMonthlyValue().get(0).getDate()
                )
        );
        assertThat(acMonthly.get(7).getLstMonthlyValue())
                .extracting(
                        MonthlyValue::getActualValue,
                        MonthlyValue::getCharacterValue,
                        MonthlyValue::getDate
                ).containsExactly(
                tuple(
                        exMonthly.get(7).getLstMonthlyValue().get(0).getActualValue(),
                        exMonthly.get(7).getLstMonthlyValue().get(0).getCharacterValue(),
                        exMonthly.get(7).getLstMonthlyValue().get(0).getDate()
                )
        );
        assertThat(acMonthly.get(8).getLstMonthlyValue())
                .extracting(
                        MonthlyValue::getActualValue,
                        MonthlyValue::getCharacterValue,
                        MonthlyValue::getDate
                ).containsExactly(
                tuple(
                        exMonthly.get(8).getLstMonthlyValue().get(0).getActualValue(),
                        exMonthly.get(8).getLstMonthlyValue().get(0).getCharacterValue(),
                        exMonthly.get(8).getLstMonthlyValue().get(0).getDate()
                )
        );
        assertThat(acMonthly.get(9).getLstMonthlyValue())
                .extracting(
                        MonthlyValue::getActualValue,
                        MonthlyValue::getCharacterValue,
                        MonthlyValue::getDate
                ).containsExactly(
                tuple(
                        exMonthly.get(9).getLstMonthlyValue().get(0).getActualValue(),
                        exMonthly.get(9).getLstMonthlyValue().get(0).getCharacterValue(),
                        exMonthly.get(9).getLstMonthlyValue().get(0).getDate()
                )
        );
    }
}
