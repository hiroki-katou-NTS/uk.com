package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.math.BigDecimal;
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
* かんたん計算基底項目区分
*/
@AllArgsConstructor
@Getter
public class BasicCalculationItemCategory extends DomainObject
{
    
    /**
    * 基底項目区分
    */
    private BaseItemClassification baseItemClassification;
    
    /**
    * 基底項目固定値
    */
    private Optional<BaseItemFixedValue> baseItemFixedValue;
    
    public BasicCalculationItemCategory(int baseItemClassification, BigDecimal baseItemFixedValue) {
        this.baseItemClassification = EnumAdaptor.valueOf(baseItemClassification, BaseItemClassification.class);
        this.baseItemFixedValue = baseItemFixedValue == null ? Optional.empty() : Optional.of(new BaseItemFixedValue(baseItemFixedValue));
    }
    
}
