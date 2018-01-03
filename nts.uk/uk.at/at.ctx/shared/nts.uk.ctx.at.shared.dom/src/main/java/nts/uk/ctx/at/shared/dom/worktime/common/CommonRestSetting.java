/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class CommonRestSetting.
 */
//共通の休憩設定
@Getter
@AllArgsConstructor
public class CommonRestSetting extends DomainObject{
	
	/** The calculate method. */
	//休憩時間中に退勤した場合の計算方法
	private RestTimeOfficeWorkCalcMethod calculateMethod;

	/**
	 * Instantiates a new common rest setting.
	 *
	 * @param memento the memento
	 */
	public CommonRestSetting(CommonRestSettingGetmemento memento) {
		this.calculateMethod = memento.getCalculateMethod();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(CommonRestSettingSetmemento memento) {
		memento.setCalculateMethod(this.calculateMethod);
	}
}
