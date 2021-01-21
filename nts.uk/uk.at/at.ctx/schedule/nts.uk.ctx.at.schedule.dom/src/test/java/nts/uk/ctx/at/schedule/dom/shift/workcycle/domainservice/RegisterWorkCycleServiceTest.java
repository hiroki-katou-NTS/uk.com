package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class RegisterWorkCycleServiceTest {

    @Injectable
    RegisterWorkCycleService.Require require;


    @Test
    public void test_create_1() {
        String cid = "DUMMY";
        String code = "DUMMY";
        WorkCycleInfo info = WorkCycleInfo.create(1, new WorkInformation("typecd", "timecd"));
        List<WorkCycleInfo> infos = new ArrayList<>();
        infos.add(info);
        WorkCycle workCycle = WorkCycle.create(cid, code, "name", infos);

        new Expectations() {
            {
                require.exists(cid, code);
                result = true;
            }
        };

        NtsAssert.businessException("Msg_3", () -> {
            RegisterWorkCycleService.register(
                    require,
                    workCycle, //DUMMY
                    true  //DUMMY
            );
        });
    }

    @Test
    public void testWhenHasErrorCode() {

        WorkCycle workCycle = WorkCycle.create("CID001", "COD001", "Name001", Arrays.asList(
                WorkCycleInfo.create(2, new WorkInformation("WType001", "WTime001")),
                WorkCycleInfo.create(3, new WorkInformation("WType002", "WTime002")),
                WorkCycleInfo.create(4, new WorkInformation("WType004", null)),
                WorkCycleInfo.create(4, new WorkInformation("WType005", null)),
                WorkCycleInfo.create(4, new WorkInformation("WType006", null)),
                WorkCycleInfo.create(4, new WorkInformation("WType003", "WTime003"))
                )
        );

        new Expectations(WorkInformation.class) {
            {
                workCycle.getInfos().get(0).getWorkInformation().checkErrorCondition(require);
                returns(ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE,ErrorStatusWorkInfo.WORKTYPE_WAS_ABOLISHED,
                        ErrorStatusWorkInfo.WORKTIME_ARE_REQUIRE_NOT_SET,ErrorStatusWorkInfo.NORMAL,
                        ErrorStatusWorkInfo.NORMAL,ErrorStatusWorkInfo.WORKTIME_ARE_SET_WHEN_UNNECESSARY);
            }
        };
        WorkCycleCreateResult result = RegisterWorkCycleService.register( require, workCycle, true);
        assertThat(result.isHasError()).isTrue();
        assertThat(result.getErrorStatusList().get(0)).isEqualByComparingTo(ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE);
        assertThat(result.getErrorStatusList().get(1)).isEqualByComparingTo(ErrorStatusWorkInfo.WORKTYPE_WAS_ABOLISHED);
        assertThat(result.getErrorStatusList().get(2)).isEqualByComparingTo(ErrorStatusWorkInfo.WORKTIME_ARE_REQUIRE_NOT_SET);
        assertThat(result.getErrorStatusList().get(3)).isEqualByComparingTo(ErrorStatusWorkInfo.NORMAL);
        assertThat(result.getErrorStatusList().get(4)).isEqualByComparingTo(ErrorStatusWorkInfo.NORMAL);
        assertThat(result.getErrorStatusList().get(5)).isEqualByComparingTo(ErrorStatusWorkInfo.WORKTIME_ARE_SET_WHEN_UNNECESSARY);

    }

    @Test
    public void testInsert() {
        WorkCycle workCycle = WorkCycle.create("CID001", "COD001", "Name001", Arrays.asList(
                WorkCycleInfo.create(2, new WorkInformation("WType001", null)),
                WorkCycleInfo.create(3, new WorkInformation("WType002", null))
                )
        );
        new Expectations(WorkInformation.class) {
            {
                workCycle.getInfos().get(0).getWorkInformation().checkErrorCondition(require);
                returns(ErrorStatusWorkInfo.NORMAL,ErrorStatusWorkInfo.NORMAL);

            }
        };
        WorkCycleCreateResult result = RegisterWorkCycleService.register(require, workCycle, true);

        assertThat(result.getAtomTask().isPresent()).isTrue();
        assertThat(result.getErrorStatusList().size()).isEqualTo(0);
        assertThat(result.isHasError()).isFalse();
        NtsAssert.atomTask(
                () -> result.getAtomTask().get(),
                any -> require.insert(any.get()));

    }

    @Test
    public void testUpdate() {
        WorkCycle workCycle = WorkCycle.create("CID001", "COD001", "Name001", Arrays.asList(
                WorkCycleInfo.create(2, new WorkInformation("WType001", "WTime001")),
                WorkCycleInfo.create(3, new WorkInformation("WType002", null))
                )
        );

        new Expectations(WorkInformation.class) {
            {
                workCycle.getInfos().get(0).getWorkInformation().checkErrorCondition(require);
                returns(ErrorStatusWorkInfo.NORMAL,ErrorStatusWorkInfo.NORMAL);
            }
        };
        WorkCycleCreateResult result = RegisterWorkCycleService.register(require, workCycle, false);

        assertThat(result.getAtomTask().isPresent()).isTrue();
        assertThat(result.getErrorStatusList().size()).isEqualTo(0);
        assertThat(result.isHasError()).isFalse();
        NtsAssert.atomTask(
                () -> result.getAtomTask().get(),
                any -> require.update(any.get()));
    }

}
