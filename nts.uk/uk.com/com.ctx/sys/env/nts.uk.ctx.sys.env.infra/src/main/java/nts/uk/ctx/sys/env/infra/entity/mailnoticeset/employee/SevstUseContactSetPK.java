/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.infra.entity.mailnoticeset.employee;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class SevstUseContactSetPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Column(name = "CID")
    private String cid;

    /** The sid. */
    @Column(name = "SID")
    private String sid;

	/**
	 * Instantiates a new sevst use contact set PK.
	 */
	public SevstUseContactSetPK() {
	}
	
    /**
     * Instantiates a new sevst use contact set PK.
     *
     * @param cid the cid
     * @param sid the sid
     */
    public SevstUseContactSetPK(String cid, String sid) {
        this.cid = cid;
        this.sid = sid;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (sid != null ? sid.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SevstUseContactSetPK)) {
            return false;
        }
        SevstUseContactSetPK other = (SevstUseContactSetPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
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
        return "javaapplication1.SevstUseContactSetPK[ cid=" + cid + ", sid=" + sid + " ]";
    }
}
