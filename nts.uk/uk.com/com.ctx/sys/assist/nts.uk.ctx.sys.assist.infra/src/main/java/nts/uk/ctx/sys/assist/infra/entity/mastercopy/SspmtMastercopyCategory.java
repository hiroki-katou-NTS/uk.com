package nts.uk.ctx.sys.assist.infra.entity.mastercopy;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the SSPMT_MASTERCOPY_CATEGORY database table.
 * 
 */
@Entity
@Table(name="SSPMT_MASTERCOPY_CATEGORY")
@NamedQuery(name="SspmtMastercopyCategory.findAll", query="SELECT s FROM SspmtMastercopyCategory s")
public class SspmtMastercopyCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="MASTER_COPY_ID")
	private String masterCopyId;

	@Column(name="CATEGORY_ORDER")
	private BigDecimal categoryOrder;

	@Column(name="CID")
	private String cid;

	@Column(name="EXCLUS_VER")
	private BigDecimal exclusVer;

	@Column(name="INS_CCD")
	private String insCcd;

	@Column(name="INS_DATE")
	private String insDate;

	@Column(name="INS_PG")
	private String insPg;

	@Column(name="INS_SCD")
	private String insScd;

	@Column(name="MASTER_COPY_CATEGORY")
	private Object masterCopyCategory;

	@Column(name="SYSTEM_TYPE")
	private BigDecimal systemType;

	@Column(name="UPD_CCD")
	private String updCcd;

	@Column(name="UPD_DATE")
	private String updDate;

	@Column(name="UPD_PG")
	private String updPg;

	@Column(name="UPD_SCD")
	private String updScd;

	public SspmtMastercopyCategory() {
	}

	public String getMasterCopyId() {
		return this.masterCopyId;
	}

	public void setMasterCopyId(String masterCopyId) {
		this.masterCopyId = masterCopyId;
	}

	public BigDecimal getCategoryOrder() {
		return this.categoryOrder;
	}

	public void setCategoryOrder(BigDecimal categoryOrder) {
		this.categoryOrder = categoryOrder;
	}

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public BigDecimal getExclusVer() {
		return this.exclusVer;
	}

	public void setExclusVer(BigDecimal exclusVer) {
		this.exclusVer = exclusVer;
	}

	public String getInsCcd() {
		return this.insCcd;
	}

	public void setInsCcd(String insCcd) {
		this.insCcd = insCcd;
	}

	public String getInsDate() {
		return this.insDate;
	}

	public void setInsDate(String insDate) {
		this.insDate = insDate;
	}

	public String getInsPg() {
		return this.insPg;
	}

	public void setInsPg(String insPg) {
		this.insPg = insPg;
	}

	public String getInsScd() {
		return this.insScd;
	}

	public void setInsScd(String insScd) {
		this.insScd = insScd;
	}

	public Object getMasterCopyCategory() {
		return this.masterCopyCategory;
	}

	public void setMasterCopyCategory(Object masterCopyCategory) {
		this.masterCopyCategory = masterCopyCategory;
	}

	public BigDecimal getSystemType() {
		return this.systemType;
	}

	public void setSystemType(BigDecimal systemType) {
		this.systemType = systemType;
	}

	public String getUpdCcd() {
		return this.updCcd;
	}

	public void setUpdCcd(String updCcd) {
		this.updCcd = updCcd;
	}

	public String getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(String updDate) {
		this.updDate = updDate;
	}

	public String getUpdPg() {
		return this.updPg;
	}

	public void setUpdPg(String updPg) {
		this.updPg = updPg;
	}

	public String getUpdScd() {
		return this.updScd;
	}

	public void setUpdScd(String updScd) {
		this.updScd = updScd;
	}

}