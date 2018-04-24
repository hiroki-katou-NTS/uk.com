package nts.uk.ctx.at.function.dom.annualworkschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* 帳表に出力する項目-Master
*/
@AllArgsConstructor
@Getter
public class ItemOutTblBookMaster extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コード
    */
    private int cd;
    
    /**
    * 並び順
    */
    private int sortBy;
    
    /**
    * 見出し名称
    */
    private String headingName;
    
    
}
