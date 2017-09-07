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
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.JpaEntity;

/**
 * The Class KshstOverTimeLangBrd.
 */

@Getter
@Setter
@Entity
@Table(name = "KSHST_OVER_TIME_LANG_BRD")
public class KshstOverTimeLangBrd extends JpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshst over time lang brd PK. */
    @EmbeddedId
    protected KshstOverTimeLangBrdPK kshstOverTimeLangBrdPK;
    
    /** The name. */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "NAME")
    private String name;

    /**
     * Instantiates a new kshst over time lang brd.
     */
    public KshstOverTimeLangBrd() {
    }

    /**
     * Instantiates a new kshst over time lang brd.
     *
     * @param kshstOverTimeLangBrdPK the kshst over time lang brd PK
     */
    public KshstOverTimeLangBrd(KshstOverTimeLangBrdPK kshstOverTimeLangBrdPK) {
        this.kshstOverTimeLangBrdPK = kshstOverTimeLangBrdPK;
    }

    /**
     * Instantiates a new kshst over time lang brd.
     *
     * @param kshstOverTimeLangBrdPK the kshst over time lang brd PK
     * @param exclusVer the exclus ver
     * @param name the name
     */
	public KshstOverTimeLangBrd(KshstOverTimeLangBrdPK kshstOverTimeLangBrdPK, String name) {
		this.kshstOverTimeLangBrdPK = kshstOverTimeLangBrdPK;
		this.name = name;
	}

    /**
     * Instantiates a new kshst over time lang brd.
     *
     * @param cid the cid
     * @param brdItemNo the brd item no
     * @param languageId the language id
     */
    public KshstOverTimeLangBrd(String cid, short brdItemNo, String languageId) {
        this.kshstOverTimeLangBrdPK = new KshstOverTimeLangBrdPK(cid, brdItemNo, languageId);
    }

   

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshstOverTimeLangBrdPK != null ? kshstOverTimeLangBrdPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshstOverTimeLangBrd)) {
			return false;
		}
		KshstOverTimeLangBrd other = (KshstOverTimeLangBrd) object;
		if ((this.kshstOverTimeLangBrdPK == null && other.kshstOverTimeLangBrdPK != null)
				|| (this.kshstOverTimeLangBrdPK != null
						&& !this.kshstOverTimeLangBrdPK.equals(other.kshstOverTimeLangBrdPK))) {
			return false;
		}
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshstOverTimeLangBrd[ kshstOverTimeLangBrdPK=" + kshstOverTimeLangBrdPK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstOverTimeLangBrdPK;
	}
	
    
}
