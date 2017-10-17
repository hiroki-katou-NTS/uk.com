/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.schedulemanagementcontrol;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KscmtSchManaContr.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCMT_SCH_MANAGE_CTR")
public class KscmtSchManaageCtr implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The sid. */
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SID")
    private String sid;
    
    /** The sch manage atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "SCH_MANAGE_ATR")
    private int schManageAtr;

    /**
     * Instantiates a new kscmt sch mana contr.
     */
    public KscmtSchManaageCtr() {
    }

    /**
     * Instantiates a new kscmt sch mana contr.
     *
     * @param sid the sid
     */
    public KscmtSchManaageCtr(String sid) {
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
        if (!(object instanceof KscmtSchManaageCtr)) {
            return false;
        }
        KscmtSchManaageCtr other = (KscmtSchManaageCtr) object;
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
        return "entity.KscmtSchManaContr[ sid=" + sid + " ]";
    }
    
    
}
