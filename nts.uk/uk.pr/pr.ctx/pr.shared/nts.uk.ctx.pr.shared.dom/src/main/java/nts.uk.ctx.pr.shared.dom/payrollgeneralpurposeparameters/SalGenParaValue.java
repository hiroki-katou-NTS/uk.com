package nts.uk.ctx.pr.shared.dom.payrollgeneralpurposeparameters;

import java.util.Optional;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.enums.EnumAdaptor;

/**
* 給与汎用パラメータ値
*/
@Getter
public class SalGenParaValue extends AggregateRoot {
    
    /**
    * 履歴ID
    */
    private String historyId;
    
    /**
    * 選択肢
    */
    private Optional<Integer> selection;
    
    /**
    * 有効区分
    */
    private ParaAvailableValue availableAtr;
    
    /**
    * 値（数値）
    */
    private Optional<ParamNumber> numValue;
    
    /**
    * 値（文字）
    */
    private Optional<ParamCharacter> charValue;
    
    /**
    * 値（時間）
    */
    private Optional<ParamTime> timeValue;
    
    /**
    * 対象区分
    */
    private Optional<ParaTargetAtr> targetAtr;
    
    public SalGenParaValue(String hisId, Integer selection, int availableAtr, String numberValue, String characterValue, Integer timeValue, Integer targetAtr) {
        this.historyId = hisId;
        this.availableAtr = EnumAdaptor.valueOf(availableAtr, ParaAvailableValue.class);
        this.charValue = characterValue == null ? Optional.empty() : Optional.of(new ParamCharacter(characterValue));
        this.numValue = numberValue == null ? Optional.empty() : Optional.of(new ParamNumber(numberValue));
        this.targetAtr = targetAtr == null ? Optional.empty() : Optional.of(EnumAdaptor.valueOf(targetAtr, ParaTargetAtr.class));
        this.timeValue = timeValue == null ? Optional.empty() : Optional.of(new ParamTime(timeValue));
        this.selection = Optional.ofNullable(selection);
    }
    
}
