package nts.uk.screen.at.app.ktgwidget.ktg004;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author thanhpv
 * @name 対象社員の勤怠情報
 */
@NoArgsConstructor
@Getter
@Setter
public class AttendanceInforDto {

	//フレックス繰越時間
	private String flexCarryOverTime = "0";

	//フレックス時間
	private String flexTime = "0";
	
	//休出時間
	private String holidayTime = "0";
	
	//残業時間
	private String overTime = "0";
	
	//就業時間外深夜時間
	private String nigthTime = "0";
	
	//早退回数
	private String early = "0";
	
	//遅刻回数
	private String late = "0";
	
	//日別実績のエラー有無
	private boolean dailyErrors = false;
}
