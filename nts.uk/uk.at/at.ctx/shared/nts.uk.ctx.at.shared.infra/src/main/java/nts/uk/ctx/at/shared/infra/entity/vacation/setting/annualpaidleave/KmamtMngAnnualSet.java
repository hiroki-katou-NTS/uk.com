/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.annualpaidleave;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The Class KmamtMngAnnualSet.
 */
@Setter
@Getter
@Entity
@Table(name = "KMAMT_MNG_ANNUAL_SET")
public class KmamtMngAnnualSet extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The cid. */
    @Id
    @Basic(optional = false)
    @Column(name = "CID")
    private String cid;
    
    /** 管理区分 */
    @Column(name = "HALF_MANAGE_ATR")
    private Integer halfManageAtr;

    /** 参照先 */
    @Column(name = "HALF_MAX_REFERENCE")
    private Integer halfMaxReference;

    /** 会社一律上限回数 */
    @Column(name = "HALF_MAX_UNIFORM_COMP")
    private Integer halfMaxUniformComp;

    /**年休を出勤日数として加算する */
    @Column(name = "IS_WORK_DAY_CAL")
    private Integer isWorkDayCal;

    /** 保持年数 */
    @Column(name = "RETENTION_YEAR")
    private Integer retentionYear;

    /** 端数処理区分 */
    @Column(name = "HALF_ROUND_PROC")
    private Integer halfRoundProc;

    /** 年間所定労働日数 */
    @Column(name = "SCHEDULD_WORKING_DAYS")
    private Double scheduleWorkingDays;

    /**
     * Instantiates a new kmamt mng annual set.
     */
    public KmamtMngAnnualSet() {
    }

    /**
     * Instantiates a new kmamt mng annual set.
     *
     * @param cid the cid
     */
    public KmamtMngAnnualSet(String cid) {
        this.cid = cid;
    }


    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cid != null ? cid.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KmamtMngAnnualSet)) {
            return false;
        }
        KmamtMngAnnualSet other = (KmamtMngAnnualSet) object;
        if ((this.cid == null && other.cid != null) || (this.cid != null && !this.cid.equals(other.cid))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
     */
    @Override
    protected Object getKey() {
        return this.cid;
    }
}
