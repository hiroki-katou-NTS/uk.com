package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;

import java.util.Optional;

public class ElementAttribute extends DomainObject{

    /**
     * マスタ数値区分
     */
    private Optional<MasterNumericInformation> masterNumericInformation;

    /**
     * 固定の要素
     */
    private Optional<ElementType> fixedElement;

    /**
     * 任意追加の要素
     */
    private Optional<ItemNameCode> optionalAdditionalElement;

    public ElementAttribute (Integer masterNumericInformation, Integer fixedElement, String optionalAdditionalElement) {
        this.masterNumericInformation = masterNumericInformation == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(masterNumericInformation, MasterNumericInformation.class));
        this.fixedElement = fixedElement == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(fixedElement, ElementType.class));
        this.optionalAdditionalElement = optionalAdditionalElement == null ? Optional.empty() : Optional.of(new ItemNameCode(optionalAdditionalElement));


    }

}
