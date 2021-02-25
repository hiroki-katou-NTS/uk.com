/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.holiday;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.JpaEntity;

/**
 * The Class KshmtHd60hConMed.
 */

@Getter
@Setter
@Entity
@Table(name = "KSHMT_HD60H_CON_MED")
public class KshmtHd60hConMed extends JpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The cid. */
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CID")
    private String cid;
    
    /** The round time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "ROUND_TIME")
    private int roundTime;
    
    /** The rounding. */
    @NotNull
    @Column(name = "ROUNDING")
    private int rounding;
    
    /** The super hd unit. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "SUPER_HD_UNIT")
    private int superHdUnit;
    /**
     * Instantiates a new kshst super hd con med.
     */
    public KshmtHd60hConMed() {
    }

    /**
     * Instantiates a new kshst super hd con med.
     *
     * @param cid the cid
     */
    public KshmtHd60hConMed(String cid) {
        this.cid = cid;
    }

    /**
     * Instantiates a new kshst super hd con med.
     *
     * @param cid the cid
     * @param roundTime the round time
     */
    public KshmtHd60hConMed(String cid, short roundTime) {
        this.cid = cid;
        this.roundTime = roundTime;
    }


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (cid != null ? cid.hashCode() : 0);
		return hash;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof KshmtHd60hConMed)) {
			return false;
		}
		KshmtHd60hConMed other = (KshmtHd60hConMed) object;
		if ((this.cid == null && other.cid != null)
				|| (this.cid != null && !this.cid.equals(other.cid))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KshmtHd60hConMed[ cid=" + cid + " ]";
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.cid;
	}
    
}
