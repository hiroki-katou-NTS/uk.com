package nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * 特別休暇使用
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpecialLeaveUseImport {
    // 使用日数
    private Optional<Integer> usedDays;

    // 使用時間
    private Optional<Integer> usedTime;
}
