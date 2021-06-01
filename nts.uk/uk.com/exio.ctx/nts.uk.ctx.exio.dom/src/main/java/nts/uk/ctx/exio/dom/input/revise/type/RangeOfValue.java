package nts.uk.ctx.exio.dom.input.revise.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.exio.dom.input.setting.source.ExternalImportRawNumber;

/**
 * 
 * 値の有効範囲
 *
 */
@Getter
@AllArgsConstructor
public class RangeOfValue {
	
	/** 開始桁数 */
	private ExternalImportRawNumber startRaw;
	
	/** 終了桁数 */
	private ExternalImportRawNumber endRaw;
	
	/**
	 * 値の抽出
	 * @param target
	 * @return
	 */
	public String extract(String target) {
		return target.substring(this.startRaw.v(), this.endRaw.v());
	}
	
	/**
	 * 値の抽出
	 * @param target
	 * @return
	 */
	public int extract(int target) {
		return Integer.parseInt(
				String.valueOf(target).substring(this.startRaw.v(), this.endRaw.v()));
	}
}
