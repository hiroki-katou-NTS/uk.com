package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.testing.exception.BusinessExceptionAssert;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.RegisterWorkCycleService.Require;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class RegisterWorkCycleServiceTest {

    @Injectable
    Require require;

    @Injectable
    WorkInformation.Require workRequire;

    @Test
    public void test_create_1() {
        String cid = "DUMMY";
        String code = "DUMMY";
        WorkCycleInfo info = new WorkCycleInfo(1, "timecd", "typecd", 2);
        List<WorkCycleInfo> infos = new ArrayList<>();
        infos.add(info);
        WorkCycle workCycle = new WorkCycle(cid, code, "name", infos);

        new Expectations() {
            {
                require.exists(cid, code);
                result = true;
            }
        };

        NtsAssert.businessException("Msg_3", () -> {
            RegisterWorkCycleService.register(
                    workRequire,
                    require,
                    workCycle, //DUMMY
                    true  //DUMMY
            );
        });
    }

    @Test
    public void testWhenHasErrorCode() {
        String cid = "DUMMY";
        String code = "DUMMY";
        WorkCycleInfo info = new WorkCycleInfo(1, "DUMMY", "DUMMY", 2);
        List<WorkCycleInfo> infos = new ArrayList<>();
        infos.add(info);
        WorkCycle workCycle = new WorkCycle(cid, code, "DUMMY", infos);
        new Expectations() {
            {
                require.exists(cid, code);
                result = false;
            }
        };
        WorkCycleCreateResult result = RegisterWorkCycleService.register(workRequire, require, workCycle, true);
        List<ErrorStatusWorkInfo> expectedError = new ArrayList<>();
        expectedError.add(ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE);
        Assert.assertEquals(Optional.empty(), result.getAtomTask());
        Assert.assertEquals(expectedError, result.getErrorStatusList());
        assertThat(result.isHasError()).isTrue();
    }

    @Test
    public void testInsert() {
        String cid = "DUMMY";
        String code = "DUMMY";
        WorkCycleInfo info = new WorkCycleInfo(1, "DUMMY", null, 2);
        List<WorkCycleInfo> infos = new ArrayList<>();
        infos.add(info);
        WorkCycle workCycle = new WorkCycle(cid, code, "DUMMY", infos);
        List<ErrorStatusWorkInfo> errors = new ArrayList<>();
        errors.add(ErrorStatusWorkInfo.NORMAL);
        new Expectations() {
            {
                require.exists(cid, code);
                result = false;

                workRequire.findByPK(anyString);
                result = Optional.of(new WorkType());

                workRequire.checkNeededOfWorkTimeSetting(anyString);
                result = SetupType.OPTIONAL;
            }
        };
        WorkCycleCreateResult result = RegisterWorkCycleService.register(workRequire, require, workCycle, true);
        assertThat(result.getAtomTask().isPresent()).isTrue();
        NtsAssert.atomTask(
                () -> result.getAtomTask().get(),
                any -> require.insert((WorkCycle) any.get())
        );
    }

    @Test
    public void testUpdate() {
        String cid = "DUMMY";
        String code = "DUMMY";
        WorkCycleInfo info = new WorkCycleInfo(1, "DUMMY", null, 2);
        List<WorkCycleInfo> infos = new ArrayList<>();
        infos.add(info);
        WorkCycle workCycle = new WorkCycle(cid, code, "DUMMY", infos);
        List<ErrorStatusWorkInfo> errors = new ArrayList<>();
        errors.add(ErrorStatusWorkInfo.NORMAL);
        new Expectations() {
            {
                workRequire.findByPK(anyString);
                result = Optional.of(new WorkType());

                workRequire.checkNeededOfWorkTimeSetting(anyString);
                result = SetupType.OPTIONAL;
            }
        };
        WorkCycleCreateResult result = RegisterWorkCycleService.register(workRequire, require, workCycle, false);
        assertThat(result.getAtomTask().isPresent()).isTrue();
        NtsAssert.atomTask(
                () -> result.getAtomTask().get(),
                any -> require.update((WorkCycle) any.get())
        );
    }

}
