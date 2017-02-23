package nts.uk.ctx.basic.infra.entity.organization.workplace;

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
	private String genericName;

	@Column(name = "HIERARCHY_ID")
	private String hierarchyId;

	@Column(name = "HIERARCHY_ID_01")
	private String hierarchyId01;

	@Column(name = "HIERARCHY_ID_02")
	private String hierarchyId02;

	@Column(name = "HIERARCHY_ID_03")
	private String hierarchyId03;

	@Column(name = "HIERARCHY_ID_04")
	private String hierarchyId04;

	@Column(name = "HIERARCHY_ID_05")
	private String hierarchyId05;

	@Column(name = "HIERARCHY_ID_06")
	private String hierarchyId06;

	@Column(name = "HIERARCHY_ID_07")
	private String hierarchyId07;

	@Column(name = "HIERARCHY_ID_08")
	private String hierarchyId08;

	@Column(name = "HIERARCHY_ID_09")
	private String hierarchyId09;

	@Column(name = "HIERARCHY_ID_10")
	private String hierarchyId10;

	@Column(name = "WKP_OUT_CD")
	private String externalCode;

	@Column(name = "PARENT_CHILD_ATR1")
	private String parentChildAttribute1;

	@Column(name = "PARENT_CHILD_ATR2")
	private String parentChildAttribute2;

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

	public String getGenericName() {
		return genericName;
	}

	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}

	public String getHierarchyId() {
		return hierarchyId;
	}

	public void setHierarchyId(String hierarchyId) {
		this.hierarchyId = hierarchyId;
	}

	public String getHierarchyId01() {
		return hierarchyId01;
	}

	public void setHierarchyId01(String hierarchyId01) {
		this.hierarchyId01 = hierarchyId01;
	}

	public String getHierarchyId02() {
		return hierarchyId02;
	}

	public void setHierarchyId02(String hierarchyId02) {
		this.hierarchyId02 = hierarchyId02;
	}

	public String getHierarchyId03() {
		return hierarchyId03;
	}

	public void setHierarchyId03(String hierarchyId03) {
		this.hierarchyId03 = hierarchyId03;
	}

	public String getHierarchyId04() {
		return hierarchyId04;
	}

	public void setHierarchyId04(String hierarchyId04) {
		this.hierarchyId04 = hierarchyId04;
	}

	public String getHierarchyId05() {
		return hierarchyId05;
	}

	public void setHierarchyId05(String hierarchyId05) {
		this.hierarchyId05 = hierarchyId05;
	}

	public String getHierarchyId06() {
		return hierarchyId06;
	}

	public void setHierarchyId06(String hierarchyId06) {
		this.hierarchyId06 = hierarchyId06;
	}

	public String getHierarchyId07() {
		return hierarchyId07;
	}

	public void setHierarchyId07(String hierarchyId07) {
		this.hierarchyId07 = hierarchyId07;
	}

	public String getHierarchyId08() {
		return hierarchyId08;
	}

	public void setHierarchyId08(String hierarchyId08) {
		this.hierarchyId08 = hierarchyId08;
	}

	public String getHierarchyId09() {
		return hierarchyId09;
	}

	public void setHierarchyId09(String hierarchyId09) {
		this.hierarchyId09 = hierarchyId09;
	}

	public String getHierarchyId10() {
		return hierarchyId10;
	}

	public void setHierarchyId10(String hierarchyId10) {
		this.hierarchyId10 = hierarchyId10;
	}

	public String getExternalCode() {
		return externalCode;
	}

	public void setExternalCode(String externalCode) {
		this.externalCode = externalCode;
	}

	public String getParentChildAttribute1() {
		return parentChildAttribute1;
	}

	public void setParentChildAttribute1(String parentChildAttribute1) {
		this.parentChildAttribute1 = parentChildAttribute1;
	}

	public String getParentChildAttribute2() {
		return parentChildAttribute2;
	}

	public void setParentChildAttribute2(String parentChildAttribute2) {
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
