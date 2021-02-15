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
 * The Class KshstOverTimeLangName.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHST_OVER_TIME_NAME_LANG")
public class KshstOverTimeNameLang extends JpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshst over time lang name PK. */
    @EmbeddedId
    protected KshstOverTimeNameLangPK kshstOverTimeNameLangPK;
    
    /** The name. */
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;

    /**
     * Instantiates a new kshst over time lang name.
     */
    public KshstOverTimeNameLang() {
    }

    /**
     * Instantiates a new kshst over time lang name.
     *
     * @param KshstOverTimeNameLangPK the kshst over time lang name PK
     */
    public KshstOverTimeNameLang(KshstOverTimeNameLangPK kshstOverTimeNameLangPK) {
        this.kshstOverTimeNameLangPK = kshstOverTimeNameLangPK;
    }

    /**
     * Instantiates a new kshst over time lang name.
     *
     * @param cid the cid
     * @param overTimeNo the over time no
     * @param languageId the language id
     */
    public KshstOverTimeNameLang(String cid, int overTimeNo, String languageId) {
        this.kshstOverTimeNameLangPK = new KshstOverTimeNameLangPK(cid, overTimeNo, languageId);
    }

    /**
     * Gets the kshst over time lang name PK.
     *
     * @return the kshst over time lang name PK
     */
    public KshstOverTimeNameLangPK getKshstOverTimeNameLangPK() {
        return kshstOverTimeNameLangPK;
    }

    /**
     * Sets the kshst over time lang name PK.
     *
     * @param KshstOverTimeNameLangPK the new kshst over time lang name PK
     */
    public void setKshstOverTimeNameLangPK(KshstOverTimeNameLangPK kshstOverTimeNameLangPK) {
        this.kshstOverTimeNameLangPK = kshstOverTimeNameLangPK;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshstOverTimeNameLangPK != null ? kshstOverTimeNameLangPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshstOverTimeNameLang)) {
			return false;
		}
		KshstOverTimeNameLang other = (KshstOverTimeNameLang) object;
		if ((this.kshstOverTimeNameLangPK == null && other.kshstOverTimeNameLangPK != null)
				|| (this.kshstOverTimeNameLangPK != null
						&& !this.kshstOverTimeNameLangPK.equals(other.kshstOverTimeNameLangPK))) {
			return false;
		}
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshstOverTimeLangName[ KshstOverTimeNameLangPK=" + kshstOverTimeNameLangPK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstOverTimeNameLangPK;
	}
    
}
