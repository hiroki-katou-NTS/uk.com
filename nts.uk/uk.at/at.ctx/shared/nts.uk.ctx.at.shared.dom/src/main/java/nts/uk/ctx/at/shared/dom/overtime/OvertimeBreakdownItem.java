/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.overtime;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class OvertimeBreakdownItem.
 */
// 時間外超過の内訳項目
@Getter
public class OvertimeBreakdownItem extends DomainObject{
	
	/** The use classification. */
	// 使用区分
	private UseClassification useClassification;
	
	/** The be breakdown item no. */
	// 内訳項目NO
	private BreakdownItemNo breakdownItemNo;
	
	/** The name. */
	// 名称
	private BreakdownItemName name;
	
	/** The product number. */
	// 積上番号
	private ProductNumber productNumber;

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((breakdownItemNo == null) ? 0 : breakdownItemNo.hashCode());
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
		OvertimeBreakdownItem other = (OvertimeBreakdownItem) obj;
		if (breakdownItemNo != other.breakdownItemNo)
			return false;
		return true;
	}

}
