/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * 半日年休管理
 * The Class HalfDayManage.
 */
@Builder
public class HalfDayManage implements Serializable{

    /**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

    /** The manage type. */
    // 管理区分
    @Getter
    public ManageDistinct manageType;

    /** The reference. */
    // 参照先
    public MaxDayReference reference;

    /** The max number uniform company. */
    // 会社一律上限回数
    public AnnualNumberDay maxNumberUniformCompany;

    //端数処理区分
    public RoundProcessingClassification roundProcesCla;
}
