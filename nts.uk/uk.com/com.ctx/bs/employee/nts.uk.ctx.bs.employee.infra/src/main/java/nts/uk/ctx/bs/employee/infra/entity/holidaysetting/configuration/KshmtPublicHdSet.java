/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nts.uk.ctx.bs.employee.infra.entity.holidaysetting.configuration;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KshmtPublicHdSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSHMT_PUBLIC_HD_SET")
public class KshmtPublicHdSet extends UkJpaEntity implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
   
    /** The exclus ver. */
    @Column(name = "EXCLUS_VER")
    private int exclusVer;

    /** The cid. */
    @Id
    @Column(name = "CID")
    private String cid;

    /** The is manage com public hd. */
    @Column(name = "IS_MANAGE_COM_PUBLIC_HD")
    private int isManageComPublicHd;
   
    /** The public hd manage atr. */
    @Column(name = "PUBLIC_HD_MANAGE_ATR")
    private int publicHdManageAtr;
    
    /** The period. */
    @Column(name = "PERIOD")
    private int period;
   
    /** The full date. */
    @Column(name = "FULL_DATE")
    @Temporal(TemporalType.DATE)
    private Date fullDate;
   
    /** The day month. */
    @Column(name = "DAY_MONTH")
    private int dayMonth;
    
    /** The determine start D. */
    @Column(name = "DETERMINE_START_D")
    private int determineStartD;
    
    /** The is weekly hd check. */
    @Column(name = "IS_WEEKLY_HD_CHECK")
    private int isWeeklyHdCheck;

    /**
     * Instantiates a new kshmt public hd set.
     */
    public KshmtPublicHdSet() {
    	super();
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KshmtPublicHdSet)) {
            return false;
        }
        KshmtPublicHdSet other = (KshmtPublicHdSet) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

	/* (non-Javadoc)
	 * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
	 */
	@Override
	protected Object getKey() {
		return this.cid;
	}
    
}
