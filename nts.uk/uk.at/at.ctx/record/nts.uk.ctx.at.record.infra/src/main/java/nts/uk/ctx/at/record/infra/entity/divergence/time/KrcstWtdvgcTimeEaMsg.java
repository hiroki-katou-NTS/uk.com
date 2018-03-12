package nts.uk.ctx.at.record.infra.entity.divergence.time;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the KRCST_WTDVGC_TIME_EA_MSG database table.
 * 
 */
@Entity
@Table(name="KRCST_WTDVGC_TIME_EA_MSG")
@NamedQuery(name="KrcstWtdvgcTimeEaMsg.findAll", query="SELECT k FROM KrcstWtdvgcTimeEaMsg k")
public class KrcstWtdvgcTimeEaMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private KrcstWtdvgcTimeEaMsgPK id;

	@Column(name="ALARM_MESSAGE")
	private String alarmMessage;

	@Column(name="ERROR_MESSAGE")
	private String errorMessage;

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

	public KrcstWtdvgcTimeEaMsg() {
	}

	public KrcstWtdvgcTimeEaMsgPK getId() {
		return this.id;
	}

	public void setId(KrcstWtdvgcTimeEaMsgPK id) {
		this.id = id;
	}

	public String getAlarmMessage() {
		return this.alarmMessage;
	}

	public void setAlarmMessage(String alarmMessage) {
		this.alarmMessage = alarmMessage;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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