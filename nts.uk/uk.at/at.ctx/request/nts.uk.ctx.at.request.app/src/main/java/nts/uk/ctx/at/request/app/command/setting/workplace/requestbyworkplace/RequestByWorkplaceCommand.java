package nts.uk.ctx.at.request.app.command.setting.workplace.requestbyworkplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.setting.workplace.appuseset.ApplicationUseSetCommand;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.request.dom.setting.workplace.requestbyworkplace.RequestByWorkplace;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestByWorkplaceCommand {
    private String workplaceId;
    private List<ApplicationUseSetCommand> settings;

    public RequestByWorkplace toDomain(String companyId) {
        return new RequestByWorkplace(
                companyId,
                workplaceId,
                new ApprovalFunctionSet(settings.stream().map(ApplicationUseSetCommand::toDomain).collect(Collectors.toList()))
        );
    }
}
