package nts.uk.ctx.basic.infra.entity.organization.position;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CMNMT_JOB_TITLE")
public class CmnmtJobTittle implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public CmnmtJobTittlePK cmnmtJobTittlePK;
	
	@Basic(optional = false)
	@Column(name ="STR_YM")
	public GeneralDate strYm;

	@Basic(optional = false)
	@Column(name = "END_YM")
	public GeneralDate endYm;
	
	@Basic(optional = false)
	@Column(name = "MEMO")
	public String memo;
	
	@Basic(optional = false)
	@Column(name ="JOBNAME")
	public String jobName;

	@Basic(optional = false)
	@Column(name ="JOB_OUT_CODE")
	public String jobOutCode;
	
}
