package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.primitive.PrimitiveValueBase;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementAttribute;

@Data
@NoArgsConstructor
public class ElementAttributeCommand {

    /**
     * マスタ数値区分
     */
    private Integer masterNumericInformation;

    /**
     * 固定の要素
     */
    private Integer fixedElement;

    /**
     * 任意追加の要素
     */
    private String optionalAdditionalElement;

    public ElementAttribute fromCommandToDomain() {
        return new ElementAttribute(masterNumericInformation, fixedElement, optionalAdditionalElement);
    }

}
