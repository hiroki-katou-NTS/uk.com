package nts.uk.ctx.pr.core.dom.wageprovision.speclayout;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 明細書レイアウト
*/
@Getter
public class SpecificationLayout extends AggregateRoot {
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 明細書コード
    */
    private SpecCode specCode;
    
    /**
    * 明細書名称
    */
    private SpecName specName;
    
    public SpecificationLayout(String cid, String specCd, String specName) {
        this.cid = cid;
        this.specName = new SpecName(specName);
        this.specCode = new SpecCode(specCd);
    }
    
}
