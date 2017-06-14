/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.infra.entity.company.organization.workplace;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * The Class KwpmtWorkHist.
 */
@Getter
@Setter
@Entity
@Table(name = "KWPMT_WORK_HIST")
public class KwpmtWorkHist implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kwpmt work hist PK. */
    @EmbeddedId
    protected KwpmtWorkHistPK kwpmtWorkHistPK;
    
    /** The str D. */
    @Column(name = "STR_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate strD;
    
    /** The end D. */
    @Column(name = "END_D")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate endD;

    /**
     * Instantiates a new kwpmt work hist.
     */
    public KwpmtWorkHist() {
    }
    

    /**
     * Instantiates a new kwpmt work hist.
     *
     * @param kwpmtWorkHistPK the kwpmt work hist PK
     */
    public KwpmtWorkHist(KwpmtWorkHistPK kwpmtWorkHistPK) {
        this.kwpmtWorkHistPK = kwpmtWorkHistPK;
    }

    /**
     * Instantiates a new kwpmt work hist.
     *
     * @param ccid the ccid
     * @param histId the hist id
     */
    public KwpmtWorkHist(String ccid, String histId) {
        this.kwpmtWorkHistPK = new KwpmtWorkHistPK(ccid, histId);
    }

    /**
     * Gets the kwpmt work hist PK.
     *
     * @return the kwpmt work hist PK
     */
    public KwpmtWorkHistPK getKwpmtWorkHistPK() {
        return kwpmtWorkHistPK;
    }

    /**
     * Sets the kwpmt work hist PK.
     *
     * @param kwpmtWorkHistPK the new kwpmt work hist PK
     */
    public void setKwpmtWorkHistPK(KwpmtWorkHistPK kwpmtWorkHistPK) {
        this.kwpmtWorkHistPK = kwpmtWorkHistPK;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kwpmtWorkHistPK != null ? kwpmtWorkHistPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KwpmtWorkHist)) {
			return false;
		}
		KwpmtWorkHist other = (KwpmtWorkHist) object;
		if ((this.kwpmtWorkHistPK == null && other.kwpmtWorkHistPK != null)
			|| (this.kwpmtWorkHistPK != null
				&& !this.kwpmtWorkHistPK.equals(other.kwpmtWorkHistPK))) {
			return false;
		}
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KwpmtWorkHist[ kwpmtWorkHistPK=" + kwpmtWorkHistPK + " ]";
    }
    
}
