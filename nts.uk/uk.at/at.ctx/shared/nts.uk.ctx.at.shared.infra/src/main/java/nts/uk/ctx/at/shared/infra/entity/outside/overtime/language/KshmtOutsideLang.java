/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outside.overtime.language;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.JpaEntity;

/**
 * The Class KshmtOutsideLangName.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_OUTSIDE_LANG")
public class KshmtOutsideLang extends JpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshst over time lang name PK. */
    @EmbeddedId
    protected KshmtOutsideLangPK kshmtOutsideLangPK;
    
    /** The name. */
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;

    /**
     * Instantiates a new kshst over time lang name.
     */
    public KshmtOutsideLang() {
    }

    /**
     * Instantiates a new kshst over time lang name.
     *
     * @param KshmtOutsideLangPK the kshst over time lang name PK
     */
    public KshmtOutsideLang(KshmtOutsideLangPK kshmtOutsideLangPK) {
        this.kshmtOutsideLangPK = kshmtOutsideLangPK;
    }

    /**
     * Instantiates a new kshst over time lang name.
     *
     * @param cid the cid
     * @param overTimeNo the over time no
     * @param languageId the language id
     */
    public KshmtOutsideLang(String cid, int overTimeNo, String languageId) {
        this.kshmtOutsideLangPK = new KshmtOutsideLangPK(cid, overTimeNo, languageId);
    }

    /**
     * Gets the kshst over time lang name PK.
     *
     * @return the kshst over time lang name PK
     */
    public KshmtOutsideLangPK getKshmtOutsideLangPK() {
        return kshmtOutsideLangPK;
    }

    /**
     * Sets the kshst over time lang name PK.
     *
     * @param KshmtOutsideLangPK the new kshst over time lang name PK
     */
    public void setKshmtOutsideLangPK(KshmtOutsideLangPK kshmtOutsideLangPK) {
        this.kshmtOutsideLangPK = kshmtOutsideLangPK;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtOutsideLangPK != null ? kshmtOutsideLangPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtOutsideLang)) {
			return false;
		}
		KshmtOutsideLang other = (KshmtOutsideLang) object;
		if ((this.kshmtOutsideLangPK == null && other.kshmtOutsideLangPK != null)
				|| (this.kshmtOutsideLangPK != null
						&& !this.kshmtOutsideLangPK.equals(other.kshmtOutsideLangPK))) {
			return false;
		}
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshmtOutsideLangName[ KshmtOutsideLangPK=" + kshmtOutsideLangPK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtOutsideLangPK;
	}
    
}
