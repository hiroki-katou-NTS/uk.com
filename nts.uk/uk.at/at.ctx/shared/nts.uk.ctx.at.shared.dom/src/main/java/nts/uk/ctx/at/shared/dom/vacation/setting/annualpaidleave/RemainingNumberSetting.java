/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.io.Serializable;

import lombok.Builder;

/**
 * The Class RemainingNumberSetting.
 */
// 残数設定
@Builder
public class RemainingNumberSetting implements Serializable{

    /**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/** The retention year. */
    // 保持年数
    public RetentionYear retentionYear;
}
