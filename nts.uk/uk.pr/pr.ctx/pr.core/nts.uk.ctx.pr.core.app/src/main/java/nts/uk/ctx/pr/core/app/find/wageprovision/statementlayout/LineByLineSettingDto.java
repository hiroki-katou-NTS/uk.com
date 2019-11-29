package nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.LineByLineSetting;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.SettingByItem;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Value
public class LineByLineSettingDto {
    /**
     * 印字設定
     */
    private Integer printSet;

    /**
     * 行番号
     */
    private int lineNumber;

    /**
     * 項目別設定
     */
    private List<SettingByItemDto> listSetByItem;

    public LineByLineSettingDto(LineByLineSetting domain) {
        this.printSet = domain.getPrintSet().value;
        this.lineNumber = domain.getLineNumber();
        this.listSetByItem = domain.getListSetByItem().stream().map(i -> new SettingByItemDto(i)).collect(Collectors.toList());
    }
}
