/**
 * 
 */
package nts.uk.screen.at.app.ksu001.displayinworkinformation;

import java.util.List;

import lombok.Value;
import nts.uk.screen.at.app.ksu001.getworkscheduleshift.WorkScheduleShiftDto;

/**
 * @author laitv
 * 【Output】
     ・List<勤務種類, 必須任意不要区分, 出勤休日区分>
    ・List<就業時間帯の設定>
    ・List<勤務予定（勤務情報）dto>
 *
 */
@Value
public class DisplayInWorkInfoResult {
	
	List<WorkTypeInfomation> listWorkTypeInfo; // List<勤務種類, 必須任意不要区分, 出勤休日区分>
	public List<WorkScheduleShiftDto> listWorkScheduleShift;  // List<勤務予定（シフト）dto>
	
	
	
}
