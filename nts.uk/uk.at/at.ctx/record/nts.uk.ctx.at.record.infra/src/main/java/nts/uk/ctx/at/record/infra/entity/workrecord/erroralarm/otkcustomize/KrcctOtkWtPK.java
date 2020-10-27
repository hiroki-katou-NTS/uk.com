package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.otkcustomize;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the KRCMT_OTK_HD_CK_WKTP_NTGT database table.
 * 
 */
@Embeddable
public class KrcctOtkWtPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CID")
	public String cid;

	@Column(name="WORKTYPE_CD")
	public String worktypeCd;

	public KrcctOtkWtPK() {
	}

	public KrcctOtkWtPK(String cid, String worktypeCd) {
		super();
		this.cid = cid;
		this.worktypeCd = worktypeCd;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KrcctOtkWtPK)) {
			return false;
		}
		KrcctOtkWtPK castOther = (KrcctOtkWtPK)other;
		return 
			this.cid.equals(castOther.cid)
			&& this.worktypeCd.equals(castOther.worktypeCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + this.worktypeCd.hashCode();
		
		return hash;
	}
}