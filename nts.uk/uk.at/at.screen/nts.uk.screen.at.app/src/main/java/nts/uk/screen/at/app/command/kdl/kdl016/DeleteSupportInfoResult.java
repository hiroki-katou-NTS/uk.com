package nts.uk.screen.at.app.command.kdl.kdl016;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.gul.collection.CollectionUtil;

import java.util.List;

@Data
@NoArgsConstructor
public class DeleteSupportInfoResult {
    private boolean isError;
    private List<EmployeeErrorResult> errorResults;

    public DeleteSupportInfoResult(List<EmployeeErrorResult> errorResults) {
        this.errorResults = errorResults;
        this.isError = !CollectionUtil.isEmpty(errorResults);
    }
}
