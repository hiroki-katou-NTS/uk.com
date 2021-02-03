package nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

import java.util.List;

/**
 * refactor4 (refactor 4)
 * 特別休暇情報
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpecialLeaveInfoImport {
    // 未消化数
    private List<SpecialLeaveGrantRemainingNumberImport> remainingNumberOfGrantData;

    // 年月日
    private GeneralDate date;

    // 残数
    private RemainingNumberOfSpecialLeaveInfoImport remainingInfo;

}
