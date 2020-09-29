package nts.uk.ctx.at.schedule.dom.workschedule.domainservice;

import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;


/**
 * «Temporary» シフトの編集状態
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.勤務予定.勤務予定
 * @author HieuLt
 *
 */
@Getter
@RequiredArgsConstructor

public class ShiftEditState {

	/** 社員ID **/
	private final String employeeID;
	/** 年月日**/
	private final GeneralDate date ;
	/** <日別勤怠の編集状態>**/
	private final Optional<EditStateSetting> optEditStateOfDailyAttd;
}


