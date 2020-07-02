package nts.uk.ctx.at.schedule.infra.repository.employeeinfo.scheduleteam;

import java.util.List;
import java.util.Optional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamOrder;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRepository;

/**
 * スケジュールチームRepository
 * @author HieuLt
 * 
 */
public class JpaScheduleTeamRepository extends JpaRepository implements ScheduleTeamRepository{

	@Override
	public void insert(ScheduleTeam scheduleTeam, ScheduleTeamOrder scheduleTeamOrder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ScheduleTeam scheduleTeam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ScheduleTeamOrder scheduleTeamOrder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyID, String WKPGRPID, String scheduleTeamCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ScheduleTeam> getAllScheduleTeamWorkgroup(String companyID, String WKPGRPID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ScheduleTeam> getAllSchedule(String companyID, List<String> listWKPGRPID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ScheduleTeam> getScheduleTeam(String companyID, String WKPGRPID, String scheduleTeamCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkExistScheduleTeam(String companyID, String WKPGRPID, String scheduleTeamCd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<ScheduleTeamOrder> getOrderScheduleTeam(String companyID, String WKPGRPID) {
		// TODO Auto-generated method stub
		return null;
	}

}
