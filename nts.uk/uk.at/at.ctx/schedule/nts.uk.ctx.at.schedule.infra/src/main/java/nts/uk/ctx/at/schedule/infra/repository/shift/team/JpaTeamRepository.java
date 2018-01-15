package nts.uk.ctx.at.schedule.infra.repository.shift.team;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.team.Team;
import nts.uk.ctx.at.schedule.dom.shift.team.TeamRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.team.KscmtTeam;
import nts.uk.ctx.at.schedule.infra.entity.shift.team.KscmtTeamPK;

@Stateless
public class JpaTeamRepository extends JpaRepository implements TeamRepository {

	/**
	 * select a KscmtTeam ALL
	 */
	private static final String SELECT_NO_WHERE = "SELECT k FROM KscmtTeam k";

	/**
	 * select by WorkPlaceId
	 */
	private static final String GET_BY_WORK_PLACE = SELECT_NO_WHERE + " WHERE k.kscmtTeamPk.workplaceId = :workplaceId ORDER BY k.kscmtTeamPk.teamCode ASC ";
	/**
	 * select by team code
	 */
	private static final String FIND_BY_TEAM_CODE = SELECT_NO_WHERE + " WHERE k.kscmtTeamPk.teamCode = :teamCode";

	/**
	 * select KscmtTeam by work place
	 */
	@Override
	public List<Team> getTeamByWorkPlace(String workplaceId) {
		return this.queryProxy().query(GET_BY_WORK_PLACE, KscmtTeam.class).setParameter("workplaceId", workplaceId)
				.getList(x -> toDomain(x));

	}

	/**
	 * convert entity to domain
	 * 
	 * @param entity
	 * @return
	 */
	private static Team toDomain(KscmtTeam entity) {
		Team domain = Team.createFromJavaType(entity.kscmtTeamPk.workplaceId, entity.kscmtTeamPk.teamCode,
				entity.teamName);
		return domain;
	}

	/**
	 * insert team
	 */
	@Override
	public void addTeam(Team domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	/**
	 * convert domain to entity
	 * 
	 * @param domain
	 * @return
	 */
	private static KscmtTeam toEntity(Team domain) {
		val entity = new KscmtTeam();
		entity.kscmtTeamPk = new KscmtTeamPK(domain.getWorkPlaceId(), domain.getTeamCode().v());
		entity.teamName = domain.getTeamName().v();
		return entity;
	}

	@Override
	public Optional<Team> findTeamByTeamCode(String teamCode) {
		return this.queryProxy().query(FIND_BY_TEAM_CODE, KscmtTeam.class).setParameter("teamCode", teamCode)
				.getSingle(x -> toDomain(x));
	}

	@Override
	public void updateTeam(Team team) {
		KscmtTeamPK key = new KscmtTeamPK(team.getWorkPlaceId(), team.getTeamCode().v());
		KscmtTeam entity = this.queryProxy().find(key, KscmtTeam.class).get();
		entity.teamName = team.getTeamName().v();
		this.commandProxy().update(entity);
	}

	@Override
	public void removeTeam(String workPlaceId, String teamCode) {
		KscmtTeamPK key = new KscmtTeamPK(workPlaceId, teamCode);
		this.commandProxy().remove(KscmtTeam.class, key);
	}

	@Override
	public Optional<Team> findTeamByPK(String workPlaceId, String teamCode) {
		KscmtTeamPK key = new KscmtTeamPK(workPlaceId, teamCode);
		return this.queryProxy().find(key, KscmtTeam.class).map(entity -> toDomain(entity));
	}

}
