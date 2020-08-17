package nts.uk.screen.at.app.ksu001.getworkscheduleshift;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;


/**
 *
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShiftEditStateDto {

	/** 社員ID **/
	private  String employeeID;
	/** 年月日**/
	private  GeneralDate date ;
	
	/** <日別勤怠の編集状態>**/
	//HAND_CORRECTION_MYSELF(0), 手修正（本人）
	//HAND_CORRECTION_OTHER(1), 手修正（他人） 
	//REFLECT_APPLICATION(2), 申請反映
	//IMPRINT(3); 打刻反映
	private  int optEditStateOfDailyAttd;
}


