package nts.uk.ctx.exio.dom.exo.cdconvert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;


/**
* コード変換詳細
*/
@AllArgsConstructor
@Getter
public class CdConvertDetail extends AggregateRoot
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
    
    
}
