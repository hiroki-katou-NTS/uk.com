/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscmtSchCreatorPK.
 */
@Getter
@Setter
@Embeddable
public class KscdtScheExeTargetPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The exe id. */
	@Basic(optional = false)
    @NotNull
    @Column(name = "EXE_ID")
    private String exeId;
    
    /** The sid. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "SID")
    private String sid;

    /**
     * Instantiates a new kscmt sch creator PK.
     */
    public KscdtScheExeTargetPK() {
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exeId != null ? exeId.hashCode() : 0);
        hash += (sid != null ? sid.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KscdtScheExeTargetPK)) {
            return false;
        }
        KscdtScheExeTargetPK other = (KscdtScheExeTargetPK) object;
        if ((this.exeId == null && other.exeId != null) || (this.exeId != null && !this.exeId.equals(other.exeId))) {
            return false;
        }
        if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KscmtSchCreatorPK[ exeId=" + exeId + ", sid=" + sid + " ]";
    }


	/**
	 * Instantiates a new kscmt sch creator PK.
	 *
	 * @param exeId the exe id
	 * @param sid the sid
	 */
	public KscdtScheExeTargetPK(String exeId, String sid) {
		super();
		this.exeId = exeId;
		this.sid = sid;
	}
    
}
