package nts.uk.ctx.at.schedule.dom.appreflectprocess.service;

import nts.arc.time.GeneralDate;

public interface UpdateScheCommonAppRelect {
	/**
	 * 勤務予定基本情報
	 * 勤務種類=INPUT．勤務種類コード
	 * 就業時間帯=INPUT．就業時間帯コード
	 * @param employeeId
	 * @param baseDate
	 */
	public void updateScheWorkTimeType(String employeeId, GeneralDate baseDate, String workTypeCode, String workTimeCode);


}
