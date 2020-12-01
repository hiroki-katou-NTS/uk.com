package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

/**
 * The present closing period dto 現在の締め期間 
 * @author DungDV
 */
@Data
@Builder
public class PresentClosingPeriodDto {
    // 処理年月
    private int processingYm;
    
    // 締め開始日
    private GeneralDate closureStartDate;
    
    // 締め終了日
    private GeneralDate closureEndDate;
}
