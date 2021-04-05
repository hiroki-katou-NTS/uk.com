package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.domainservice;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.*;
import nts.uk.shr.com.color.ColorCode;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class HelperCheckExist {
    public static final String DATE_FORMAT = "yyyy/MM/dd";
    public static final TaskFrameNo taskFrameNo = new TaskFrameNo(3);
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

    public static final List<TaskCode> codeList = Arrays.asList(
            new TaskCode("CODE001"),
            new TaskCode("CODE002"),
            new TaskCode("CODE003"),
            new TaskCode("CODE004"),
            new TaskCode("CODE005")
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

    static List<Task> createDomain(List<TaskCode> codes) {
        return codes.stream().map(e -> new Task(e, new TaskFrameNo(5),
                cooperationInfo, childWorkList, expirationDate, displayInfo)).collect(Collectors.toList());

    }
}

