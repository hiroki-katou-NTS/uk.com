package nts.uk.ctx.at.shared.dom.workmanagement.domainservice;


import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.*;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workframe.WorkFrameName;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workframe.WorkFrameSetting;
import nts.uk.ctx.at.shared.dom.workmanagement.aggregateroot.workmaster.Work;
import nts.uk.shr.com.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(JMockit.class)
public class CopyChildWorkSettingDomainServiceTest {

    @Injectable
    CopyChildWorkSettingDomainService.Require require;

    /**
     * case 複写先がない: Msg_365
     */
    @Test
    public void testThereIsNoCopyDestination() {
        NtsAssert.businessException("Msg_365",
                () -> CopyChildWorkSettingDomainService
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

                require.getOptionalWork("cid", Helper.taskFrameNo, Helper.code);
                result = Optional.empty();
            }
        };

        NtsAssert.businessException("Msg_1185", () -> {
            CopyChildWorkSettingDomainService.doCopy(require, Helper.taskFrameNo, Helper.code, Helper.codeList);
        });
    }

    /**
     * case Success
     */
    @Test
    public void testCopySuccess() {
        val listRs = Helper.createDomains(Helper.codeList);
        val copySourceSetting = Helper.createDomain();
        new Expectations(AppContexts.class, Work.class) {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.getOptionalWork("cid", Helper.taskFrameNo, Helper.code);
                result = Optional.of(copySourceSetting);

                require.getListWork("cid", Helper.taskFrameNo, Helper.childWorkList);
                result = listRs;

                listRs.get(0).changeChildWorkList(require, Helper.childWorkList);

            }
        };


            NtsAssert.atomTask(
                    () -> CopyChildWorkSettingDomainService.doCopy(require, Helper.taskFrameNo
                            , Helper.code, Helper.childWorkList),
                    any -> require.update(any.get()));





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
                new TaskCode("CODE001")

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

        static List<WorkFrameSetting> frameSettingList() {
            return Arrays.asList(
                    new WorkFrameSetting(
                            new TaskFrameNo(1),
                            new WorkFrameName("Name01"),
                            UseAtr.USE
                    ),
                    new WorkFrameSetting(
                            new TaskFrameNo(5),
                            new WorkFrameName("Name05"),
                            UseAtr.NOTUSE

                    ), new WorkFrameSetting(
                            new TaskFrameNo(3),
                            new WorkFrameName("Name03"),
                            UseAtr.USE

                    ), new WorkFrameSetting(
                            new TaskFrameNo(4),
                            new WorkFrameName("Name04"),
                            UseAtr.USE

                    ), new WorkFrameSetting(
                            new TaskFrameNo(2),
                            new WorkFrameName("Name02"),
                            UseAtr.USE

                    )
            );
        }

        static List<WorkFrameSetting> frameSettingList02() {
            return Arrays.asList(
                    new WorkFrameSetting(
                            new TaskFrameNo(1),
                            new WorkFrameName("Name01"),
                            UseAtr.USE
                    ),
                    new WorkFrameSetting(
                            new TaskFrameNo(5),
                            new WorkFrameName("Name05"),
                            UseAtr.USE

                    ), new WorkFrameSetting(
                            new TaskFrameNo(3),
                            new WorkFrameName("Name03"),
                            UseAtr.USE

                    ), new WorkFrameSetting(
                            new TaskFrameNo(4),
                            new WorkFrameName("Name04"),
                            UseAtr.USE

                    ), new WorkFrameSetting(
                            new TaskFrameNo(2),
                            new WorkFrameName("Name02"),
                            UseAtr.USE

                    )
            );
        }

        static List<Work> createDomains(List<TaskCode> codes) {
            return codes.stream().map(e -> new Work(e, new TaskFrameNo(3),
                    cooperationInfo, childWorkList, expirationDate, displayInfo)).collect(Collectors.toList());

        }

        static Work createDomain() {

            return new Work(code, new TaskFrameNo(3), cooperationInfo, childWorkList, expirationDate, displayInfo);
        }
    }
}
