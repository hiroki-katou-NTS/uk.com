package nts.uk.ctx.at.schedule.app.find.schedule.scheduleteam;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;
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
//		ScheduleTeamDetailDto scheduleTeamDetailDto = new ScheduleTeamDetailDto();
//		Optional<ScheduleTeam> optScheduleTeam = scheduleTeamRepository.getScheduleTeam(companyId, WKPGRPID, scheduleTeamCd);
//		if (optScheduleTeam.isPresent()) {
//			scheduleTeamDetailDto.setCode(optScheduleTeam.get().getScheduleTeamCd().v());
//			scheduleTeamDetailDto.setName(optScheduleTeam.get().getScheduleTeamName().v());
//			if (optScheduleTeam.get().getRemarks().isPresent()) {
//				scheduleTeamDetailDto.setNote(optScheduleTeam.get().getRemarks().get().v());
//			}
//		}
//		return scheduleTeamDetailDto;
//		WKPGRPID = "3d9b24dc-c2c4-400e-8737-edcef04db18b";
		return scheduleTeamRepository.getScheduleTeam(companyId, WKPGRPID, scheduleTeamCd)
				.map(x -> new ScheduleTeamDetailDto(x.getScheduleTeamCd().v(), x.getScheduleTeamName().v(),
						x.getRemarks().orElse(null).v()))
				.orElse(null);
		
	}
}
