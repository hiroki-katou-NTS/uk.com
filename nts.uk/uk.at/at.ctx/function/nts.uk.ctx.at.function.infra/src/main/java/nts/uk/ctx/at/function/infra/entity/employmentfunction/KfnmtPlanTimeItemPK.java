/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.function.infra.entity.employmentfunction;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KfnmtPlanTimeItemPK.
 */
@Getter
@Setter
@Embeddable
public class KfnmtPlanTimeItemPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The cid. */
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    
    /** The schedule id. */
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 36)
    @Column(name = "SCHEDULE_ID")
    private String scheduleId;
    
    /** The atd id. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "ATD_ID")
    private Integer atdId;

    /**
     * Instantiates a new kfnst plan time item PK.
     */
    public KfnmtPlanTimeItemPK() {
    }

    /**
     * Instantiates a new kfnst plan time item PK.
     *
     * @param cid the cid
     * @param scheduleId the schedule id
     * @param atdId the atd id
     */
    public KfnmtPlanTimeItemPK(String cid, String scheduleId, Integer atdId) {
        this.cid = cid;
        this.scheduleId = scheduleId;
        this.atdId = atdId;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((atdId == null) ? 0 : atdId.hashCode());
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((scheduleId == null) ? 0 : scheduleId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KfnmtPlanTimeItemPK other = (KfnmtPlanTimeItemPK) obj;
		if (atdId == null) {
			if (other.atdId != null)
				return false;
		} else if (!atdId.equals(other.atdId))
			return false;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (scheduleId == null) {
			if (other.scheduleId != null)
				return false;
		} else if (!scheduleId.equals(other.scheduleId))
			return false;
		return true;
	}
}
