/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.DomainObject;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumExtra60HRate;

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
	
	/** The premium extra 60 H rates. */
	// 換算率: 60H超休換算率
	@Setter
	private List<PremiumExtra60HRate> premiumExtra60HRates;
	
	/** The Constant MAX_SIZE_ATTENDANCE_ITEM. */
	public static final int MAX_SIZE_ATTENDANCE_ITEM = 100;
	
	/** The Constant SIZE_ONE. */
	public static final int SIZE_ONE = 1;
	
	/**
	 * Instantiates a new outside OTBRD item.
	 *
	 * @param memento the memento
	 */
	public OutsideOTBRDItem(UseClassification useCls, BreakdownItemNo breakdownItemNo,
			BreakdownItemName name, ProductNumber productNumber,
			List<Integer> attendanceItemIds, List<PremiumExtra60HRate> premiumExtra60HRates) {
		this.useClassification = useCls;
		this.breakdownItemNo = breakdownItemNo;
		this.name = name;
		this.productNumber = productNumber;
		this.attendanceItemIds = attendanceItemIds;
		this.premiumExtra60HRates = premiumExtra60HRates;
		
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
	
	/**
	 * 	[1] 時間外超過の内訳に対応する月次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceIdByNo() {
		switch(this.breakdownItemNo.value) {
		case 1:
			return Arrays.asList(536,546,556,566,576,2069);
		case 2: 
			return Arrays.asList(537,547,557,567,577,2070);
		case 3: 
			return Arrays.asList(538,548,558,568,578,2071);
		case 4: 
			return Arrays.asList(539,549,559,569,579,2072);
		case 5:
			return Arrays.asList(540,550,560,570,580,2073);
		case 6: 
			return Arrays.asList(541,551,561,571,581,2074);
		case 7: 
			return Arrays.asList(542,552,562,572,582,2075);
		case 8: 
			return Arrays.asList(543,553,563,573,583,2076);
		case 9:
			return Arrays.asList(544,554,564,574,584,2077);
		default : //10
			return Arrays.asList(545,555,565,575,585,2078);
		}
	}
	
	/**
	 * 	[2] 利用できない月次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceIdNotAvailable() {
		if(this.useClassification == UseClassification.UseClass_NotUse) {
			return this.getMonthlyAttendanceIdByNo();
		}
		return new ArrayList<>();
	}

}
