/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class KmfmtMngAnnualSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KMFMT_MNG_ANNUAL_SET")
public class KmfmtMngAnnualSet implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The kmfmt mng annual set PK. */
    @EmbeddedId
    protected KmfmtMngAnnualSetPK kmfmtMngAnnualSetPK;

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
    
    /** The half day mng atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "WORK_DAY_CAL")
    private int workDayCal;
    
    /** The half day mng atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "HALF_DAY_MNG_ATR")
    private int halfDayMngAtr;

    /** The mng reference. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "MNG_REFERENCE")
    private int mngReference;

    /** The c uniform max number. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "C_UNIFORM_MAX_NUMBER")
    private BigDecimal cUniformMaxNumber;

    /** The max day one year. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "MAX_DAY_ONE_YEAR")
    private BigDecimal maxDayOneYear;

    /** The remain day max num. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "REMAIN_DAY_MAX_NUM")
    private int remainDayMaxNum;

    /** The retention year. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "RETENTION_YEAR")
    private int retentionYear;

    /** The year vaca priority. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "YEAR_VACA_PRIORITY")
    private int yearVacaPriority;

    /** The permit type. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "PERMIT_TYPE")
    private int permitType;

    /** The remain num display. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "REMAIN_NUM_DISPLAY")
    private int remainNumDisplay;

    /** The next grant day display. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "NEXT_GRANT_DAY_DISPLAY")
    private int nextGrantDayDisplay;

    /** The time mng atr. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME_MNG_ATR")
    private int timeMngAtr;

    /** The time unit. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME_UNIT")
    private int timeUnit;

    /** The time max day mng. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME_MAX_DAY_MNG")
    private int timeMaxDayMng;

    /** The time mng reference. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME_MNG_REFERENCE")
    private int timeMngReference;

    /** The time mng max day. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME_MNG_MAX_DAY")
    private int timeMngMaxDay;

    /** The enough one day. */
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENOUGH_ONE_DAY")
    private int enoughOneDay;

    /**
     * Instantiates a new kmfmt mng annual set.
     */
    public KmfmtMngAnnualSet() {
    }

    /**
     * Instantiates a new kmfmt mng annual set.
     *
     * @param kmfmtMngAnnualSetPK
     *            the kmfmt mng annual set PK
     */
    public KmfmtMngAnnualSet(KmfmtMngAnnualSetPK kmfmtMngAnnualSetPK) {
        this.kmfmtMngAnnualSetPK = kmfmtMngAnnualSetPK;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kmfmtMngAnnualSetPK != null ? kmfmtMngAnnualSetPK.hashCode() : 0);
        return hash;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KmfmtMngAnnualSet)) {
            return false;
        }
        KmfmtMngAnnualSet other = (KmfmtMngAnnualSet) object;
        if ((this.kmfmtMngAnnualSetPK == null && other.kmfmtMngAnnualSetPK != null)
                || (this.kmfmtMngAnnualSetPK != null && !this.kmfmtMngAnnualSetPK.equals(other.kmfmtMngAnnualSetPK))) {
            return false;
        }
        return true;
    }
}
