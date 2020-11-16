package nts.uk.ctx.at.function.dom.outputitemsofworkstatustable;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
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
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@RunWith(JMockit.class)
public class CreateDisplayContentWorkStatusDSTest {

    @Injectable
    private CreateDisplayContentWorkStatusDService.Require require;

    private final DatePeriod datePeriod = new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1));

    private final List<EmployeeInfor> employeeInfors = Arrays.asList(new EmployeeInfor(
            "eplId01",
            "eplCode01",
            "eplName01",
            "wplId01"

    ), new EmployeeInfor(
            "eplId01",
            "eplCode01",
            "eplName01",
            "wplId01"
    ));
    private final WorkStatusOutputSettings outputSettings01 = new WorkStatusOutputSettings(
            "settingId",
            new OutputItemSettingCode("outPutSettingCode"),
            new OutputItemSettingName("oputSettingName"),
            "employeeId",
            EnumAdaptor.valueOf(1, SettingClassificationCommon.class),
            Arrays.asList(
                    new OutputItem(
                            1,
                            new FormOutputItemName("itemName01"),
                            true,
                            EnumAdaptor.valueOf(1, IndependentCalcClassic.class),
                            EnumAdaptor.valueOf(1, DailyMonthlyClassification.class),
                            EnumAdaptor.valueOf(1, CommonAttributesOfForms.class),
                            Arrays.asList(
                                    new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 1),
                                    new OutputItemDetailAttItem(EnumAdaptor.valueOf(2, OperatorsCommonToForms.class), 1)
                            )
                    ),
                    new OutputItem(
                            2,
                            new FormOutputItemName("itemName02"),
                            true,
                            EnumAdaptor.valueOf(2, IndependentCalcClassic.class),
                            EnumAdaptor.valueOf(2, DailyMonthlyClassification.class),
                            EnumAdaptor.valueOf(2, CommonAttributesOfForms.class),
                            Arrays.asList(
                                    new OutputItemDetailAttItem(
                                            EnumAdaptor.valueOf(2, OperatorsCommonToForms.class),
                                            2),
                                    new OutputItemDetailAttItem(
                                            EnumAdaptor.valueOf(1, OperatorsCommonToForms.class),
                                            2)
                            )
                    ))

    );
    private final WorkStatusOutputSettings outputSettings02 = new WorkStatusOutputSettings(
            "settingId",
            new OutputItemSettingCode("outPutSettingCode"),
            new OutputItemSettingName("oputSettingName"),
            "employeeId",
            EnumAdaptor.valueOf(1, SettingClassificationCommon.class),
            Arrays.asList(
                    new OutputItem(
                            1,
                            new FormOutputItemName("itemName01"),
                            true,
                            EnumAdaptor.valueOf(1, IndependentCalcClassic.class),
                            EnumAdaptor.valueOf(1, DailyMonthlyClassification.class),
                            EnumAdaptor.valueOf(3, CommonAttributesOfForms.class),
                            Arrays.asList(
                                    new OutputItemDetailAttItem(EnumAdaptor.valueOf(2, OperatorsCommonToForms.class), 1),
                                    new OutputItemDetailAttItem(EnumAdaptor.valueOf(2, OperatorsCommonToForms.class), 1)
                            )
                    ),
                    new OutputItem(
                            2,
                            new FormOutputItemName("itemName02"),
                            true,
                            EnumAdaptor.valueOf(2, IndependentCalcClassic.class),
                            EnumAdaptor.valueOf(2, DailyMonthlyClassification.class),
                            EnumAdaptor.valueOf(5, CommonAttributesOfForms.class),
                            Arrays.asList(
                                    new OutputItemDetailAttItem(
                                            EnumAdaptor.valueOf(2, OperatorsCommonToForms.class),
                                            2),
                                    new OutputItemDetailAttItem(
                                            EnumAdaptor.valueOf(1, OperatorsCommonToForms.class),
                                            2)
                            )
                    ))

    );
    private final WorkStatusOutputSettings outputSettings03 = new WorkStatusOutputSettings(
            "settingId",
            new OutputItemSettingCode("outPutSettingCode"),
            new OutputItemSettingName("oputSettingName"),
            "employeeId",
            EnumAdaptor.valueOf(1, SettingClassificationCommon.class),
            Arrays.asList(
                    new OutputItem(
                            1,
                            new FormOutputItemName("itemName01"),
                            true,
                            EnumAdaptor.valueOf(1, IndependentCalcClassic.class),
                            EnumAdaptor.valueOf(1, DailyMonthlyClassification.class),
                            EnumAdaptor.valueOf(3, CommonAttributesOfForms.class),
                            Arrays.asList(
                                    new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 1),
                                    new OutputItemDetailAttItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 1)
                            )
                    ),
                    new OutputItem(
                            2,
                            new FormOutputItemName("itemName02"),
                            true,
                            EnumAdaptor.valueOf(2, IndependentCalcClassic.class),
                            EnumAdaptor.valueOf(2, DailyMonthlyClassification.class),
                            EnumAdaptor.valueOf(5, CommonAttributesOfForms.class),
                            Arrays.asList(
                                    new OutputItemDetailAttItem(
                                            EnumAdaptor.valueOf(2, OperatorsCommonToForms.class),
                                            2),
                                    new OutputItemDetailAttItem(
                                            EnumAdaptor.valueOf(1, OperatorsCommonToForms.class),
                                            2)
                            )
                    ))

    );
    private final List<WorkPlaceInfo> workPlaceInfo = Arrays.asList(
            new WorkPlaceInfo(
                    "wplId01"
                    , "wplCode01",
                    "wplName01"),
            new WorkPlaceInfo("wplId02"
                    , "wplCode02",
                    "wplName02")
    );

    @Test
    public void test_01() {
        val listSid = employeeInfors.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        new Expectations() {
            {
                require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
                result = null;
            }
        };
        NtsAssert.businessException("Msg_1816", () -> {
            CreateDisplayContentWorkStatusDService.displayContentsOfWorkStatus(require, datePeriod, employeeInfors,
                    outputSettings01, workPlaceInfo);
        });
    }

    @Test
    public void test_02() {
        val listSid = employeeInfors.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId01",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));
        val listAttd = new AttendanceResultDto(
                "eplId01",
                GeneralDate.today(),
                Arrays.asList(new AttendanceItemDtoValue(
                        1,
                        1,
                        "ABC")
                )
        );
        new Expectations() {
            {
                require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
                result = listEmployeeStatus;

                require.getValueOf("eplId01", GeneralDate.today(),
                        Arrays.asList(1, 1));
                result = listAttd;
            }
        };
        val actual = CreateDisplayContentWorkStatusDService.displayContentsOfWorkStatus(require, datePeriod, employeeInfors,
                outputSettings01, workPlaceInfo);
        val expected = Arrays.asList(
                new DisplayContentWorkStatus(
                        "eplCode01",
                        "eplName01",
                        "wplCode01",
                        "wplName01",
                        Arrays.asList(
                                new OutputItemOneLine(
                                        0D,
                                        "itemName01",
                                        Arrays.asList(
                                                new DailyValue(
                                                        0D,
                                                        EnumAdaptor.valueOf(1, CommonAttributesOfForms.class),
                                                        "ABCABC",
                                                        GeneralDate.today()
                                                ),
                                                new DailyValue(
                                                        0D,
                                                        EnumAdaptor.valueOf(1, CommonAttributesOfForms.class),
                                                        "",
                                                        GeneralDate.today().addDays(1)
                                                )
                                        )
                                ),
                                new OutputItemOneLine(
                                        0D,
                                        "itemName02",
                                        Arrays.asList(
                                                new DailyValue(
                                                        0D,
                                                        EnumAdaptor.valueOf(2, CommonAttributesOfForms.class),
                                                        "",
                                                        GeneralDate.today()
                                                ),
                                                new DailyValue(
                                                        0D,
                                                        EnumAdaptor.valueOf(2, CommonAttributesOfForms.class),
                                                        "",
                                                        GeneralDate.today().addDays(1)
                                                )
                                        )
                                ))
                )
        );

        assertThat (actual.get(0).getEmployeeName()).isEqualTo(expected.get(0).getEmployeeName());
        assertThat (actual.get(0).getEmployeeCode()).isEqualTo(expected.get(0).getEmployeeCode());
        assertThat (actual.get(0).getWorkPlaceCode()).isEqualTo(expected.get(0).getWorkPlaceCode());
        assertThat (actual.get(0).getWorkPlaceName()).isEqualTo(expected.get(0).getWorkPlaceName());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getTotalOfOneLine())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getTotalOfOneLine());
        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutPutItemName())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutPutItemName());

        assertThat (actual.get(0).getOutputItemOneLines().get(1).getTotalOfOneLine())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(1).getTotalOfOneLine());

        assertThat (actual.get(0).getOutputItemOneLines().get(1).getOutPutItemName())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(1).getOutPutItemName());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getActualValue())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getActualValue());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getAttributes())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getAttributes());


        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getCharacterValue())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getCharacterValue());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getDate())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getDate());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getActualValue())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getActualValue());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getAttributes())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getAttributes());


        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getCharacterValue())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getCharacterValue());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getDate())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getDate());

    }

    @Test
    public void test_03() {
        val listSid = employeeInfors.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId01",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));
        val listAttd = new AttendanceResultDto(
                "eplId01",
                GeneralDate.today(),
                Arrays.asList(new AttendanceItemDtoValue(
                        1,
                        1,
                        "12")
                )
        );
        new Expectations() {
            {
                require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
                result = listEmployeeStatus;

                require.getValueOf("eplId01", GeneralDate.today(),
                        Arrays.asList(1, 1));
                result = listAttd;
            }
        };
        val actual = CreateDisplayContentWorkStatusDService.displayContentsOfWorkStatus(require, datePeriod, employeeInfors,
                outputSettings02, workPlaceInfo);
        val expected = Arrays.asList(
                new DisplayContentWorkStatus(
                        "eplCode01",
                        "eplName01",
                        "wplCode01",
                        "wplName01",
                        Arrays.asList(
                                new OutputItemOneLine(
                                        -24D,
                                        "itemName01",
                                        Arrays.asList(
                                                new DailyValue(
                                                        -24D,
                                                        EnumAdaptor.valueOf(3, CommonAttributesOfForms.class),
                                                        "",
                                                        GeneralDate.today()
                                                ),
                                                new DailyValue(
                                                        0D,
                                                        EnumAdaptor.valueOf(3, CommonAttributesOfForms.class),
                                                        "",
                                                        GeneralDate.today().addDays(1)
                                                )
                                        )
                                ),
                                new OutputItemOneLine(
                                        0D,
                                        "itemName02",
                                        Arrays.asList(
                                                new DailyValue(
                                                        0D,
                                                        EnumAdaptor.valueOf(2, CommonAttributesOfForms.class),
                                                        "",
                                                        GeneralDate.today()
                                                ),
                                                new DailyValue(
                                                        0D,
                                                        EnumAdaptor.valueOf(2, CommonAttributesOfForms.class),
                                                        "",
                                                        GeneralDate.today().addDays(1)
                                                )
                                        )
                                ))
                )
        );

        assertThat (actual.get(0).getEmployeeName()).isEqualTo(expected.get(0).getEmployeeName());
        assertThat (actual.get(0).getEmployeeCode()).isEqualTo(expected.get(0).getEmployeeCode());
        assertThat (actual.get(0).getWorkPlaceCode()).isEqualTo(expected.get(0).getWorkPlaceCode());
        assertThat (actual.get(0).getWorkPlaceName()).isEqualTo(expected.get(0).getWorkPlaceName());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getTotalOfOneLine())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getTotalOfOneLine());
        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutPutItemName())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutPutItemName());

        assertThat (actual.get(0).getOutputItemOneLines().get(1).getTotalOfOneLine())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(1).getTotalOfOneLine());

        assertThat (actual.get(0).getOutputItemOneLines().get(1).getOutPutItemName())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(1).getOutPutItemName());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getActualValue())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getActualValue());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getAttributes())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getAttributes());


        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getCharacterValue())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getCharacterValue());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getDate())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getDate());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getActualValue())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getActualValue());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getAttributes())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getAttributes());


        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getCharacterValue())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getCharacterValue());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getDate())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getDate());

    }
    @Test
    public void test_04() {
        val listSid = employeeInfors.stream().map(EmployeeInfor::getEmployeeId).collect(Collectors.toList());
        val listEmployeeStatus = Arrays.asList(
                new StatusOfEmployee("eplId01",
                        Arrays.asList(
                                new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1))
                        )));
        val listAttd = new AttendanceResultDto(
                "eplId01",
                GeneralDate.today(),
                Arrays.asList(new AttendanceItemDtoValue(
                        1,
                        1,
                        "12")
                )
        );
        new Expectations() {
            {
                require.getListAffComHistByListSidAndPeriod(listSid, datePeriod);
                result = listEmployeeStatus;

                require.getValueOf("eplId01", GeneralDate.today(),
                        Arrays.asList(1, 1));
                result = listAttd;
            }
        };
        val actual = CreateDisplayContentWorkStatusDService.displayContentsOfWorkStatus(require, datePeriod, employeeInfors,
                outputSettings03, workPlaceInfo);
        val expected = Arrays.asList(
                new DisplayContentWorkStatus(
                        "eplCode01",
                        "eplName01",
                        "wplCode01",
                        "wplName01",
                        Arrays.asList(
                                new OutputItemOneLine(
                                        24D,
                                        "itemName01",
                                        Arrays.asList(
                                                new DailyValue(
                                                        24D,
                                                        EnumAdaptor.valueOf(3, CommonAttributesOfForms.class),
                                                        "",
                                                        GeneralDate.today()
                                                ),
                                                new DailyValue(
                                                        0D,
                                                        EnumAdaptor.valueOf(3, CommonAttributesOfForms.class),
                                                        "",
                                                        GeneralDate.today().addDays(1)
                                                )
                                        )
                                ),
                                new OutputItemOneLine(
                                        0D,
                                        "itemName02",
                                        Arrays.asList(
                                                new DailyValue(
                                                        0D,
                                                        EnumAdaptor.valueOf(2, CommonAttributesOfForms.class),
                                                        "",
                                                        GeneralDate.today()
                                                ),
                                                new DailyValue(
                                                        0D,
                                                        EnumAdaptor.valueOf(2, CommonAttributesOfForms.class),
                                                        "",
                                                        GeneralDate.today().addDays(1)
                                                )
                                        )
                                ))
                )
        );

        assertThat (actual.get(0).getEmployeeName()).isEqualTo(expected.get(0).getEmployeeName());
        assertThat (actual.get(0).getEmployeeCode()).isEqualTo(expected.get(0).getEmployeeCode());
        assertThat (actual.get(0).getWorkPlaceCode()).isEqualTo(expected.get(0).getWorkPlaceCode());
        assertThat (actual.get(0).getWorkPlaceName()).isEqualTo(expected.get(0).getWorkPlaceName());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getTotalOfOneLine())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getTotalOfOneLine());
        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutPutItemName())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutPutItemName());

        assertThat (actual.get(0).getOutputItemOneLines().get(1).getTotalOfOneLine())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(1).getTotalOfOneLine());

        assertThat (actual.get(0).getOutputItemOneLines().get(1).getOutPutItemName())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(1).getOutPutItemName());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getActualValue())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getActualValue());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getAttributes())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getAttributes());


        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getCharacterValue())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getCharacterValue());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getDate())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getDate());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getActualValue())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getActualValue());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getAttributes())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getAttributes());


        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getCharacterValue())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getCharacterValue());

        assertThat (actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getDate())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getDate());

    }
}
