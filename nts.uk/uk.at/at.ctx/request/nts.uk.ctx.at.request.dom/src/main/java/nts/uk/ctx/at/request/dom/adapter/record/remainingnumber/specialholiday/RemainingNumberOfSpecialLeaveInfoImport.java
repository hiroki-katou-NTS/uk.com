package nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 特別休暇情報残数
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RemainingNumberOfSpecialLeaveInfoImport {
    // 未消化数
    private NumberOfUndigestedSpecialLeave undigestedNumber;

    // 特別休暇(マイナスあり)
    private SpecialLeaveImport specialLeaveWithMinus;

    // 特別休暇(マイナスなし)
    private SpecialLeaveImport specialLeaveNoMinus;
}
