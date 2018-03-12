package nts.uk.ctx.at.record.infra.entity.divergence.time;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the KRCST_DRT database table.
 * 
 */
@Entity
@Table(name="KRCST_DRT")
@NamedQuery(name="KrcstDrt.findAll", query="SELECT k FROM KrcstDrt k")
public class KrcstDrt implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KrcstDrtPK id;

	@Column(name="ALARM_TIME")
	private BigDecimal alarmTime;

	@Column(name="DVGC_TIME_USE_SET")
	private BigDecimal dvgcTimeUseSet;

	@Column(name="ERROR_TIME")
	private BigDecimal errorTime;

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

	public KrcstDrt() {
	}

	public KrcstDrtPK getId() {
		return this.id;
	}

	public void setId(KrcstDrtPK id) {
		this.id = id;
	}

	public BigDecimal getAlarmTime() {
		return this.alarmTime;
	}

	public void setAlarmTime(BigDecimal alarmTime) {
		this.alarmTime = alarmTime;
	}

	public BigDecimal getDvgcTimeUseSet() {
		return this.dvgcTimeUseSet;
	}

	public void setDvgcTimeUseSet(BigDecimal dvgcTimeUseSet) {
		this.dvgcTimeUseSet = dvgcTimeUseSet;
	}

	public BigDecimal getErrorTime() {
		return this.errorTime;
	}

	public void setErrorTime(BigDecimal errorTime) {
		this.errorTime = errorTime;
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

}