package nts.uk.ctx.basic.infra.entity.organization.position;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CMNMT_JOB_TITLE")
public class CmnmtJobTitle implements Serializable{

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public CmnmtJobTitlePK cmnmtJobTitlePK;
	
	public CmnmtJobTitlePK getCmnmtJobTitlePK() {
		return cmnmtJobTitlePK;
	}

	@Basic(optional = false)
	@Column(name ="STR_D")
	public LocalDate startDate;

	@Basic(optional = false)
	@Column(name = "END_D")
	public LocalDate endDate;
	
	@Basic(optional = false)
	@Column(name = "MEMO")
	public String memo;
	
	@Basic(optional = false)
	@Column(name ="JOBNAME")
	public String jobName;

	@Basic(optional = false)
	@Column(name ="JOB_OUT_CODE")
	public String jobOutCode;
	
	@Basic(optional = false)
	@Column(name ="PRESENCE_CHECK_SCOPE_SET")
	public int presenceCheckScopeSet;
	
	@Basic(optional = false)
	@Column(name ="HIERARCHY_ORDER_CD")
	public String hiterarchyOrderCode;
	
	public void setCmnmtJobTittlePK(CmnmtJobTitlePK cmnmtJobTitlePK) {
		this.cmnmtJobTitlePK = cmnmtJobTitlePK;
	}
	
	

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobOutCode() {
		return jobOutCode;
	}

	public void setJobOutCode(String jobOutCode) {
		this.jobOutCode = jobOutCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	public int getPresenceCheckScopeSet() {
		return presenceCheckScopeSet;
	}



	public void setPresenceCheckScopeSet(int presenceCheckScopeSet) {
		this.presenceCheckScopeSet = presenceCheckScopeSet;
	}



	public String getHiterarchyOrderCode() {
		return hiterarchyOrderCode;
	}



	public void setHiterarchyOrderCode(String hiterarchyOrderCode) {
		this.hiterarchyOrderCode = hiterarchyOrderCode;
	}

	
}
