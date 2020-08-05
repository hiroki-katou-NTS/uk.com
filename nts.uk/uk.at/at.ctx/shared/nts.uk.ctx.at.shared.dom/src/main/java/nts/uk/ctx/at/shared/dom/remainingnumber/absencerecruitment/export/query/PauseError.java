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
	OFFSETNUMBER(1),
	
	/**
	 * 先取り制限エラー
	 */
	PREFETCH_ERROR(2);
	public final Integer value;
}
