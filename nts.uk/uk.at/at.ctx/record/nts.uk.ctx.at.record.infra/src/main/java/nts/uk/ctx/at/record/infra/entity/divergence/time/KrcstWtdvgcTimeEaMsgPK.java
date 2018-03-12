package nts.uk.ctx.at.record.infra.entity.divergence.time;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KRCST_WTDVGC_TIME_EA_MSG database table.
 * 
 */
@Embeddable
public class KrcstWtdvgcTimeEaMsgPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CID")
	private String cid;

	@Column(name="DVGC_TIME_NO")
	private long dvgcTimeNo;

	@Column(name="WORKTYPE_CD")
	private String worktypeCd;

	public KrcstWtdvgcTimeEaMsgPK() {
	}
	public String getCid() {
		return this.cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public long getDvgcTimeNo() {
		return this.dvgcTimeNo;
	}
	public void setDvgcTimeNo(long dvgcTimeNo) {
		this.dvgcTimeNo = dvgcTimeNo;
	}
	public String getWorktypeCd() {
		return this.worktypeCd;
	}
	public void setWorktypeCd(String worktypeCd) {
		this.worktypeCd = worktypeCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KrcstWtdvgcTimeEaMsgPK)) {
			return false;
		}
		KrcstWtdvgcTimeEaMsgPK castOther = (KrcstWtdvgcTimeEaMsgPK)other;
		return 
			this.cid.equals(castOther.cid)
			&& (this.dvgcTimeNo == castOther.dvgcTimeNo)
			&& this.worktypeCd.equals(castOther.worktypeCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + ((int) (this.dvgcTimeNo ^ (this.dvgcTimeNo >>> 32)));
		hash = hash * prime + this.worktypeCd.hashCode();
		
		return hash;
	}
}