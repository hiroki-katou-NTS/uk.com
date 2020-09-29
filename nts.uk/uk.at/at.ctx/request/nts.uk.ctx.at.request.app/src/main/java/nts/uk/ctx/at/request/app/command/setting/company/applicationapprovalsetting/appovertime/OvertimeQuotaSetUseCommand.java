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

    public static List<OvertimeQuotaSetUse> toDomains(List<OvertimeQuotaSetUseCommand> commands) {
        List<OvertimeQuotaSetUse> domains = new ArrayList<>();
        Map<Object, List<OvertimeQuotaSetUseCommand>> group = commands.stream().collect(Collectors.groupingBy(e -> new HashMap() {{
            put(e.getOvertimeAtr(), e.getFlexAtr());
        }}));
        group.forEach((key, value) -> {
            OvertimeQuotaSetUse quota = OvertimeQuotaSetUse.create((Integer) ((Map)key).keySet().toArray()[0], (Integer) ((Map)key).values().toArray()[0], value.stream().map(OvertimeQuotaSetUseCommand::getOverTimeFrame).collect(Collectors.toList()));
            domains.add(quota);
        });
        return domains;
    }

}
