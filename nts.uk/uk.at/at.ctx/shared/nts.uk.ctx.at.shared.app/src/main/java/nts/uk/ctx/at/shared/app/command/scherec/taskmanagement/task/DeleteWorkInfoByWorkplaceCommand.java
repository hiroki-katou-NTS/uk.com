package nts.uk.ctx.at.shared.app.command.scherec.taskmanagement.task;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteWorkInfoByWorkplaceCommand {
    // 会社ID
    private String companyId;
    // 職場ID:
    private String workplaceId;
}
