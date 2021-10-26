package nts.uk.ctx.at.aggregation.dom.form9;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.aggregation.dom.common.ScheRecAtr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * 社員の出力医療時間
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.様式９.社員の出力医療時間を取得する.社員の出力医療時間
 * @author lan_lt
 *
 */
@Value
public class MedicalTimeOfEmployee {
	
	/** 社員ID **/
	private final String employeeId;
	
	/** 年月日 **/
	private final GeneralDate ymd;
	
	/** 予実区分**/
	private final ScheRecAtr scheRecAtr;
	
	/** 日勤時間 **/
	private final Form9OutputMedicalTime dayShiftHours;
	
	/** 夜勤時間 **/
	private final Form9OutputMedicalTime nightShiftHours;
	
	/** 総夜勤時間 **/
	private final Form9OutputMedicalTime totalNightShiftHours;
	
	/**
	 * 作る
	 * @param dailyWork 日別勤怠
	 * @param scheRecAtr 予実区分
	 * @return
	 */
	public static MedicalTimeOfEmployee create(IntegrationOfDaily dailyWork, ScheRecAtr scheRecAtr) {
		
		return new MedicalTimeOfEmployee(dailyWork.getEmployeeId()
				,	dailyWork.getYmd(), scheRecAtr
				,	new Form9OutputMedicalTime(new AttendanceTime(480), true)
				,	new Form9OutputMedicalTime(new AttendanceTime(480), true)
				,	new Form9OutputMedicalTime(new AttendanceTime(480), true)
					);
	}
	
}
