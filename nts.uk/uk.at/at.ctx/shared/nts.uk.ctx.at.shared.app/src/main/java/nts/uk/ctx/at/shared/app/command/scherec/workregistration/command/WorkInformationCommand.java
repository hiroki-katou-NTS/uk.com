package nts.uk.ctx.at.shared.app.command.scherec.workregistration.command;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

import java.util.List;

@Data
@NoArgsConstructor
public class WorkInformationCommand {

    private Integer taskFrameNo;

    private String code;

    private GeneralDate startDate;

    private GeneralDate endDate;

    private ExternalCooperationInfoCommand cooperationInfo;

    private TaskDisplayInfoCommand displayInfo;

    private List<String> childTaskList;

}
