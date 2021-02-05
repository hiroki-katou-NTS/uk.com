package nts.uk.ctx.at.request.dom.adapter.record.remainingnumber.specialholiday;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

/**
 * 特別休暇未消化数
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NumberOfUndigestedSpecialLeave {
    // 日数
    private Integer days;

    // 時間
    private Optional<Integer> time;
}
