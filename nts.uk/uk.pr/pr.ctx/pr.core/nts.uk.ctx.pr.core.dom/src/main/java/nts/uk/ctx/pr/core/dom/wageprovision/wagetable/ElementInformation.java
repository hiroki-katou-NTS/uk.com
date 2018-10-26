package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

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
    private Optional<ElementAttribute> twoDimensionalElement;
    
    /**
    * 三次元要素
    */
    private Optional<ElementAttribute> threeDimensionalElement;
    
    public ElementInformation(Integer oneMasterNumericClassification, Integer oneFixedElement, String oneOptionalAdditionalElement,
                              Integer twoMasterNumericClassification, Integer twoFixedElement, String twoOptionalAdditionalElement,
                              Integer threeMasterNumericClassification, Integer threeFixedElement, String threeOptionalAdditionalElement) {
        this.oneDimensionalElement = new ElementAttribute(oneMasterNumericClassification, oneFixedElement, oneOptionalAdditionalElement);
        this.twoDimensionalElement = Optional.of(new ElementAttribute(twoMasterNumericClassification, twoFixedElement, twoOptionalAdditionalElement));
        this.threeDimensionalElement = Optional.of(new ElementAttribute(threeMasterNumericClassification, threeFixedElement, threeOptionalAdditionalElement));
    }

    public ElementInformation(ElementAttribute oneDimensionalElement, ElementAttribute twoDimensionalElement, ElementAttribute threeDimensionalElement) {
        this.oneDimensionalElement = oneDimensionalElement;
        this.twoDimensionalElement = Optional.of(twoDimensionalElement);
        this.threeDimensionalElement = Optional.of(threeDimensionalElement);
    }
    
}
