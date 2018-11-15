package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementItem;

/**
* 要素項目
*/

@Data
@NoArgsConstructor
public class ElementItemCommand {
    // Merge domain 要素項目, 要素項目（マスタ）, 要素項目（数値）
    /**
     * 要素項目（マスタ）.マスタコード
     */
    private String masterCode;

    /**
     * 要素項目(数値).枠番
     */
    private Integer frameNumber;

    /**
     * .要素項目(数値)当該枠下限
     */
    private Integer frameLowerLimit;

    /**
     * 要素項目(数値).当該枠上限
     */
    private Integer frameUpperLimit;

    public ElementItem fromCommandToDomain() {
        return new ElementItem(masterCode, frameNumber, frameLowerLimit, frameUpperLimit);
    }
    
}
