package nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive;

import lombok.AllArgsConstructor;
/**
 * 対象選択区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum SelectedAtr {
	/**
	 * 自動
	 */
	AUTOMATIC(0,"自動"),
	/**
	 * 申請
	 */
	REQUEST(1, "申請"),
	/**
	 * 手動
	 */
	MANUAL(2, "手動");
	public final Integer value;
	public final String name;
}
