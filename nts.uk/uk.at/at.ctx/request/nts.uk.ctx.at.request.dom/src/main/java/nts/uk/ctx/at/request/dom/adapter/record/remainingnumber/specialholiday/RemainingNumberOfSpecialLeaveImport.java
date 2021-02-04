package nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * 特別休暇残数
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RemainingNumberOfSpecialLeaveImport {
    // 付与前
    private SpecialLeaveRemainingNumberInfoImport beforeGrant;

    // 合計
    private SpecialLeaveRemainingNumberInfoImport total;

    // 付与後
    private Optional<SpecialLeaveRemainingNumberInfoImport> afterGrant;
}
