package nts.uk.ctx.at.schedule.infra.repository.employeeinfo.scheduleteam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRepository;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.scheduleteam.KscmtScheduleTeam;
import nts.uk.ctx.at.schedule.infra.entity.employeeinfo.scheduleteam.KscmtScheduleTeamPk;
import nts.uk.shr.com.context.AppContexts;

/**
 * スケジュールチームRepository
 * 
 * @author HieuLt
 * 
 */
@Stateless
public class JpaScheduleTeamRepository extends JpaRepository implements ScheduleTeamRepository {

	private static final String SELECT = "SELECT c FROM KscmtScheduleTeam c ";

	private static final String SELECT_BY_KEY = SELECT + " WHERE c.pk.CID = :CID " + " AND c.pk.WKPGRPID = :WKPGRPID "
			+ " AND c.pk.scheduleTeamCd = :scheduleTeamCd ";

	private static final String SELECT_BY_CID_WKPGRPID = SELECT + " WHERE c.pk.CID = :CID "
			+ " AND c.pk.WKPGRPID = :WKPGRPID ORDER BY c.dispOrder ASC ";
	private static final String SELECT_ALL = SELECT + " WHERE c.pk.CID = :CID "
			+ " AND c.pk.WKPGRPID IN :listWKPGRPID ORDER BY c.pk.WKPGRPID ASC , c.dispOrder ASC ";

	@Override
	public void insert(ScheduleTeam scheduleTeam) {
		this.commandProxy().insert(KscmtScheduleTeam.toEntity(scheduleTeam));
	}

	@Override
	public void update(ScheduleTeam scheduleTeam) {
		Optional<KscmtScheduleTeam> scheduleTeamEntity = this.queryProxy().query(SELECT_BY_KEY, KscmtScheduleTeam.class)
				.setParameter("CID", AppContexts.user().companyId())
				.setParameter("WKPGRPID", scheduleTeam.getWKPGRPID())
				.setParameter("scheduleTeamCd", scheduleTeam.getScheduleTeamCd()).getSingle();
		if (scheduleTeamEntity.isPresent()) {
			KscmtScheduleTeam newEntity = scheduleTeamEntity.get();
			newEntity.fromEntity(scheduleTeam);
			this.commandProxy().update(newEntity);
		}
	}

	@Override
	public void delete(String companyID, String WKPGRPID, String scheduleTeamCd) {
		Optional<ScheduleTeam> scheduleTeam = this.getScheduleTeam(companyID, WKPGRPID, scheduleTeamCd);
		if (scheduleTeam.isPresent())
			this.commandProxy().remove(KscmtScheduleTeam.class,
					new KscmtScheduleTeamPk(companyID, WKPGRPID, scheduleTeamCd));
	}

	@Override
	public List<ScheduleTeam> getAllScheduleTeamWorkgroup(String companyID, String WKPGRPID) {
		return this.queryProxy().query(SELECT_BY_CID_WKPGRPID, KscmtScheduleTeam.class).setParameter("CID", companyID)
				.setParameter("WKPGRPID", WKPGRPID).getList(c -> c.toDomain());
	}

	@Override
	public List<ScheduleTeam> getAllSchedule(String companyID, List<String> listWKPGRPID) {
		if(listWKPGRPID.isEmpty())
			return new ArrayList<>();
		return this.queryProxy().query(SELECT_ALL, KscmtScheduleTeam.class).setParameter("CID", companyID)
				.setParameter("listWKPGRPID", listWKPGRPID).getList(c -> c.toDomain());
	}

	@Override
	public Optional<ScheduleTeam> getScheduleTeam(String companyID, String WKPGRPID, String scheduleTeamCd) {
		return this.queryProxy().query(SELECT_BY_KEY, KscmtScheduleTeam.class).setParameter("CID", companyID)
				.setParameter("WKPGRPID", WKPGRPID).setParameter("scheduleTeamCd", scheduleTeamCd)
				.getSingle(c -> c.toDomain());
	}

	@Override
	public boolean checkExistScheduleTeam(String companyID, String WKPGRPID, String scheduleTeamCd) {
		Optional<ScheduleTeam> scheduleTeam = this.getScheduleTeam(companyID, WKPGRPID, scheduleTeamCd);
		if (scheduleTeam.isPresent())
			return true;
		return false;
	}
}
