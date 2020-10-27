/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.holidaysetting.employee;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtHdpubMonthdaysSyaPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtHdpubMonthdaysSyaPK implements Serializable {
    
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6208562785213544225L;
	
    /** The cid. */
    @Column(name = "CID")
    private String cid;
   
    /** The sid. */
    @Column(name = "SID")
    private String sid;
    
    /** The manage year. */
    @Column(name = "MANAGE_YEAR")
    private int manageYear;
   
    /** The month. */
    @Column(name = "MONTH")
    private int month;

    /**
     * Instantiates a new kshmt employee month day set PK.
     */
    public KshmtHdpubMonthdaysSyaPK() {
    	super();
    }

    /**
     * Instantiates a new kshmt employee month day set PK.
     *
     * @param cid the cid
     * @param sid the sid
     * @param manageYear the manage year
     * @param month the month
     */
    public KshmtHdpubMonthdaysSyaPK(String cid, String sid, short manageYear, short month) {
        this.cid = cid;
        this.sid = sid;
        this.manageYear = manageYear;
        this.month = month;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (sid != null ? sid.hashCode() : 0);
        hash += (int) manageYear;
        hash += (int) month;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtHdpubMonthdaysSyaPK)) {
            return false;
        }
        KshmtHdpubMonthdaysSyaPK other = (KshmtHdpubMonthdaysSyaPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
            return false;
        }
        if (this.manageYear != other.manageYear) {
            return false;
        }
        if (this.month != other.month) {
            return false;
        }
        return true;
    }   
}
