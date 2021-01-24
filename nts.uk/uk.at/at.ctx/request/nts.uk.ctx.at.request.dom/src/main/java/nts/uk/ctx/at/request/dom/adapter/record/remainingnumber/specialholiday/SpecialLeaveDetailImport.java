package nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

import java.util.Optional;

/**
 * 特別休暇残明細
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpecialLeaveDetailImport {
    // 付与日
    private GeneralDate grantDate;

    // 日数
    private Double days;

    // 時間
    private Optional<Integer> time;
}
