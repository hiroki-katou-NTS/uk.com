/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.MonthDay;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 看護介護休暇 Sets the list work type.
 *
 * @param listWorkType the new list work type
 */
@Setter
@Getter
@Entity
@Table(name = "KNLMT_NURSING_LEAVE_SET")//new table name:KSHMT_HDNURSING_LEAVE
public class KnlmtNursingLeaveSet extends UkJpaEntity implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The knlmtNursingLeaveSetPK. */
    @EmbeddedId
    private KnlmtNursingLeaveSetPK knlmtNursingLeaveSetPK;

    /** 管理区分 The manage type. */
    @Column(name = "MANAGE_ATR")
    private Integer manageType;

    /** 起算日 The start md. */
    @Column(name = "STR_MD")
    private Integer startMonthDay;

    /** 介護看護休暇日数 The nursing num leave day. */
    @Column(name = "NUM_LEAVE_DAY")
    private Integer nursingNumLeaveDay;

    /** 要介護看護人数 The nursing num person. */
    @Column(name = "NUM_PERSON")
    private Integer nursingNumPerson;

    /** 介護看護休暇日数2 */
    @Column(name = "NUM_LEAVE_DAY_2")
    private Integer nursingNumLeaveDay2;

    /** 要介護看護人数2 */
    @Column(name = "NUM_PERSON_2")
    private Integer nursingNumPerson2;

    /** 時間管理区分. */
    @Column(name = "TIME_MANAGE_ATR")
    private Integer timeManageAtr;

    /** 時間休暇消化単位 */
    @Column(name = "DIGESTIVE_UNIT")
    private Integer digestiveUnit;

    /** 特別休暇枠NO */
    @Column(name = "HDSP_FRAME_NO")
    private Integer hdspFrameNo;

    /** 欠勤枠NO. */
    @Column(name = "ABSENCE_FRAME_NO")
    private Integer absenceFrameNo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "knlmtNursingLeaveSet", orphanRemoval = true)
    private List<KnlmtNursingWorkType> listWorkType;

    /**
     * Instantiates a new kmfmt nursing leave set.
     */
    public KnlmtNursingLeaveSet() {
    	super();
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#hashCode()
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (knlmtNursingLeaveSetPK != null ? knlmtNursingLeaveSetPK.hashCode() : 0);
        return hash;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof KnlmtNursingLeaveSet)) {
            return false;
        }
        KnlmtNursingLeaveSet other = (KnlmtNursingLeaveSet) object;
        if ((this.knlmtNursingLeaveSetPK == null && other.knlmtNursingLeaveSetPK != null)
                || (this.knlmtNursingLeaveSetPK != null && !this.knlmtNursingLeaveSetPK.equals(
                        other.knlmtNursingLeaveSetPK))) {
            return false;
        }
        return true;
    }

    /* (non-Javadoc)
     * @see nts.arc.layer.infra.data.entity.JpaEntity#getKey()
     */
    @Override
    protected Object getKey() {
        return this.knlmtNursingLeaveSetPK;
    }
}
