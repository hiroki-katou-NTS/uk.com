/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.workfixed;


import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

/**
 * The Class WorkFixed.
 */
@Getter
//就業確定
public class WorkFixed extends AggregateRoot{
	
	/** The closure id. */
	// 締めID
	private Integer closureId;
	
	/** The confirm pid. */
	//確定者ID
	private String confirmPid;

	/** The wkp id. */
	//職場ID
	private String wkpId;
	
	/** The confirm cls status. */
	//確定区分
	private ConfirmClsStatus confirmClsStatus;
	
	/** The fixed date. */
	//確定日
	private GeneralDate fixedDate;
	
	/** The process date. */
	//処理年月
	private YearMonth processYm;
	
	/** The cid. */
    //会社ID
    private String cid;
	
	/**
	 * Instantiates a new work fixed.
	 *
	 * @param memento the memento
	 */
	public WorkFixed (WorkFixedGetMemento memento) {
		this.closureId = memento.getClosureId();
		this.confirmPid = memento.getConfirmPId();
		this.wkpId = memento.getWorkPlaceId();
		this.confirmClsStatus = memento.getConfirmClsStatus();
		this.fixedDate = memento.getFixedDate();
		this.processYm = memento.getProcessYm();
		this.cid = memento.getCid();
	}
	
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (WorkFixedSetMemento memento) {
		memento.setClosureId(this.getClosureId());;
		memento.setConfirmPId(this.confirmPid);;
		memento.setWorkplaceId(this.getWkpId());;
		memento.setConfirmClsStatus(this.getConfirmClsStatus());
		memento.setFixedDate(this.fixedDate);
		memento.setProcessYm(this.processYm);
		memento.setCid(this.cid);
	}


	/**
	 * Instantiates a new work fixed.
	 */
	public WorkFixed() {
		super();
	}

}
