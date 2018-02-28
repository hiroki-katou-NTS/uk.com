package nts.uk.ctx.exio.dom.exi.codeconvert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

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
    
    public static AcceptCdConvert createFromJavaType(Long version, String cid, String convertCd, String convertName, int acceptWithoutSetting)
    {
        AcceptCdConvert  acceptCdConvert =  new AcceptCdConvert(cid, convertCd, convertName,  acceptWithoutSetting);
        acceptCdConvert.setVersion(version);
        return acceptCdConvert;
    }
    
}
