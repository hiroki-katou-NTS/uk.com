/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscmtChildCareSch.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_CHILD_CARE_SCH")
public class KscmtChildCareSch implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt child care sch PK. */
    @EmbeddedId
    protected KscmtChildCareSchPK kscmtChildCareSchPK;
    
    /** The str time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "STR_TIME")
    private int strTime;
    
    /** The str day atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "STR_DAY_ATR")
    private int strDayAtr;
    
    /** The end time. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_TIME")
    private int endTime;
    
    /** The end day atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "END_DAY_ATR")
    private int endDayAtr;
    
    /** The child care atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILD_CARE_ATR")
    private int childCareAtr;

    /**
     * Instantiates a new kscmt child care sch.
     */
    public KscmtChildCareSch() {
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscmtChildCareSchPK != null ? kscmtChildCareSchPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KscmtChildCareSch)) {
            return false;
        }
        KscmtChildCareSch other = (KscmtChildCareSch) object;
        if ((this.kscmtChildCareSchPK == null && other.kscmtChildCareSchPK != null) || (this.kscmtChildCareSchPK != null && !this.kscmtChildCareSchPK.equals(other.kscmtChildCareSchPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KscmtChildCareSch[ kscmtChildCareSchPK=" + kscmtChildCareSchPK + " ]";
    }
    
    
}
