package nts.uk.file.at.app.export.form9;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.aggregation.dom.form9.Form9DetailOutputSetting;
import nts.uk.ctx.at.aggregation.dom.form9.Form9Layout;
import nts.uk.file.at.app.export.form9.dto.DisplayInfoRelatedToWorkplaceGroupDto;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Form9ExcelByFormatDataSource {
    /** Optional<様式９の出力レイアウト> */
    private Form9Layout form9Layout;

    /** */
    private String fileName;

    /** 様式９の詳細出力設定 */
    private Form9DetailOutputSetting detailOutputSetting;


    /** List<職場グループに関係する表示情報dto> */
    private List<DisplayInfoRelatedToWorkplaceGroupDto> infoRelatedWkpGroups;
}
