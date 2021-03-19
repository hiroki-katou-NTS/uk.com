package nts.uk.ctx.at.shared.app.command.scherec.workregistration.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.ExternalCooperationInfo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskDisplayInfo;

import java.util.List;

@Data
@NoArgsConstructor
public class WorkInformationCommand {

    private Integer taskFrameNo;

    private String code;

    private GeneralDate startDate;

    private GeneralDate endDate;

    private ExternalCooperationInfo cooperationInfo;

    private TaskDisplayInfo displayInfo;

    private List<String> childTaskList;

}
