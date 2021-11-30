/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeDomainObject;

/**
 * 遅刻早退時間の扱い
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TreatLateEarlyTime extends WorkTimeDomainObject implements Cloneable{

	/** 就業時間に含める */
	private boolean include = false;
	/** 申請により取り消した場合も含める */
	private boolean includeByApp = false;

	/**
	 * Instantiates a new em timezone late early common set.
	 *
	 * @param memento
	 *            the memento
	 */
	public TreatLateEarlyTime(EmTimezoneLateEarlyCommonSetGetMemento memento) {
		this.include = !memento.getDelFromEmTime();
		this.includeByApp = memento.getIncludeByApp();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(EmTimezoneLateEarlyCommonSetSetMemento memento) {
		memento.setDelFromEmTime(!this.include);
		memento.setIncludeByApp(this.includeByApp);
	}
	
	@Override
	public TreatLateEarlyTime clone() {
		TreatLateEarlyTime cloned = new TreatLateEarlyTime();
		try {
			cloned.include = this.include;
			cloned.includeByApp = this.includeByApp;
		}
		catch (Exception e){
			throw new RuntimeException("TreatLateEarlyTime clone error.");
		}
		return cloned;
	}
	
	/**
	 * ファクトリー
	 * @param include 就業時間に含める
	 * @param includeByApp 申請により取り消した場合に含める
	 * @return 遅刻早退時間の扱い
	 */
	public static TreatLateEarlyTime createFromJavaType(int include, int includeByApp){
		TreatLateEarlyTime myClass = new TreatLateEarlyTime();
		myClass.include = (include == 1);
		myClass.includeByApp = (includeByApp == 1);
		return myClass;
	}
	
	/**
	 * 遅刻早退を就業時間に含めるか判断する
	 * @return true:含める,false:含めない
	 */
	public boolean isIncludeLateEarlyInWorkTime() {
		return this.include;
	}
}
