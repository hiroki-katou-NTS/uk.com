package nts.uk.ctx.exio.dom.input.revise.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.csvimport.ExternalImportRowNumber;

/**
 * 
 * 値の有効範囲
 *
 */
@Getter
@AllArgsConstructor
public class RangeOfValue {
	
	/** 開始桁数 */
	private ExternalImportRowNumber start;
	
	/** 終了桁数 */
	private ExternalImportRowNumber end;
	
	/**
	 * 値の抽出
	 * @param target
	 * @return
	 */
	public String extract(String target) {
		return target.substring(this.start.v(), this.end.v());
	}
}
