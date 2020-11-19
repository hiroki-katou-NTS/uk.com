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
    private static final List<EmployeeInfor> employeeInfors = DumData.employeeInfors;
    private static final List<WorkPlaceInfo> workPlaceInfo = DumData.workPlaceInfo;
    private final OutputItemSettingCode code = new OutputItemSettingCode("ABC");
    private final OutputItemSettingName name = new OutputItemSettingName("CBA");
    private final String iD = "iD";
    private final String empId = "employeeId";
    private final SettingClassificationCommon settingCategory = SettingClassificationCommon.STANDARD_SELECTION;
    private final WorkStatusOutputSettings domain = DumData.dum(code, name, empId, iD, settingCategory);

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
                    domain, workPlaceInfo);
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
                        "25")
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
                domain, workPlaceInfo);
        val expected = DumData.expected;
        assertThat(actual.get(0).getEmployeeName()).isEqualTo(expected.get(0).getEmployeeName());
        assertThat(actual.get(0).getEmployeeCode()).isEqualTo(expected.get(0).getEmployeeCode());
        assertThat(actual.get(0).getWorkPlaceCode()).isEqualTo(expected.get(0).getWorkPlaceCode());
        assertThat(actual.get(0).getWorkPlaceName()).isEqualTo(expected.get(0).getWorkPlaceName());

        assertThat(actual.get(0).getOutputItemOneLines().get(0).getTotalOfOneLine())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getTotalOfOneLine());
        assertThat(actual.get(0).getOutputItemOneLines().get(0).getOutPutItemName())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutPutItemName());

        assertThat(actual.get(0).getOutputItemOneLines().get(1).getTotalOfOneLine())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(1).getTotalOfOneLine());

        assertThat(actual.get(0).getOutputItemOneLines().get(1).getOutPutItemName())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(1).getOutPutItemName());

        assertThat(actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getActualValue())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getActualValue());

        assertThat(actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getAttributes())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getAttributes());


        assertThat(actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getCharacterValue())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getCharacterValue());

        assertThat(actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getDate())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(0).getDate());

        assertThat(actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getActualValue())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getActualValue());

        assertThat(actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getAttributes())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getAttributes());


        assertThat(actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getCharacterValue())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getCharacterValue());

        assertThat(actual.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getDate())
                .isEqualTo(expected.get(0).getOutputItemOneLines().get(0).getOutItemValue().get(1).getDate());

    }

}
