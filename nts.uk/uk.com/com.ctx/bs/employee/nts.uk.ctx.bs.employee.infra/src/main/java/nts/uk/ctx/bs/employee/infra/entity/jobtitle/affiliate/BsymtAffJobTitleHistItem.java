/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.jobtitle.affiliate;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class BsymtAffJobTitleHistItem.
 *   所属職位履歴項目
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "BSYMT_AFF_JOB_HIST_ITEM")
public class BsymtAffJobTitleHistItem extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The historyid -  PK. */
	@Id
	@Column(name = "HISTORY_ID")
	private String hisId;

	/** The employeeId. */
	@Basic(optional = false)
	@Column(name = "SID")
	private String sid;
	
	/** The empCode. */
	@Basic(optional = false)
	@Column(name = "JOB_TITLE_ID")
	private String jobTitleId;
	
	@Basic(optional = false)
	@Column(name = "NOTE")
	private String note;

	/**
	 * Instantiates a new cempt employment.
	 */
	public BsymtAffJobTitleHistItem() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.hisId;
	}

	


}
