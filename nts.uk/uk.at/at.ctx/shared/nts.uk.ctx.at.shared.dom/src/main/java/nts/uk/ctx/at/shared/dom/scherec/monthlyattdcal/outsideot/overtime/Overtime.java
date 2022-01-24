/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;

/**
 * The Class Overtime.
 */
// 超過時間
@Getter
public class Overtime extends DomainObject implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** The super holiday 60 H occurs. */
	// 60H超休が発生する
	private boolean superHoliday60HOccurs;
	
	/** The use classification. */
	// 使用区分
	private UseClassification useClassification;
	
	/** The name. */
	//名称
	private OvertimeName name;

	/** The overtime. */
	// 超過時間
	private OvertimeValue overtime;
	
	/** The overtime no. */
	// 超過時間NO
	private OvertimeNo overtimeNo;
	
	/**
	 * Instantiates a new overtime.
	 */
	public Overtime(boolean superHoliday60HOccurs, UseClassification useClassification, 
			OvertimeName name, OvertimeValue overtime, OvertimeNo overtimeNo) {
		
		this.superHoliday60HOccurs = superHoliday60HOccurs;
		this.useClassification = useClassification;
		this.name = name;
		this.overtime = overtime;
		this.overtimeNo = overtimeNo;
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
		result = prime * result + ((overtimeNo == null) ? 0 : overtimeNo.hashCode());
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
		Overtime other = (Overtime) obj;
		if (overtimeNo != other.overtimeNo)
			return false;
		return true;
	}
	/**
	 * 	[1] 超過時間に対応する月次の勤怠項目を取得する
	 * @return
	 */
	public List<Integer> getMonthlyAttendanceIdByNo() {
		switch(this.overtimeNo.value) {
		case 1:
			return Arrays.asList(536,537,538,539,540,541,542,543,544,545);
		case 2: 
			return Arrays.asList(546,547,548,549,550,551,552,553,554,555);
		case 3: 
			return Arrays.asList(556,557,558,559,560,561,562,563,564,565);
		case 4: 
			return Arrays.asList(566,567,568,569,570,571,572,573,574,575);
		default : //5
			return Arrays.asList(576,577,578,579,580,581,582,583,584,585);
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
