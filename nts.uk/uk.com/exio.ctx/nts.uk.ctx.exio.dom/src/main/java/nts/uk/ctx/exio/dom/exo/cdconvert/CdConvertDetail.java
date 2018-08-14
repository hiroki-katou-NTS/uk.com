package nts.uk.ctx.exio.dom.exo.cdconvert;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.exio.dom.exo.outputitem.OutputItemCode;


/**
* コード変換詳細
*/
@AllArgsConstructor
@Getter
public class CdConvertDetail extends DomainObject {
	/**
	 * 会社ID
	 */
	private String cid;
    
    /**
    * コード変換コード
    */
    private ConvertCode convertCd;
    
    /**
    * 出力項目
    */
    private Optional<OutputItemCode> outputItem;
    
    /**
    * 本システムのコード
    */
    private String systemCd;
    
    /**
    * 行番号
    */
    private String lineNumber;

	public CdConvertDetail(String cid, String convertCd, String outputItem, String systemCd, String lineNumber) {
		super();
		this.cid = cid;
		this.convertCd = new ConvertCode(convertCd);
		this.outputItem = Optional.of(new OutputItemCode(outputItem));
		this.systemCd = systemCd;
		this.lineNumber = lineNumber;
		
	}
    
    
}
