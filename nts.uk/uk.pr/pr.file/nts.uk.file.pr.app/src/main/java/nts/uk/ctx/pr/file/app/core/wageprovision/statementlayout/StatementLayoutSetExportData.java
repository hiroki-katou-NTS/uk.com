package nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.StatementLayoutPattern;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StatementLayoutSetExportData {
    /**
     * 明細書コード
     */
    private String statementCode;
    /**
     * 明細書名称
     */
    private String statementName;
    /**
     * 基準年月
     */
    private YearMonth processingDate;
    /**
     * レイアウトパターン
     */
    private StatementLayoutPattern layoutPattern;
    /**
     * カテゴリ別設定
     */
    private List<SettingByCtgExportData> listSettingByCtg;
}
