package nts.uk.ctx.at.record.infra.entity.divergence.time.history;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the KRCST_COM_DRT_HIST database table.
 * 
 */
@Entity
@Table(name="KRCST_COM_DRT_HIST")
@NamedQuery(name="KrcstComDrtHist.findAll", query="SELECT k FROM KrcstComDrtHist k")
public class KrcstComDrtHist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="HIST_ID")
	private String histId;

	@Column(name="CID")
	private String cid;

	@Column(name="END_D")
	private String endD;

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

	@Column(name="STR_D")
	private String strD;

	@Column(name="UPD_CCD")
	private String updCcd;

	@Column(name="UPD_DATE")
	private String updDate;

	@Column(name="UPD_PG")
	private String updPg;

	@Column(name="UPD_SCD")
	private String updScd;

	public KrcstComDrtHist() {
	}

	public String getHistId() {
		return this.histId;
	}

	public void setHistId(String histId) {
		this.histId = histId;
	}

	public String getCid() {
		return this.cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getEndD() {
		return this.endD;
	}

	public void setEndD(String endD) {
		this.endD = endD;
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

	public String getStrD() {
		return this.strD;
	}

	public void setStrD(String strD) {
		this.strD = strD;
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