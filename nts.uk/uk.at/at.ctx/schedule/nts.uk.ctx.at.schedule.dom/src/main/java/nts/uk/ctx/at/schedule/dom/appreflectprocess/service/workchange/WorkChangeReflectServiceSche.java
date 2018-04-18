package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.workchange;

import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.CommonReflectParamSche;

/**
 * 勤務予定に反映 (勤務変更申請)勤務予定への反映
 * @author do_dt
 *
 */
public interface WorkChangeReflectServiceSche {
	/**
	 * (勤務変更申請)勤務予定への反映
	 * @param param
	 * @return
	 */
	public boolean reflectWorkChange(CommonReflectParamSche param);

}
