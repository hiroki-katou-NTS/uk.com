package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice;


import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameUsageSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Optional;

@RunWith(JMockit.class)
public class ChangeChildTaskLinkedDomainServiceTest {
    @Injectable
    ChangeChildTaskLinkedDomainService.Require require;

    /**
     * case: 作業枠利用設定が設定されてない: Msg_1959
     */
    @Test
    public void testCaseWorkFrameUsageSettingsAreNotSet() {

        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.getWorkFrameUsageSetting("cid");
                result = null;

            }
        };
        NtsAssert.businessException("Msg_1959",
                () -> ChangeChildTaskLinkedDomainService.change(require, HelperChangeChild.taskFrameNo
                        , HelperChangeChild.code, HelperChangeChild.childWorkList));

    }

    /**
     * Case : 子作業を紐付けることができません: Msg_2066
     */
    @Test
    public void testUnableToAssociateChildWork() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.getWorkFrameUsageSetting("cid");
                result = new TaskFrameUsageSetting(HelperChangeChild.frameSettingList02());
            }
        };
        NtsAssert.businessException("Msg_2066",
                () -> ChangeChildTaskLinkedDomainService.change(require, new TaskFrameNo(5)
                        , HelperChangeChild.code, HelperChangeChild.childWorkList));
    }

    /**
     * Case: 下位枠が利用しないになっている: Msg_1957
     */
    @Test
    public void testTheLowerFrameIsNotUsed() {

        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.getWorkFrameUsageSetting("cid");
                result = new TaskFrameUsageSetting(HelperChangeChild.frameSettingList());
            }
        };
        NtsAssert.businessException("Msg_1957",
                () -> ChangeChildTaskLinkedDomainService.change(require, new TaskFrameNo(4)
                        , HelperChangeChild.code, HelperChangeChild.childWorkList));

    }

    /**
     * Case: 作業マスタがない: Msg_2065
     */
    @Test
    public void testThereIsNoWorkMaster() {

        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.getWorkFrameUsageSetting("cid");
                result = new TaskFrameUsageSetting(HelperChangeChild.frameSettingList02());

                require.getOptionalWork("cid", HelperChangeChild.taskFrameNo, HelperChangeChild.code);
                result = Optional.empty();

            }
        };
        NtsAssert.businessException("Msg_2065",
                () -> ChangeChildTaskLinkedDomainService.change(require, HelperChangeChild.taskFrameNo
                        , HelperChangeChild.code, HelperChangeChild.childWorkList));

    }

    /**
     * Case: require.作業を取得する(親作業枠NO,親作業コード):  作業枠NO == 5
     * Throw BusinessException :  Msg_2066;
     */
    @Test
    public void testThrowBusinessExceptionWhenChangeWork_TaskFrameNo_is05() {
        val workOptional = HelperChangeChild.createDomain();
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.getWorkFrameUsageSetting("cid");
                result = new TaskFrameUsageSetting(HelperChangeChild.frameSettingList02());

                require.getOptionalWork("cid", new TaskFrameNo(4), HelperChangeChild.code);
                result = Optional.of(workOptional);
            }
        };
        NtsAssert.businessException("Msg_2066",
                () -> ChangeChildTaskLinkedDomainService.change(require, new TaskFrameNo(4)
                        , HelperChangeChild.code, HelperChangeChild.childWorkList));

    }

    /**
     * Case: Success
     */
    @Test
    public void testNoThrowBusinessException() {
        val workOptional = HelperChangeChild.createDomain();
        new Expectations(AppContexts.class, Task.class) {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.getWorkFrameUsageSetting("cid");
                result = new TaskFrameUsageSetting(HelperChangeChild.frameSettingList02());

                require.getOptionalWork("cid", HelperChangeChild.taskFrameNo, HelperChangeChild.code);
                result = Optional.of(workOptional);

                workOptional.changeChildTaskList(require, (List<TaskCode>) any);

            }
        };
        AtomTask atomTask = ChangeChildTaskLinkedDomainService.change(require, HelperChangeChild.taskFrameNo
                , HelperChangeChild.code, HelperChangeChild.childWorkList);
        NtsAssert.atomTask(
                () -> atomTask,
                anySupplier -> require.update(anySupplier.get()));
    }
}
