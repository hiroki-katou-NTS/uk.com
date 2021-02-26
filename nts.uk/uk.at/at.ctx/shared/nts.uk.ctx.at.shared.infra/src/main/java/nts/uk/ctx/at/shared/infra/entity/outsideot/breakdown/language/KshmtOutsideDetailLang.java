/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.language;

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
 * The Class KshstOverTimeLangBrd.
 */

@Getter
@Setter
@Entity
@Table(name = "KSHMT_OUTSIDE_DETAIL_LANG")
public class KshmtOutsideDetailLang extends JpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshst over time lang brd PK. */
    @EmbeddedId
    protected KshstOutsideOtBrdLangPK kshstOutsideOtBrdLangPK;
    
    /** The name. */
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;

    /**
     * Instantiates a new kshst over time lang brd.
     */
    public KshmtOutsideDetailLang() {
    }

    /**
     * Instantiates a new kshst over time lang brd.
     *
     * @param kshstOverTimeLangBrdPK the kshst over time lang brd PK
     */
    public KshmtOutsideDetailLang(KshstOutsideOtBrdLangPK kshstOverTimeLangBrdPK) {
        this.kshstOutsideOtBrdLangPK = kshstOverTimeLangBrdPK;
    }

    /**
     * Instantiates a new kshst over time lang brd.
     *
     * @param kshstOverTimeLangBrdPK the kshst over time lang brd PK
     * @param exclusVer the exclus ver
     * @param name the name
     */
	public KshmtOutsideDetailLang(KshstOutsideOtBrdLangPK kshstOverTimeLangBrdPK, String name) {
		this.kshstOutsideOtBrdLangPK = kshstOverTimeLangBrdPK;
		this.name = name;
	}

    /**
     * Instantiates a new kshst over time lang brd.
     *
     * @param cid the cid
     * @param brdItemNo the brd item no
     * @param languageId the language id
     */
    public KshmtOutsideDetailLang(String cid, short brdItemNo, String languageId) {
        this.kshstOutsideOtBrdLangPK = new KshstOutsideOtBrdLangPK(cid, brdItemNo, languageId);
    }

   

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshstOutsideOtBrdLangPK != null ? kshstOutsideOtBrdLangPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtOutsideDetailLang)) {
			return false;
		}
		KshmtOutsideDetailLang other = (KshmtOutsideDetailLang) object;
		if ((this.kshstOutsideOtBrdLangPK == null && other.kshstOutsideOtBrdLangPK != null)
				|| (this.kshstOutsideOtBrdLangPK != null
						&& !this.kshstOutsideOtBrdLangPK.equals(other.kshstOutsideOtBrdLangPK))) {
			return false;
		}
		return true;
	}

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KshstOverTimeLangBrd[ kshstOverTimeLangBrdPK=" + kshstOutsideOtBrdLangPK + " ]";
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstOutsideOtBrdLangPK;
	}
	
    
}
