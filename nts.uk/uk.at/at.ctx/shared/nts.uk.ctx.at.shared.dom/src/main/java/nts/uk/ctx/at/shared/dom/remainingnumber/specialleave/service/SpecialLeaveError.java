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
	 * 特別休暇不足エラー(付与前)
	 */
	BEFOREGRANT(0),
	/**
	 * 特別休暇不足エラー(付与後)
	 */
	AFTERGRANT(1),
	/**
	 * 時間特別休暇不足エラー(付与前)
	 */
	BEFOREGRANTTIME(2),
	/**
	 * 時間特別休暇不足エラー(付与後)
	 */
	AFTERGRANTTIME(3);



	public final int value;
}
