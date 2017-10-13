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
 * The Class KscmtChildCareSchedule.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_CHILD_CARE_SCHEDULE")
public class KscmtChildCareSchedule implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The kscmt child care schedule PK. */
    @EmbeddedId
    protected KscmtChildCareSchedulePK kscmtChildCareSchedulePK;
    
    /** The schcare time start. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "SCHCARE_TIME_START")
    private int schcareTimeStart;
    
    /** The schcare dayatr start. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "SCHCARE_DAYATR_START")
    private int schcareDayatrStart;
    
    /** The schcare time end. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "SCHCARE_TIME_END")
    private int schcareTimeEnd;
    
    /** The schcare dayatr end. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "SCHCARE_DAYATR_END")
    private int schcareDayatrEnd;
    
    /** The child care atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "CHILD_CARE_ATR")
    private int childCareAtr;

    /**
     * Instantiates a new kscmt child care schedule.
     */
    public KscmtChildCareSchedule() {
    }

    /**
     * Instantiates a new kscmt child care schedule.
     *
     * @param kscmtChildCareSchedulePK the kscmt child care schedule PK
     */
    public KscmtChildCareSchedule(KscmtChildCareSchedulePK kscmtChildCareSchedulePK) {
        this.kscmtChildCareSchedulePK = kscmtChildCareSchedulePK;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kscmtChildCareSchedulePK != null ? kscmtChildCareSchedulePK.hashCode() : 0);
        return hash;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KscmtChildCareSchedule)) {
			return false;
		}
		KscmtChildCareSchedule other = (KscmtChildCareSchedule) object;
		if ((this.kscmtChildCareSchedulePK == null && other.kscmtChildCareSchedulePK != null)
				|| (this.kscmtChildCareSchedulePK != null
						&& !this.kscmtChildCareSchedulePK.equals(other.kscmtChildCareSchedulePK))) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KscmtChildCareSchedule[ kscmtChildCareSchedulePK=" + kscmtChildCareSchedulePK
				+ " ]";
	}

}
