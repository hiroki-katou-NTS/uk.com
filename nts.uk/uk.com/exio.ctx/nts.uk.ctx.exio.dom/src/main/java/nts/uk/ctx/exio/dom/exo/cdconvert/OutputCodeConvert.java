package nts.uk.ctx.exio.dom.exo.cdconvert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
* 出力コード変換
*/
@AllArgsConstructor
@Getter
public class OutputCodeConvert extends AggregateRoot
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
    
    
}
