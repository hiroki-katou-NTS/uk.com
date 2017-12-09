/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.employment.history;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class BsymtEmploymentHistItem.
 */
@Entity
@AllArgsConstructor
@Table(name = "BSYMT_EMPLOYMENT_HIS_ITEM")
public class BsymtEmploymentHistItem extends UkJpaEntity implements Serializable {

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
	@Column(name = "EMP_CD")
	public String empCode;
	
	/** The empCode.
	 * 1 = dailySalary - 日給
	 * 2 = dailyMonthlySalary - 日給月給
	 * 3 = hourlySalary - 時間給
	 * 4 = monthlySalary - 月給
	 *  */
	@Basic(optional = false)
	@Column(name = "SALARY_SEGMENT")
	public int salarySegment;

	/**
	 * Instantiates a new cempt employment.
	 */
	public BsymtEmploymentHistItem() {
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
