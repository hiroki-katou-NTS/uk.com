/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.holidaysetting.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtHdpubDPerMComPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtHdpubDPerMComPK implements Serializable {
    
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2744276512513153833L;
	
    /** The cid. */
    @Column(name = "CID")
    private String cid;
   
    /** The manage year. */
    @Column(name = "MANAGE_YEAR")
    private int manageYear;
   
    /** The month. */
    @Column(name = "MONTH")
    private int month;

    /**
     * Instantiates a new kshmt com month day set PK.
     */
    public KshmtHdpubDPerMComPK() {
    	super();
    }

    /**
     * Instantiates a new kshmt com month day set PK.
     *
     * @param cid the cid
     * @param manageYear the manage year
     * @param month the month
     */
    public KshmtHdpubDPerMComPK(String cid, short manageYear, short month) {
        this.cid = cid;
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
        hash += (int) manageYear;
        hash += (int) month;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshmtHdpubDPerMComPK)) {
            return false;
        }
        KshmtHdpubDPerMComPK other = (KshmtHdpubDPerMComPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
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
