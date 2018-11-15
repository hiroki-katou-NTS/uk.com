package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* 要素情報
*/
@AllArgsConstructor
@Getter
public class ElementInformation extends DomainObject
{
    
    /**
    * 一次元要素
    */
    private ElementAttribute oneDimensionalElement;

    /**
    * 二次元要素
    */
    private Optional<ElementAttribute> threeDimensionalElement;

    /**
    * 三次元要素
    */
    private Optional<ElementAttribute> twoDimensionalElement;

    public ElementInformation(Integer firstFixedElement, String firstOptionalAddElement, Integer firstMasterNumericCls,
                              Integer secondFixedElement, String sencondOptionalAddElement, Integer secondMasterNumeric,
                              Integer thirdFixedElement, String thirdOptionAddElement, Integer thirdMasterNumeric) {
        this.oneDimensionalElement = new ElementAttribute(firstFixedElement,firstOptionalAddElement,firstMasterNumericCls);
        this.threeDimensionalElement = Optional.ofNullable((secondFixedElement == null && sencondOptionalAddElement == null && secondMasterNumeric == null)? null
                : new ElementAttribute(secondFixedElement,sencondOptionalAddElement,secondMasterNumeric));
        this.twoDimensionalElement =  Optional.ofNullable((thirdFixedElement == null && thirdOptionAddElement == null && thirdMasterNumeric == null)? null
                : new ElementAttribute(thirdFixedElement,thirdOptionAddElement,thirdMasterNumeric));
    }
}
