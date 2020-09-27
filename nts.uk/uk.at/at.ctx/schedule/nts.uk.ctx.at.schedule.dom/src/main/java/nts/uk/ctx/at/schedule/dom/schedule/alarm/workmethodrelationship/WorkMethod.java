package nts.uk.ctx.at.schedule.dom.schedule.alarm.workmethodrelationship;

import nts.uk.ctx.at.shared.dom.WorkInformation;

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
	
	public static interface Require extends WorkMethodContinuousWork.Require, WorkMethodHoliday.Require {
	
	}
	
}
