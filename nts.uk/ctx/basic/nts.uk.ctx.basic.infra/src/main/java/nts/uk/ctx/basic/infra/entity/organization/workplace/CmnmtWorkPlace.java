package nts.uk.ctx.basic.infra.entity.organization.workplace;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "CMNMT_WORK_PLACE")
public class CmnmtWorkPlace implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CmnmtWorkPlacePK cmnmtWorkPlacePK;

	@Column(name = "STR_D")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "END_D")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Column(name = "WKPNAME")
	private String name;

	@Column(name = "WKPNAME_ABB")
	private String shortName;

	@Column(name = "WKPNAME_TOTAL")
	private String fullName;

	@Column(name = "HIERARCHY_ID")
	private String hierarchyId;

	@Column(name = "WKP_OUT_CD")
	private String externalCode;

	@Column(name = "PARENT_CHILD_ATR1")
	private BigDecimal parentChildAttribute1;

	@Column(name = "PARENT_CHILD_ATR2")
	private BigDecimal parentChildAttribute2;

	@Column(name = "PARENT_WKPCD1")
	private String parentWorkCode1;

	@Column(name = "PARENT_WKPCD2")
	private String parentWorkCode2;

	public CmnmtWorkPlacePK getCmnmtWorkPlacePK() {
		return cmnmtWorkPlacePK;
	}

	public void setCmnmtWorkPlacePK(CmnmtWorkPlacePK cmnmtWorkPlacePK) {
		this.cmnmtWorkPlacePK = cmnmtWorkPlacePK;
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

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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

	public BigDecimal getParentChildAttribute1() {
		return parentChildAttribute1;
	}

	public void setParentChildAttribute1(BigDecimal parentChildAttribute1) {
		this.parentChildAttribute1 = parentChildAttribute1;
	}

	public BigDecimal getParentChildAttribute2() {
		return parentChildAttribute2;
	}

	public void setParentChildAttribute2(BigDecimal parentChildAttribute2) {
		this.parentChildAttribute2 = parentChildAttribute2;
	}

	public String getParentWorkCode1() {
		return parentWorkCode1;
	}

	public void setParentWorkCode1(String parentWorkCode1) {
		this.parentWorkCode1 = parentWorkCode1;
	}

	public String getParentWorkCode2() {
		return parentWorkCode2;
	}

	public void setParentWorkCode2(String parentWorkCode2) {
		this.parentWorkCode2 = parentWorkCode2;
	}

}
