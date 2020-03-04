package nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementPrintAtr;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
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
