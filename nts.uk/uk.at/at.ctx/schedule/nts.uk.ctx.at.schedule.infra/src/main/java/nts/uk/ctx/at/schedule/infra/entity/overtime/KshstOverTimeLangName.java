/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.overtime;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.JpaEntity;

/**
 * The Class KshstOverTimeLangName.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHST_OVER_TIME_LANG_NAME")
public class KshstOverTimeLangName extends JpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshst over time lang name PK. */
    @EmbeddedId
    protected KshstOverTimeLangNamePK kshstOverTimeLangNamePK;
    
    /** The name. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "NAME")
    private String name;

    /**
     * Instantiates a new kshst over time lang name.
     */
    public KshstOverTimeLangName() {
    }

    /**
     * Instantiates a new kshst over time lang name.
     *
     * @param kshstOverTimeLangNamePK the kshst over time lang name PK
     */
    public KshstOverTimeLangName(KshstOverTimeLangNamePK kshstOverTimeLangNamePK) {
        this.kshstOverTimeLangNamePK = kshstOverTimeLangNamePK;
    }

    /**
     * Instantiates a new kshst over time lang name.
     *
     * @param cid the cid
     * @param overTimeNo the over time no
     * @param languageId the language id
     */
    public KshstOverTimeLangName(String cid, short overTimeNo, String languageId) {
        this.kshstOverTimeLangNamePK = new KshstOverTimeLangNamePK(cid, overTimeNo, languageId);
    }

    /**
     * Gets the kshst over time lang name PK.
     *
     * @return the kshst over time lang name PK
     */
    public KshstOverTimeLangNamePK getKshstOverTimeLangNamePK() {
        return kshstOverTimeLangNamePK;
    }

    /**
     * Sets the kshst over time lang name PK.
     *
     * @param kshstOverTimeLangNamePK the new kshst over time lang name PK
     */
    public void setKshstOverTimeLangNamePK(KshstOverTimeLangNamePK kshstOverTimeLangNamePK) {
        this.kshstOverTimeLangNamePK = kshstOverTimeLangNamePK;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshstOverTimeLangNamePK != null ? kshstOverTimeLangNamePK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshstOverTimeLangName)) {
			return false;
		}
		KshstOverTimeLangName other = (KshstOverTimeLangName) object;
		if ((this.kshstOverTimeLangNamePK == null && other.kshstOverTimeLangNamePK != null)
				|| (this.kshstOverTimeLangNamePK != null
						&& !this.kshstOverTimeLangNamePK.equals(other.kshstOverTimeLangNamePK))) {
			return false;
		}
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshstOverTimeLangName[ kshstOverTimeLangNamePK=" + kshstOverTimeLangNamePK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstOverTimeLangNamePK;
	}
    
}
