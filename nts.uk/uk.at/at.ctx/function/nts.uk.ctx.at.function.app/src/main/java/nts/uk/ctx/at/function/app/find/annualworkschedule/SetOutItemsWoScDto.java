package nts.uk.ctx.at.function.app.find.annualworkschedule;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoSc;

/**
* 年間勤務表（36チェックリスト）の出力項目設定
*/
@AllArgsConstructor
@Value
public class SetOutItemsWoScDto
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
    
    
    public static SetOutItemsWoScDto fromDomain(SetOutItemsWoSc domain)
    {
        return new SetOutItemsWoScDto(domain.getCid(), domain.getCd(), domain.getName(), domain.getOutNumExceedTime36Agr(), domain.getDisplayFormat());
    }
    
}
