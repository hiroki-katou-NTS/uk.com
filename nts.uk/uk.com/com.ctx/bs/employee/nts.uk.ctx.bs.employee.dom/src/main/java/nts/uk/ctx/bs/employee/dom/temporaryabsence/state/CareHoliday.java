/**
 * 
 */
package nts.uk.ctx.bs.employee.dom.temporaryabsence.state;

import lombok.Getter;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;

/**
 * @author danpv Domain Name : 介護休業
 *
 */
@Getter
public class CareHoliday extends TempAbsenceHisItem {

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

	public CareHoliday() {

	}

	/**
	 * @param sameFamily
	 * @param sameFamilyDays
	 * @param familyMemberId
	 */
	private CareHoliday(String historyId, String employeeId, GenericString remarks, Integer soInsPayCategory,
			Boolean sameFamily, Integer sameFamilyDays, String familyMemberId) {
		super(LeaveHolidayType.NURSING_CARE_LEAVE, historyId, employeeId, remarks, soInsPayCategory);
		this.sameFamily = sameFamily;
		this.sameFamilyDays = sameFamilyDays;
		this.familyMemberId = familyMemberId;
	}

	public static CareHoliday init(String historyId, String employeeId, String remarks,
			Integer soInsPayCategory, Boolean sameFamily, Integer sameFamilyDays, String familyMemberId) {
		return new CareHoliday(historyId, employeeId, new GenericString(remarks), soInsPayCategory, sameFamily,
				sameFamilyDays, familyMemberId);
	}

}
