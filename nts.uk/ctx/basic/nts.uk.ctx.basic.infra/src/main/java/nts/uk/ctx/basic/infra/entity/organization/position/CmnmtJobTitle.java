package nts.uk.ctx.basic.infra.entity.organization.position;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.TableEntity;

@Entity
@Table(name="CMNMT_JOB_TITLE")
@AllArgsConstructor
@NoArgsConstructor
public class CmnmtJobTitle extends TableEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
    public CmnmtJobTitlePK cmnmtJobTitlePK;

	@Basic(optional = false)
	@Column(name = "EXCLUS_VER")
	public int exclusVersion;
	
	@Basic(optional = false)
	@Column(name = "JOBNAME")
	public String jobName;
	
	@Basic(optional = false)
	@Column(name = "PRESENCE_CHECK_SCOPE_SET")
	public int presenceCheckScopeSet;
	
	@Basic(optional = true)
	@Column(name = "JOB_OUT_CD")
	public String jobOutCode;
	
	@Basic(optional = true)
	@Column(name = "MEMO")
	public String memo;
	
	@Basic(optional = false)
	@Column(name = "HIERARCHY_ORDER_CD")
	public String hierarchyOrderCode;
	
	
}

	


