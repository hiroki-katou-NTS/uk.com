/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.executionlog;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

/**
 * The Class KscmtScheduleErrLogPK.
 */
@Getter
@Setter
@Embeddable
public class KscdtScheErrLogPK implements Serializable {
    
    /** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The exe id. */
    @Column(name = "EXE_ID")
    private String exeId;
    
    /** The sid. */
    @Column(name = "SID")
    private String sid;
    
    /** The ymd. */
    @Column(name = "YMD")
    @Convert(converter = GeneralDateToDBConverter.class)
    private GeneralDate ymd;

    /**
     * Instantiates a new kscmt schedule err log PK.
     */
    public KscdtScheErrLogPK() {
    }

    /**
     * Instantiates a new kscmt schedule err log PK.
     *
     * @param exeId the exe id
     * @param sid the sid
     * @param ymd the ymd
     */
    public KscdtScheErrLogPK(String exeId, String sid, GeneralDate ymd) {
        this.exeId = exeId;
        this.sid = sid;
        this.ymd = ymd;
    }


    /**
     * Gets the exe id.
     *
     * @return the exe id
     */
    public String getExeId() {
        return exeId;
    }

    /**
     * Sets the exe id.
     *
     * @param exeId the new exe id
     */
    public void setExeId(String exeId) {
        this.exeId = exeId;
    }

    /**
     * Gets the sid.
     *
     * @return the sid
     */
    public String getSid() {
        return sid;
    }

    /**
     * Sets the sid.
     *
     * @param sid the new sid
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (exeId != null ? exeId.hashCode() : 0);
        hash += (sid != null ? sid.hashCode() : 0);
        return hash;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof KscdtScheErrLogPK)) {
			return false;
		}
		KscdtScheErrLogPK other = (KscdtScheErrLogPK) object;
		if ((this.exeId == null && other.exeId != null)
				|| (this.exeId != null && !this.exeId.equals(other.exeId))) {
			return false;
		}
		if ((this.sid == null && other.sid != null)
				|| (this.sid != null && !this.sid.equals(other.sid))) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "entity.KscmtScheduleErrLogPK[ exeId=" + exeId + ", sid=" + sid + " ]";
	}
	
    
}
