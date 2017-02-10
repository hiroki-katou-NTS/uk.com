package nts.uk.ctx.basic.infra.entity.organization.department;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "CMNMT_DEP")
public class CmnmtDep implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CmnmtDepPK cmnmtDepPK;

	@Column(name = "STR_D")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "END_D")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Column(name = "DEPNAME")
	private String name;

	@Column(name = "DEPNAME_TOTAL")
	private String fullName;

	@Column(name = "HIERARCHY_ID")
	private String hierarchyId;

	@Column(name = "DEP_OUT_CD")
	private String externalCode;

	public CmnmtDepPK getCmnmtDepPK() {
		return cmnmtDepPK;
	}

	public void setCmnmtDepPK(CmnmtDepPK cmnmtDepPK) {
		this.cmnmtDepPK = cmnmtDepPK;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getHierarchyId() {
		return hierarchyId;
	}

	public void setHierarchyId(String hierarchyId) {
		this.hierarchyId = hierarchyId;
	}

	public String getExternalCode() {
		return externalCode;
	}

	public void setExternalCode(String externalCode) {
		this.externalCode = externalCode;
	}

}
