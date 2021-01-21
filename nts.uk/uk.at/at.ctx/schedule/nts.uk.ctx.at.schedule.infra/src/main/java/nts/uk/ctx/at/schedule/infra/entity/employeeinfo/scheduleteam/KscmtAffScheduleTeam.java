package nts.uk.ctx.at.schedule.infra.entity.employeeinfo.scheduleteam;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.BelongScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
@Entity
@NoArgsConstructor
@Table(name = "KSCMT_AFF_SCHEDULE_TEAM")
public class KscmtAffScheduleTeam extends  ContractUkJpaEntity{

	@EmbeddedId
	public KscmtAffScheduleTeamPk pk;
	
	/** 職場グループID **/
	@Column(name = "WKPGRP_ID")
	public String WKPGRPID;
	
	/** コード **/
	@Column(name = "CD")
	public String scheduleTeamCd;
	
	@Override
	protected Object getKey() {
		
		return this.pk;
	}

	public KscmtAffScheduleTeam(KscmtAffScheduleTeamPk pk, String wKPGRPID, String scheduleTeamCd) {
		super();
		this.pk = pk;
		this.WKPGRPID = wKPGRPID;
		this.scheduleTeamCd = scheduleTeamCd;
	}
	public static KscmtAffScheduleTeam toEntity(BelongScheduleTeam belongScheduleTeam){
		return new KscmtAffScheduleTeam(
				new KscmtAffScheduleTeamPk(AppContexts.user().companyId(), belongScheduleTeam.getEmployeeID()),
				belongScheduleTeam.getWKPGRPID(),
				belongScheduleTeam.getScheduleTeamCd().v());
	} 
	
	public BelongScheduleTeam toDomain(){
		return new BelongScheduleTeam(
				pk.employeeID,
				this.WKPGRPID,
				new ScheduleTeamCd(this.scheduleTeamCd));
	}
	
	public void fromEntity(BelongScheduleTeam belongScheduleTeam){
		KscmtAffScheduleTeamPk pk = new KscmtAffScheduleTeamPk(AppContexts.user().companyId(), belongScheduleTeam.getEmployeeID());
		this.pk = pk;
		this.WKPGRPID = belongScheduleTeam.getWKPGRPID();
		this.scheduleTeamCd = belongScheduleTeam.getScheduleTeamCd().v();
	}

}
