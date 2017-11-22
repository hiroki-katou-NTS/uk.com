/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.temporaryabsence.state;

import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;

/**
 * @author danpv
 * Domain Name : 介護休業
 *
 */
public class CareHoliday extends TempAbsenceHisItem{
	
	/**
	 * 同一家族の休業有無
	 */
	private Boolean sameFamily;
	
	/**
	 * 同一家族の短時間勤務日数
	 */
	private Integer sameFamilyDays;
	
	/**
	 * 家族メンバーId Family member id
	 */
	private String familyMemberId;
	
	
	
	

	public Boolean getSameFamily() {
		return sameFamily;
	}

	public Integer getSameFamilyDays() {
		return sameFamilyDays;
	}

	public String getFamilyMemberId() {
		return familyMemberId;
	}

}
