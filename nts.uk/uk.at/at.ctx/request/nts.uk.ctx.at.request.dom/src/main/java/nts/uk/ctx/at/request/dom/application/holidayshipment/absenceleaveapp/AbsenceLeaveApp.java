package nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 振休申請
 * 
 * @author sonnlb
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceLeaveApp extends AggregateRoot {
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 勤務種類
	 */
	private WorkTypeCode workTypeCD;
	/**
	 * 就業時間帯変更
	 */
	private NotUseAtr changeWorkHoursType;
	/**
	 * 就業時間帯
	 */
	private String workTimeCD;
	/**
	 * 勤務時間1
	 */
	private AbsenceLeaveWorkingHour WorkTime1;
	/**
	 * 勤務時間2
	 */
	private AbsenceLeaveWorkingHour WorkTime2;
	/**
	 * 消化対象代休管理
	 */
	private List<SubTargetDigestion> subTargetDigestions;
	/**
	 * 消化対象振休管理
	 */
	private List<SubDigestion> subDigestions;

	public Integer getStime1(){
		if(this.WorkTime1 == null){
			return null;
		}
		if(this.WorkTime1.getStartTime() == null){
			return null;
		}
		return this.WorkTime1.getStartTime().v();
	}
}
