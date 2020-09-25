package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * 勤務方法
 * @author lan_lt
 *
 */
public interface WorkMethod {
	
	/**
	 * 勤務方法の種類を取得する
	 * @return
	 */
	WorkMethodClassfication getWorkMethodClassification();
	
	/**
	 * 該当するか判定する	
	 * @param require
	 * @param workInfor
	 * @return
	 */
	boolean determineIfApplicable(Require require, WorkInformation workInfor );
	
	public static interface Require {
		//[R-1] 1日休日か
		boolean checkHoliday(String workTypeCode);
		
		//[R-2] 勤務種類を取得する	
		Optional<WorkType> getWorkType(String workTypeCode);
		
	}
	
}
