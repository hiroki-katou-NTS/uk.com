package nts.uk.ctx.at.function.dom.annualworkschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 年間勤務表（36チェックリスト）の出力項目設定
*/
@AllArgsConstructor
@Getter
public class SetOutItemsWoSc extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コード
    */
    private String cd;
    
    /**
    * 名称
    */
    private String name;
    
    /**
    * 36協定時間を超過した月数を出力する
    */
    private int outNumExceedTime36Agr;
    
    /**
    * 表示形式
    */
    private int displayFormat;
    
    
}
