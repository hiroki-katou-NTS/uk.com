package nts.uk.ctx.pr.core.dom.wageprovision.formula;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.enums.EnumAdaptor;

import javax.xml.soap.Detail;

/**
* 詳細計算式
*/
@Getter
public class DetailFormulaSetting extends AggregateRoot {
    
    /**
    * 端数処理(詳細計算式)
    */
    private Rounding roundingMethod;
    
    /**
    * 端数処理位置
    */
    private RoundingPosition roundingPosition;
    
    /**
    * 参照月
    */
    private ReferenceMonth referenceMonth;
    
    /**
    * 履歴ID
    */
    private String historyId;
    
    /**
    * 詳細計算式（コード）
    */
    private List<DetailCalculationFormula> detailCalculationFormula;
    
    public DetailFormulaSetting(Integer roundingMethod, Integer roundingPosition, Integer referenceMonth, String elementOrder, List<DetailCalculationFormula> detailCalculationFormula) {
        this.roundingMethod = EnumAdaptor.valueOf(roundingMethod, Rounding.class);
        this.roundingPosition = EnumAdaptor.valueOf(roundingPosition, RoundingPosition.class);
        this.referenceMonth = EnumAdaptor.valueOf(referenceMonth, ReferenceMonth.class);
        this.detailCalculationFormula = detailCalculationFormula;
        this.historyId = historyId;
    }
    
}
