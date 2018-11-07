package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.enums.EnumAdaptor;

/**
* かんたん計算基準金額項目
*/
@AllArgsConstructor
@Getter
public class BasicCalculationStandardAmount extends DomainObject
{
    
    /**
    * 基準金額区分
    */
    private StandardAmountClassification standardAmountClassification;
    
    /**
    * 基準金額固定値
    */
    private Optional<BasePriceFixedAmount> standardFixedValue;
    
    /**
    * 対象項目コードリスト
    */
    private List<TargetItemCode> targetItemCodeList;
    
    public BasicCalculationStandardAmount(int standardAmountClassification, BigDecimal standardFixedValue, List<String> targetItemCodeList) {
        this.standardAmountClassification = EnumAdaptor.valueOf(standardAmountClassification, StandardAmountClassification.class);
        this.standardFixedValue = standardFixedValue == null ? Optional.empty() : Optional.of(new BasePriceFixedAmount(standardFixedValue));
        this.targetItemCodeList = targetItemCodeList.stream().map(item -> new TargetItemCode(item)).collect(Collectors.toList());
    }
    
}
