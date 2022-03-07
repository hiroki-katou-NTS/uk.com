package nts.uk.screen.at.app.command.kdl.kdl016;

import lombok.Data;

import java.util.List;

@Data
public class DeleteSupportInfoCommand {
    private List<String> employeeIds;
}
