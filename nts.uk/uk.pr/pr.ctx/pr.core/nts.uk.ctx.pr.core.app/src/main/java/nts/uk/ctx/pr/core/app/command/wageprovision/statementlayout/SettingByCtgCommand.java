package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByCtg;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Value
public class SettingByCtgCommand {
    private Integer ctgAtr;
    private List<LineByLineSettingCommand> listLineByLineSet;

    public SettingByCtg toDomain() {
        return new SettingByCtg(ctgAtr, listLineByLineSet.stream().map(i -> i.toDomain()).collect(Collectors.toList()));
    }
}
