package nts.uk.ctx.workflow.dom.approvermanagement.workroot.service.unregisterapproval;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnregisteredApprovalCheckResult {
    private Integer route; // （個人（1）、職場（2）、会社（3））
    private Boolean common;
    private Optional<String> workplaceId = Optional.empty();
    private List<ErrorContent> errorContents = new ArrayList<>();
}
