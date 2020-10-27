/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.workdayoff.frame;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "KSHMT_HD_WORK_FRAME")
public class KshmtHdWorkFrame extends ContractUkJpaEntity implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshst workdayoff frame PK. */
    @EmbeddedId
    protected KshmtHdWorkFramePK kshmtHdWorkFramePK;
    
    /** The exclus ver. */
    @Column(name = "EXCLUS_VER")
    private int exclusVer;
  
    /** The use atr. */
    @Column(name = "USE_ATR")
    private short useAtr;
   
    /** The wdo fr name. */
    @Column(name = "WDO_FR_NAME")
    private String wdoFrName;
   
    /** The trans fr name. */
    @Column(name = "TRANS_FR_NAME")
    private String transFrName;

    /** The role. */
    @Column(name = "ROLE")
    private short role;
    
    /**
     * Instantiates a new kshst workdayoff frame.
     */
    public KshmtHdWorkFrame() {
    	super();
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshmtHdWorkFramePK != null ? kshmtHdWorkFramePK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KshmtHdWorkFrame)) {
            return false;
        }
        KshmtHdWorkFrame other = (KshmtHdWorkFrame) object;
        if ((this.kshmtHdWorkFramePK == null && other.kshmtHdWorkFramePK != null) || (this.kshmtHdWorkFramePK != null && !this.kshmtHdWorkFramePK.equals(other.kshmtHdWorkFramePK))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshmtHdWorkFramePK;
	}
    
}
