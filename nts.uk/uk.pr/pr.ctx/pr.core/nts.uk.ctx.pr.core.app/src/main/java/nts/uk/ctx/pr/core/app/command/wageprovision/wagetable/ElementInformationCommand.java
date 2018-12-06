package nts.uk.ctx.pr.core.app.command.wageprovision.wagetable;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.app.find.wageprovision.wagetable.ElementAttributeDto;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.ElementInformation;

import java.util.Optional;

/**
* 要素情報
*/

@Data
@NoArgsConstructor
public class ElementInformationCommand
{

    /**
    * 一次元要素
    */
    private ElementAttributeCommand oneDimensionalElement;

    /**
    * 二次元要素
    */
    private ElementAttributeCommand twoDimensionalElement;

    /**
    * 三次元要素
    */
    private ElementAttributeCommand threeDimensionalElement;

    public ElementInformation fromCommandToDomain() {
        return new ElementInformation(oneDimensionalElement.fromCommandToDomain(), twoDimensionalElement.fromCommandToDomain(), threeDimensionalElement.fromCommandToDomain());
    }
    
}
