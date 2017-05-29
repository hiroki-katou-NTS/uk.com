/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KmfmtOccurVacationSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KMFMT_OCCUR_VACATION_SET")
public class KmfmtOccurVacationSet implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The ins date. */
    @Column(name = "INS_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date insDate;

    /** The ins ccd. */
    @Size(max = 4)
    @Column(name = "INS_CCD")
    private String insCcd;

    /** The ins scd. */
    @Size(max = 12)
    @Column(name = "INS_SCD")
    private String insScd;

    /** The ins pg. */
    @Size(max = 14)
    @Column(name = "INS_PG")
    private String insPg;

    /** The upd date. */
    @Column(name = "UPD_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updDate;

    /** The upd ccd. */
    @Size(max = 4)
    @Column(name = "UPD_CCD")
    private String updCcd;

    /** The upd scd. */
    @Size(max = 12)
    @Column(name = "UPD_SCD")
    private String updScd;

    /** The upd pg. */
    @Size(max = 14)
    @Column(name = "UPD_PG")
    private String updPg;

    /** The exclus ver. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXCLUS_VER")
    private int exclusVer;

    /** The cid. */
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;

    /** The occurr division. */
    @Basic(optional = false)
    @Column(name = "OCCURR_DIVISION")
    private Integer occurrDivision;

    /** The transf division. */
    @Basic(optional = false)
    @Column(name = "TRANSF_DIVISION")
    private Integer transfDivision;

    /** The one day time. */
    @Size(max = 8)
    @Column(name = "ONE_DAY_TIME")
    private String oneDayTime;

    /** The half day time. */
    @Size(max = 8)
    @Column(name = "HALF_DAY_TIME")
    private String halfDayTime;

    /** The certain time. */
    @Size(max = 8)
    @Column(name = "CERTAIN_TIME")
    private String certainTime;

    /**
     * Instantiates a new kmfmt occur vacation set.
     */
    public KmfmtOccurVacationSet() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KmfmtOccurVacationSet)) {
            return false;
        }
        KmfmtOccurVacationSet other = (KmfmtOccurVacationSet) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }
}
