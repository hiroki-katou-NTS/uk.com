package nts.uk.ctx.at.record.pub.dailyprocess.scheduletime;

import java.util.List;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * RequestList No91 
 * Import Class
 * @author keisuke_hoshina
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleTimePubImport {

	//社員ID
	String employeeId;
	
	//年月日
	GeneralDate targetDate;
	
	//勤務種類コード
	WorkTypeCode workTypeCode;
	
	//就業時間帯コード
	WorkTimeCode workTimeCode;
	
	//開始時刻1～2 sorted ASC
	List<Integer> startClock;
	
	//終了時刻1～2 sorted ASC
	List<Integer> endClock;
	
	//休憩開始時刻1~10 sorted ASC
	List<Integer> breakStartTime;
	
	//休憩終了時刻1~10 sorted ASC
	List<Integer> breakEndTime;
	
	//育児介護開始時刻 1~2 sorted ASC
	List<Integer> childCareStartTime;
	
	//育児介護終了時刻 1~2 sorted ASC
	List<Integer> childCareEndTime;
}
