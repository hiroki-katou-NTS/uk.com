package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service;

import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.servicedto.TimeReflectScheDto;

/**
 * 開始時刻の反映
 * @author dudt
 *
 */
public interface StartEndTimeReflectScheService {
	/**
	 * domain 「勤務予定基本情報」、「勤務予定項目状態」を更新する
	 * @param startTimeDto
	 */
	public void updateStartTimeRflect(TimeReflectScheDto startTimeDto);

}
