package nts.uk.ctx.at.schedule.app.find.schedule.scheduleteam;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * <<Query>> スケジュールチームを取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.社員情報.スケジュールチーム.App.スケジュールチームを取得する 
 * @author quytb
 *
 */

@Stateless
public class ScheduleTeamDetailQuery {
	@Inject
	private ScheduleTeamRepository scheduleTeamRepository;

	public ScheduleTeamDetailDto getDetailScheduleTeam(String WKPGRPID, String scheduleTeamCd) {
		String companyId = AppContexts.user().companyId();
		return scheduleTeamRepository.getScheduleTeam(companyId, WKPGRPID, scheduleTeamCd)
				.map(x -> new ScheduleTeamDetailDto(x.getScheduleTeamCd().v(), x.getScheduleTeamName().v(),
						x.getRemarks().orElse(null).v()))
				.orElse(null);		
	}
}
