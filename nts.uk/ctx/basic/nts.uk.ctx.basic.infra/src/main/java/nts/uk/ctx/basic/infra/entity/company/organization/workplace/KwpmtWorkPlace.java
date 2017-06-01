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
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KwpmtWorkPlace.
 */

@Getter
@Setter
@Entity
@Table(name = "KWPMT_WORK_PLACE")
@XmlRootElement
public class KwpmtWorkPlace extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kwpmt work place PK. */
    @EmbeddedId
    protected KwpmtWorkPlacePK kwpmtWorkPlacePK;
    
    /** The str D. */
    @Column(name = "STR_D")
	@Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate strD;
    
    /** The end D. */
    @Column(name = "END_D")
	@Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate endD;
    
    /** The wkpcd. */
    @Column(name = "WKPCD")
    private String wkpcd;
    
    /** The wkpname. */
    @Column(name = "WKPNAME")
    private String wkpname;

    /**
     * Instantiates a new kwpmt work place.
     */
    public KwpmtWorkPlace() {
    }

    /**
     * Instantiates a new kwpmt work place.
     *
     * @param kwpmtWorkPlacePK the kwpmt work place PK
     */
    public KwpmtWorkPlace(KwpmtWorkPlacePK kwpmtWorkPlacePK) {
        this.kwpmtWorkPlacePK = kwpmtWorkPlacePK;
    }


    /**
     * Instantiates a new kwpmt work place.
     *
     * @param ccid the ccid
     * @param wkpid the wkpid
     */
    public KwpmtWorkPlace(String ccid, String wkpid) {
        this.kwpmtWorkPlacePK = new KwpmtWorkPlacePK(ccid, wkpid);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kwpmtWorkPlacePK != null ? kwpmtWorkPlacePK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof KwpmtWorkPlace)) {
			return false;
		}
		KwpmtWorkPlace other = (KwpmtWorkPlace) object;
		if ((this.kwpmtWorkPlacePK == null && other.kwpmtWorkPlacePK != null)
			|| (this.kwpmtWorkPlacePK != null
				&& !this.kwpmtWorkPlacePK.equals(other.kwpmtWorkPlacePK))) {
			return false;
		}
		return true;
	 }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KwpmtWorkPlace[ kwpmtWorkPlacePK=" + kwpmtWorkPlacePK + " ]";
    }

	@Override
	protected Object getKey() {
		return this.getKwpmtWorkPlacePK();
	}
    
}
