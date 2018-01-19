/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.classification.affiliate_ver1;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class BsymtAffClassHistory_Ver1.
 */
@Entity
@Table(name = "BSYMT_AFF_CLASS_HISTORY")
@NoArgsConstructor
public class BsymtAffClassHistory_Ver1 extends UkJpaEntity{

	/** The history id. */
	@Id
	@Column(name = "HISTORY_ID")
	public String historyId;
	
	/** The cid. */
	@Column(name = "CID")
	public String cid;
	
	/** The sid. */
	@Column(name = "SID")
	public String sid;
	
	/** The start date. */
	@Column(name = "START_DATE")
	public GeneralDate startDate;
	
	/** The end date. */
	@Column(name = "END_DATE")
	public GeneralDate endDate;
	
	/**
	 * Instantiates a new bsymt aff class history ver 1.
	 *
	 * @param historyId the history id
	 * @param cid the cid
	 * @param sid the sid
	 * @param startDate the start date
	 * @param endDate the end date
	 */
	public BsymtAffClassHistory_Ver1(String historyId, String cid, String sid,
			GeneralDate startDate, GeneralDate endDate) {
		super();
		this.historyId = historyId;
		this.cid = cid;
		this.sid = sid;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	/** The bsymt aff class hist item. */
	// Add by ThanhNC
	@OneToOne
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "HISTORY_ID", referencedColumnName = "HISTORY_ID") })
	public BsymtAffClassHistItem_Ver1 bsymtAffClassHistItem;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return historyId;
	}

}
