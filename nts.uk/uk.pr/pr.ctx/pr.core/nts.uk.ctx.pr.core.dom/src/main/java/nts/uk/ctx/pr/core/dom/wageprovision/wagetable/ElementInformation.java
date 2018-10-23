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
    private ElementAttribute firstDimensionalElement;
    
    /**
    * 二次元要素
    */
    private Optional<ElementAttribute> thirdDimensionalElement;
    
    /**
    * 三次元要素
    */
    private Optional<ElementAttribute> secondDimensionalElement;
    
    public ElementInformation(Integer firstMasterNumericClassification, Integer firstFixedElement, String firstOptionalAddinationElement,
                              Integer secondMasterNumericClassification, Integer secondFixedElement, String secondOptionalAddinationElement,
                              Integer thirdMasterNumericClassification, Integer thirdFixedElement, String thirdOptionalAddinationElement) {
        this.firstDimensionalElement = new ElementAttribute(firstMasterNumericClassification, firstFixedElement, firstOptionalAddinationElement);
        this.thirdDimensionalElement = Optional.of(new ElementAttribute(secondMasterNumericClassification, secondFixedElement, secondOptionalAddinationElement));
        this.secondDimensionalElement = Optional.of(new ElementAttribute(thirdMasterNumericClassification, thirdFixedElement, thirdOptionalAddinationElement));
    }
    
}
