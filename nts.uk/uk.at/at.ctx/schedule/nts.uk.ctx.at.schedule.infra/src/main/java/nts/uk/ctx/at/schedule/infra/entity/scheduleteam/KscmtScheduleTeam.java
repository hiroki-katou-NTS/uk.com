package nts.uk.ctx.at.schedule.infra.entity.scheduleteam;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeam;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamCd;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamName;
import nts.uk.ctx.at.schedule.dom.employeeinfo.scheduleteam.ScheduleTeamRemarks;

/**
 * 
 * @author quytb
 *
 */

@Entity
@Table(name = "KSCMT_SCHEDULE_TEAM")
@AllArgsConstructor
@NoArgsConstructor
public class KscmtScheduleTeam implements Serializable{
	
	private static final long serialVersionUID = 4517677990842273227L;
	
	@EmbeddedId
	private KscmtScheduleTeamPK kscmtScheduleTeamPK;
	
	/** スケジュールチーム名称  */
	@Column(name = "NAME")
	private String scheduleTeamName;
	
	/** スケジュールチーム備考 */
	@Column(name = "NOTE")
	private String remarks;
	
	public ScheduleTeam toDomain(){		
		return new ScheduleTeam(this.kscmtScheduleTeamPK.getWorkplaceGroupId(), 
				new ScheduleTeamCd(this.kscmtScheduleTeamPK.getScheduleTeamCd()), 
				new ScheduleTeamName(this.scheduleTeamName), 
				Optional.of(new ScheduleTeamRemarks(this.remarks)));		
	}

}
