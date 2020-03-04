package nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 社員ローマ字氏名届情報
*/
@Getter
public class EmpNameReport extends AggregateRoot {
    
    /**
    * 社員ID
    */
    private String empId;

    /**
     * CID
     */
    private String cid;
    
    /**
    * 本人設定
    */
    private NameNotificationSet personalSet;
    
    /**
    * 配偶者設定
    */
    private NameNotificationSet spouse;
    
    public EmpNameReport(String empId, String cid, NameNotificationSet personalSet, NameNotificationSet spouse) {
        this.empId = empId;
        this.cid = cid;
        this.personalSet = personalSet;
        this.spouse = spouse;
    }
    
}
