package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.ElementItemDto;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ContentElementAttribute;

/**
 * 要素属性
 */
@Data
@NoArgsConstructor
public class ContentElementAttributeCommand {

    /**
     * 第一要素項目
     */
    private ElementItemCommand firstElementItem;

    /**
     * 第二要素項目
     */
    private ElementItemCommand secondElementItem;

    /**
     * 第三要素項目
     */
    private ElementItemCommand thirdElementItem;

    public ContentElementAttribute fromCommandToDomain(){
        return new ContentElementAttribute(firstElementItem.fromCommandToDomain(), secondElementItem.fromCommandToDomain(), thirdElementItem.fromCommandToDomain());
    }


}
