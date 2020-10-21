package outputitemsofworkstatustable;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingCode;
import nts.uk.ctx.at.function.dom.dailyworkschedule.OutputItemSettingName;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.*;
import nts.uk.ctx.at.function.dom.outputitemsofworkstatustable.enums.*;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;


@RunWith(JMockit.class)
public class CreateDisplayContentWorkStatusDSTest {

    @Injectable
    private CreateDisplayContentWorkStatusDService.Require require;

    private final DatePeriod datePeriod = new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(10));

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
    private final WorkStatusOutputSettings workStatusOutputSettings = new WorkStatusOutputSettings(
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
                            EnumAdaptor.valueOf(1, IndependentCalculationClassification.class),
                            EnumAdaptor.valueOf(1, DailyMonthlyClassification.class),
                            EnumAdaptor.valueOf(1, CommonAttributesOfForms.class),
                            Arrays.asList(
                                    new OutputItemDetailSelectionAttendanceItem(EnumAdaptor.valueOf(1, OperatorsCommonToForms.class), 1),
                                    new OutputItemDetailSelectionAttendanceItem(EnumAdaptor.valueOf(2, OperatorsCommonToForms.class), 1)
                            )
                    ),
                    new OutputItem(
                            2,
                            new FormOutputItemName("itemName"),
                            true,
                            EnumAdaptor.valueOf(2, IndependentCalculationClassification.class),
                            EnumAdaptor.valueOf(2, DailyMonthlyClassification.class),
                            EnumAdaptor.valueOf(2, CommonAttributesOfForms.class),
                            Arrays.asList(
                                    new OutputItemDetailSelectionAttendanceItem(
                                            EnumAdaptor.valueOf(2, OperatorsCommonToForms.class),
                                            2),
                                    new OutputItemDetailSelectionAttendanceItem(
                                            EnumAdaptor.valueOf(1, OperatorsCommonToForms.class),
                                            2)
                            )
                    ))

    );
    @Test
    public void test_01(){

    }

}
