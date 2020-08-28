package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycleInfo;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice.RegisterWorkCycleService.Require;

import java.util.ArrayList;
import java.util.Arrays;
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
        WorkCycleInfo info = WorkCycleInfo.WorkCycleInfo(1, new WorkInformation("timecd", "typecd"));
        List<WorkCycleInfo> infos = new ArrayList<>();
        infos.add(info);
        WorkCycle workCycle = WorkCycle.WorkCycle(cid, code, "name", infos);

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
        WorkCycle workCycle = WorkCycle.WorkCycle("CID001", "COD001", "Name001", Arrays.asList(
                WorkCycleInfo.WorkCycleInfo(2, new WorkInformation("WTime001", "WType001")),
                WorkCycleInfo.WorkCycleInfo(3, new WorkInformation("WTime002", "WType002")),
                WorkCycleInfo.WorkCycleInfo(4, new WorkInformation(null, "WType004")),
                WorkCycleInfo.WorkCycleInfo(4, new WorkInformation(null, "WType005")),
                WorkCycleInfo.WorkCycleInfo(4, new WorkInformation(null, "WType006")),
                WorkCycleInfo.WorkCycleInfo(4, new WorkInformation("WTime003", "WType003"))
                )
        );
        WorkType workTypeDeprecated = new WorkType("001", new WorkTypeCode("WTime001"),new WorkTypeSymbolicName(""), new WorkTypeName(""),
                new WorkTypeAbbreviationName(""), new WorkTypeMemo(""), new DailyWork(), DeprecateClassification.Deprecated, CalculateMethod.DO_NOT_GO_TO_WORK
        );
        WorkType workType = new WorkType("001", new WorkTypeCode("WTime001"),new WorkTypeSymbolicName(""), new WorkTypeName(""),
                new WorkTypeAbbreviationName(""), new WorkTypeMemo(""), new DailyWork(), DeprecateClassification.NotDeprecated, CalculateMethod.DO_NOT_GO_TO_WORK
        );
        new Expectations() {
            {
                require.findByPK(workCycle.getInfos().get(0).getWorkInformation().getWorkTypeCode().v());
                result = Optional.empty();

                require.findByPK(workCycle.getInfos().get(1).getWorkInformation().getWorkTypeCode().v());
                result = Optional.of(workTypeDeprecated);

                require.findByPK(workCycle.getInfos().get(2).getWorkInformation().getWorkTypeCode().v());
                result = Optional.of(workType);

                require.checkNeededOfWorkTimeSetting(workCycle.getInfos().get(2).getWorkInformation().getWorkTypeCode().v());
                result = SetupType.REQUIRED;

                require.findByPK(workCycle.getInfos().get(3).getWorkInformation().getWorkTypeCode().v());
                result = Optional.of(workType);

                require.checkNeededOfWorkTimeSetting(workCycle.getInfos().get(3).getWorkInformation().getWorkTypeCode().v());
                result = SetupType.OPTIONAL;

                require.findByPK(workCycle.getInfos().get(4).getWorkInformation().getWorkTypeCode().v());
                result = Optional.of(workType);

                require.checkNeededOfWorkTimeSetting(workCycle.getInfos().get(4).getWorkInformation().getWorkTypeCode().v());
                result = SetupType.NOT_REQUIRED;

                require.findByPK(workCycle.getInfos().get(5).getWorkInformation().getWorkTypeCode().v());
                result = Optional.of(workType);

                require.checkNeededOfWorkTimeSetting(workCycle.getInfos().get(5).getWorkInformation().getWorkTypeCode().v());
                result = SetupType.NOT_REQUIRED;
            }
        };
        WorkCycleCreateResult result = RegisterWorkCycleService.register( require, workCycle, true);
        assertThat(result.isHasError()).isTrue();
        assertThat(ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE).isEqualByComparingTo(result.getErrorStatusList().get(0));
        assertThat(ErrorStatusWorkInfo.WORKTYPE_WAS_ABOLISHED).isEqualByComparingTo(result.getErrorStatusList().get(1));
        assertThat(ErrorStatusWorkInfo.WORKTIME_ARE_REQUIRE_NOT_SET).isEqualByComparingTo(result.getErrorStatusList().get(2));
        assertThat(ErrorStatusWorkInfo.NORMAL).isEqualByComparingTo(result.getErrorStatusList().get(3));
        assertThat(ErrorStatusWorkInfo.NORMAL).isEqualByComparingTo(result.getErrorStatusList().get(4));
        assertThat(ErrorStatusWorkInfo.WORKTIME_ARE_SET_WHEN_UNNECESSARY).isEqualByComparingTo(result.getErrorStatusList().get(5));

    }

    @Test
    public void testInsert() {
        WorkCycle workCycle = WorkCycle.WorkCycle("CID001", "COD001", "Name001", Arrays.asList(
                WorkCycleInfo.WorkCycleInfo(2, new WorkInformation(null, "WType001")),
                WorkCycleInfo.WorkCycleInfo(3, new WorkInformation(null, "WType002"))
                )
        );

        WorkType workType = new WorkType("001", new WorkTypeCode("WTime001"),new WorkTypeSymbolicName(""), new WorkTypeName(""),
                new WorkTypeAbbreviationName(""), new WorkTypeMemo(""), new DailyWork(), DeprecateClassification.NotDeprecated, CalculateMethod.DO_NOT_GO_TO_WORK
        );
        new Expectations() {
            {
                require.exists(workCycle.getCid(), workCycle.getCode().v());
                result = false;

                require.findByPK(workCycle.getInfos().get(0).getWorkInformation().getWorkTypeCode().v());
                result = Optional.of(workType);

                require.checkNeededOfWorkTimeSetting(workCycle.getInfos().get(0).getWorkInformation().getWorkTypeCode().v());
                result = SetupType.OPTIONAL;

                require.findByPK(workCycle.getInfos().get(1).getWorkInformation().getWorkTypeCode().v());
                result = Optional.of(workType);

                require.checkNeededOfWorkTimeSetting(workCycle.getInfos().get(1).getWorkInformation().getWorkTypeCode().v());
                result = SetupType.NOT_REQUIRED;
            }
        };
        WorkCycleCreateResult result = RegisterWorkCycleService.register(require, workCycle, true);
        assertThat(result.getAtomTask().isPresent()).isTrue();
        assertThat(result.getErrorStatusList().size()).isEqualTo(0);
        assertThat(result.isHasError()).isFalse();
    }

    @Test
    public void testUpdate() {
        WorkCycle workCycle = WorkCycle.WorkCycle("CID001", "COD001", "Name001", Arrays.asList(
                WorkCycleInfo.WorkCycleInfo(2, new WorkInformation("WTime001", "WType001")),
                WorkCycleInfo.WorkCycleInfo(3, new WorkInformation(null, "WType002"))
                )
        );

        WorkType workType = new WorkType("001", new WorkTypeCode("WTime001"),new WorkTypeSymbolicName(""), new WorkTypeName(""),
                new WorkTypeAbbreviationName(""), new WorkTypeMemo(""), new DailyWork(), DeprecateClassification.NotDeprecated, CalculateMethod.DO_NOT_GO_TO_WORK
        );
        new Expectations() {
            {
                require.findByPK(workCycle.getInfos().get(0).getWorkInformation().getWorkTypeCode().v());
                result = Optional.of(workType);

                require.checkNeededOfWorkTimeSetting(workCycle.getInfos().get(0).getWorkInformation().getWorkTypeCode().v());
                result = SetupType.OPTIONAL;

                require.findByCode(workCycle.getInfos().get(0).getWorkInformation().getWorkTimeCode().v());
                result = Optional.of(new WorkTimeSetting());

                require.findByPK(workCycle.getInfos().get(1).getWorkInformation().getWorkTypeCode().v());
                result = Optional.of(workType);

                require.checkNeededOfWorkTimeSetting(workCycle.getInfos().get(1).getWorkInformation().getWorkTypeCode().v());
                result = SetupType.NOT_REQUIRED;
            }
        };
        WorkCycleCreateResult result = RegisterWorkCycleService.register(require, workCycle, false);
        assertThat(result.getAtomTask().isPresent()).isTrue();
        assertThat(result.getErrorStatusList().size()).isEqualTo(0);
        assertThat(result.isHasError()).isFalse();
    }

}
