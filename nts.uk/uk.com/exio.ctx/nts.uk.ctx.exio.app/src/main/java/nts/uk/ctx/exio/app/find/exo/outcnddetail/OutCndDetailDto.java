package nts.uk.ctx.exio.app.find.exo.outcnddetail;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetail;

/**
* 出力条件詳細(定型)
*/
@AllArgsConstructor
@Value
public class OutCndDetailDto{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 条件設定コード
    */
    private String companyCndSetCd;
    
    /**
    * 条件SQL
    */
    private String exterOutCdnSql;
    
    
    public static OutCndDetailDto fromDomain(OutCndDetail domain)
    {
        return new OutCndDetailDto(domain.getCid(), domain.getCompanyCndSetCd().v(), domain.getExterOutCdnSql().v());
    }
}