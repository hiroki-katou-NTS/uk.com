/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class BsydtWkpConfigInfoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
    @Column(name = "CID")
    private String cid;

	@NotNull
    @Column(name = "HISTORY_ID")
    private String historyId;

	public BsydtWkpConfigInfoPK() {
	}

	public BsydtWkpConfigInfoPK(String cid, String historyId) {
        this.cid = cid;
        this.historyId = historyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (historyId != null ? historyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BsydtWkpConfigInfoPK)) {
            return false;
        }
        BsydtWkpConfigInfoPK other = (BsydtWkpConfigInfoPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.historyId == null && other.historyId != null) || (this.historyId != null && !this.historyId.equals(other.historyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BsydtWkpConfigInfoPK[ cid=" + cid + ", historyId=" + historyId + " ]";
    }
    
}
