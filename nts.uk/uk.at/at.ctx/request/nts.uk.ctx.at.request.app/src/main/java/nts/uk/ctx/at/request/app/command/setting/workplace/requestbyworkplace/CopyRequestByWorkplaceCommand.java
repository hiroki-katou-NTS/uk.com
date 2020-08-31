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
public class CopyRequestByWorkplaceCommand {
    private String workplaceId;
    private List<String> targetWorkplaceIds;
}
