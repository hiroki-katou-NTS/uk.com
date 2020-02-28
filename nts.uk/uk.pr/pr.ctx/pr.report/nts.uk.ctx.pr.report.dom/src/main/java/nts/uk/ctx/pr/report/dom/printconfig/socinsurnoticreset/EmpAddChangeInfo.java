package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 社員住所変更届情報
*/
@Getter
public class EmpAddChangeInfo extends AggregateRoot {
    
    /**
    * 社員ID
    */
    private String sid;
    
    /**
    * 本人設定
    */
    private AddChangeSetting personalSet;
    
    /**
    * 被扶養配偶者設定
    */
    private AddChangeSetting spouse;
    
    public EmpAddChangeInfo(String sid, AddChangeSetting personalSet, AddChangeSetting spouse) {
        this.sid = sid;
        this.personalSet = personalSet;
        this.spouse = spouse;
    }
    
}
