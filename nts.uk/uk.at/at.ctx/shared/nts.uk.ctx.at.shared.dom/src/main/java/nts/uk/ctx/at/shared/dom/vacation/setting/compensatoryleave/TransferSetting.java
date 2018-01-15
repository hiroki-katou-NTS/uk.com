/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.Getter;

/**
 * The Class TransferSetting.
 */
// 代休振替設定
@Getter
public class TransferSetting {
    
	//一定時間
	/** The certain time. */
	private OneDayTime certainTime;
	
	//使用区分
	/** The use division. */
	private boolean useDivision;
	
	//指定時間.1日の時間
	/** The one day time. */
	private OneDayTime oneDayTime;
	
	//指定時間.半日の時間
	/** The half day time. */
	private OneDayTime halfDayTime;
	
	//振替区分
	/** The transfer division. */
	private TransferSettingDivision transferDivision;
	
	/**
	 * Instantiates a new compensatory transfer setting.
	 *
	 * @param menento the menento
	 */
	public TransferSetting(TransferSettingGetMemento memento) {
		this.certainTime = memento.getCertainTime();
		this.useDivision = memento.isUseDivision();
		this.transferDivision = memento.getTransferDivision();
		this.oneDayTime = memento.getOneDayTime();
		this.halfDayTime = memento.getHalfDayTime();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(TransferSettingSetMemento memento) {
		memento.setCertainTime(this.certainTime);
		memento.setOneDayTime(this.oneDayTime);
		memento.setHalfDayTime(this.halfDayTime);
		memento.setTransferDivision(this.transferDivision);
		memento.setUseDivision(this.useDivision);
	}
}
