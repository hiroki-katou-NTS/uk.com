package nts.uk.ctx.at.schedule.infra.repository.shift.team.teamsetting;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.team.TeamCode;
import nts.uk.ctx.at.schedule.dom.shift.team.teamsetting.TeamSet;
import nts.uk.ctx.at.schedule.dom.shift.team.teamsetting.TeamSetRepository;
import nts.uk.ctx.at.schedule.infra.entity.shift.team.teamsetting.KscmtTeamSetting;
import nts.uk.ctx.at.schedule.infra.entity.shift.team.teamsetting.KscmtTeamSettingPK;

/**
 * teamset repository implement
 * 
 * @author Trung Tran
 *
 */
@Stateless
public class JpaTeamSetRepository extends JpaRepository implements TeamSetRepository {
	/**
	 * get all team set
	 */
	private static final String GET_ALL_DEFAULT = "Select c from KscmtTeamSetting c";
	/**
	 * remove team set
	 */
	private static final String REMOVE_TEAM_SET = "DELETE FROM KscmtTeamSetting c ";

	private static final String BY_CODE = "WHERE c.ksctTeamSetPk.workPlaceId = :workPlaceId AND c.teamCode = :teamCode";

	@Override
	public List<TeamSet> getAllTeamSet() {
		return this.queryProxy().query(GET_ALL_DEFAULT, KscmtTeamSetting.class).getList(t -> toDomain(t));
	}

	/**
	 * convert entity obj to domain obj
	 * 
	 * @param entity
	 * @return domain object
	 */
	private TeamSet toDomain(KscmtTeamSetting entity) {
		return new TeamSet(new TeamCode(entity.teamCode), entity.ksctTeamSetPk.sId,
				entity.ksctTeamSetPk.workPlaceId);
	}

	private KscmtTeamSetting toEntity(TeamSet domain) {
		KscmtTeamSettingPK pk = new KscmtTeamSettingPK(domain.getSId(), domain.getWorkPlaceId());
		return new KscmtTeamSetting(pk, domain.getTeamCode().v());
	}

	/**
	 * add teamSet
	 */
	@Override
	public void addTeamSet(TeamSet domain) {
		KscmtTeamSetting entity = toEntity(domain);
		this.commandProxy().insert(entity);
	}

	@Override
	public void removeTeamSetByTeamCode(String workPlaceId, String teamCode) {
		this.getEntityManager().createQuery(REMOVE_TEAM_SET + BY_CODE).setParameter("workPlaceId", workPlaceId)
				.setParameter("teamCode", teamCode).executeUpdate();
	}

	@Override
	public void removeListTeamSet(List<String> employees, String workPlaceId) {
		employees.stream().forEach(sId -> {
			KscmtTeamSettingPK pk = new KscmtTeamSettingPK(sId, workPlaceId);
			if (this.getEntityManager().find(KscmtTeamSetting.class, pk) != null) {
				this.commandProxy().remove(KscmtTeamSetting.class, pk);
			}
		});
	}

	@Override
	public void removeTeamSet(String sId, String workPlace) {
		KscmtTeamSettingPK pk = new KscmtTeamSettingPK(sId, workPlace);
		this.commandProxy().remove(KscmtTeamSetting.class, pk);
	}

}
