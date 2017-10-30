/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.find.workrecord.workfixed;


import lombok.Builder;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.ConfirmClsStatus;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedSetMemento;

/* (non-Javadoc)
 * @see java.lang.Object#toString()
 */
@Builder

/**
 * Gets the process date.
 *
 * @return the process date
 */
@Getter
public class WorkFixedFinderDto implements WorkFixedSetMemento{
	
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
	private Integer processDate;

	/**
	 * Instantiates a new work fixed finder dto.
	 */
	public WorkFixedFinderDto() {
		super();
	}

	/**
	 * Instantiates a new work fixed finder dto.
	 *
	 * @param closureId the closure id
	 * @param confirmPid the confirm pid
	 * @param wkpId the wkp id
	 * @param confirmClsStatus the confirm cls status
	 * @param fixedDate the fixed date
	 * @param processDate the process date
	 */
	public WorkFixedFinderDto(Integer closureId, String confirmPid, String wkpId, ConfirmClsStatus confirmClsStatus,
			GeneralDate fixedDate, Integer processDate) {
		super();
		this.closureId = closureId;
		this.confirmPid = confirmPid;
		this.wkpId = wkpId;
		this.confirmClsStatus = confirmClsStatus;
		this.fixedDate = fixedDate;
		this.processDate = processDate;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedSetMemento#setClosureId(java.lang.Integer)
	 */
	@Override
	public void setClosureId(Integer closureId) {
		this.closureId = closureId;
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedSetMemento#setConfirmPId(java.lang.String)
	 */
	@Override
	public void setConfirmPId(String confirmPid) {
		this.confirmPid = confirmPid;
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedSetMemento#setWorkplaceId(java.lang.String)
	 */
	@Override
	public void setWorkplaceId(String wkpId) {
		this.wkpId = wkpId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedSetMemento#setConfirmClsStatus(nts.uk.ctx.at.record.dom.workrecord.workfixed.ConfirmClsStatus)
	 */
	@Override
	public void setConfirmClsStatus(ConfirmClsStatus confirmClsStatus) {
		this.confirmClsStatus = confirmClsStatus;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedSetMemento#setFixedDate(nts.arc.time.GeneralDate)
	 */
	@Override
	public void setFixedDate(GeneralDate fixedDate) {
		this.fixedDate = fixedDate;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixedSetMemento#setProcessDate(java.lang.Integer)
	 */
	@Override
	public void setProcessDate(Integer processDate) {
		this.processDate = processDate;
	}
		
}
