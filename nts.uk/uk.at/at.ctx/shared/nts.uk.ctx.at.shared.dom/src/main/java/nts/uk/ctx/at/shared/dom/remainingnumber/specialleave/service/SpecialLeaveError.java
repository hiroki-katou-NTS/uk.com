package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import lombok.AllArgsConstructor;

/**
 * 特別休暇エラー
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum SpecialLeaveError {
	/**
	 * 特別休暇残数不足エラー(付与前)
	 */
	BEFOREGRANT(0),
	/**
	 * 特別休暇残数不足エラー(付与後)
	 */
	AFTERGRANT(1),
	/**
	 * 特別休暇期間外利用エラー
	 */
	OUTOFUSE(2);
	public final int value;
}
