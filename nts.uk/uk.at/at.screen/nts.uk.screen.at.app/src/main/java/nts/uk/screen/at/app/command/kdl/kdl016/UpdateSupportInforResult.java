package nts.uk.screen.at.app.command.kdl.kdl016;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class UpdateSupportInforResult extends DeleteSupportInfoResult {
    public UpdateSupportInforResult(List<EmployeeErrorResult> errorResults) {
        super(errorResults);
    }
}