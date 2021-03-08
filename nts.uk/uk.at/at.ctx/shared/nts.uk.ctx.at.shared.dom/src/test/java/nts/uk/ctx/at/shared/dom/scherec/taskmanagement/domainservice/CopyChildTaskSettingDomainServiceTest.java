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
                        .doCopy(require, Helper.taskFrameNo, Helper.code, Collections.emptyList()));

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

                require.getOptionalTask("cid", Helper.taskFrameNo, Helper.code);
                result = Optional.empty();
            }
        };

        NtsAssert.businessException("Msg_1185", () -> {
            CopyChildTaskSettingDomainService.doCopy(require, Helper.taskFrameNo, Helper.code, Helper.codeList);
        });
    }

    /**
     * case Success
     */
    @Test
    public void testCopySuccess() {
        val listRs = Helper.createDomains(Helper.codeList);
        val copySourceSetting = Helper.createDomain();

        new Expectations(AppContexts.class, Task.class) {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.getOptionalTask("cid", Helper.taskFrameNo, Helper.code);
                result = Optional.of(copySourceSetting);

                require.getListTask("cid", Helper.taskFrameNo, Helper.childWorkList);
                result = listRs;

                listRs.get(0).changeChildTaskList(require, Helper.childWorkList);

                listRs.get(1).changeChildTaskList(require, Helper.childWorkList);

                listRs.get(2).changeChildTaskList(require, Helper.childWorkList);

            }
        };
        AtomTask persist = CopyChildTaskSettingDomainService.doCopy(require, Helper.taskFrameNo
                , Helper.code, Helper.childWorkList);
        persist.run();
        new Verifications() {
            {
                require.update((Task) any);
                times = 3;
            }
        };

    }

    public static class Helper

    {
        private static final String DATE_FORMAT = "yyyy/MM/dd";
        private static final TaskCode code = new TaskCode("CODE");
        private static final TaskFrameNo taskFrameNo = new TaskFrameNo(3);
        private static final ExternalCooperationInfo cooperationInfo = new ExternalCooperationInfo(
                Optional.of(new TaskExternalCode("externalCode1")),
                Optional.of(new TaskExternalCode("externalCode2")),
                Optional.of(new TaskExternalCode("externalCode3")),
                Optional.of(new TaskExternalCode("externalCode4")),
                Optional.of(new TaskExternalCode("externalCode5"))
        );

        private static final List<TaskCode> childWorkList = Arrays.asList(
                new TaskCode("CODE01"),
                new TaskCode("CODE02"),
                new TaskCode("CODE03"),
                new TaskCode("CODE04"),
                new TaskCode("CODE05")
        );

        private static final List<TaskCode> codeList = Arrays.asList(
                new TaskCode("CODE001"),
                new TaskCode("CODE002"),
                new TaskCode("CODE003")

        );
        private static final GeneralDate startDate = GeneralDate.fromString("2021/01/01", DATE_FORMAT);
        private static final GeneralDate endDate = GeneralDate.fromString("2021/05/15", DATE_FORMAT);
        private static final DatePeriod expirationDate = new DatePeriod(startDate, endDate);
        private static final TaskDisplayInfo displayInfo = new TaskDisplayInfo(
                new TaskName("TaskName"),
                new TaskAbName("TaskAbName"),
                Optional.of(new ColorCode("ColorCode")),
                Optional.of(new TaskNote("TaskNote"))

        );

        static List<Task> createDomains(List<TaskCode> codes) {
            return codes.stream().map(e -> new Task(e, new TaskFrameNo(3),
                    cooperationInfo, childWorkList, expirationDate, displayInfo)).collect(Collectors.toList());

        }

        static Task createDomain() {

            return new Task(code, new TaskFrameNo(3), cooperationInfo, childWorkList, expirationDate, displayInfo);
        }
    }
}
