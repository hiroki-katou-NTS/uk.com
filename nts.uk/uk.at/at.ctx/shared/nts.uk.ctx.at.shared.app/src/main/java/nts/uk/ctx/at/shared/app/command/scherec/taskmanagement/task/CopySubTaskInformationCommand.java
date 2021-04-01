package nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CopySubTaskInformationCommand {
    private Integer taskFrameNo;
    private String copySource;
    private List<String> copyDestinationList;
}
