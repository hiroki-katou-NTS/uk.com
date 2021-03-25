package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice;


import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.*;
import nts.uk.shr.com.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(JMockit.class)
public class CopyChildTaskSettingDomainServiceTest {

    @Injectable
    CopyChildTaskSettingDomainService.Require require;

    /**
     * case 複写先がない: Msg_365
     */
    @Test
    public void testThereIsNoCopyDestination() {
        NtsAssert.businessException("Msg_365",
                () -> CopyChildTaskSettingDomainService
                        .doCopy(require, HelperCopyChildTask.taskFrameNo, HelperCopyChildTask.code, Collections.emptyList()));

    }

    /**
     * case 複写元のデータが存在しません: Msg_1185
     */
    @Test
    public void testCopySourceDataDoesNotExist() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.getOptionalTask("cid", HelperCopyChildTask.taskFrameNo, HelperCopyChildTask.code);
                result = Optional.empty();
            }
        };

        NtsAssert.businessException("Msg_1185", () -> {
            CopyChildTaskSettingDomainService.doCopy(require, HelperCopyChildTask.taskFrameNo, HelperCopyChildTask.code, HelperCopyChildTask.codeList);
        });
    }

    /**
     * case Success
     */
    @Test
    public void testCopySuccess() {
        val listRs = HelperCopyChildTask.createDomains(HelperCopyChildTask.codeList);
        val copySourceSetting = HelperCopyChildTask.createDomain();

        new Expectations(AppContexts.class, Task.class) {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.getOptionalTask("cid", HelperCopyChildTask.taskFrameNo, HelperCopyChildTask.code);
                result = Optional.of(copySourceSetting);

                require.getListTask("cid", HelperCopyChildTask.taskFrameNo, HelperCopyChildTask.childWorkList);
                result = listRs;

                listRs.get(0).changeChildTaskList(require, HelperCopyChildTask.childWorkList);

                listRs.get(1).changeChildTaskList(require, HelperCopyChildTask.childWorkList);

                listRs.get(2).changeChildTaskList(require, HelperCopyChildTask.childWorkList);

            }
        };
        AtomTask persist = CopyChildTaskSettingDomainService.doCopy(require, HelperCopyChildTask.taskFrameNo
                , HelperCopyChildTask.code, HelperCopyChildTask.childWorkList);
        persist.run();
        new Verifications() {
            {
                require.update((Task) any);
                times = 3;
            }
        };

    }

    /**
     * case Success
     */
    @Test
    public void testCopySuccessWithListOneElement() {
        val listRs = HelperCopyChildTask.createDomains(HelperCopyChildTask.childList);
        val copySourceSetting = HelperCopyChildTask.createDomain();

        new Expectations(AppContexts.class, Task.class) {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.getOptionalTask("cid", HelperCopyChildTask.taskFrameNo, HelperCopyChildTask.code);
                result = Optional.of(copySourceSetting);

                require.getListTask("cid", HelperCopyChildTask.taskFrameNo, HelperCopyChildTask.codeList);
                result = listRs;

                listRs.get(0).changeChildTaskList(require, HelperCopyChildTask.childWorkList);
            }
        };
        AtomTask persist = CopyChildTaskSettingDomainService.doCopy(require, HelperCopyChildTask.taskFrameNo
                , HelperCopyChildTask.code, HelperCopyChildTask.codeList);
        persist.run();
        new Verifications() {
            {
                require.update((Task) any);
                times = 1;
            }
        };

    }

}
