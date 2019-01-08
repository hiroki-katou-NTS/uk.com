package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import lombok.AllArgsConstructor;

/**
 * 振休エラー
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum PauseError {
	/**
	 * 振休残数不足エラー
	 */
	PAUSEREMAINNUMBER(0),
	/**
	 * 相殺できないエラー
	 */
	OFFSETNUMBER(1);
	public final Integer value;
}
