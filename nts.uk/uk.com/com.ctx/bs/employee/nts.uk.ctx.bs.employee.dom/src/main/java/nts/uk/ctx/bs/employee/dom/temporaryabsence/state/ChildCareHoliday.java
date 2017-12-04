/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.temporaryabsence.state;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;

/**
 * @author danpv Domain Name : 育児休業
 */
@Getter
public class ChildCareHoliday extends TempAbsenceHisItem {

	/**
	 * Optional 同一家族の休業有無
	 */
	private Boolean sameFamily;

	/**
	 * Optional 子の区分
	 */
	private Integer childType;

	/**
	 * Optional 家族メンバーId Family member id
	 */
	private String familyMemberId;

	/**
	 * Optional 縁組成立の年月日
	 */
	private GeneralDate createDate;

	/**
	 * Optional 配偶者が休業しているか
	 */
	private Boolean spouseIsLeave;

	/**
	 * @param sameFamily
	 * @param childType
	 * @param familyMemberId
	 * @param createDate
	 * @param spouseIsLeave
	 */
	private ChildCareHoliday(String historyId, String employeeId, GenericString remarks, Integer soInsPayCategory,
			Boolean sameFamily, Integer childType, String familyMemberId, GeneralDate createDate,
			Boolean spouseIsLeave) {
		super(LeaveHolidayType.CHILD_CARE_NURSING, historyId, employeeId, remarks, soInsPayCategory);
		this.sameFamily = sameFamily;
		this.childType = childType;
		this.familyMemberId = familyMemberId;
		this.createDate = createDate;
		this.spouseIsLeave = spouseIsLeave;
	}

	public static ChildCareHoliday init(String historyId, String employeeId, String remarks,
			Integer soInsPayCategory, Boolean sameFamily, Integer childType, String familyMemberId,
			GeneralDate createDate, Boolean spouseIsLeave) {
		return new ChildCareHoliday(historyId, employeeId, new GenericString(remarks), soInsPayCategory, sameFamily,
				childType, familyMemberId, createDate, spouseIsLeave);

	}

}
