package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutSet;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Value
public class StatementLayoutSetCommand {
    private String histId;
    private Integer layoutPattern;
    private List<SettingByCtgCommand> listSettingByCtg;

    public StatementLayoutSet toDomain() {
        return new StatementLayoutSet(histId, layoutPattern, listSettingByCtg.stream().map(i -> i.toDomain()).collect(Collectors.toList()));
    }
}
