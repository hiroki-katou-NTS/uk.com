/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.infra.entity.vacation.setting.annualpaidleave;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KmfmtMngAnnualSetPK.
 */
@Setter
@Getter
@Embeddable
public class KmfmtMngAnnualSetPK implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The cid. */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;

    /** The mng id. */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "MNG_ID")
    private String mngId;

    /**
     * Instantiates a new kmfmt mng annual set PK.
     */
    public KmfmtMngAnnualSetPK() {
    }

    /**
     * Instantiates a new kmfmt mng annual set PK.
     *
     * @param cid
     *            the cid
     * @param mngId
     *            the mng id
     */
    public KmfmtMngAnnualSetPK(String cid, String mngId) {
        this.cid = cid;
        this.mngId = mngId;
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
        hash += (mngId != null ? mngId.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KmfmtMngAnnualSetPK)) {
            return false;
        }
        KmfmtMngAnnualSetPK other = (KmfmtMngAnnualSetPK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if ((this.mngId == null && other.mngId != null) || (this.mngId != null && !this.mngId.equals(other.mngId))) {
            return false;
        }
        return true;
    }
}
