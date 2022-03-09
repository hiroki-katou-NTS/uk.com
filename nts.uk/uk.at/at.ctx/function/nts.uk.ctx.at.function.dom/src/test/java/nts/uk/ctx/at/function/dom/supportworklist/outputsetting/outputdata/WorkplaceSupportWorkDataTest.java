package nts.uk.ctx.at.function.dom.supportworklist.outputsetting.outputdata;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.supportworklist.SupportWorkDetailsHelper;
import nts.uk.ctx.at.function.dom.supportworklist.aggregationsetting.SupportWorkDetails;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.EmployeeExtractCondition;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.SupportWorkOutputDataRequire;
import nts.uk.ctx.at.function.dom.supportworklist.outputsetting.WorkplaceTotalDisplaySetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class WorkplaceSupportWorkDataTest {
    @Injectable
    SupportWorkOutputDataRequire require;

    @Test
    public void testGetters() {
        WorkplaceSupportWorkData data = new WorkplaceSupportWorkData(
                require,
                "000000000003-0001",
                "workplace-id-0001",
                Arrays.asList(SupportWorkDetailsHelper.createDetailData("", GeneralDate.today(), Arrays.asList(930))),
                new WorkplaceTotalDisplaySetting(
                        NotUseAtr.NOT_USE,
                        NotUseAtr.NOT_USE,
                        NotUseAtr.NOT_USE,
                        NotUseAtr.NOT_USE
                ),
                EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
        );

        NtsAssert.invokeGetters(data);
    }

    @Test
    public void testNotDisplayTotalOneDay() {
        List<Integer> attendanceItemIds = Arrays.asList(929, 930, 1305, 1306, 1309, 1336, 2191);
        SupportWorkDetails supportWorkDetail1 = SupportWorkDetailsHelper.createDetailData("employee-id-0001", GeneralDate.today(), attendanceItemIds);
        supportWorkDetail1.setSupportWork(true);
        SupportWorkDetails supportWorkDetail2 = SupportWorkDetailsHelper.createDetailData("employee-id-0002", GeneralDate.today(), attendanceItemIds);

        WorkplaceSupportWorkData data = new WorkplaceSupportWorkData(
                require,
                "000000000003-0001",
                "workplace-id-0001",
                Arrays.asList(supportWorkDetail1, supportWorkDetail2),
                new WorkplaceTotalDisplaySetting(
                        NotUseAtr.NOT_USE,
                        NotUseAtr.USE,
                        NotUseAtr.USE,
                        NotUseAtr.USE
                ),
                EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
        );

        assertThat(data.getTotalAffiliation().get().getPeopleCount()).isEqualTo(1);
        assertThat(data.getTotalSupport().get().getPeopleCount()).isEqualTo(1);
        assertThat(data.getTotalWorkplace().get().getPeopleCount()).isEqualTo(2);
        assertThat(data.getSupportDetails().size()).isEqualTo(1);
        assertThat(data.getSupportWorkDetails().get(0).getTotalDetailOfDay().isPresent()).isEqualTo(false);
    }

    @Test
    public void testNotDisplaySupportDetail() {
        List<Integer> attendanceItemIds = Arrays.asList(929, 930, 1305, 1306, 1309, 1336, 2191);
        SupportWorkDetails supportWorkDetail1 = SupportWorkDetailsHelper.createDetailData("employee-id-0001", GeneralDate.today(), attendanceItemIds);
        supportWorkDetail1.setSupportWork(true);
        SupportWorkDetails supportWorkDetail2 = SupportWorkDetailsHelper.createDetailData("employee-id-0002", GeneralDate.today(), attendanceItemIds);

        WorkplaceSupportWorkData data = new WorkplaceSupportWorkData(
                require,
                "",
                "workplace-id-0001",
                Arrays.asList(supportWorkDetail1, supportWorkDetail2),
                new WorkplaceTotalDisplaySetting(
                        NotUseAtr.USE,
                        NotUseAtr.NOT_USE,
                        NotUseAtr.USE,
                        NotUseAtr.USE
                ),
                EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
        );

        assertThat(data.getTotalAffiliation().get().getPeopleCount()).isEqualTo(1);
        assertThat(data.getTotalSupport().get().getPeopleCount()).isEqualTo(1);
        assertThat(data.getTotalWorkplace().get().getPeopleCount()).isEqualTo(2);
        assertThat(data.getSupportDetails().size()).isEqualTo(0);
        assertThat(data.getSupportWorkDetails().get(0).getTotalDetailOfDay().get().getPeopleCount()).isEqualTo(2);
    }

    @Test
    public void testNotDisplaySupportWorkplace() {
        List<Integer> attendanceItemIds = Arrays.asList(929, 930, 1305, 1306, 1309, 1336, 2191);
        SupportWorkDetails supportWorkDetail1 = SupportWorkDetailsHelper.createDetailData("employee-id-0001", GeneralDate.today(), attendanceItemIds);
        supportWorkDetail1.setSupportWork(true);
        SupportWorkDetails supportWorkDetail2 = SupportWorkDetailsHelper.createDetailData("employee-id-0002", GeneralDate.today(), attendanceItemIds);

        WorkplaceSupportWorkData data = new WorkplaceSupportWorkData(
                require,
                "000000000003-0001",
                "workplace-id-0001",
                Arrays.asList(supportWorkDetail1, supportWorkDetail2),
                new WorkplaceTotalDisplaySetting(
                        NotUseAtr.USE,
                        NotUseAtr.USE,
                        NotUseAtr.NOT_USE,
                        NotUseAtr.USE
                ),
                EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
        );

        assertThat(data.getTotalAffiliation().isPresent()).isEqualTo(false);
        assertThat(data.getTotalSupport().isPresent()).isEqualTo(false);
        assertThat(data.getTotalWorkplace().get().getPeopleCount()).isEqualTo(2);
        assertThat(data.getSupportDetails().size()).isEqualTo(1);
        assertThat(data.getSupportWorkDetails().get(0).getTotalDetailOfDay().get().getPeopleCount()).isEqualTo(2);
    }

    @Test
    public void testNotDisplayWorkplaceTotal() {
        List<Integer> attendanceItemIds = Arrays.asList(929, 930, 1305, 1306, 1309, 1336, 2191);
        SupportWorkDetails supportWorkDetail1 = SupportWorkDetailsHelper.createDetailData("employee-id-0001", GeneralDate.today(), attendanceItemIds);
        supportWorkDetail1.setSupportWork(true);
        SupportWorkDetails supportWorkDetail2 = SupportWorkDetailsHelper.createDetailData("employee-id-0002", GeneralDate.today(), attendanceItemIds);

        WorkplaceSupportWorkData data = new WorkplaceSupportWorkData(
                require,
                "000000000003-0001",
                "workplace-id-0001",
                Arrays.asList(supportWorkDetail1, supportWorkDetail2),
                new WorkplaceTotalDisplaySetting(
                        NotUseAtr.USE,
                        NotUseAtr.USE,
                        NotUseAtr.USE,
                        NotUseAtr.NOT_USE
                ),
                EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
        );

        assertThat(data.getTotalAffiliation().get().getPeopleCount()).isEqualTo(1);
        assertThat(data.getTotalSupport().get().getPeopleCount()).isEqualTo(1);
        assertThat(data.getTotalWorkplace().isPresent()).isEqualTo(false);
        assertThat(data.getSupportDetails().size()).isEqualTo(1);
        assertThat(data.getSupportWorkDetails().get(0).getTotalDetailOfDay().get().getPeopleCount()).isEqualTo(2);
    }

    @Test
    public void testSupportDetailByAffiliationInfo() {
        List<Integer> attendanceItemIds = Arrays.asList(929, 930, 1305, 1306, 1309, 1336, 2191);
        SupportWorkDetails supportWorkDetail1 = SupportWorkDetailsHelper.createDetailData("employee-id-0001", GeneralDate.today(), attendanceItemIds);
        supportWorkDetail1.setSupportWork(true);
        SupportWorkDetails supportWorkDetail2 = SupportWorkDetailsHelper.createDetailData("employee-id-0002", GeneralDate.today(), attendanceItemIds);

        WorkplaceSupportWorkData data = new WorkplaceSupportWorkData(
                require,
                "000000000003-0001",
                "workplace-id-0001",
                Arrays.asList(supportWorkDetail1, supportWorkDetail2),
                new WorkplaceTotalDisplaySetting(
                        NotUseAtr.USE,
                        NotUseAtr.USE,
                        NotUseAtr.USE,
                        NotUseAtr.USE
                ),
                EmployeeExtractCondition.EXTRACT_EMPLOYEES_GO_TO_SUPPORT
        );

        assertThat(data.getSupportDetails().size()).isEqualTo(1);
        assertThat(data.getSupportDetails().get(0).getSupportDestination()).isEqualTo("workInfo");
    }

    @Test
    public void testSupportDetailByWorkInfo() {
        List<Integer> attendanceItemIds = Arrays.asList(929, 930, 1305, 1306, 1309, 1336, 2191);
        SupportWorkDetails supportWorkDetail1 = SupportWorkDetailsHelper.createDetailData("employee-id-0001", GeneralDate.today(), attendanceItemIds);
        supportWorkDetail1.setSupportWork(true);
        SupportWorkDetails supportWorkDetail2 = SupportWorkDetailsHelper.createDetailData("employee-id-0002", GeneralDate.today(), attendanceItemIds);

        WorkplaceSupportWorkData data = new WorkplaceSupportWorkData(
                require,
                "000000000003-0001",
                "workplace-id-0001",
                Arrays.asList(supportWorkDetail1, supportWorkDetail2),
                new WorkplaceTotalDisplaySetting(
                        NotUseAtr.USE,
                        NotUseAtr.USE,
                        NotUseAtr.USE,
                        NotUseAtr.USE
                ),
                EmployeeExtractCondition.EXTRACT_ALL_WORKING_EMPLOYEES
        );

        assertThat(data.getSupportDetails().size()).isEqualTo(1);
        assertThat(data.getSupportDetails().get(0).getSupportDestination()).isEqualTo("affiliationInfo");
    }
}
