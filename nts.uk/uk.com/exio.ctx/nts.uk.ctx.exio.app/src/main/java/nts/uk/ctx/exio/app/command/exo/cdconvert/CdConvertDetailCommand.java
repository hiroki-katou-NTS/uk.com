package nts.uk.ctx.exio.app.command.exo.cdconvert;

import lombok.Value;

@Value
public class CdConvertDetailCommand
{
    
    /**
    * コード変換コード
    */
    private String convertCd;
    
    /**
    * 出力項目
    */
    private String outputItem;
    
    /**
    * 本システムのコード
    */
    private String systemCd;
    
    /**
    * 行番号
    */
    private String lineNumber;
    
    private Long version;

}
