package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.holidayworktime;

import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ScheAndRecordSameChangeFlg;

public interface HolidayWorkReflectProcess {
	/**
	 * 予定勤種・就時の反映
	 * @param employeeId
	 * @param baseDate
	 * @param workTypeCode
	 * @param workTimeCode 就業時間帯コード
	 * @param scheReflectFlg
	 * @param scheAndRecordSameChangeFlg
	 */
	public void updateScheWorkTimeType(String employeeId, 
			GeneralDate baseDate, 
			String workTypeCode, 
			String workTimeCode, 
			boolean scheReflectFlg, 
			ScheAndRecordSameChangeFlg scheAndRecordSameChangeFlg);
	/**
	 * 予定勤種・就時を反映できるかチェックする
	 * @param employeeId
	 * @param baseDate
	 * @param workTimeCode 就業時間帯コード
	 * @param scheReflectFlg 予定反映区分
	 * @param scheAndRecordSameChangeFlg 予定と実績を同じに変更する区分
	 * @return
	 */
	public boolean checkScheWorkTimeReflect(String employeeId, 
			GeneralDate baseDate, 
			String workTimeCode, 
			boolean scheReflectFlg, 
			ScheAndRecordSameChangeFlg scheAndRecordSameChangeFlg);
	/**
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @param mapWorkTimeFrame 休出時間1～10
	 */
	public void reflectWorkTimeFrame(String employeeId, GeneralDate baseDate, Map<Integer, Integer> mapWorkTimeFrame);

}
