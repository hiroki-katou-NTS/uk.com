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
    * 処理区分NO
    */
    private int processCateNo;
    
    /**
    * ユーザID
    */
    private String uid;
    
    public PerProcessClsSet(String companyId, int processCateNo, String userId) {
        this.cid = companyId;
        this.uid = userId;
        this.processCateNo = processCateNo;
    }
    
}
