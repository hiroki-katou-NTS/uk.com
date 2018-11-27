package nts.uk.ctx.pr.core.dom.wageprovision.statementlayout.export;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.YearMonth;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StatementLayoutExportData {
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
     * カテゴリ別設定
     */
    private List<SettingByCtgExportData> listSettingByCtg;
}
