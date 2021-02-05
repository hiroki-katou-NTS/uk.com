package nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 特別休暇
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpecialLeaveImport {
    // 使用数
    private SpecialLeaveUsageInfoImport numberOfUse;

    // 残数
    private RemainingNumberOfSpecialLeaveImport remaining;
}
