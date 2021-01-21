/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;

/**
 * The Class Outside overtime breakdown item.
 */
// 時間外超過の内訳項目
@Getter
public class OutsideOTBRDItem extends DomainObject implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
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
	
	/** The Constant MAX_SIZE_ATTENDANCE_ITEM. */
	public static final int MAX_SIZE_ATTENDANCE_ITEM = 100;
	
	/** The Constant SIZE_ONE. */
	public static final int SIZE_ONE = 1;

	
	/**
	 * Instantiates a new outside OTBRD item.
	 *
	 * @param memento the memento
	 */
	public OutsideOTBRDItem(OutsideOTBRDItemGetMemento memento) {
		this.useClassification = memento.getUseClassification();
		this.breakdownItemNo = memento.getBreakdownItemNo();
		this.name = memento.getName();
		this.productNumber = memento.getProductNumber();
		this.attendanceItemIds = memento.getAttendanceItemIds();
		
		// validate domain
		if (this.isOverlapAttendanceItemId()) {
			throw new BusinessException("Msg_487");
		}
		if (!CollectionUtil.isEmpty(this.attendanceItemIds)
				&& this.attendanceItemIds.size() > MAX_SIZE_ATTENDANCE_ITEM) {
			throw new BusinessException("Msg_489");
		}
	}
	
	/**
	 * Checks if is overlap attendance item id.
	 *
	 * @return true, if is overlap attendance item id
	 */
	private boolean isOverlapAttendanceItemId() {
		if (this.useClassification == UseClassification.UseClass_NotUse) {
			return false;
		}
		if (CollectionUtil.isEmpty(this.attendanceItemIds)) {
			return false;
		}

		for (int item : this.attendanceItemIds) {
			if (Collections.frequency(this.attendanceItemIds, item) > SIZE_ONE) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if is use class.
	 *
	 * @return the boolean
	 */
	public Boolean isUseClass() {
		return this.useClassification == UseClassification.UseClass_Use;
	}
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OutsideOTBRDItemSetMemento memento){
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
		OutsideOTBRDItem other = (OutsideOTBRDItem) obj;
		if (breakdownItemNo != other.breakdownItemNo)
			return false;
		return true;
	}

}
