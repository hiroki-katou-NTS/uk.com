package nts.uk.ctx.pr.core.app.command.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.LineByLineSetting;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Value
public class LineByLineSettingCommand {
    private Integer printSet;
    private int lineNumber;
    private List<SettingByItemCommand> listSetByItem;

    public LineByLineSetting toDomain() {
        return new LineByLineSetting(printSet, lineNumber, listSetByItem.stream().map(i -> i.toDomain()).collect(Collectors.toList()));
    }
}
