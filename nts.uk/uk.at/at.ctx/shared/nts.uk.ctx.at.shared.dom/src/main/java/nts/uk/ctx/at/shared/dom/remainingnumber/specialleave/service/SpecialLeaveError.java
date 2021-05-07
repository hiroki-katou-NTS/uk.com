package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
