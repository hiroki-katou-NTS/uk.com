package nts.uk.ctx.at.record.infra.entity.divergence.time;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KRCST_DVGC_TIME_EA_MSG database table.
 * 
 */
@Embeddable
public class KrcstDvgcTimeEaMsgPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CID")
	private String cid;

	@Column(name="DVGC_TIME_NO")
	private long dvgcTimeNo;

	public KrcstDvgcTimeEaMsgPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KrcstDvgcTimeEaMsgPK)) {
			return false;
		}
		KrcstDvgcTimeEaMsgPK castOther = (KrcstDvgcTimeEaMsgPK)other;
		return 
			this.cid.equals(castOther.cid)
			&& (this.dvgcTimeNo == castOther.dvgcTimeNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + ((int) (this.dvgcTimeNo ^ (this.dvgcTimeNo >>> 32)));
		
		return hash;
	}
}