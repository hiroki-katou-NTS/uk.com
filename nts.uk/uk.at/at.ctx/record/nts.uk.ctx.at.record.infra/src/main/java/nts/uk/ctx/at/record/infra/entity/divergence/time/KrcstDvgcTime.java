package nts.uk.ctx.at.record.infra.entity.divergence.time;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the KRCST_DVGC_TIME database table.
 * 
 */
@Entity
@Table(name="KRCST_DVGC_TIME")
@NamedQuery(name="KrcstDvgcTime.findAll", query="SELECT k FROM KrcstDvgcTime k")
public class KrcstDvgcTime implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KrcstDvgcTimePK id;

	@Column(name="DVGC_REASON_INPUTED")
	private BigDecimal dvgcReasonInputed;

	@Column(name="DVGC_REASON_SELECTED")
	private BigDecimal dvgcReasonSelected;

	@Column(name="DVGC_TIME_NAME")
	private String dvgcTimeName;

	@Column(name="DVGC_TIME_USE_SET")
	private BigDecimal dvgcTimeUseSet;

	@Column(name="DVGC_TYPE")
	private BigDecimal dvgcType;

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

	@Column(name="REASON_INPUT_CANCELED")
	private BigDecimal reasonInputCanceled;

	@Column(name="REASON_SELECT_CANCELED")
	private BigDecimal reasonSelectCanceled;

	@Column(name="UPD_CCD")
	private String updCcd;

	@Column(name="UPD_DATE")
	private String updDate;

	@Column(name="UPD_PG")
	private String updPg;

	@Column(name="UPD_SCD")
	private String updScd;

	public KrcstDvgcTime() {
	}

	public KrcstDvgcTimePK getId() {
		return this.id;
	}

	public void setId(KrcstDvgcTimePK id) {
		this.id = id;
	}

	public BigDecimal getDvgcReasonInputed() {
		return this.dvgcReasonInputed;
	}

	public void setDvgcReasonInputed(BigDecimal dvgcReasonInputed) {
		this.dvgcReasonInputed = dvgcReasonInputed;
	}

	public BigDecimal getDvgcReasonSelected() {
		return this.dvgcReasonSelected;
	}

	public void setDvgcReasonSelected(BigDecimal dvgcReasonSelected) {
		this.dvgcReasonSelected = dvgcReasonSelected;
	}

	public String getDvgcTimeName() {
		return this.dvgcTimeName;
	}

	public void setDvgcTimeName(String dvgcTimeName) {
		this.dvgcTimeName = dvgcTimeName;
	}

	public BigDecimal getDvgcTimeUseSet() {
		return this.dvgcTimeUseSet;
	}

	public void setDvgcTimeUseSet(BigDecimal dvgcTimeUseSet) {
		this.dvgcTimeUseSet = dvgcTimeUseSet;
	}

	public BigDecimal getDvgcType() {
		return this.dvgcType;
	}

	public void setDvgcType(BigDecimal dvgcType) {
		this.dvgcType = dvgcType;
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

	public BigDecimal getReasonInputCanceled() {
		return this.reasonInputCanceled;
	}

	public void setReasonInputCanceled(BigDecimal reasonInputCanceled) {
		this.reasonInputCanceled = reasonInputCanceled;
	}

	public BigDecimal getReasonSelectCanceled() {
		return this.reasonSelectCanceled;
	}

	public void setReasonSelectCanceled(BigDecimal reasonSelectCanceled) {
		this.reasonSelectCanceled = reasonSelectCanceled;
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