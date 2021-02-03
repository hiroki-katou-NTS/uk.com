/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.entity.dailyworkschedule;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class KfnmtPrintRemarkContPK.
 * @author HoangDD
 */
@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class KfnmtRptWkDaiOutnotePK implements Serializable {
	
	/** The Constant serialVersionUID. */
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The layout id. */
	@Column(name="LAYOUT_ID")
	private String layoutId;

	/** The print item. */
	@Column(name="PRINT_ITEM")
	private BigDecimal printItem;


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
		KfnmtRptWkDaiOutnotePK other = (KfnmtRptWkDaiOutnotePK) obj;

		if (printItem != other.printItem)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (printItem.intValue() ^ (printItem.intValue() >>> 32));
		return result;
	}
}