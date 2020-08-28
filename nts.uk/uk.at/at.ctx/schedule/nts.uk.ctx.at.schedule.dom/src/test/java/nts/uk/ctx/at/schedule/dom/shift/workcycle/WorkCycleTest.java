package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.error.BusinessException;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.testing.exception.BusinessExceptionAssert;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.ErrorStatusWorkInfo;
import nts.uk.ctx.at.shared.dom.worktype.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.integration.junit4.JMockit;
import org.junit.runner.RunWith;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class WorkCycleTest {

    @Injectable
    private WorkInformation.Require require;

    @Test
    public void testEmptyCycleInfoList() {
        List<WorkCycleInfo> infos = new ArrayList<>();
        NtsAssert.businessException("Msg_1688", ()->{
            WorkCycle item = WorkCycle.WorkCycle("cid","code","name", infos);
        });
    }

    @Test
    public void testGetWorkInfo_01() {
        WorkCycle item = WorkCycle.WorkCycle("CID001", "COD001", "Name001", Arrays.asList(
                WorkCycleInfo.WorkCycleInfo(2, new WorkInformation("WTime001", "WType001")),
                WorkCycleInfo.WorkCycleInfo(3, new WorkInformation("WTime002", "WType002")),
                WorkCycleInfo.WorkCycleInfo(4, new WorkInformation("WTime003", "WType003"))
        ));
        assertThat(item.getWorkInfo(5, 0)).isEqualToComparingFieldByField(item.getInfos().get(1));
    }

    @Test
    public void testGetWorkInfo_02() {
        WorkCycle item = WorkCycle.WorkCycle("CID001", "COD001", "Name001", Arrays.asList(
                WorkCycleInfo.WorkCycleInfo(2, new WorkInformation("WTime001", "WType001")),
                WorkCycleInfo.WorkCycleInfo(3, new WorkInformation("WTime002", "WType002")),
                WorkCycleInfo.WorkCycleInfo(4, new WorkInformation("WTime003", "WType003"))
        ));
        assertThat(item.getWorkInfo(12, 1)).isEqualToComparingFieldByField(item.getInfos().get(0));
    }

    @Test
    public void testGetWorkInfo_03() {
        WorkCycle item = WorkCycle.WorkCycle("CID001", "COD001", "Name001", Arrays.asList(
                WorkCycleInfo.WorkCycleInfo(2, new WorkInformation("WTime001", "WType001")),
                WorkCycleInfo.WorkCycleInfo(3, new WorkInformation("WTime002", "WType003")),
                WorkCycleInfo.WorkCycleInfo(4, new WorkInformation("WTime003", "WType003"))
        ));
        assertThat(item.getWorkInfo(3, 5)).isEqualToComparingFieldByField(item.getInfos().get(2));
    }

    @Test
    public void testGetter(){
        WorkCycle item = WorkCycle.WorkCycle("CID001", "COD001", "Name001", Arrays.asList(
                WorkCycleInfo.WorkCycleInfo(2, new WorkInformation("WTime001", "WType001")),
                WorkCycleInfo.WorkCycleInfo(3, new WorkInformation("WTime002", "WType002")),
                WorkCycleInfo.WorkCycleInfo(4, new WorkInformation("WTime003", "WType003"))
        ));
        NtsAssert.invokeGetters(item);
    }

    @Test
    public void testGetErrorList() {
        WorkCycle item = WorkCycle.WorkCycle("CID001", "COD001", "Name001", Arrays.asList(
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
                require.findByPK(item.getInfos().get(0).getWorkInformation().getWorkTypeCode().v());
                result = Optional.empty();

                require.findByPK(item.getInfos().get(1).getWorkInformation().getWorkTypeCode().v());
                result = Optional.of(workTypeDeprecated);

                require.findByPK(item.getInfos().get(2).getWorkInformation().getWorkTypeCode().v());
                result = Optional.of(workType);

                require.checkNeededOfWorkTimeSetting(item.getInfos().get(2).getWorkInformation().getWorkTypeCode().v());
                result = SetupType.REQUIRED;

                require.findByPK(item.getInfos().get(3).getWorkInformation().getWorkTypeCode().v());
                result = Optional.of(workType);

                require.checkNeededOfWorkTimeSetting(item.getInfos().get(3).getWorkInformation().getWorkTypeCode().v());
                result = SetupType.OPTIONAL;

                require.findByPK(item.getInfos().get(4).getWorkInformation().getWorkTypeCode().v());
                result = Optional.of(workType);

                require.checkNeededOfWorkTimeSetting(item.getInfos().get(4).getWorkInformation().getWorkTypeCode().v());
                result = SetupType.NOT_REQUIRED;

                require.findByPK(item.getInfos().get(5).getWorkInformation().getWorkTypeCode().v());
                result = Optional.of(workType);

                require.checkNeededOfWorkTimeSetting(item.getInfos().get(5).getWorkInformation().getWorkTypeCode().v());
                result = SetupType.NOT_REQUIRED;
            }
        };
        List<ErrorStatusWorkInfo> errorStatusWorkInfos = item.checkError(require);
        assertThat(ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE).isEqualByComparingTo(errorStatusWorkInfos.get(0));
        assertThat(ErrorStatusWorkInfo.WORKTYPE_WAS_ABOLISHED).isEqualByComparingTo(errorStatusWorkInfos.get(1));
        assertThat(ErrorStatusWorkInfo.WORKTIME_ARE_REQUIRE_NOT_SET).isEqualByComparingTo(errorStatusWorkInfos.get(2));
        assertThat(ErrorStatusWorkInfo.NORMAL).isEqualByComparingTo(errorStatusWorkInfos.get(3));
        assertThat(ErrorStatusWorkInfo.NORMAL).isEqualByComparingTo(errorStatusWorkInfos.get(4));
        assertThat(ErrorStatusWorkInfo.WORKTIME_ARE_SET_WHEN_UNNECESSARY).isEqualByComparingTo(errorStatusWorkInfos.get(5));
    }



}
