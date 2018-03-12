package nts.uk.ctx.at.record.infra.entity.divergence.time;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KRCST_DRT database table.
 * 
 */
@Embeddable
public class KrcstDrtPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="HIST_ID")
	private String histId;

	@Column(name="DVGC_TIME_NO")
	private long dvgcTimeNo;

	public KrcstDrtPK() {
	}
	public String getHistId() {
		return this.histId;
	}
	public void setHistId(String histId) {
		this.histId = histId;
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
		if (!(other instanceof KrcstDrtPK)) {
			return false;
		}
		KrcstDrtPK castOther = (KrcstDrtPK)other;
		return 
			this.histId.equals(castOther.histId)
			&& (this.dvgcTimeNo == castOther.dvgcTimeNo);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.histId.hashCode();
		hash = hash * prime + ((int) (this.dvgcTimeNo ^ (this.dvgcTimeNo >>> 32)));
		
		return hash;
	}
}