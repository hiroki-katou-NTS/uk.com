package nts.uk.ctx.at.schedule.app.find.schedule.scheduleteam;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * <<Query>>スケジュールチームを並び順に取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.社員情報.スケジュールチーム.App.スケジュールチームを並び順に取得する
 * @author quytb
 *
 */

@Stateless
public class ScheduleTeamQuery {
	@Inject
	private ScheduleTeamRepository scheduleTeamRepository;
	
	/** スケジュールチームを並び順に取得する */
	public List<ScheduleTeamDto> getListScheduleTeam(String WKPGRPID){
		String companyId = AppContexts.user().companyId();
		return scheduleTeamRepository.getAllScheduleTeamWorkgroup(companyId, WKPGRPID).stream()
			.map(x -> new ScheduleTeamDto(x.getScheduleTeamCd().v(), x.getScheduleTeamName().v()))
			.collect(Collectors.toList());		
	}
}
