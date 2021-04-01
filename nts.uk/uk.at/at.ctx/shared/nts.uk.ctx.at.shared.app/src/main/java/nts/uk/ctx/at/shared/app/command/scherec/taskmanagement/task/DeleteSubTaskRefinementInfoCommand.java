package nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteSubTaskRefinementInfoCommand {
    private Integer taskFrameNo;
    private String parentWorkCode;

}
