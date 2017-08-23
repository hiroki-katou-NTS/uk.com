/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.schedule.infra.entity.shift.total.times;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author NWS_THANHNC_PC
 */
@Setter
@Getter
@Entity
@Table(name = "KSHST_TOTAL_TIMES")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "KshstTotalTimes.findAll", query = "SELECT k FROM KshstTotalTimes k"),
		@NamedQuery(name = "KshstTotalTimes.findByInsDate", query = "SELECT k FROM KshstTotalTimes k WHERE k.insDate = :insDate"),
		@NamedQuery(name = "KshstTotalTimes.findByInsCcd", query = "SELECT k FROM KshstTotalTimes k WHERE k.insCcd = :insCcd"),
		@NamedQuery(name = "KshstTotalTimes.findByInsScd", query = "SELECT k FROM KshstTotalTimes k WHERE k.insScd = :insScd"),
		@NamedQuery(name = "KshstTotalTimes.findByInsPg", query = "SELECT k FROM KshstTotalTimes k WHERE k.insPg = :insPg"),
		@NamedQuery(name = "KshstTotalTimes.findByUpdDate", query = "SELECT k FROM KshstTotalTimes k WHERE k.updDate = :updDate"),
		@NamedQuery(name = "KshstTotalTimes.findByUpdCcd", query = "SELECT k FROM KshstTotalTimes k WHERE k.updCcd = :updCcd"),
		@NamedQuery(name = "KshstTotalTimes.findByUpdScd", query = "SELECT k FROM KshstTotalTimes k WHERE k.updScd = :updScd"),
		@NamedQuery(name = "KshstTotalTimes.findByUpdPg", query = "SELECT k FROM KshstTotalTimes k WHERE k.updPg = :updPg"),
		@NamedQuery(name = "KshstTotalTimes.findByExclusVer", query = "SELECT k FROM KshstTotalTimes k WHERE k.exclusVer = :exclusVer"),
		@NamedQuery(name = "KshstTotalTimes.findByCid", query = "SELECT k FROM KshstTotalTimes k WHERE k.kshstTotalTimesPK.cid = :cid"),
		@NamedQuery(name = "KshstTotalTimes.findByTotalTimesNo", query = "SELECT k FROM KshstTotalTimes k WHERE k.kshstTotalTimesPK.totalTimesNo = :totalTimesNo"),
		@NamedQuery(name = "KshstTotalTimes.findByUseAtr", query = "SELECT k FROM KshstTotalTimes k WHERE k.useAtr = :useAtr"),
		@NamedQuery(name = "KshstTotalTimes.findByCountAtr", query = "SELECT k FROM KshstTotalTimes k WHERE k.countAtr = :countAtr"),
		@NamedQuery(name = "KshstTotalTimes.findByTotalTimesName", query = "SELECT k FROM KshstTotalTimes k WHERE k.totalTimesName = :totalTimesName"),
		@NamedQuery(name = "KshstTotalTimes.findByTotalTimesAbname", query = "SELECT k FROM KshstTotalTimes k WHERE k.totalTimesAbname = :totalTimesAbname"),
		@NamedQuery(name = "KshstTotalTimes.findBySummaryAtr", query = "SELECT k FROM KshstTotalTimes k WHERE k.summaryAtr = :summaryAtr") })
public class KshstTotalTimes implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	protected KshstTotalTimesPK kshstTotalTimesPK;
	@Column(name = "INS_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date insDate;
	@Size(max = 4)
	@Column(name = "INS_CCD")
	private String insCcd;
	@Size(max = 12)
	@Column(name = "INS_SCD")
	private String insScd;
	@Size(max = 14)
	@Column(name = "INS_PG")
	private String insPg;
	@Column(name = "UPD_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updDate;
	@Size(max = 4)
	@Column(name = "UPD_CCD")
	private String updCcd;
	@Size(max = 12)
	@Column(name = "UPD_SCD")
	private String updScd;
	@Size(max = 14)
	@Column(name = "UPD_PG")
	private String updPg;
	@Basic(optional = false)
	@NotNull
	@Column(name = "EXCLUS_VER")
	private int exclusVer;
	@Basic(optional = false)
	@NotNull
	@Column(name = "USE_ATR")
	private short useAtr;
	@Basic(optional = false)
	@NotNull
	@Column(name = "COUNT_ATR")
	private short countAtr;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 12)
	@Column(name = "TOTAL_TIMES_NAME")
	private String totalTimesName;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 4)
	@Column(name = "TOTAL_TIMES_ABNAME")
	private String totalTimesAbname;
	@Basic(optional = false)
	@NotNull
	@Column(name = "SUMMARY_ATR")
	private short summaryAtr;

	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "TOTAL_TIMES_NO", referencedColumnName = "TOTAL_TIMES_NO", insertable = false, updatable = false) })
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	public List<KshstTotalSubjects> listTotalSubjects;

	@JoinColumns({ @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "TOTAL_TIMES_NO", referencedColumnName = "TOTAL_TIMES_NO", insertable = false, updatable = false) })
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	public KshstTotalCondition totalCondition;

	public KshstTotalTimes() {
	}

	public KshstTotalTimes(KshstTotalTimesPK kshstTotalTimesPK) {
		this.kshstTotalTimesPK = kshstTotalTimesPK;
	}

	public KshstTotalTimes(KshstTotalTimesPK kshstTotalTimesPK, Date insDate, String insCcd, String insScd,
			String insPg, Date updDate, String updCcd, String updScd, String updPg, int exclusVer, short useAtr,
			short countAtr, String totalTimesName, String totalTimesAbname, short summaryAtr,
			List<KshstTotalSubjects> listTotalSubjects, KshstTotalCondition totalCondition) {
		this.kshstTotalTimesPK = kshstTotalTimesPK;
		this.insDate = insDate;
		this.insCcd = insCcd;
		this.insScd = insScd;
		this.insPg = insPg;
		this.updDate = updDate;
		this.updCcd = updCcd;
		this.updScd = updScd;
		this.updPg = updPg;
		this.exclusVer = exclusVer;
		this.useAtr = useAtr;
		this.countAtr = countAtr;
		this.totalTimesName = totalTimesName;
		this.totalTimesAbname = totalTimesAbname;
		this.summaryAtr = summaryAtr;
		this.listTotalSubjects = listTotalSubjects;
		this.totalCondition = totalCondition;
	}

	public KshstTotalTimes(String cid, short totalTimesNo) {
		this.kshstTotalTimesPK = new KshstTotalTimesPK(cid, totalTimesNo);
	}

	public KshstTotalTimesPK getKshstTotalTimesPK() {
		return kshstTotalTimesPK;
	}

	public void setKshstTotalTimesPK(KshstTotalTimesPK kshstTotalTimesPK) {
		this.kshstTotalTimesPK = kshstTotalTimesPK;
	}

	public Date getInsDate() {
		return insDate;
	}

	public void setInsDate(Date insDate) {
		this.insDate = insDate;
	}

	public String getInsCcd() {
		return insCcd;
	}

	public void setInsCcd(String insCcd) {
		this.insCcd = insCcd;
	}

	public String getInsScd() {
		return insScd;
	}

	public void setInsScd(String insScd) {
		this.insScd = insScd;
	}

	public String getInsPg() {
		return insPg;
	}

	public void setInsPg(String insPg) {
		this.insPg = insPg;
	}

	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	public String getUpdCcd() {
		return updCcd;
	}

	public void setUpdCcd(String updCcd) {
		this.updCcd = updCcd;
	}

	public String getUpdScd() {
		return updScd;
	}

	public void setUpdScd(String updScd) {
		this.updScd = updScd;
	}

	public String getUpdPg() {
		return updPg;
	}

	public void setUpdPg(String updPg) {
		this.updPg = updPg;
	}

	public int getExclusVer() {
		return exclusVer;
	}

	public void setExclusVer(int exclusVer) {
		this.exclusVer = exclusVer;
	}

	public short getUseAtr() {
		return useAtr;
	}

	public void setUseAtr(short useAtr) {
		this.useAtr = useAtr;
	}

	public short getCountAtr() {
		return countAtr;
	}

	public void setCountAtr(short countAtr) {
		this.countAtr = countAtr;
	}

	public String getTotalTimesName() {
		return totalTimesName;
	}

	public void setTotalTimesName(String totalTimesName) {
		this.totalTimesName = totalTimesName;
	}

	public String getTotalTimesAbname() {
		return totalTimesAbname;
	}

	public void setTotalTimesAbname(String totalTimesAbname) {
		this.totalTimesAbname = totalTimesAbname;
	}

	public short getSummaryAtr() {
		return summaryAtr;
	}

	public void setSummaryAtr(short summaryAtr) {
		this.summaryAtr = summaryAtr;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (kshstTotalTimesPK != null ? kshstTotalTimesPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof KshstTotalTimes)) {
			return false;
		}
		KshstTotalTimes other = (KshstTotalTimes) object;
		if ((this.kshstTotalTimesPK == null && other.kshstTotalTimesPK != null)
				|| (this.kshstTotalTimesPK != null && !this.kshstTotalTimesPK.equals(other.kshstTotalTimesPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entity.KshstTotalTimes[ kshstTotalTimesPK=" + kshstTotalTimesPK + " ]";
	}

}
