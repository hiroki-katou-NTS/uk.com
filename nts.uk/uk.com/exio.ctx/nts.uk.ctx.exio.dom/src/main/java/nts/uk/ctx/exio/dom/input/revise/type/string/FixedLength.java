package nts.uk.ctx.exio.dom.input.revise.type.string;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportRowNumber;

/**
 * 
 * 固定長編集
 *
 */
@Getter
@AllArgsConstructor
public class FixedLength {
	
	/** 桁長 */
	private ExternalImportRowNumber length;
	
	/** 編集方法 */
	private FixedLengthReviseMethod reviseMethod;
	
	public String fix(String target) {
		if(this.length.v() < target.length()) {
			// 「編集値」の前部から「コード編集桁」分を切り出して「編集値」とする
			return target.substring(this.length.v());
		}
		else {
			// 編集方法に従って桁数を補完する
			return this.reviseMethod.complement(target, length.v());
		}
	}
}
