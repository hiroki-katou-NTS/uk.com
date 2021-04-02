package nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.task;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteWorkInfoByWorkplaceCommand {
    // 職場ID:
    private String workPlaceId;
}
