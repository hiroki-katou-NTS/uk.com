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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class BsymtAffClassHistItem_Ver1.
 */
@Entity
@Table(name = "BSYMT_AFF_CLASS_HIS_ITEM")
@NoArgsConstructor
public class BsymtAffClassHistItem_Ver1 extends UkJpaEntity {

	/** The history id. */
	@Id
	@Column(name = "HISTORY_ID")
	public String historyId;

	/** The sid. */
	@Column(name = "SID")
	public String sid;

	/** The classification code. */
	@Column(name = "CLASSIFICATION_CODE")
	public String classificationCode;
	
	/**
	 * Instantiates a new bsymt aff class hist item ver 1.
	 *
	 * @param historyId the history id
	 * @param sid the sid
	 * @param classificationCode the classification code
	 */
	public BsymtAffClassHistItem_Ver1(String historyId, String sid, String classificationCode) {
		super();
		this.historyId = historyId;
		this.sid = sid;
		this.classificationCode = classificationCode;
	}
	
	/** The bsymt aff class history. */
	// Add by ThanhNC
	@OneToOne
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "HISTORY_ID", referencedColumnName = "HISTORY_ID") })
	public BsymtAffClassHistory_Ver1 bsymtAffClassHistory;

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return historyId;
	}

}
