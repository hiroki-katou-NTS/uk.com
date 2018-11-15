package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.ElementRangeDto;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementRangeSetting;

/**
* 要素範囲設定
*/
@Data
@NoArgsConstructor
public class ElementRangeSettingCommand {

    /**
    * 第一要素範囲
    */
    private ElementRangeCommand firstElementRange;

    /**
    * 第二要素範囲
    */
    private ElementRangeCommand secondElementRange;

    /**
    * 第三要素範囲
    */
    private ElementRangeCommand thirdElementRange;

    /**
    * 履歴ID
    */
    private String historyID;

    public ElementRangeSetting fromCommandToDomaim() {
        return new ElementRangeSetting(firstElementRange.fromCommandToDomain(), secondElementRange.fromCommandToDomain(), thirdElementRange.fromCommandToDomain(), historyID);
    }
    
}
