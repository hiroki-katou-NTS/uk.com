package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameName;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameSetting;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.*;
import nts.uk.shr.com.color.ColorCode;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
public class HelperChangeChild
{
    public static final String DATE_FORMAT = "yyyy/MM/dd";
    public static final TaskCode code = new TaskCode("CODE");
    public static final TaskFrameNo taskFrameNo = new TaskFrameNo(3);
    public static final TaskFrameNo taskFrameNo_is05 = new TaskFrameNo(5);
    public static final ExternalCooperationInfo cooperationInfo = new ExternalCooperationInfo(
            Optional.of(new TaskExternalCode("externalCode1")),
            Optional.of(new TaskExternalCode("externalCode2")),
            Optional.of(new TaskExternalCode("externalCode3")),
            Optional.of(new TaskExternalCode("externalCode4")),
            Optional.of(new TaskExternalCode("externalCode5"))
    );

    public static final List<TaskCode> childWorkList = Arrays.asList(
            new TaskCode("CODE01"),
            new TaskCode("CODE02"),
            new TaskCode("CODE03"),
            new TaskCode("CODE04"),
            new TaskCode("CODE05")
    );
    public static final GeneralDate startDate = GeneralDate.fromString("2021/01/01", DATE_FORMAT);
    public static final GeneralDate endDate = GeneralDate.fromString("2021/05/15", DATE_FORMAT);
    public static final DatePeriod expirationDate = new DatePeriod(startDate, endDate);
    public static final TaskDisplayInfo displayInfo = new TaskDisplayInfo(
            new TaskName("TaskName"),
            new TaskAbName("TaskAbName"),
            Optional.of(new ColorCode("ColorCode")),
            Optional.of(new TaskNote("TaskNote"))

    );

    public static List<TaskFrameSetting> frameSettingList() {
        return Arrays.asList(
                new TaskFrameSetting(
                        new TaskFrameNo(1),
                        new TaskFrameName("Name01"),
                        UseAtr.USE
                ),
                new TaskFrameSetting(
                        new TaskFrameNo(5),
                        new TaskFrameName("Name05"),
                        UseAtr.NOTUSE

                ), new TaskFrameSetting(
                        new TaskFrameNo(3),
                        new TaskFrameName("Name03"),
                        UseAtr.USE

                ), new TaskFrameSetting(
                        new TaskFrameNo(4),
                        new TaskFrameName("Name04"),
                        UseAtr.USE

                ), new TaskFrameSetting(
                        new TaskFrameNo(2),
                        new TaskFrameName("Name02"),
                        UseAtr.USE

                )
        );
    }

    public static List<TaskFrameSetting> frameSettingList02() {
        return Arrays.asList(
                new TaskFrameSetting(
                        new TaskFrameNo(1),
                        new TaskFrameName("Name01"),
                        UseAtr.USE
                ),
                new TaskFrameSetting(
                        new TaskFrameNo(5),
                        new TaskFrameName("Name05"),
                        UseAtr.USE

                ), new TaskFrameSetting(
                        new TaskFrameNo(3),
                        new TaskFrameName("Name03"),
                        UseAtr.USE

                ), new TaskFrameSetting(
                        new TaskFrameNo(4),
                        new TaskFrameName("Name04"),
                        UseAtr.USE

                ), new TaskFrameSetting(
                        new TaskFrameNo(2),
                        new TaskFrameName("Name02"),
                        UseAtr.USE

                )
        );
    }

    public static Task createDomain() {

        return new Task(code, new TaskFrameNo(5), cooperationInfo, childWorkList, expirationDate, displayInfo);
    }
}
