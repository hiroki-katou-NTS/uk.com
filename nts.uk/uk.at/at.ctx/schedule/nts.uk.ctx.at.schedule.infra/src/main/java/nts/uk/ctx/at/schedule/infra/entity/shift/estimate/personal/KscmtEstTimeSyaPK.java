/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.personal;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscmtEstTimeSyaPK.
 */
@Getter
@Setter
@Embeddable
public class KscmtEstTimeSyaPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The sid. */
	@Basic(optional = false)
    @NotNull
    @Column(name = "SID")
    private String sid;
    
    /** The target year. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "TARGET_YEAR")
    private int targetYear;
    
    /** The target cls. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "TARGET_CLS")
    private int targetCls;

    /**
     * Instantiates a new kscmt est time per set PK.
     */
    public KscmtEstTimeSyaPK() {
    }

    /**
     * Instantiates a new kscmt est time per set PK.
     *
     * @param sid the sid
     * @param targetYear the target year
     * @param targetCls the target cls
     */
    public KscmtEstTimeSyaPK(String sid, int targetYear, int targetCls) {
        this.sid = sid;
        this.targetYear = targetYear;
        this.targetCls = targetCls;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sid == null) ? 0 : sid.hashCode());
		result = prime * result + targetCls;
		result = prime * result + targetYear;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KscmtEstTimeSyaPK other = (KscmtEstTimeSyaPK) obj;
		if (sid == null) {
			if (other.sid != null)
				return false;
		} else if (!sid.equals(other.sid))
			return false;
		if (targetCls != other.targetCls)
			return false;
		if (targetYear != other.targetYear)
			return false;
		return true;
	}

}
