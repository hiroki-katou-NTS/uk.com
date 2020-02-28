package nts.uk.ctx.pr.core.dom.wageprovision.processdatecls;

import java.util.Optional;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.enums.EnumAdaptor;

/**
* 個人処理区分設定
*/
@Getter
public class PerProcessClsSet extends AggregateRoot {
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * ユーザID
    */
    private String uid;


    /**
     * 処理区分NO
     */
    private int processCateNo;
    
    public PerProcessClsSet(String companyId,  String userId, int processCateNo) {
        this.cid = companyId;
        this.uid = userId;
        this.processCateNo = processCateNo;
    }
    
}
