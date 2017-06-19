/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KclmtCompensLeaveEmpPK.
 */
@Setter
@Getter
@Embeddable
public class KclmtAcquisitionEmpPK implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The cid. */
    @Basic(optional = false)
    @Column(name = "CID")
    private String cid;

    /** The empcd. */
    @Basic(optional = false)
    @Column(name = "EMPCD")
    private String empcd;

    /**
     * Instantiates a new kclmt compens leave emp PK.
     */
    public KclmtAcquisitionEmpPK() {
    }

    /**
     * Instantiates a new kclmt compens leave emp PK.
     *
     * @param cid
     *            the cid
     * @param empcd
     *            the empcd
     */
    public KclmtAcquisitionEmpPK(String cid, String empcd) {
        this.cid = cid;
        this.empcd = empcd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (empcd != null ? empcd.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KclmtAcquisitionEmpPK)) {
            return false;
        }
        KclmtAcquisitionEmpPK other = (KclmtAcquisitionEmpPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.empcd == null && other.empcd != null) || (this.empcd != null && !this.empcd.equals(other.empcd))) {
            return false;
        }
        return true;
    }

}
