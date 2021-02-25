package nts.uk.screen.at.app.ktgwidget.find.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author tutt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PresentClosingPeriodImportDto {
    // 処理年月
    private Integer processingYm;
    
    // 締め開始日
    private String closureStartDate;
    
    // 締め終了日
    private String closureEndDate;	
}
