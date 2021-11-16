package nts.uk.ctx.exio.dom.input.setting.assembly.revise.codeconvert;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * コード変換詳細
 */
@Getter
public class CodeConvertDetail extends DomainObject {
	
	/** 前 */
	private CodeConvertValue before;
	
	/** 後 */
	private CodeConvertValue after;
	
	public CodeConvertDetail(String before, String after) {
		super();
		this.before = new CodeConvertValue(before);
		this.after = new CodeConvertValue(after);
	}
}
