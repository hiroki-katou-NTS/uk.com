package nts.uk.ctx.basic.infra.entity.organization.position;



import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="CMNMT_JOB_HIST")
public class CmnmtJobHist {
	
	@EmbeddedId
    public CmnmtJobHistPK cmnmtJobHistPK;

	@Basic(optional = false)
	@Column(name = "STR_D")
	public GeneralDate startDate;
	
	@Basic(optional = false)
	@Column(name = "END_D")
	public GeneralDate endDate;

	@Basic(optional = false)
	@Column(name = "EXCLUS_VER")
	public int exclusVersion;

}