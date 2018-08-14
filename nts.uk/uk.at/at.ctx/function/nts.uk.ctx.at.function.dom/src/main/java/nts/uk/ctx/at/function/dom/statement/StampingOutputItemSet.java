/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.dom.statement;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class StampingOutputItemSet.
 */
// 打刻一覧出力項目設定
@Getter
public class StampingOutputItemSet extends AggregateRoot{
	
	/** The company ID. */
	// 会社ID
	private CompanyId companyID;
	
	/** The stamp output set code. */
	// コード
	private StampOutputSettingCode stampOutputSetCode;
	
	/** The stamp output set name. */
	// 名称
	private StampOutputSettingName stampOutputSetName;
	
	/** The output emboss method. */
	// 打刻方法の出力
	private boolean outputEmbossMethod;
	
	/** The output work hours. */
	// 就業時間帯の出力
	private boolean outputWorkHours; 
	
	/** The output set location. */
	// 設定場所の出力
	private boolean outputSetLocation;
	
	/** The output pos infor. */
	// 位置情報の出力
	private boolean outputPosInfor;
	
	/** The output OT. */
	// 残業時間の出力
	private boolean outputOT;
	
	/** The output night time. */
	// 深夜時間の出力
	private boolean outputNightTime;
	
	/** The output support card. */
	// 応援カードの出力
	private boolean outputSupportCard;
	
	/**
	 * Instantiates a new stamping output item set.
	 *
	 * @param memento the memento
	 */
	public StampingOutputItemSet(StampingOutputItemSetGetMemento memento) {
		this.companyID = memento.getCompanyID();
		this.stampOutputSetCode = memento.getStampOutputSetCode();
		this.stampOutputSetName = memento.getStampOutputSetName();
		this.outputEmbossMethod = memento.getOutputEmbossMethod();
		this.outputWorkHours = memento.getOutputWorkHours();
		this.outputSetLocation = memento.getOutputSetLocation();
		this.outputPosInfor = memento.getOutputPosInfor();
		this.outputOT = memento.getOutputOT();
		this.outputNightTime = memento.getOutputNightTime();
		this.outputSupportCard = memento.getOutputSupportCard();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(StampingOutputItemSetSetMemento memento) {
		memento.setCompanyID(this.companyID);
		memento.setStampOutputSetCode(this.stampOutputSetCode);
		memento.setStampOutputSetName(this.stampOutputSetName);
		memento.setOutputEmbossMethod(this.outputEmbossMethod);
		memento.setOutputWorkHours(this.outputWorkHours);
		memento.setOutputSetLocation(this.outputSetLocation);
		memento.setOutputPosInfor(this.outputPosInfor);
		memento.setOutputOT(this.outputOT);
		memento.setOutputNightTime(this.outputNightTime);
		memento.setOutputSupportCard(this.outputSupportCard);
	}

}
