/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.shift.autocalsetting;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class AutoCalUseUnitSetting.
 */
// 時間外の自動計算の利用単位設定
@Getter
public class UseUnitAutoCalSetting extends DomainObject {

	/** The job. */
	// 職位の自動計算設定をする
	private UseClassification useJobSet;

	/** The workplace. */
	// 職場の自動計算設定をする
	private UseClassification useWkpSet;

	/** The jobwkp. */
	// 職場・職位の自動計算設定を行う
	private UseClassification useJobwkpSet;

}
