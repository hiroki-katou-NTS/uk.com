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
* かんたん計算係数区分
*/
@AllArgsConstructor
@Getter
public class BasicCalculationFactorClassification extends DomainObject
{
    
    /**
    * 係数固定値
    */
    private Optional<CoefficientFixedValue> coefficientFixedValue;
    
    /**
    * 係数項目
    */
    private CoefficientItem coefficientItem;
    
    public BasicCalculationFactorClassification(BigDecimal coefficientFixedValue, CoefficientItem coefficientItem) {
        this.coefficientItem = coefficientItem;
        this.coefficientFixedValue = coefficientFixedValue == null ? Optional.empty() : Optional.of(new CoefficientFixedValue(coefficientFixedValue));
    }
    
}
