package nts.uk.ctx.exio.dom.exi.codeconvert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/**
* コード変換詳細
*/
@AllArgsConstructor
@Getter
@Setter
public class CdConvertDetails extends AggregateRoot
{
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * コード変換コード
    */
    private String convertCd;
    
    /**
    * 行番号
    */
    private int lineNumber;
    
    /**
    * 出力項目
    */
    private String outputItem;
    
    /**
    * 本システムのコード
    */
    private String systemCd;
    
    public static CdConvertDetails createFromJavaType(Long version, String cid, String convertCd, int lineNumber, String outputItem, String systemCd)
    {
        CdConvertDetails  cdConvertDetails =  new CdConvertDetails(cid, convertCd, lineNumber, outputItem,  systemCd);
        cdConvertDetails.setVersion(version);
        return cdConvertDetails;
    }
    
}
