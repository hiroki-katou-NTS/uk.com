package nts.uk.file.at.app.export.form9;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.file.at.app.export.form9.dto.Form9ColorSettingsDTO;
import nts.uk.file.at.app.export.form9.dto.WorkplaceGroupInfoDto;

import java.util.List;

@Getter
@AllArgsConstructor
public class Form9ExcelByFormatQuery {
    /** 対象期間 - A4_5 */
    private GeneralDate startDate;
    private GeneralDate endDate;

    /** 職場グループリスト - A2_2 */
    private List<WorkplaceGroupInfoDto> wkpGroupList;

    /** 様式９のコード - A5_2 */
    private String code;

    /** 取得対象 - A6_2: Enum ScheRecGettingAtr */
    private int acquireTarget;

    /** 様式９の色設定 - A7 */
    private Form9ColorSettingsDTO colorSetting;
}
