package nts.uk.screen.at.app.ksu001.getworkscheduleshift;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.workschedule.domainservice.ShiftEditState;


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
	private  Integer editStateSetting;
	
	public static ShiftEditStateDto toDto(ShiftEditState domain) {
		
		return new ShiftEditStateDto(
				domain.getEmployeeID(), 
				domain.getDate(),
				domain.getOptEditStateOfDailyAttd().isPresent() ? domain.getOptEditStateOfDailyAttd().get().value : null);
	}
}


