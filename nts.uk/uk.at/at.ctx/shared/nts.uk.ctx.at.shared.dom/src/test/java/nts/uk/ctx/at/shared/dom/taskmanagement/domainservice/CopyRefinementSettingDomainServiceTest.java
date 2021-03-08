package nts.uk.ctx.at.shared.dom.taskmanagement.domainservice;


import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.*;
import nts.uk.ctx.at.shared.dom.taskmanagement.aggregateroot.tasknarrowingdown.NarrowingDownTaskByWorkplace;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(JMockit.class)
public class CopyRefinementSettingDomainServiceTest {
    @Injectable
    CopyRefinementSettingDomainService.Require require;

    /**
     * case 複写元のデータが存在しません: Msg_1185
     */
    @Test
    public void testSourceDataDoesNotExist() {

        new Expectations() {{
            require.getListWorkByWpl("copySourceWplId");
            result = Collections.emptyList();
        }};

        NtsAssert.businessException("Msg_1185", () -> CopyRefinementSettingDomainService.doCopy(require,
                "copySourceWplId", "copyDestinationWplId"));
    }

    /**
     * 説明:職場別作業の絞込を別の職場に複写する
     */
    @Test
    public void testCaseSuccess() {
//        new Expectations() {{
//            require.getListWorkByWpl("copySourceWplId");
//            result = Helper.createDomainNarrowing(Helper.codeList);
//
//            require.getListWorkByWpl("copyDestinationWplId");
//            result = Helper.createDomainNarrowing(Helper.codeList);
//        }};
//
//        AtomTask atomTask = CopyRefinementSettingDomainService.doCopy(require,
//                "copySourceWplId", "copyDestinationWplId");
//        atomTask.run();
//        new Verifications() {
//            {
//                require.delete((String) any, (TaskFrameNo) any);
//                times = 3;
//
//                require.insert((NarrowingDownTaskByWorkplace) any);
//                times = 3;
//            }
//        };
    }

    public static class Helper

    {
        private static final List<TaskCode> childWorkList = Arrays.asList(
                new TaskCode("CODE01"),
                new TaskCode("CODE02"),
                new TaskCode("CODE03"),
                new TaskCode("CODE04"),
                new TaskCode("CODE05")
        );
        private static final List<TaskFrameNo> codeList = Arrays.asList(
                new TaskFrameNo(1),
                new TaskFrameNo(2),
                new TaskFrameNo(3)
        );
        static List<NarrowingDownTaskByWorkplace> createDomainNarrowing(List<TaskFrameNo> taskFrameNoList) {
            return taskFrameNoList.stream().map(e -> new NarrowingDownTaskByWorkplace("copySourceWplId",
                    e, childWorkList)).collect(Collectors.toList());
        }
    }
}
