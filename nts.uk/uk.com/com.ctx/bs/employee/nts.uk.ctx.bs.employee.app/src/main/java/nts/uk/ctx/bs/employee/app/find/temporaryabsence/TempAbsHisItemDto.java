/**
 * 
 */
package nts.uk.ctx.bs.employee.app.find.temporaryabsence;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHisItem;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.TempAbsenceHistory;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.CareHoliday;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.ChildCareHoliday;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.LeaveHolidayType;
import nts.uk.ctx.bs.employee.dom.temporaryabsence.state.MidweekClosure;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

/**
 * @author danpv
 *
 */
@Setter
@Getter
public class TempAbsHisItemDto extends PeregDomainDto {

	/**
	 * 休職期間
	 */
	//@PeregItem("IS00087")
	//private DateHistoryItem dateHistoryItem;

	/**
	 * 休業開始日
	 */
	@PeregItem("IS00088")
	private GeneralDate startDate;

	/**
	 * 休業復職日
	 */
	@PeregItem("IS00089")
	private GeneralDate endDate;

	/**
	 * 休業区分
	 */
	@PeregItem("IS00090")
	private LeaveHolidayType leaveHolidayType;

	/**
	 * 家族の同一の要介護状態について介護休業したことがあるか （介護休業の場合）
	 */
	@PeregItem("IS00091")
	private boolean careSameFamily;

	/**
	 * 対象の家族についてのこれまでの介護休業および介護短時間勤務の日数 （介護休業の場合）
	 */
	@PeregItem("IS00092")
	private Integer sameFamilyDays;

	/**
	 * 出産種別 （産前休業の場合）
	 */
	@PeregItem("IS00093")
	private boolean multiple;

	/**
	 * 子の区分 （育児休業の場合）
	 */
	@PeregItem("IS00094")
	private int childType;

	/**
	 * 縁組成立の年月日 （育児休業で子が養子の場合）
	 */
	@PeregItem("IS00095")
	private GeneralDate createDate;

	/**
	 * 同じ子について育児休業をしたことがあるか （育児休業の場合）
	 */
	@PeregItem("IS00096")
	private Boolean childCareSameFamily;

	/**
	 * １歳を超えての休業の申出の場合で申出者が育児休業中でない場合、配偶者が休業しているか （育児休業の場合）
	 */
	@PeregItem("IS00097")
	private Boolean spouseIsLeave;

	/**
	 * 社会保険支給対象区分
	 */
	@PeregItem("IS00098")
	private String remarks;

	/**
	 * 備考
	 */
	@PeregItem("IS00099")
	private Integer soInsPayCategory;

	public TempAbsHisItemDto() {

	}

	public TempAbsHisItemDto(String recordId, String employeeId) {
		super(recordId, employeeId, null);
	}

	public static TempAbsHisItemDto createFromDomain(TempAbsenceHistory history, TempAbsenceHisItem histItem) {
		TempAbsHisItemDto dto = new TempAbsHisItemDto(histItem.getHistoryId(), histItem.getEmployeeId());
		dto.setRemarks(histItem.getRemarks().v());
		dto.setSoInsPayCategory(histItem.getSoInsPayCategory());
		//dto.setDateHistoryItem(history.getDateHistoryItems().get(0));
		dto.setStartDate(history.getDateHistoryItems().get(0).start());
		dto.setEndDate(history.getDateHistoryItems().get(0).end());
		dto.setLeaveHolidayType(histItem.getLeaveHolidayType());
		switch (histItem.getLeaveHolidayType()) {
		case MIDWEEK_CLOSURE:
			MidweekClosure midweekClosure = (MidweekClosure) histItem;
			dto.setMultiple(midweekClosure.getMultiple());
			break;
		case CHILD_CARE_NURSING:
			ChildCareHoliday childCareHoliday = (ChildCareHoliday) histItem;
			dto.setChildType(childCareHoliday.getChildType());
			dto.setCreateDate(childCareHoliday.getCreateDate());
			dto.setChildCareSameFamily(childCareHoliday.getSameFamily());
			dto.setSpouseIsLeave(childCareHoliday.getSpouseIsLeave());
			break;
		case NURSING_CARE_LEAVE:
			CareHoliday careHoliday = (CareHoliday) histItem;
			dto.setCareSameFamily(careHoliday.getSameFamily());
			dto.setSameFamilyDays(careHoliday.getSameFamilyDays());
			break;
		default:
			break;
		}
		return dto;
	}
}
