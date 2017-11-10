/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.entity.employeeinfo;

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
 * The Class KscstScheCreSet.
 */
@Getter
@Setter
@Entity
@Table(name = "KSCST_SCHE_CRE_SET")
public class KscstScheCreSet implements Serializable {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The sid. */
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SID")
    private String sid;
    
    /** The basic cre method. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "BASIC_CRE_METHOD")
    private Integer basicCreMethod;
    
    /** The ref type. */
    @Column(name = "REF_TYPE")
    private Integer refType;
    
    /** The ref bus day cal. */
    @Column(name = "REF_BUS_DAY_CAL")
    private Integer refBusDayCal;
    
    /** The ref basic work. */
    @Column(name = "REF_BASIC_WORK")
    private Integer refBasicWork;
    
    /** The ref working hour. */
    @Column(name = "REF_WORKING_HOUR")
    private Integer refWorkingHour;

    /**
     * Instantiates a new kscmt per sch cre set.
     */
    public KscstScheCreSet() {
    }

    /**
     * Instantiates a new kscmt per sch cre set.
     *
     * @param sid the sid
     */
    public KscstScheCreSet(String sid) {
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
        if (!(object instanceof KscstScheCreSet)) {
            return false;
        }
        KscstScheCreSet other = (KscstScheCreSet) object;
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
        return "entity.KscmtPerSchCreSet[ sid=" + sid + " ]";
    }
    
}
