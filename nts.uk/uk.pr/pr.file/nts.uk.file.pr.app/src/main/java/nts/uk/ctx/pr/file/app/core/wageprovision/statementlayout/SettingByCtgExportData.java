package nts.uk.ctx.pr.file.app.core.wageprovision.statementlayout;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.CategoryAtr;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class SettingByCtgExportData {
    /**
     * カテゴリ区分
     */
    private CategoryAtr ctgAtr;

    /**
     * 行別設定
     */
    private List<LineByLineSettingExportData> listLineByLineSet;

}
