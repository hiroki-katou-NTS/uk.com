package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 所属スケジュールチーム	
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
 * @author HieuLT
 *
 */
@AllArgsConstructor
@Getter
public class BelongScheduleTeam implements DomainAggregate{

	/** 社員ID**/
	private final String employeeID;

	/** 職場グループID**/
	private final String WKPGRPID; 

	/** スケジュールチームコード **/
	private ScheduleTeamCd scheduleTeamCd;
	
	
}
