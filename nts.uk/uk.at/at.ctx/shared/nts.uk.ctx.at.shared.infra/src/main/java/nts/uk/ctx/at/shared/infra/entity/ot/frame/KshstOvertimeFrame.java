/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.ot.frame;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KshstOvertimeFrame.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHST_OVERTIME_FRAME")
public class KshstOvertimeFrame extends ContractUkJpaEntity implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kshst overtime frame PK. */
    @EmbeddedId
    protected KshstOvertimeFramePK kshstOvertimeFramePK;
    
    /** The exclus ver. */
    @Column(name = "EXCLUS_VER")
    private int exclusVer;
    
    /** The use atr. */
    @Column(name = "USE_ATR")
    private short useAtr;
    
    /** The ot fr name. */
    @Column(name = "OT_FR_NAME")
    private String otFrName;
    
    /** The trans fr name. */
    @Column(name = "TRANS_FR_NAME")
    private String transFrName;

    /**
     * Instantiates a new kshst overtime frame.
     */
    public KshstOvertimeFrame() {
    	super();
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kshstOvertimeFramePK != null ? kshstOvertimeFramePK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshstOvertimeFrame)) {
            return false;
        }
        KshstOvertimeFrame other = (KshstOvertimeFrame) object;
        if ((this.kshstOvertimeFramePK == null && other.kshstOvertimeFramePK != null) || (this.kshstOvertimeFramePK != null && !this.kshstOvertimeFramePK.equals(other.kshstOvertimeFramePK))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.kshstOvertimeFramePK;
	}
    
}
