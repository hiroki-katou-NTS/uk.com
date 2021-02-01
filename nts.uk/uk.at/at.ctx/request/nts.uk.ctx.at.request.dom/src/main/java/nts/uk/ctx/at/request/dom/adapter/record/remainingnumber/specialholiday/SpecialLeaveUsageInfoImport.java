package nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * 特別休暇使用情報
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpecialLeaveUsageInfoImport {
    // 付与前
    private SpecialLeaveUseImport beforeGrant;

    // 合計
    private SpecialLeaveUseImport total;

    // 時間休暇使用回数
    private Integer numberOfTimesUsed;

    // 時間休暇使用日数
    private Integer numberOfDaysUsed;

    // 付与後
    private Optional<SpecialLeaveUseImport> afterGrant;
}
