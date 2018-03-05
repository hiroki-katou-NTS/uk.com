package nts.uk.ctx.exio.dom.exi.codeconvert;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 受入コード変換
*/
@AllArgsConstructor
@Getter
@Setter
public class AcceptCdConvert extends AggregateRoot
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
    * コード変換名称
    */
    private String convertName;
    
    /**
    * 設定のないコードの受入
    */
    private int acceptWithoutSetting;
    
    /**
     * List convert detail data
     */
    private List<CdConvertDetails> listConvertDetails;
    
    public static AcceptCdConvert createFromJavaType(String cid, String convertCd, String convertName, int acceptWithoutSetting, List<CdConvertDetails> listConvertDetails)
    {
        AcceptCdConvert  acceptCdConvert =  new AcceptCdConvert(cid, convertCd, convertName,  acceptWithoutSetting, listConvertDetails);
        //acceptCdConvert.setVersion(version);
        return acceptCdConvert;
    }
    
}
