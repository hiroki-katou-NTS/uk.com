package nts.uk.ctx.at.record.infra.entity.divergence.reason;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KRCST_DVGC_REASON database table.
 * 
 */
@Embeddable
public class KrcstDvgcReasonPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="[NO]")
	private long no;

	@Column(name="CID")
	private String cid;

	@Column(name="REASON_CODE")
	private String reasonCode;

	public KrcstDvgcReasonPK() {
	}
	public long getNo() {
		return this.no;
	}
	public void setNo(long no) {
		this.no = no;
	}
	public String getCid() {
		return this.cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getReasonCode() {
		return this.reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KrcstDvgcReasonPK)) {
			return false;
		}
		KrcstDvgcReasonPK castOther = (KrcstDvgcReasonPK)other;
		return 
			(this.no == castOther.no)
			&& this.cid.equals(castOther.cid)
			&& this.reasonCode.equals(castOther.reasonCode);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.no ^ (this.no >>> 32)));
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + this.reasonCode.hashCode();
		
		return hash;
	}
}