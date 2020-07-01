package nts.uk.ctx.at.schedule.infra.repository.employeeinfo.scheduleteam;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeamRepository;

/**
 * 所属スケジュールチームRepository			
 * @author HieuLt
 *
 */
@Stateless
public class JpaBelongScheduleTeamRepository extends JpaRepository implements BelongScheduleTeamRepository{

	@Override
	public void insert(BelongScheduleTeam belongScheduleTeam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(BelongScheduleTeam belongScheduleTeam) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyID, String empID, String WKPGRPID, String scheduleTeamCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyID, String empID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyID, String WKPGRPID, String scheduleTeamCd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BelongScheduleTeam> getAll(String companyID, String WKPGRPID, String scheduleTeamCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<BelongScheduleTeam> get(String companyID, String empID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<BelongScheduleTeam> get(String companyID, List<String> empID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String companyID, String empID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<BelongScheduleTeam> get(String companyID, String empID, String WKPGRPID, String scheduleTeamCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean exists(String companyID, String empID, String WKPGRPID, String scheduleTeamCd) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Optional<BelongScheduleTeam> get(String companyID, String WKPGRPID, String empID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<BelongScheduleTeam> get(String companyID, String WKPGRPID, List<String> empID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getEmpScheduleTeam(String companyID, String WKPGRPID, String scheduleTeamCd) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<BelongScheduleTeam> getScheduleTeam(String companyID, String empID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean checkempBelongScheduleTeam(String companyId, String empID) {
		// TODO Auto-generated method stub
		return false;
	}

}
