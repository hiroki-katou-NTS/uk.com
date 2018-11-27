package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.export;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementPrintAtr;

import java.util.List;

@AllArgsConstructor
@Getter
public class LineByLineSettingExportData {
    /**
     * 印字設定
     */
    private StatementPrintAtr printSet;

    /**
     * 行番号
     */
    private int lineNumber;

    /**
     * 項目別設定
     */
    private List<SettingByItemExportData> listSetByItem;
}
