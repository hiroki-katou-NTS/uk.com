/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.ot.autocalsetting.wkpjob;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class AutoCalSettingDto.
 */

/**
 * Gets the cal atr.
 *
 * @return the cal atr
 */

/**
 * Gets the cal atr.
 *
 * @return the cal atr
 */
@Getter

/**
 * Sets the cal atr.
 *
 * @param calAtr
 *            the new cal atr
 */

/**
 * Sets the cal atr.
 *
 * @param calAtr
 *            the new cal atr
 */

/**
 * Sets the cal atr.
 *
 * @param calAtr
 *            the new cal atr
 */
@Setter
public class AutoCalSettingDto {

	/** The up limit ot set. */
	// 上限残業時間の設定
	private Integer upLimitOtSet;

	/** The cal atr. */
	/// 計算区分
	private Integer calAtr;

	/**
	 * Instantiates a new auto cal setting dto.
	 *
	 * @param upLimitOtSet
	 *            the up limit ot set
	 * @param calAtr
	 *            the cal atr
	 */
	public AutoCalSettingDto(Integer upLimitOtSet, Integer calAtr) {
		super();
		this.upLimitOtSet = upLimitOtSet;
		this.calAtr = calAtr;
	}

}
