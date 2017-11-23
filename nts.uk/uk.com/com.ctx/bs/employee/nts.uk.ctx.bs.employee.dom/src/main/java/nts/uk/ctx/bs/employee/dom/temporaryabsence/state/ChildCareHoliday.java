/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.temporaryabsence.state;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;

/**
 * @author danpv
 * Domain Name : 育児休業
 */
public class ChildCareHoliday extends TempAbsenceHisItem{
	
	/**
	 * Optional
	 * 同一家族の休業有無
	 */
	private Boolean sameFamily;
	
	/**
	 * Optional
	 * 子の区分
	 */
	private Integer childType;
	
	/**
	 * Optional
	 * 家族メンバーId Family member id
	 */
	private String familyMemberId;
	
	/**
	 * Optional
	 * 縁組成立の年月日
	 */
	private GeneralDate createDate;
	
	/**
	 * Optional
	 * 配偶者が休業しているか
	 */
	private Boolean spouseIsLeave; 
	
	/**
	 * @param sameFamily
	 * @param childType
	 * @param familyMemberId
	 * @param createDate
	 * @param spouseIsLeave
	 */
	public ChildCareHoliday(Boolean sameFamily, Integer childType, String familyMemberId, GeneralDate createDate,
			Boolean spouseIsLeave) {
		super();
		this.sameFamily = sameFamily;
		this.childType = childType;
		this.familyMemberId = familyMemberId;
		this.createDate = createDate;
		this.spouseIsLeave = spouseIsLeave;
	}

	public Boolean getSameFamily() {
		return sameFamily;
	}

	public Integer getChildType() {
		return childType;
	}

	public String getFamilyMemberId() {
		return familyMemberId;
	}

	public GeneralDate getCreateDate() {
		return createDate;
	}

	public Boolean getSpouseIsLeave() {
		return spouseIsLeave;
	}

}
