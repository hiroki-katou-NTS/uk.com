package nts.uk.ctx.exio.dom.exo.cdconvert;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.DomainObject;


/**
* コード変換詳細
*/
@AllArgsConstructor
@Getter
public class CdConvertDetail extends DomainObject
{
    
    /**
    * コード変換コード
    */
    private ConvertCode convertCd;
    
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

	public CdConvertDetail(String convertCd, String outputItem, String systemCd, String lineNumber) {
		super();
		this.convertCd = EnumAdaptor.valueOf(Integer.parseInt(convertCd), ConvertCode.class);
		this.outputItem = outputItem;
		this.systemCd = systemCd;
		this.lineNumber = lineNumber;
	}
    
    
}
