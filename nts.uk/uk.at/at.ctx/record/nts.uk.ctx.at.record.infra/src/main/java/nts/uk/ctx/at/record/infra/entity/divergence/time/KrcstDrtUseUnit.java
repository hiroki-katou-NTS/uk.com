package nts.uk.ctx.at.record.infra.entity.divergence.time;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the KRCST_DRT_USE_UNIT database table.
 * 
 */
@Entity
@Table(name="KRCST_DRT_USE_UNIT")
@NamedQuery(name="KrcstDrtUseUnit.findAll", query="SELECT k FROM KrcstDrtUseUnit k")
public class KrcstDrtUseUnit implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
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

	@Column(name="UPD_CCD")
	private String updCcd;

	@Column(name="UPD_DATE")
	private String updDate;

	@Column(name="UPD_PG")
	private String updPg;

	@Column(name="UPD_SCD")
	private String updScd;

	@Column(name="WORKTYPE_USE_SET")
	private BigDecimal worktypeUseSet;

	public KrcstDrtUseUnit() {
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

	public BigDecimal getWorktypeUseSet() {
		return this.worktypeUseSet;
	}

	public void setWorktypeUseSet(BigDecimal worktypeUseSet) {
		this.worktypeUseSet = worktypeUseSet;
	}

}