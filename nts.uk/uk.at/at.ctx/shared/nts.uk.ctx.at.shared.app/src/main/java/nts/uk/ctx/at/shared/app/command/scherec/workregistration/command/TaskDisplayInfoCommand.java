package nts.uk.ctx.at.shared.app.command.scherec.workregistration.command;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskDisplayInfoCommand {
    /** 名称　 */
    private String taskName;

    /** 略名　*/
    private String taskAbName;

    /** 作業色　*/
    private String color;

    /** 備考　*/
    private String taskNote;
}
