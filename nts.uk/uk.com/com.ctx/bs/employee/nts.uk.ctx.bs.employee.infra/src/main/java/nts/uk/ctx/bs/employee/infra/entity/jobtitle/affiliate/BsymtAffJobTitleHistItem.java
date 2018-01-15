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
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class BsymtAffJobTitleHistItem.
 *   所属職位履歴項目
 */
@Entity
@AllArgsConstructor
@Table(name = "BSYMT_AFF_JOB_HIST_ITEM")
public class BsymtAffJobTitleHistItem extends UkJpaEntity implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The historyid -  PK. */
	@Id
	@Column(name = "HISTORY_ID")
	public String hisId;

	/** The employeeId. */
	@Basic(optional = false)
	@Column(name = "SID")
	public String sid;
	
	/** The empCode. */
	@Basic(optional = false)
	@Column(name = "JOB_TITLE_ID")
	public String jobTitleId;
	
	@Basic(optional = false)
	@Column(name = "NOTE")
	public String note;

	/**
	 * Instantiates a new cempt employment.
	 */
	public BsymtAffJobTitleHistItem() {
		super();
	}

	@Override
	protected Object getKey() {
		return this.hisId;
	}

}
