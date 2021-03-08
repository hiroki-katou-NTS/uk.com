package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice;


import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameName;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.*;
import nts.uk.shr.com.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(JMockit.class)
public class CheckExistenceMasterDomainServiceTest {

    @Injectable
    CheckExistenceMasterDomainService.Require require;

    /**
     * case 作業は削除されている: Msg_2065
     */
    @Test
    public void testWorkHasBeenDeletedIsTrue() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.getTask("cid", Helper.taskFrameNo);
                result = Helper.createDomain(Helper.childWorkList);


            }
        };
        NtsAssert.businessException("Msg_2065",
                () -> CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, Helper.taskFrameNo,
                        Helper.codeList));
    }

    /**
     * case: No exit
     */

    @Test
    public void testWorkHasBeenDeletedIsFalse() {
        new Expectations(AppContexts.class) {
            {
                AppContexts.user().companyId();
                result = "cid";

                require.getTask("cid", Helper.taskFrameNo);
                result = Helper.createDomain(Helper.codeList);


            }
        };

        CheckExistenceMasterDomainService.checkExistenceTaskMaster(require, Helper.taskFrameNo,
                Helper.codeList);
    }

    public static class Helper

    {
        private static final String DATE_FORMAT = "yyyy/MM/dd";
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
                new TaskCode("CODE003"),
                new TaskCode("CODE004"),
                new TaskCode("CODE005")
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

        static List<Task> createDomain(List<TaskCode> codes) {
            return codes.stream().map(e -> new Task(e, new TaskFrameNo(5),
                    cooperationInfo, childWorkList, expirationDate, displayInfo)).collect(Collectors.toList());

        }
    }
}
