/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.overtime.breakdown;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.overtime.ProductNumber;
import nts.uk.ctx.at.shared.dom.overtime.UseClassification;

/**
 * The Class OvertimeBreakdownItem.
 */
// 時間外超過の内訳項目
@Getter
public class OvertimeBRDItem extends DomainObject{
	
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
	
	/** The attendance item ids. */
	// 集計項目一覧
	private List<Integer> attendanceItemIds;

	
	/**
	 * Instantiates a new overtime BRD item.
	 *
	 * @param mement the mement
	 */
	public OvertimeBRDItem(OvertimeBRDItemGetMemento memento) {
		this.useClassification = memento.getUseClassification();
		this.breakdownItemNo = memento.getBreakdownItemNo();
		this.name = memento.getName();
		this.productNumber = memento.getProductNumber();
		this.attendanceItemIds = memento.getAttendanceItemIds();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OvertimeBRDItemSetMemento memento){
		memento.setUseClassification(this.useClassification);
		memento.setBreakdownItemNo(this.breakdownItemNo);
		memento.setName(this.name);
		memento.setProductNumber(this.productNumber);
		memento.setAttendanceItemIds(this.attendanceItemIds);
	}
	
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
		OvertimeBRDItem other = (OvertimeBRDItem) obj;
		if (breakdownItemNo != other.breakdownItemNo)
			return false;
		return true;
	}

}
