/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.request.infra.entity.valication.history;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KrqmtVacationHistoryPK.
 */
@Embeddable
@Getter

@Setter
public class KrqmtVacationHistoryPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name="CID")
	private String cid;

	/** The history id. */
	@Column(name="HISTORY_ID")
	private String historyId;

	/** The worktype cd. */
	@Column(name="WORKTYPE_CD")
	private String worktypeCd;

	/**
	 * Instantiates a new krqmt vacation history PK.
	 */
	public KrqmtVacationHistoryPK() {
    	super();
    }

	/**
	 * Instantiates a new krqmt vacation history PK.
	 *
	 * @param cid the cid
	 * @param histId the hist id
	 * @param workTypeCode the work type code
	 */
	public KrqmtVacationHistoryPK(String cid, String histId, String workTypeCode) {
        this.cid = cid;
        this.historyId = histId;
        this.worktypeCd = workTypeCode; 
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.cid.hashCode();
		hash = hash * prime + this.historyId.hashCode();
		hash = hash * prime + this.worktypeCd.hashCode();
		
		return hash;
	}
}