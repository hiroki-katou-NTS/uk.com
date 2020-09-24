package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;

/**
 * 勤務方法
 * @author lan_lt
 *
 */
public interface WorkMethod {
	//勤務方法の種類を取得する
	WorkTypeClassification getWorkTypeClassification();
	
	//該当するか判定する	
	boolean determineIfApplicable(Require require, WorkInformation workInfor );
	
	public static interface Require {
		//[R-1] 1日休日か
		boolean checkHoliday(String cid, String workTypeCode);
		
		//[R-2] 勤務種類を取得する	
		Optional<WorkType> getWorkType(String cid, String workTypeCode);
		
	}
	
}
