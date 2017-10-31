/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.department;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class AffiliationDepartment. - 所属部門
 */
@Getter
public class AffiliationDepartment extends AggregateRoot {
	
	/** The id. */
	private String id;
	
	/** The period. */
	private DatePeriod period;
	
	/** The employee id. */
	private String employeeId;
	
	/** The department id. */
	private String departmentId;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AffiliationDepartment other = (AffiliationDepartment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
