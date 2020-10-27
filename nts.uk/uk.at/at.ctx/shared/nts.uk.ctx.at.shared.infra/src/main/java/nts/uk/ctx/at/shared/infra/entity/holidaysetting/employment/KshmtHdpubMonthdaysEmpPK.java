/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.at.shared.infra.entity.holidaysetting.employment;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KshmtHdpubMonthdaysEmpPK.
 */
@Getter
@Setter
@Embeddable
public class KshmtHdpubMonthdaysEmpPK implements Serializable {
  
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3004996840253370805L;
	
    /** The cid. */
    @Column(name = "CID")
    private String cid;
   
    /** The emp cd. */
    @Column(name = "EMP_CD")
    private String empCd;
   
    /** The manage year. */
    @Column(name = "MANAGE_YEAR")
    private int manageYear;
   
    /** The month. */
    @Column(name = "MONTH")
    private int month;

    /**
     * Instantiates a new kshmt emp month day set PK.
     */
    public KshmtHdpubMonthdaysEmpPK() {
    	super();
    }

    /**
     * Instantiates a new kshmt emp month day set PK.
     *
     * @param cid the cid
     * @param empCd the emp cd
     * @param manageYear the manage year
     * @param month the month
     */
    public KshmtHdpubMonthdaysEmpPK(String cid, String empCd, int manageYear, int month) {
        this.cid = cid;
        this.empCd = empCd;
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
        hash += (empCd != null ? empCd.hashCode() : 0);
        hash += (int) manageYear;
        hash += (int) month;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtHdpubMonthdaysEmpPK)) {
            return false;
        }
        KshmtHdpubMonthdaysEmpPK other = (KshmtHdpubMonthdaysEmpPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.empCd == null && other.empCd != null) || (this.empCd != null && !this.empCd.equals(other.empCd))) {
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
