/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.plannedyearholiday.frame;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscstPlanYearHdFramePK.
 */
@Getter
@Setter
@Embeddable

public class KscstPlanYearHdFramePK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8132729865943830812L;
	
    /** The cid. */
    @Column(name = "CID")
    private String cid;
   
    /** The plan year hd no. */
    @Column(name = "PLAN_YEAR_HD_NO")
    private short planYearHdNo;

    /**
     * Instantiates a new kscst plan year hd frame PK.
     */
    public KscstPlanYearHdFramePK() {
    	super();
    }

    /**
     * Instantiates a new kscst plan year hd frame PK.
     *
     * @param cid the cid
     * @param planYearHdNo the plan year hd no
     */
    public KscstPlanYearHdFramePK(String cid, short planYearHdNo) {
        this.cid = cid;
        this.planYearHdNo = planYearHdNo;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        hash += (int) planYearHdNo;
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KscstPlanYearHdFramePK)) {
            return false;
        }
        KscstPlanYearHdFramePK other = (KscstPlanYearHdFramePK) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        if (this.planYearHdNo != other.planYearHdNo) {
            return false;
        }
        return true;
    }
}
