package nts.uk.ctx.exio.app.find.exi.codeconvert;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.exi.codeconvert.AcceptCdConvert;

/**
* 受入コード変換
*/
@AllArgsConstructor
@Value
public class AcceptCdConvertDto
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
    
    
    private Long version;
    public static AcceptCdConvertDto fromDomain(AcceptCdConvert domain)
    {
        return new AcceptCdConvertDto(domain.getCid(), domain.getConvertCd(), domain.getConvertName(), domain.getAcceptWithoutSetting(), domain.getVersion());
    }
    
}
