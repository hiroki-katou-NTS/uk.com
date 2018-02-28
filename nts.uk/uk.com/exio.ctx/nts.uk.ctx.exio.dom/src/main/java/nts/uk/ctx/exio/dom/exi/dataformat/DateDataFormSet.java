package nts.uk.ctx.exio.dom.exi.dataformat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 日付型データ形式設定
*/
@AllArgsConstructor
@Getter
@Setter
public class DateDataFormSet extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 条件設定コード
    */
    private String conditionSetCd;
    
    /**
    * 受入項目番号
    */
    private int acceptItemNum;
    
    /**
    * 固定値
    */
    private int fixedValue;
    
    /**
    * 固定値の値
    */
    private String valueOfFixedValue;
    
    /**
    * 形式選択
    */
    private int formatSelection;
    
    public static DateDataFormSet createFromJavaType(Long version, String cid, String conditionSetCd, int acceptItemNum, int fixedValue, String valueOfFixedValue, int formatSelection)
    {
        DateDataFormSet  dateDataFormSet =  new DateDataFormSet(cid, conditionSetCd, acceptItemNum, fixedValue, valueOfFixedValue,  formatSelection);
        dateDataFormSet.setVersion(version);
        return dateDataFormSet;
    }
    
}
