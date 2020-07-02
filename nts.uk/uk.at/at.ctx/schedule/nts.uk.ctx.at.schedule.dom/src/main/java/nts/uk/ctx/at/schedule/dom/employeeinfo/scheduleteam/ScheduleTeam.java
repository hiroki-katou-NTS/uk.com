package nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
/**
 * スケジュールチーム
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務予定.社員情報.スケジュールチーム
 * @author Hieult
 *
 */
@AllArgsConstructor
public class ScheduleTeam  implements DomainAggregate{
	/**職場グループID **/
	@Getter
	private final String WKPGRPID;
	
	/** スケジュールチームコード **/
	@Getter
	private final ScheduleTeamCd scheduleTeamCd;
	
	/** スケジュールチーム名称 **/
	@Getter
	private  ScheduleTeamName scheduleTeamName;
	
	/** スケジュールチーム備考**/
	@Getter
	private Optional<ScheduleTeamRemarks> remarks;
	

}
