package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.domainservice;

import java.util.Optional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamName;

/**
 * 社員所属チーム情報	
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
 * @author HieuLt
 *
 */
@Getter
@RequiredArgsConstructor
public class EmpTeamInfor {
	/** 社員ID **/
	private final String employeeID;
	
	/** Optional<スケジュールチームコード> --- チームコード **/
	private final Optional<ScheduleTeamCd> optScheduleTeamCd;
	
	/** Optional<スケジュールチーム名称> ---チーム名称**/
	private final Optional<ScheduleTeamName> optScheduleTeamName;
	
	
	
	/**
	 * [C-1] 社員所属チーム情報
	 * 説明：新しい＜社員所属チーム情報＞を作る。
	 * @param employeeID
	 * @return
	 * 
	 */
	public static EmpTeamInfor get(String employeeID){
		return new EmpTeamInfor(employeeID, Optional.empty(), Optional.empty());
	}
	
	/**
	 * [C-2] 社員所属チーム情報
	 * 説明：新しい＜社員所属チーム情報＞を作る。
	 * @param employeeID
	 * @param scheduleTeamCd
	 * @param scheduleTeamName
	 * @return
	 */
	public static EmpTeamInfor create(String employeeID , ScheduleTeamCd scheduleTeamCd , ScheduleTeamName scheduleTeamName){
		return new EmpTeamInfor(employeeID,Optional.ofNullable(scheduleTeamCd)
				,Optional.ofNullable(scheduleTeamName));
	}
	
}
