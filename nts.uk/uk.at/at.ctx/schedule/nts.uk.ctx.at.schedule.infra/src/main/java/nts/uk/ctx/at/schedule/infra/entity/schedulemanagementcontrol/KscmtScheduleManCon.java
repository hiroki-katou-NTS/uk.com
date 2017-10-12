/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedulemanagementcontrol;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscmtScheduleManCon.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_SCHEDULE_MAN_CON")
public class KscmtScheduleManCon implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The sid. */
    @Id
    @Column(name = "SID")
    private String sid;
    
    /** The schedule man atr. */
    @Column(name = "SCHEDULE_MAN_ATR")
    private int scheduleManAtr;

    /**
     * Instantiates a new kscmt schedule man con.
     */
    public KscmtScheduleManCon() {
    }

    /**
     * Instantiates a new kscmt schedule man con.
     *
     * @param sid the sid
     */
    public KscmtScheduleManCon(String sid) {
        this.sid = sid;
    }


    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sid != null ? sid.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KscmtScheduleManCon)) {
            return false;
        }
        KscmtScheduleManCon other = (KscmtScheduleManCon) object;
        if ((this.sid == null && other.sid != null) || (this.sid != null && !this.sid.equals(other.sid))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "entity.KscmtScheduleManCon[ sid=" + sid + " ]";
    }
    
}
