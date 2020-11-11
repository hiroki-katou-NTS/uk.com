package nts.uk.ctx.workflow.app.command.agent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SendEmailCommand {
    private String approverId;
    private String emailContent;
}
