/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.employment;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * Gets the name.
 *
 * @return the name
 */
@Getter

/**
 * Sets the name.
 *
 * @param name the new name
 */
@Setter
@Entity
@Table(name = "CEMPT_EMPLOYMENT")
public class CemptEmployment extends UkJpaEntity implements Serializable {


	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	protected CemptEmploymentPK cemptEmploymentPK;
	
	/** The salary closure id. */
	@Column(name="SALARY_CLOSURE_ID")
	private Integer salaryClosureId;
	
	/** The work closure id. */
	@Column(name="WORK_CLOSURE_ID")
	private Integer workClosureId;
	
	/** The name. */
	@Basic(optional = false)
	@Column(name="NAME")
	private String name;
	
	
	/**
	 * Instantiates a new cempt employment.
	 */
	public CemptEmployment() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((cemptEmploymentPK == null) ? 0 : cemptEmploymentPK.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((salaryClosureId == null) ? 0 : salaryClosureId.hashCode());
		result = prime * result + ((workClosureId == null) ? 0 : workClosureId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		CemptEmployment other = (CemptEmployment) obj;
		if (cemptEmploymentPK == null) {
			if (other.cemptEmploymentPK != null)
				return false;
		} else if (!cemptEmploymentPK.equals(other.cemptEmploymentPK))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (salaryClosureId == null) {
			if (other.salaryClosureId != null)
				return false;
		} else if (!salaryClosureId.equals(other.salaryClosureId))
			return false;
		if (workClosureId == null) {
			if (other.workClosureId != null)
				return false;
		} else if (!workClosureId.equals(other.workClosureId))
			return false;
		return true;
	}

	
}
