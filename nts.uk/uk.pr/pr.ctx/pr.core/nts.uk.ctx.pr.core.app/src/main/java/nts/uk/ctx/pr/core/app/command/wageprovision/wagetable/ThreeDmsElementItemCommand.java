package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementItem;

/**
* 要素項目
*/

@Data
@NoArgsConstructor
public class ThreeDmsElementItemCommand {
    // Merge domain 要素項目, 要素項目（マスタ）, 要素項目（数値）
    /**
     * 要素項目（マスタ）.マスタコード
     */
    private String masterCode;

    /**
     * 要素項目(数値).枠番
     */
    private Long frameNumber;

    /**
     * .要素項目(数値)当該枠下限
     */
    private BigDecimal frameLowerLimit;

    /**
     * 要素項目(数値).当該枠上限
     */
    private BigDecimal frameUpperLimit;
    
    private List<TwoDmsElementItemCommand> listFirstDms;

    public ElementItem fromCommandToDomain() {
        return new ElementItem(masterCode, frameNumber, frameLowerLimit, frameUpperLimit);
    }
    
}
