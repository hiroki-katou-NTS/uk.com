package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.ItemNameCode;

import java.util.Optional;

@Getter
public class ElementAttribute extends DomainObject {
    /**
     * 一次元要素
     */
    private Optional<ElementType> fixedElement;

    /**
     * 二次元要素
     */
    private Optional<ItemNameCode> optionalAdditionalElement;

    /**
     * 三次元要素
     */
    private Optional<MasterNumericInfomation> masterNumericClassification;

    public ElementAttribute(Integer fixedElement, String optionalAdditionalElement, Integer masterNumericClassification) {
        this.fixedElement = Optional.ofNullable(fixedElement == null? null : EnumAdaptor.valueOf(fixedElement,ElementType.class));
        this.optionalAdditionalElement = Optional.ofNullable(optionalAdditionalElement == null? null :new ItemNameCode(optionalAdditionalElement));
        this.masterNumericClassification = Optional.ofNullable(masterNumericClassification == null? null : EnumAdaptor.valueOf(masterNumericClassification,MasterNumericInfomation.class));
    }
}
