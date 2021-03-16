package nts.uk.ctx.at.shared.app.command.scherec.workregistration.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;

import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class RegisterWorkInformationCommand {
    private Integer taskFrameNo;
    private String taskCode;
}
