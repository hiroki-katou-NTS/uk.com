/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.entity.dailyworkschedule;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class KfnmtRptWkDaiOutnotePK.
 * @author LienPTK
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class KfnmtRptWkDaiOutatdPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The layout id. */
	@Column(name="LAYOUT_ID")
	private String layoutId;

	/** The order no. */
	@Column(name="ORDER_NO")
	private long orderNo;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (orderNo ^ (orderNo >>> 32));
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
		KfnmtRptWkDaiOutatdPK other = (KfnmtRptWkDaiOutatdPK) obj;
		if (layoutId == null) {
			if (other.layoutId != null)
				return false;
		} else if (!layoutId.equals(other.layoutId))
			return false;
		if (orderNo != other.orderNo)
			return false;
		return true;
	}
}