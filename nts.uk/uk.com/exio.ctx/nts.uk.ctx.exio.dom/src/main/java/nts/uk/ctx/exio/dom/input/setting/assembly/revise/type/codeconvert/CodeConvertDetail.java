package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type.codeconvert;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * コード変換詳細
 */
@Getter
public class CodeConvertDetail extends DomainObject {
	
	/** 出力項目 */
	private CodeConvertValue targetCode;
	
	/** 本システムのコード */
	private CodeConvertValue systemCode;
	
	public CodeConvertDetail(String targetCode, String systemCode) {
		super();
		this.targetCode = new CodeConvertValue(targetCode);
		this.systemCode = new CodeConvertValue(systemCode);
	}
}
