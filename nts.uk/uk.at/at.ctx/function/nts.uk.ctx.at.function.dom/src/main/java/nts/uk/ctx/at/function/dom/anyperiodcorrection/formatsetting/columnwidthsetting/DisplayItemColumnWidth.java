package nts.uk.ctx.at.function.dom.anyperiodcorrection.formatsetting.columnwidthsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 表示項目の列幅
 */
@Getter
@AllArgsConstructor
public class DisplayItemColumnWidth {
    /**勤怠項目ID*/
    private int attendanceItemId;

    /**列幅*/
    @Setter
    private int columnWidth;
}
