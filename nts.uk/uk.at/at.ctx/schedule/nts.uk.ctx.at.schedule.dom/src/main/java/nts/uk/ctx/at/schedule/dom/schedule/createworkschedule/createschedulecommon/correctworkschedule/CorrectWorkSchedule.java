package nts.uk.ctx.at.schedule.dom.schedule.createworkschedule.createschedulecommon.correctworkschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.dailyprocess.calc.CalculateDailyRecordServiceCenterNew;
import nts.uk.ctx.at.shared.dom.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.enums.ExecutionType;

/**
 * 勤務予定を補正する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定処理.作成処理.アルゴリズム.勤務予定処理.勤務予定作成する.勤務予定作成共通処理.勤務予定を補正する
 * @author tutk
 *
 */
@Stateless
public class CorrectWorkSchedule {
	
	@Inject
	private CalculateDailyRecordServiceCenterNew centerNew;
	
//	@Inject
//	private ICorrectionAttendanceRule rule;
	
	public WorkSchedule correctWorkSchedule(WorkSchedule workSchedule,String employeeId,GeneralDate targetDate) {
		//勤務予定から日別勤怠（Work）に変換する
		//TODO : Chưa remove từ record sang shared nên chưa sử dụng được (tạo 1 biến class IntegrationOfDaily , biến nào k có thì để empty) (TKT-TQP)
		IntegrationOfDaily daily = new IntegrationOfDaily(workSchedule.getWorkInfo(), null, workSchedule.getAffInfo(), 
				Optional.empty(), new ArrayList<>(), Optional.empty(), workSchedule.getLstBreakTime(), workSchedule.getOptAttendanceTime(), 
				workSchedule.getOptTimeLeaving(), workSchedule.getOptSortTimeWork(), Optional.empty(), Optional.empty(), 
				Optional.empty(), workSchedule.getLstEditState(), Optional.empty(), new ArrayList<>());
		//勤怠ルールの補正処理 
		//TODO Thuật toán này hiện chưa ai làm + cũng chưa phải làm. nên tạm thời bỏ qua (TKT-TQP)
		//勤務予定情報を計算する
		//TODO: đang để record -> có thể sẽ chuyển về shared (mr Hoshina dang ban vs a Lau)
		//centerNew.calculate(employeeId, targetDate, ExecutionType.NORMAL_EXECUTION, null, true);
		return workSchedule; //Return tạm thời
		
	}
	
	private OutputCalcSchedule calcWorkScheduleInfo(IntegrationOfDaily integrationOfDaily, String employeeId,GeneralDate targetDate) {
		List<IntegrationOfDaily> lstInteOfDaily = new ArrayList<>();
		lstInteOfDaily.add(integrationOfDaily);
		lstInteOfDaily = centerNew.calculatePassCompanySetting(CalculateOption.asDefault(), lstInteOfDaily, ExecutionType.valueOf(ExecutionType.NORMAL_EXECUTION.nameId));
		
		return null;
	}

}
