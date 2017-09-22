/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.infra.entity.workplace;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;


@Embeddable
@Getter
@Setter
public class BsymtWorkplaceHistPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
    @Column(name = "CID")
    private String cid;
	
    @NotNull
    @Column(name = "WKPID")
    private String wkpid;
    
    @NotNull
    @Column(name = "HISTORY_ID")
    private String historyId;

	public BsymtWorkplaceHistPK() {
	}

	public BsymtWorkplaceHistPK(String cid, String wkpid, String historyId) {
        this.cid = cid;
        this.wkpid = wkpid;
        this.historyId = historyId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (wkpid != null ? wkpid.hashCode() : 0);
        hash += (historyId != null ? historyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof BsymtWorkplaceHistPK)) {
            return false;
        }
        BsymtWorkplaceHistPK other = (BsymtWorkplaceHistPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.wkpid == null && other.wkpid != null) || (this.wkpid != null && !this.wkpid.equals(other.wkpid))) {
            return false;
        }
        if ((this.historyId == null && other.historyId != null) || (this.historyId != null && !this.historyId.equals(other.historyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.BsymtWorkplacePK[ cid=" + cid + ", wkpid=" + wkpid + ", historyId=" + historyId + " ]";
    }
    
}
