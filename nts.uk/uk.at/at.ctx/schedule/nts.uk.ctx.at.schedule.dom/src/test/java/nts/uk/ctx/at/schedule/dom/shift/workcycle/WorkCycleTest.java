package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import mockit.Injectable;
import nts.arc.error.BusinessException;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.testing.exception.BusinessExceptionAssert;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.integration.junit4.JMockit;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class WorkCycleTest {

    @Injectable
    private WorkInformation.Require require;

    @Test
    public void testEmptyCycleInfoList() {
        List<WorkCycleInfo> infos = new ArrayList<>();
        NtsAssert.businessException("Msg_1688", ()->{
            WorkCycle item = new WorkCycle("cid","code","name", infos);
        });
    }

    @Test
    public void testGetWorkInfo() {
        List<WorkCycleInfo> infos = WorkCycleTestHelper.WorkCycleInfoHelper.createListForTest(3);
        WorkCycle item = WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos);
        WorkCycleInfo result = item.getWorkInfo(10, 2);
        assertThat(result.getWorkInformation().getWorkTypeCode().v()).isEqualTo("002");
        assertThat(result.getWorkInformation().getWorkTimeCode().v()).isEqualTo("002");
    }

    @Test
    public void testGetWorkInfo_01() {
        List<WorkCycleInfo> infos = WorkCycleTestHelper.WorkCycleInfoHelper.createListForTest(3);
        WorkCycle item = WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos);
        WorkCycleInfo result = item.getWorkInfo(10, 10);
        assertThat(result.getWorkInformation().getWorkTypeCode().v()).isEqualTo("001");
        assertThat(result.getWorkInformation().getWorkTimeCode().v()).isEqualTo("001");
    }

    @Test
    public void testGetter(){
        List<WorkCycleInfo> infos = WorkCycleTestHelper.WorkCycleInfoHelper.createListForTest(3);
        WorkCycle item = WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos);
        NtsAssert.invokeGetters(item);
    }

    @Test
    public void testGetter_01() {
        WorkCycle item = new WorkCycle();
        NtsAssert.invokeGetters(item);
    }

    @Test
    public void testGetErrorList() {
        List<WorkCycleInfo> infos = WorkCycleTestHelper.WorkCycleInfoHelper.createListForTest(3);
        WorkCycle item = WorkCycleTestHelper.WorkCycleHelper.createWorkCycleForTest(infos);
        List<ErrorStatusWorkInfo> errors = item.checkError(require);
        List<ErrorStatusWorkInfo> expected = new ArrayList<>();
        expected.add(ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE);
        expected.add(ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE);
        expected.add(ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE);
        assertThat(errors).isEqualTo(expected);
    }



}
