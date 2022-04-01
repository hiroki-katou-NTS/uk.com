package nts.uk.ctx.at.function.dom.supportworklist.outputsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 出力項目
 */
@AllArgsConstructor
@Getter
public class OutputItem {
    /**
     * 勤怠項目ID
     */
    private int attendanceItemId;

    /**
     * 表示順
     */
    private int displayOrder;
}
