/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.holidaysetting.workplace;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtHdpubDPerMWkpPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtHdpubDPerMWkpPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5206367938934072862L;
	
    /** The cid. */
    @Column(name = "CID")
    private String cid;
   
    /** The wkp id. */
    @Column(name = "WKP_ID")
    private String wkpId;
   
    /** The manage year. */
    @Column(name = "MANAGE_YEAR")
    private int manageYear;
   
    /** The month. */
    @Column(name = "MONTH")
    private int month;

    /**
     * Instantiates a new kshmt wkp month day set PK.
     */
    public KshmtHdpubDPerMWkpPK() {
    	super();
    }

    /**
     * Instantiates a new kshmt wkp month day set PK.
     *
     * @param cid the cid
     * @param wkpId the wkp id
     * @param manageYear the manage year
     * @param month the month
     */
    public KshmtHdpubDPerMWkpPK(String cid, String wkpId, int manageYear, int month) {
        this.cid = cid;
        this.wkpId = wkpId;
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
        hash += (wkpId != null ? wkpId.hashCode() : 0);
        hash += (int) manageYear;
        hash += (int) month;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtHdpubDPerMWkpPK)) {
            return false;
        }
        KshmtHdpubDPerMWkpPK other = (KshmtHdpubDPerMWkpPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.wkpId == null && other.wkpId != null) || (this.wkpId != null && !this.wkpId.equals(other.wkpId))) {
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
