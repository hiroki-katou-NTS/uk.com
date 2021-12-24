/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.entity.workrecord.workfixed;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The Class KrcstWorkFixed.
 */
@Getter
@Setter
@Entity
@Table(name = "KRCST_WORK_FIXED")
public class KrcstWorkFixed extends ContractUkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The krcst work fixed PK. */
    @EmbeddedId
    protected KrcstWorkFixedPK krcstWorkFixedPK;
    
    /** The confirm pid. */
    @Column(name = "CONFIRM_PID")
    private String confirmPid;
    
    /** The confirm cls. */
    @Column(name = "CONFIRM_CLS")
    private int confirmCls;
     
    /** The fixed date. */
    @Column(name = "FIXED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fixedDate;
    
    /** The process ym. */
    @Column(name = "PROCESS_YM")
    private int processYm;

    /**
     * Instantiates a new krcst work fixed.
     */
    public KrcstWorkFixed() {
    	super();
    }

    /**
     * Instantiates a new krcst work fixed.
     *
     * @param krcstWorkFixedPK the krcst work fixed PK
     */
    public KrcstWorkFixed(KrcstWorkFixedPK krcstWorkFixedPK) {
        this.krcstWorkFixedPK = krcstWorkFixedPK;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (krcstWorkFixedPK != null ? krcstWorkFixedPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KrcstWorkFixed)) {
            return false;
        }
        KrcstWorkFixed other = (KrcstWorkFixed) object;
        if ((this.krcstWorkFixedPK == null && other.krcstWorkFixedPK != null) || (this.krcstWorkFixedPK != null && !this.krcstWorkFixedPK.equals(other.krcstWorkFixedPK))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {	
		return this.krcstWorkFixedPK;
	}
    
}
