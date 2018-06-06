package nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive;

import lombok.AllArgsConstructor;
/**
 * 対象選択区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum SelectedAtr {
	AUTOMATIC(0,""),
	REQUEST(1, ""),
	MANUAL(2, "");
	public final Integer value;
	public final String name;
}
