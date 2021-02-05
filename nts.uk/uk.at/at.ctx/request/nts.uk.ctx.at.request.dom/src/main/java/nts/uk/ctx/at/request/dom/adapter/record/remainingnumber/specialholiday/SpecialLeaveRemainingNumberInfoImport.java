package nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * 特別休暇残数情報
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpecialLeaveRemainingNumberInfoImport {
    // 日数
    private Double days;

    // 明細
    private List<SpecialLeaveDetailImport> details;

    // 時間
    private Optional<Integer> time;
}
