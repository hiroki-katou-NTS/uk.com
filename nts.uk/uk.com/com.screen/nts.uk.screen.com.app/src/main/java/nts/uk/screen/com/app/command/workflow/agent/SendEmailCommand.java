package nts.uk.screen.com.app.command.workflow.agent;

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
