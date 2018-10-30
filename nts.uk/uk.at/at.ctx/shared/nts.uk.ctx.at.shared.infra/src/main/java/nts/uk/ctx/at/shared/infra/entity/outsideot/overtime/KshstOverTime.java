/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.outsideot.overtime;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshstOverTime.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHST_OVER_TIME")
public class KshstOverTime extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshst over time PK. */
    @EmbeddedId
    protected KshstOverTimePK kshstOverTimePK;
    
    /** The is 60 h super hd. */
    @Column(name = "IS_60H_SUPER_HD")
    private int is60hSuperHd;
    
    /** The use atr. */
    @Column(name = "USE_ATR")
    private int useAtr;
    
    /** The name. */
    @Column(name = "NAME")
    private String name;
    
    /** The over time. */
    @Column(name = "OVER_TIME")
    private int overTime;

    /**
     * Instantiates a new kshst over time.
     */
    public KshstOverTime() {
    	super();
    }

    /**
     * Instantiates a new kshst over time.
     *
     * @param kshstOverTimePK the kshst over time PK
     */
    public KshstOverTime(KshstOverTimePK kshstOverTimePK) {
        this.kshstOverTimePK = kshstOverTimePK;
    }


    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshstOverTimePK != null ? kshstOverTimePK.hashCode() : 0);
        return hash;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KshstOverTime)) {
			return false;
		}
		KshstOverTime other = (KshstOverTime) object;
		if ((this.kshstOverTimePK == null && other.kshstOverTimePK != null)
				|| (this.kshstOverTimePK != null
						&& !this.kshstOverTimePK.equals(other.kshstOverTimePK))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstOverTimePK;
	}
    
}
