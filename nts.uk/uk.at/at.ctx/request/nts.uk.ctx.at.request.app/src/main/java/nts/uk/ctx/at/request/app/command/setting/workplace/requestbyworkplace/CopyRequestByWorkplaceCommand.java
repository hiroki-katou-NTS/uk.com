package nts.uk.ctx.at.request.app.command.setting.workplace.requestbyworkplace;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CopyRequestByWorkplaceCommand {
    private String workplaceId;
    private List<String> targetWorkplaceIds;
}
