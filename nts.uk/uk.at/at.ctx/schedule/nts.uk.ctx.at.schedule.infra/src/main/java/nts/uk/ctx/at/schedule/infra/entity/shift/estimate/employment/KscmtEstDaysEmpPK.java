/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.shift.estimate.employment;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscmtEstDaysEmpPK.
 */
@Getter
@Setter
@Embeddable
public class KscmtEstDaysEmpPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The cid. */
	@Basic(optional = false)
    @NotNull
    @Column(name = "CID")
    private String cid;
    
    /** The empcd. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EMPCD")
    private String empcd;
    
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
     * Instantiates a new kscmt est days emp set PK.
     */
    public KscmtEstDaysEmpPK() {
    }

    /**
     * Instantiates a new kscmt est days emp set PK.
     *
     * @param cid the cid
     * @param empcd the empcd
     * @param targetYear the target year
     * @param targetCls the target cls
     */
    public KscmtEstDaysEmpPK(String cid, String empcd, int targetYear, int targetCls) {
        this.cid = cid;
        this.empcd = empcd;
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
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result + ((empcd == null) ? 0 : empcd.hashCode());
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
		KscmtEstDaysEmpPK other = (KscmtEstDaysEmpPK) obj;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (empcd == null) {
			if (other.empcd != null)
				return false;
		} else if (!empcd.equals(other.empcd))
			return false;
		if (targetCls != other.targetCls)
			return false;
		if (targetYear != other.targetYear)
			return false;
		return true;
	}

    
}
