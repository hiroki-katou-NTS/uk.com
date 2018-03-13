package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service;

import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.servicedto.TimeReflectScheDto;
/**
 * 終了時刻の反映
 * @author dudt
 *
 */
public interface EndTimeReflectScheService {
	/**
	 * 「勤務予定基本情報」、「勤務予定項目状態」を編集する
	 * @param timeDto
	 */
	public void updateEndTimeRflect(TimeReflectScheDto timeDto);

}
