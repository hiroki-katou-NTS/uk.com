package nts.uk.ctx.exio.app.command.exo.cdconvert;

import lombok.Value;

@Value
public class OutputCodeConvertCommand
{
    
    /**
    * コード変換コード
    */
    private String convertCd;
    
    /**
    * コード変換名称
    */
    private String convertName;
    
    /**
    * 会社ID
    */
    private String cid;
    
    /**
    * 設定のないコードの出力
    */
    private int acceptWithoutSetting;
    
    private Long version;

}
