package nts.uk.ctx.at.request.infra.entity.valication.history;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

/**
 * The primary key class for the KRQMT_VACATION_HISTORY database table.
 * 
 */
@Embeddable
@Getter
@Setter
public class KrqmtVacationHistoryPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="CID")
	private String cid;

	@Column(name="HISTORY_ID")
	private String historyId;

	@Column(name="WORKTYPE_CD")
	private String worktypeCd;

	public KrqmtVacationHistoryPK() {
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof KrqmtVacationHistoryPK)) {
			return false;
		}
		KrqmtVacationHistoryPK castOther = (KrqmtVacationHistoryPK)other;
		return 
			this.cid.equals(castOther.cid)
			&& this.historyId.equals(castOther.historyId)
			&& this.worktypeCd.equals(castOther.worktypeCd);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + this.historyId.hashCode();
		hash = hash * prime + this.worktypeCd.hashCode();
		
		return hash;
	}
}