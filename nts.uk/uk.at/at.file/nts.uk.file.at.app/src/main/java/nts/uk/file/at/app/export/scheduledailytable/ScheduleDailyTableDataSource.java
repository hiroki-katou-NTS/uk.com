package nts.uk.file.at.app.export.scheduledailytable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDailyTableDataSource {
    // 会社名
    private String companyName;

    // 勤務計画実施表の名称
    private String scheduleDailyTableName;

    // コメント
    private String comment;

    // List<勤務計画実施表の印鑑欄見出し>
    private List<String> headingTitles;

    // List<職場グループに関係する表示情報dto>
    private List<WkpGroupRelatedDisplayInfoDto> displayInfos;
}
