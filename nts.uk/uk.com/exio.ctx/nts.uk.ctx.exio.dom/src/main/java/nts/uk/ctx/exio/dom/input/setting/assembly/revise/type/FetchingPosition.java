package nts.uk.ctx.exio.dom.input.setting.assembly.revise.type;

import lombok.Value;

/**
 * 値の読み取り位置
 */
@Value
public class FetchingPosition {
	
	/** 開始位置 */
	private FetchingDigit start;
	
	/** 読み取る長さ */
	private FetchingDigit length;
	
	/**
	 * 値の抽出
	 * @param target
	 * @return
	 */
	public String extract(String target) {
		int end = start.v() + length.v();
		return target.substring(start.v(), end);
	}
}
