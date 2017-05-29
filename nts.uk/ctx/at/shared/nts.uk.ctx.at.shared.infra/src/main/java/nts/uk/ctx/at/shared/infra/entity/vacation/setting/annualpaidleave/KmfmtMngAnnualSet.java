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
 * The Class KmfmtMngAnnualSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KMFMT_MNG_ANNUAL_SET")
public class KmfmtMngAnnualSet implements Serializable {

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
    
    /** The cid. */
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "CID")
    private String cid;
    
    /** The half day mng atr. */
    @Basic(optional = false)
    @Column(name = "WORK_DAY_CAL")
    private Integer workDayCal;
    
    /** The half day mng atr. */
    @Basic(optional = false)
    @Column(name = "HALF_DAY_MNG_ATR")
    private Integer halfDayMngAtr;

    /** The mng reference. */
    @Basic(optional = false)
    @Column(name = "MNG_REFERENCE")
    private Integer mngReference;

    /** The c uniform max number. */
    @Basic(optional = false)
    @Column(name = "C_UNIFORM_MAX_NUMBER")
    private BigDecimal cUniformMaxNumber;

    /** The max day one year. */
    @Basic(optional = false)
    @Column(name = "MAX_DAY_ONE_YEAR")
    private BigDecimal maxDayOneYear;

    /** The remain day max num. */
    @Basic(optional = false)
    @Column(name = "REMAIN_DAY_MAX_NUM")
    private Integer remainDayMaxNum;

    /** The retention year. */
    @Basic(optional = false)
    @Column(name = "RETENTION_YEAR")
    private Integer retentionYear;

    /** The year vaca priority. */
    @Basic(optional = false)
    @Column(name = "YEAR_VACA_PRIORITY")
    private Integer yearVacaPriority;

    /** The permit type. */
    @Basic(optional = false)
    @Column(name = "PERMIT_TYPE")
    private Integer permitType;

    /** The remain num display. */
    @Basic(optional = false)
    @Column(name = "REMAIN_NUM_DISPLAY")
    private Integer remainNumDisplay;

    /** The next grant day display. */
    @Basic(optional = false)
    @Column(name = "NEXT_GRANT_DAY_DISPLAY")
    private Integer nextGrantDayDisplay;

    /** The time mng atr. */
    @Basic(optional = false)
    @Column(name = "TIME_MNG_ATR")
    private Integer timeMngAtr;

    /** The time unit. */
    @Basic(optional = false)
    @Column(name = "TIME_UNIT")
    private Integer timeUnit;

    /** The time max day mng. */
    @Basic(optional = false)
    @Column(name = "TIME_MAX_DAY_MNG")
    private Integer timeMaxDayMng;

    /** The time mng reference. */
    @Basic(optional = false)
    @Column(name = "TIME_MNG_REFERENCE")
    private Integer timeMngReference;

    /** The time mng max day. */
    @Basic(optional = false)
    @Column(name = "TIME_MNG_MAX_DAY")
    private Integer timeMngMaxDay;

    /** The enough one day. */
    @Basic(optional = false)
    @Column(name = "ENOUGH_ONE_DAY")
    private Integer enoughOneDay;

    /**
     * Instantiates a new kmfmt mng annual set.
     */
    public KmfmtMngAnnualSet() {
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)O
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KmfmtMngAnnualSet)) {
            return false;
        }
        KmfmtMngAnnualSet other = (KmfmtMngAnnualSet) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }
}
