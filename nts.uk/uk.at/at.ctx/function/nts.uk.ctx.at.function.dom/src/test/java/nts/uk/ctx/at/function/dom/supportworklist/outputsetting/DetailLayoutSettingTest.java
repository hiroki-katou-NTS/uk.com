package nts.uk.ctx.at.function.dom.supportworklist.outputsetting;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportAggregationUnit;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkAggregationSetting;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata.SupportWorkOutputData;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class DetailLayoutSettingTest {
    @Injectable
    SupportWorkOutputDataRequire require;

    @Test
    public void testGetters() {
        DetailLayoutSetting setting = new DetailLayoutSetting(
                EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES,
                new DetailDisplaySetting(NotUseAtr.NOT_USE, new ArrayList<>()),
                new GrandTotalDisplaySetting(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE),
                new WorkplaceTotalDisplaySetting(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE),
                NotUseAtr.NOT_USE
        );

        NtsAssert.invokeGetters(setting);
    }

    @Test
    public void testGetOutputDataByWorkplaceError() {
        String companyId = "000000000003-0001";

        DetailLayoutSetting setting = new DetailLayoutSetting(
                EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES,
                new DetailDisplaySetting(NotUseAtr.NOT_USE, new ArrayList<>()),
                new GrandTotalDisplaySetting(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE),
                new WorkplaceTotalDisplaySetting(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE),
                NotUseAtr.NOT_USE
        );

        NtsAssert.businessException("Msg_3263", () -> {
            setting.getOutputDataByWorkplace(
                    require,
                    companyId,
                    new DatePeriod(GeneralDate.today(), GeneralDate.today()),
                    GeneralDate.today(),
                    Arrays.asList("")
            );
        });
    }

    @Test
    public void testGetOutputDataByWorkplaceSuccess() {
        String companyId = "000000000003-0001";
        String workplaceId = "workplace-id-0001";

        DetailLayoutSetting setting = new DetailLayoutSetting(
                EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES,
                new DetailDisplaySetting(NotUseAtr.NOT_USE, new ArrayList<>()),
                new GrandTotalDisplaySetting(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE),
                new WorkplaceTotalDisplaySetting(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE),
                NotUseAtr.NOT_USE
        );

        new Expectations() {{
            require.getSetting(companyId);
            result = Optional.of(new SupportWorkAggregationSetting(
                    SupportAggregationUnit.WORK_LOCATION,
                    Optional.empty(),
                    Optional.empty()
            ));
        }};

        SupportWorkOutputData outputData = setting.getOutputDataByWorkplace(
                require,
                companyId,
                new DatePeriod(GeneralDate.today(), GeneralDate.today()),
                GeneralDate.today(),
                Arrays.asList(workplaceId)
        );

        assertThat(outputData.getSupportWorkDataList().size()).isEqualTo(0);
    }

    @Test
    public void testGetOutputDataByWorkLocationError() {
        String companyId = "000000000003-0001";

        DetailLayoutSetting setting = new DetailLayoutSetting(
                EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES,
                new DetailDisplaySetting(NotUseAtr.NOT_USE, new ArrayList<>()),
                new GrandTotalDisplaySetting(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE),
                new WorkplaceTotalDisplaySetting(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE),
                NotUseAtr.NOT_USE
        );

        NtsAssert.businessException("Msg_3263", () -> {
            setting.getOutputDataByWorkLocation(
                    require,
                    companyId,
                    new DatePeriod(GeneralDate.today(), GeneralDate.today()),
                    Arrays.asList("")
            );
        });
    }

    @Test
    public void testGetOutputDataByWorkLocationSuccess() {
        String companyId = "000000000003-0001";
        String workLocationCode = "0001";

        DetailLayoutSetting setting = new DetailLayoutSetting(
                EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES,
                new DetailDisplaySetting(NotUseAtr.NOT_USE, new ArrayList<>()),
                new GrandTotalDisplaySetting(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE),
                new WorkplaceTotalDisplaySetting(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, NotUseAtr.NOT_USE),
                NotUseAtr.NOT_USE
        );

        new Expectations() {{
            require.getSetting(companyId);
            result = Optional.of(new SupportWorkAggregationSetting(
                    SupportAggregationUnit.WORK_LOCATION,
                    Optional.empty(),
                    Optional.empty()
            ));
        }};

        SupportWorkOutputData outputData = setting.getOutputDataByWorkLocation(
                require,
                companyId,
                new DatePeriod(GeneralDate.today(), GeneralDate.today()),
                Arrays.asList(workLocationCode)
        );

        assertThat(outputData.getSupportWorkDataList().size()).isEqualTo(0);
    }
}
