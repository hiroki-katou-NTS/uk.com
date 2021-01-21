package nts.uk.ctx.at.schedule.infra.entity.employeeinfo.scheduleteam;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamName;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRemarks;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * Table_KSCMT_SCHEDULE_TEAM
 * 
 * @author HieuLt
 *
 */
@Entity
@NoArgsConstructor
@Table(name = "KSCMT_SCHEDULE_TEAM")
public class KscmtScheduleTeam extends ContractUkJpaEntity {

	@EmbeddedId
	public KscmtScheduleTeamPk pk;

	/** 名称 **/
	@NotNull
	@Column(name = "NAME")
	public String scheduleTeamName;

	/** 備考 **/
	@Column(name = "NOTE")
	public String remarks;
	
	/** 並び順**/
	@Column(name = "DISPORDER")
	public int dispOrder;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public KscmtScheduleTeam(KscmtScheduleTeamPk pk, String scheduleTeamName, String remarks, int dispOrder) {
		super();
		this.pk = pk;
		this.scheduleTeamName = scheduleTeamName;
		this.remarks = remarks;
		this.dispOrder = dispOrder;
	}

	public static KscmtScheduleTeam toEntity(ScheduleTeam scheduleTeam) {
		return new KscmtScheduleTeam(
				new KscmtScheduleTeamPk(AppContexts.user().companyId(), scheduleTeam.getWKPGRPID(),
						scheduleTeam.getScheduleTeamCd().v()),
				scheduleTeam.getScheduleTeamName().v(), scheduleTeam.getRemarks().get().v(),
				Integer.parseInt(scheduleTeam.getScheduleTeamCd().v()));
	}

	public ScheduleTeam toDomain() {
		return new ScheduleTeam(pk.WKPGRPID, new ScheduleTeamCd(pk.scheduleTeamCd),
								new ScheduleTeamName(this.scheduleTeamName),
								this.remarks == null ? Optional.empty():Optional.of(new ScheduleTeamRemarks(this.remarks)));
				};

	public void fromEntity(ScheduleTeam scheduleTeam){
		KscmtScheduleTeamPk pk = new KscmtScheduleTeamPk(AppContexts.user().companyId(), scheduleTeam.getWKPGRPID(), scheduleTeam.getScheduleTeamCd().v());
		this.pk = pk;
		this.scheduleTeamName = scheduleTeam.getScheduleTeamName().v();

		this.remarks = scheduleTeam.getRemarks().isPresent() ? scheduleTeam.getRemarks().get().v() : null;
	}

	

	

	
}
