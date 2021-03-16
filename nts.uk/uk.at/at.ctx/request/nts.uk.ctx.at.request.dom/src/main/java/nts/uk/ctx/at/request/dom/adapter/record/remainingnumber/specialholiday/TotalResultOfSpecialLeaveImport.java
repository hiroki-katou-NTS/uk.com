package nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.gul.util.value.Finally;

import java.util.List;
import java.util.Optional;

/**
 * 特別休暇の集計結果
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TotalResultOfSpecialLeaveImport {
    // エラー情報
    private List<SpecialLeaveError> errorInfo;

    // 特別休暇情報(付与時点)
    private Optional<SpecialLeaveInfoImport> atGrantTimeInfo;

    // 特別休暇情報(消滅)
    private Optional<SpecialLeaveInfoImport> disappearanceInfo;

    // 特別休暇情報(期間終了日の翌日開始時点)
    private Finally<SpecialLeaveInfoImport> afterEndPeriodInfo;

    // 特別休暇情報(期間終了日時点)
    private Finally<SpecialLeaveInfoImport> atEndPeriodInfo;
}
