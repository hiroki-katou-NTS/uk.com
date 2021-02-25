package nts.uk.ctx.at.request.app.command.setting.company.applicationapprovalsetting.appovertime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.OvertimeQuotaSetUse;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OvertimeQuotaSetUseCommand {
    private Integer overtimeAtr;
    private Integer flexAtr;
    private Integer overTimeFrame;

    public static OvertimeQuotaSetUse toDomains(List<OvertimeQuotaSetUseCommand> commands) {
        return OvertimeQuotaSetUse.create(
                commands.get(0).overtimeAtr,
                commands.get(0).flexAtr,
                commands.stream().map(OvertimeQuotaSetUseCommand::getOverTimeFrame).collect(Collectors.toList())
        );
    }

}
